package com.everybox.everybox.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public enum Category {
    FRUIT("과일"),
    VEGETABLE("채소"),
    FROZEN("냉동식품"),
    RAMEN("라면"),
    MEAL("식사"),
    DAIRY("유제품"),
    BREAD("빵"),
    WATER("생수"),
    BEVERAGE("음료");

    private final String displayName;
}