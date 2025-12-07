package ru.baza.questions;

public enum Answer {
    LOW("low"),
    SHORT("short"),
    PERSONAL("personal"),
    MEDIUM("medium"),
    HIGH("high"),
    LARGE_PROJECT("large project"),
    SMALL_BUSINESS("small business"),
    UNCERTAINTY("uncertainty")
    ;
    private final String text;

    Answer(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
