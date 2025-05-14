package com.everybox.everybox.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
class VerificationCode {
    private final String code;
    private final LocalDateTime expiresAt;
}