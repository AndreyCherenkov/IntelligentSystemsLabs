package ru.baza.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для пометки методов в базе знаний как правил экспертной системы.
 *
 * <p>Каждый метод, помеченный {@code @Rule}, рассматривается реализацией движка
 * {@code AbstractRuleEngine} как правило вида:
 *
 * <pre>
 * ЕСЛИ (all-все условия истинны) И (any-хотя бы одно условие истинно)
 * ТО выполнить метод-правило
 * </pre>
 *
 * <p>Условия правила задаются в виде массива {@link FactCondition}.
 *
 * <h3>Массивы условий</h3>
 * <ul>
 *   <li><b>all</b> — все перечисленные условия обязаны быть истинны.
 *   <li><b>any</b> — хотя бы одно условие обязано быть истинным.
 * </ul>
 *
 * <p>Если массив {@code any} пустой, то он автоматически считается истинным,
 * и правило проверяется только по условиям из {@code all}.
 *
 * <h3>Пример использования:</h3>
 * <pre>{@code
 * @Rule(
 *     all = {
 *         @FactCondition(name = "weather", value = "rain"),
 *         @FactCondition(name = "season", value = "autumn")
 *     },
 *     any = {
 *         @FactCondition(name = "temperature", value = "cold"),
 *         @FactCondition(name = "wind", value = "strong")
 *     }
 * )
 * private void ruleRainAutumn() {
 *     System.out.println("Осенью дождь — бери зонт!");
 * }
 * }</pre>
 *
 * <p>Метод с аннотацией должен быть без аргументов. Доступ (private/public)
 * не имеет значения — {@code RuleEngine} вызывает его через reflection.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Rule {

    /**
     * Набор условий, которые должны быть выполнены все (логическое И).
     *
     * @return массив обязательных условий
     */
    FactCondition[] all() default {};

    /**
     * Набор условий, из которых должно выполняться хотя бы одно (логическое ИЛИ).
     *
     * @return массив альтернативных условий
     */
    FactCondition[] any() default {};
}
