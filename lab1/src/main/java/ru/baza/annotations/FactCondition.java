package ru.baza.annotations;

import java.lang.annotation.*;

/**
 * Представляет одно условие факта, используемое в правилах экспертной системы.
 * <p>
 * Аннотация применяется внутри другой аннотации {@link ru.baza.annotations.Rule}
 * для описания условий вида <b>name=value</b>.
 *
 * <p>Пример:
 * <pre>{@code
 * @Rule(
 *     all = {
 *         @FactCondition(name = "weather", value = "rain"),
 *         @FactCondition(name = "season", value = "autumn")
 *     }
 * )
 * }</pre>
 *
 * <p>Является аннотацией-контейнером без собственного Target, но используется
 * исключительно внутри других аннотаций.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({})
public @interface FactCondition {

    /**
     * Имя проверяемого факта.
     *
     * <p>Должно совпадать с {@code Fact.name()} для успешного сопоставления.
     *
     * @return имя факта
     */
    String name();

    /**
     * Ожидаемое значение факта.
     *
     * <p>Должно совпадать с {@code Fact.value()} для успешного сопоставления.
     *
     * @return значение факта
     */
    String value();
}
