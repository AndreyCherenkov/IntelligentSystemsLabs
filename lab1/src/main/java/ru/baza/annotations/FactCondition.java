package ru.baza.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({})
public @interface FactCondition {
    String name();
    String value();
}

