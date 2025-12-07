package ru.baza.questions;

public enum Condition {
    COMPLEXITY("complexity"),
    BUDGET("budget"),
    TIME("time"),
    SCALE("scale"),
    PERFORMANCE("performance");

    final String name;

    Condition(String name) {
        this.name = name;
    }

}
