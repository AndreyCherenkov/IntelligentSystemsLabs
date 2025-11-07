package ru.baza;

import java.util.*;

@Deprecated
public class InferenceEngine {

    // Хранение посещённых целей, чтобы избежать циклов
    private final Set<String> visitedGoals = new HashSet<>();

    /**
     * Реализация обратного вывода (backward chaining)
     * @param goal — целевая гипотеза, которую хотим доказать
     * @param facts — известные факты (условия, которые уже истинны)
     * @param rules — список правил
     * @return true, если цель можно вывести; false, если нет
     */
    public boolean backwardChaining(String goal, Set<String> facts, List<Rule> rules) {

        // 1. Если цель уже известна (факт) — возвращаем истину
        if (facts.contains(goal)) {
            return true;
        }

        // 2. Если цель уже проверялась (для предотвращения зацикливания)
        if (visitedGoals.contains(goal)) {
            return false;
        }

        // 3. Отмечаем цель как посещённую
        visitedGoals.add(goal);

        // 4. Перебираем правила, которые могут привести к цели
        for (Rule rule : rules) {
            if (rule.conclusion.equals(goal)) {

                boolean allConditionsTrue = true;

                // 5. Проверяем каждое условие правила
                for (Map.Entry<Condition, Answer> cond : rule.conditions.entrySet()) {
                    String subGoal = cond.getKey().name() + "=" + cond.getValue().name();

                    // рекурсивно проверяем достижимость подцели
                    if (!backwardChaining(subGoal, facts, rules)) {
                        allConditionsTrue = false;
                        break;
                    }
                }

                // 6. Если все условия истинны — цель доказана
                if (allConditionsTrue) {
                    facts.add(goal); // можно запомнить для ускорения
                    return true;
                }
            }
        }

        // 7. Ни одно правило не доказало цель
        return false;
    }
}
