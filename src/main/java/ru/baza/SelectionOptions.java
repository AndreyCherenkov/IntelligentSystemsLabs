package ru.baza;

public enum SelectionOptions {
    LOW("low"),
    MEDIUM("medium"),
    HIGH("high"),
    SHORT("short"),
    LONG("long"),
    PERSONAL("personal"),
    SMALL_BUSINESS("small business"),
    LARGE_PROJECT("large project");

    final String text;

    SelectionOptions(String text) {
        this.text = text;
    }
}
