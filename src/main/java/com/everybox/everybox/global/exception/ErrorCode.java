package com.everybox.everybox.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_EMAIL_INVALID(HttpStatus.BAD_REQUEST, "대학생 이메일(@xxx.ac.kr)만 가입할 수 있습니다."),
    USER_EMAIL_DUPLICATED(HttpStatus.CONFLICT, "이미 가입된 이메일입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "가입되지 않은 이메일입니다."),
    USER_PASSWORD_MISMATCH(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");

    private final HttpStatus status;
    private final String message;
}
