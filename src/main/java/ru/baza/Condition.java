package ru.baza;

import com.google.gson.annotations.JsonAdapter;
import ru.baza.adapter.ConditionAdapter;

@JsonAdapter(ConditionAdapter.class)
public enum Condition {
    COMPLEXITY("complexity"),
    BUDGET("budget"),
    TIME("time"),
    SCALE("scale"),
    PERFORMANCE("performance");

    String name;

    Condition(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
