package com.everybox.everybox.dto;

import lombok.Data;

@Data
public class UserSignupRequestDto {
    private String username;
    private String password;
    private String nickname;
    private String email; //메일발송용
}