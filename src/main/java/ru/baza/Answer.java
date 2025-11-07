package ru.baza;

import com.google.gson.annotations.JsonAdapter;
import ru.baza.adapter.AnswerAdapter;

@JsonAdapter(AnswerAdapter.class)
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
    String text;

    Answer(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
