package com.everybox.everybox.experience;

import com.everybox.everybox.experience.ExpType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GainExp {
    ExpType value();
}
