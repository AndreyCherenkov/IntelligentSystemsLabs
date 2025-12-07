package ru.baza.model;

import ru.baza.annotations.FactCondition;

import java.util.Objects;

/**
 * Представляет факт в экспертной системе.
 *
 * <p>Факт описывается парой «имя–значение». Например:
 * <pre>new Fact("Температура", "Высокая")</pre>
 *
 * <p>Используется в механизмах прямого и обратного вывода
 * для проверки выполнения условий правил.
 *
 * @param name  имя факта
 * @param value значение факта
 */
public record Fact(String name, String value) {

    /**
     * Проверяет, соответствует ли факт заданному условию.
     *
     * @param cond условие из аннотации {@link FactCondition}
     * @return {@code true}, если имя и значение совпадают
     */
    public boolean matches(FactCondition cond) {
        return name.equals(cond.name()) && value.equals(cond.value());
    }

    /**
     * Возвращает строковое представление факта в виде
     * <pre>имя=значение</pre> в нижнем регистре.
     */
    @Override
    public String toString() {
        return name.toLowerCase() + "=" + value.toLowerCase();
    }

    /**
     * Сравнивает два факта по имени и значению.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fact fact)) return false;
        return Objects.equals(name, fact.name) && Objects.equals(value, fact.value);
    }

    /**
     * Вычисляет хеш-код на основе имени и значения.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }
}
