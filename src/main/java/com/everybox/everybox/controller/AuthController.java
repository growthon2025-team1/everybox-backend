package com.everybox.everybox.controller;

import com.everybox.everybox.docs.AuthDocs;
import com.everybox.everybox.dto.KakaoLoginRequestDto;
import com.everybox.everybox.dto.UserLoginRequestDto;
import com.everybox.everybox.dto.UserSignupRequestDto;
import com.everybox.everybox.dto.UserResponseDto;
import com.everybox.everybox.global.APIResponse;
import com.everybox.everybox.service.UserService;
import com.everybox.everybox.util.JwtUtil;
import com.everybox.everybox.security.JwtAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController implements AuthDocs {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/check-id")
    public ResponseEntity<Void> checkId(@RequestParam String request) {
        userService.isValidId(request);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody UserSignupRequestDto request) {
        UserResponseDto user = userService.registerUser(request);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserLoginRequestDto request) {
        UserResponseDto user = userService.login(request);
        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        return ResponseEntity.ok(Map.of("token", "Bearer " + token));
    }

    @GetMapping("/me")
    public UserResponseDto getMyInfo(Authentication authentication) {
        if (authentication instanceof JwtAuthentication jwtAuth) {
            Long userId = jwtAuth.getUserId();
            return userService.findDtoById(userId);
        }
        throw new IllegalArgumentException(
                "잘못된 인증 방식입니다. 반드시 JWT 토큰으로 접근해야 합니다. (토큰 누락, 만료, 헤더 확인 필요)"
        );
    }

    @PostMapping("/kakao")
    public ResponseEntity<Map<String, Object>> kakaoLogin(@RequestBody KakaoLoginRequestDto request) {
        String email = request.getEmail();
        String nickname = request.getNickname();

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("카카오 계정의 이메일 정보가 없습니다.");
        }

        // email = username
        UserResponseDto user = userService.findOrCreateKakaoUserDto(email, nickname);
        String token = jwtUtil.generateToken(user.getId(), user.getUsername());

        return ResponseEntity.ok(Map.of(
                "token", "Bearer " + token,
                "userId", user.getId(),
                "username", user.getUsername()
        ));
    }



}
