package com.everybox.everybox.docs;

import com.everybox.everybox.dto.UserLoginRequestDto;
import com.everybox.everybox.dto.UserSignupRequestDto;
import com.everybox.everybox.dto.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public interface AuthDocs {

    @Operation(
            summary = "회원 가입",
            description = "사용자 회원가입을 처리합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "회원가입 성공", content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            }
    )
    ResponseEntity<UserResponseDto> signup(
            @RequestBody UserSignupRequestDto request
    );

    @Operation(
            summary = "로그인",
            description = "사용자 로그인 후 JWT 토큰을 반환합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "로그인 성공 및 토큰 반환", content = @Content(schema = @Schema(implementation = Map.class))),
                    @ApiResponse(responseCode = "401", description = "인증 실패")
            }
    )
    ResponseEntity<Map<String, String>> login(
            @RequestBody UserLoginRequestDto request
    );

    @Operation(
            summary = "내 정보 조회",
            description = "현재 로그인한 사용자 정보를 반환합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "유저 정보 반환", content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "인증 실패")
            }
    )
    UserResponseDto getMyInfo(
            @Parameter(hidden = true) Authentication authentication
    );

}