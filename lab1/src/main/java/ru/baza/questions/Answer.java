package ru.baza.questions;

/**
 * Перечисление возможных ответов пользователя на вопросы экспертной системы.
 *
 * <p>Каждый вариант содержит человекочитаемое текстовое представление,
 * используемое при формировании фактов ({@code Fact}).
 *
 * <p>Ответы используются для сопоставления с условиями правил
 * через строковое значение {@link #getText()}.
 */
public enum Answer {
    LOW("low"),
    SHORT("short"),
    PERSONAL("personal"),
    MEDIUM("medium"),
    HIGH("high"),
    LARGE_PROJECT("large project"),
    SMALL_BUSINESS("small business"),
    UNCERTAINTY("uncertainty");

    private final String text;

    Answer(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
