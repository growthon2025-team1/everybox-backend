package com.everybox.everybox.experience;

import lombok.Getter;

@Getter
public enum ExpType {
    CREATE_POST(10L, "음식 등록하기"),
    START_CHAT(30L, "채팅 시작하기");

    private final long exp;
    private final String description;

    ExpType(long exp, String description) {
        this.exp = exp;
        this.description = description;
    }
}