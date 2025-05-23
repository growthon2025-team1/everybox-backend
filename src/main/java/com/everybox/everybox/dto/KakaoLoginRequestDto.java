    package com.everybox.everybox.dto;

    import lombok.Getter;
    import lombok.Setter;

    @Getter
    @Setter
    public class KakaoLoginRequestDto {
        private String kakaoId;
        private String email;
        private String nickname;
    }
