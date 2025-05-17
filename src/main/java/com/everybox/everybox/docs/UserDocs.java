package com.everybox.everybox.docs;

import com.everybox.everybox.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/users")
public interface UserDocs {

    @Operation(summary = "회원가입", description = "회원가입을 처리합니다.")
    @PostMapping
    ResponseEntity<?> signup(@RequestBody UserSignupRequestDto request);

    @Operation(summary = "이메일 인증 요청", description = "이메일 인증 코드를 전송합니다.")
    @PostMapping("/email/verify-request")
    ResponseEntity<?> sendVerificationMail(@RequestBody EmailSendRequestDto request, Authentication authentication);

    @Operation(summary = "이메일 인증 확인", description = "인증 코드를 확인하여 이메일 인증을 완료합니다.")
    @PostMapping("/email/verify-confirm")
    ResponseEntity<?> verifyEmail(@RequestBody EmailVerifyRequestDto request, Authentication authentication);

    @Operation(summary = "회원 정보 수정", description = "회원 정보를 수정합니다.")
    @PutMapping("/{userId}")
    ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long userId,
            @RequestBody UserUpdateRequestDto request,
            Authentication authentication);

    @Operation(summary = "회원 탈퇴", description = "해당 회원을 삭제합니다.")
    @DeleteMapping("/{userId}")
    ResponseEntity<Void> deleteUser(@PathVariable Long userId, Authentication authentication);
}