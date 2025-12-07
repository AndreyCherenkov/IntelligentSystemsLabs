package ru.baza.engine;

import ru.baza.annotations.Rule;
import ru.baza.annotations.FactCondition;
import ru.baza.knowledges.KnowledgeBase;
import ru.baza.model.Fact;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Движок правил экспертной системы, выполняющий:
 * <ul>
 *     <li>прямой вывод (forward chaining),</li>
 *     <li>обратный вывод (backward chaining).</li>
 * </ul>
 *
 * <p>Правила задаются методами внутри наследников {@link KnowledgeBase},
 * помеченными аннотацией {@link Rule}. Каждое правило описывает условия
 * на основе набора {@link FactCondition} и действия — тело метода.
 *
 * <h2>1. Прямой вывод</h2>
 * Метод {@link #run(KnowledgeBase, Collection)} последовательно проверяет все
 * правила, сравнивая условия с уже известными фактами. Если правило выполнено,
 * вызывается соответствующий метод базы знаний.
 *
 * <h2>2. Обратный вывод</h2>
 * Метод {@link #backwardChain(KnowledgeBase, Collection, String)} пытается
 * дедуцировать заданную цель по правилам:
 *
 * <ol>
 *     <li>Если цель уже содержится среди фактов — успех.</li>
 *     <li>Если найдены правила, чьи заключения совпадают с целью — проверяются их условия.</li>
 *     <li>Если условие не выполняется, движок рекурсивно пытается доказать его как новую цель.</li>
 *     <li>Если все условия правила доказаны — цель считается выведенной.</li>
 * </ol>
 *
 * <p>Используется классический механизм логического вывода, подобный тому,
 * что реализуют экспертные системы первого поколения.
 */
public class RuleEngine {

    /**
     * Выполняет прямой вывод: проверяет все правила базы знаний,
     * и при выполнении условий вызывает соответствующий метод.
     *
     * @param knowledgeBase база знаний с правилами
     * @param facts         текущий набор фактов
     * @param <T>           тип базы знаний
     */
    public <T extends KnowledgeBase> void run(T knowledgeBase, Collection<Fact> facts) {
        for (var method : getRuleMethods(knowledgeBase)) {
            var rule = method.getAnnotation(Rule.class);

            var allTrue = checkAllConditions(facts, rule.all());
            var anyTrue = checkAnyConditions(facts, rule.any());

            if (allTrue && anyTrue) {
                invokeMethod(method, knowledgeBase);
            }
        }
    }

    /**
     * Проверяет, выполнены ли все условия правила.
     *
     * @param facts      набор фактов
     * @param conditions условия правила
     * @return true, если все условия найдены среди фактов
     */
    private boolean checkAllConditions(Collection<Fact> facts, FactCondition[] conditions) {
        return Arrays.stream(conditions)
                .allMatch(cond -> facts.stream().anyMatch(f -> f.matches(cond)));
    }

    /**
     * Проверяет, выполнено ли хотя бы одно условие правила.
     *
     * @param facts      набор фактов
     * @param conditions условия правила
     * @return true, если список условий пуст или хотя бы одно условие выполнено
     */
    private boolean checkAnyConditions(Collection<Fact> facts, FactCondition[] conditions) {
        return conditions.length == 0 ||
                Arrays.stream(conditions)
                        .anyMatch(cond -> facts.stream().anyMatch(f -> f.matches(cond)));
    }

    /**
     * Извлекает методы базы знаний, отмеченные аннотацией {@link Rule}.
     *
     * @param knowledgeBase объект базы знаний
     * @param <T>           тип базы знаний
     * @return список методов-правил
     */
    private <T extends KnowledgeBase> List<Method> getRuleMethods(T knowledgeBase) {
        return Arrays.stream(knowledgeBase.getClass().getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(Rule.class))
                .collect(Collectors.toList());
    }

    // -------------------------------------------------------------------------
    //                           ОБРАТНЫЙ ВЫВОД
    // -------------------------------------------------------------------------

    /**
     * Выполняет обратный вывод (backward chaining) для доказательства цели.
     *
     * @param knowledgeBase база знаний
     * @param facts         текущие факты
     * @param goal          искомая цель
     * @param <T>           тип базы знаний
     * @return true, если цель доказана
     */
    public <T extends KnowledgeBase> boolean backwardChain(T knowledgeBase, Collection<Fact> facts, String goal) {
        System.out.println("🎯 Проверяем цель: " + goal);

        // 1. Если факт уже известен — доказано
        if (isKnownGoal(facts, goal)) {
            System.out.println("✅ Цель " + goal + " уже известна из фактов.");
            return true;
        }

        // 2. Ищем правила, заключение которых совпадает с целью
        for (var method : getRuleMethods(knowledgeBase)) {
            var conclusion = extractConclusionFromMethod(method);
            if (!conclusion.equalsIgnoreCase(goal)) continue;

            var rule = method.getAnnotation(Rule.class);
            System.out.println("📘 Найдено правило для " + goal + ": " + method.getName());

            // 3. Проверяем/доказываем условия правила
            if (proveAllConditions(knowledgeBase, facts, rule.all())) {
                System.out.println("✅ Условия для " + goal + " выполнены. Добавляем факт: " + goal);
                facts.add(new Fact("goal", goal));
                invokeMethod(method, knowledgeBase);
                return true;
            }
        }

        System.out.println("❌ Не удалось доказать цель: " + goal);
        return false;
    }

    /**
     * Проверяет, известна ли цель среди фактов.
     *
     * @param facts набор фактов
     * @param goal  искомая цель
     * @return true, если факт найден
     */
    private boolean isKnownGoal(Collection<Fact> facts, String goal) {
        return facts.stream().anyMatch(f ->
                f.name().equalsIgnoreCase(goal) || f.value().equalsIgnoreCase(goal)
        );
    }

    /**
     * Пытается доказать все условия правила в контексте обратного вывода.
     *
     * @param knowledgeBase база знаний
     * @param facts         текущие факты
     * @param conditions    условия правила
     * @param <T>           тип базы знаний
     * @return true, если все условия доказаны или уже известны
     */
    private <T extends KnowledgeBase> boolean proveAllConditions(
            T knowledgeBase,
            Collection<Fact> facts,
            FactCondition[] conditions
    ) {
        for (var cond : conditions) {
            var factKnown = facts.stream().anyMatch(f -> f.matches(cond));

            if (!factKnown) {
                System.out.println("🔍 Факта " + cond.name() + "=" + cond.value() +
                        " нет. Пытаемся доказать...");

                if (!backwardChain(knowledgeBase, facts, cond.value())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Извлекает заключение правила из имени метода.
     * <p>
     * Например:
     * <ul>
     *     <li>{@code ruleColdWeather → ColdWeather}</li>
     *     <li>{@code diagnoseFlu → diagnoseFlu}</li>
     * </ul>
     *
     * @param method метод-правило
     * @return строка заключения правила
     */
    private String extractConclusionFromMethod(Method method) {
        var name = method.getName();
        return name.startsWith("rule") && name.length() > 4
                ? name.substring(4)
                : name;
    }

    /**
     * Вызывает метод-правило через reflection.
     *
     * @param method        метод базы знаний
     * @param knowledgeBase объект базы знаний
     * @param <T>           тип базы знаний
     */
    private <T> void invokeMethod(Method method, T knowledgeBase) {
        try {
            if (!method.canAccess(knowledgeBase)) {
                method.setAccessible(true);
            }
            method.invoke(knowledgeBase);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Не удалось вызвать правило: " + method.getName(), e);
        }
    }
}
