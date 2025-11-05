package ru.baza;

import java.util.Map;

public record Rule(
        String conclusion,
        Map<String, String> conditions
) {
}

