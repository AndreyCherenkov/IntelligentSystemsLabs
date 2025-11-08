package ru.baza.model;

import ru.baza.annotations.FactCondition;

import java.util.Objects;

public record Fact(String name, String value) {

    public boolean matches(FactCondition cond) {
        return name.equals(cond.name()) && value.equals(cond.value());
    }

    @Override
    public String toString() {
        return name + "=" + value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fact fact)) return false;
        return Objects.equals(name, fact.name) && Objects.equals(value, fact.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }
}

