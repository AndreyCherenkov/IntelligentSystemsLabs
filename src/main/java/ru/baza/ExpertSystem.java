package ru.baza;

import java.util.*;

public class ExpertSystem {

    private final List<Rule> rules;

    public ExpertSystem(List<Rule> rules) {
        this.rules = rules;
    }

    /**
     * Запуск backward chaining для цели
     *
     * @param goal   цель (например, "WordPress")
     * @param facts  известные факты (например, ответы пользователя)
     * @return true если цель достижима
     */
    public boolean backwardChaining(String goal, Map<String, String> facts) {
        return backwardChaining(goal, facts, new HashSet<>());
    }

    /**
     * Рекурсивная функция backward chaining
     *
     * @param goal     цель
     * @param facts    текущие факты
     * @param visited  уже проверенные цели (для предотвращения циклов)
     * @return true если цель достижима
     */
    private boolean backwardChaining(String goal, Map<String, String> facts, Set<String> visited) {
        // Если цель уже есть среди фактов, считаем её достигнутой
        if (facts.containsValue(goal)) {
            return true;
        }

        // Если цель уже проверялась в этой цепочке — предотвращаем циклы
        if (visited.contains(goal)) {
            return false;
        }
        visited.add(goal);

        // Ищем правила, которые могут привести к цели
        for (Rule rule : rules) {
            if (!rule.conclusion().equalsIgnoreCase(goal)) continue;

            boolean conditionsMet = true;

            for (Map.Entry<String, String> condition : rule.conditions().entrySet()) {
                String factValue = facts.get(condition.getKey());

                // Используем FuzzyLogic для проверки соответствия условий
                if (factValue == null || FuzzyLogic.similarity(factValue, condition.getValue()) < 0.5) {
                    conditionsMet = false;
                    break;
                }
            }

            if (conditionsMet) {
                // Опционально: добавляем цель в факты
                facts.put("goal:" + goal, goal);
                return true;
            }
        }

        // Ни одно правило не подтвердило достижение цели
        return false;
    }

    /**
     * Выводит рекомендации для текущих фактов
     */
    public List<String> getRecommendations(Map<String, String> facts) {
        List<String> results = new ArrayList<>();
        for (Rule rule : rules) {
            if (backwardChaining(rule.conclusion(), new HashMap<>(facts))) {
                results.add(rule.conclusion());
            }
        }
        return results;
    }

    public static void main(String[] args) {
        // Загружаем правила
        List<Rule> rules = RulesLoader.RULES;

        ExpertSystem system = new ExpertSystem(rules);

        // Пример фактов (ответы пользователя)
        Map<String, String> facts = new HashMap<>();
        facts.put("complexity", "low");
        facts.put("budget", "low");
        facts.put("time", "short");
        facts.put("scale", "personal");

        // Получаем рекомендации
        List<String> recommendations = system.getRecommendations(facts);
        System.out.println("💡 Recommended solutions: " + recommendations);
    }
}
