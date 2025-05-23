package com.everybox.everybox.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoLoginResponseDto {
    private String token;
    private Long id;
    private String username;
    private String nickname;
    private Boolean isVerified;
    private String universityEmail;
}
