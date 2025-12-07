package ru.baza.engine;

import ru.baza.annotations.Rule;
import ru.baza.knowledges.KnowledgeBase;
import ru.baza.model.Fact;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

public class RuleEngine {

    public <T extends KnowledgeBase> void run(T knowledgeBase, Collection<Fact> facts) throws Exception {
        for (var method : knowledgeBase.getClass().getDeclaredMethods()) {
            if (!method.isAnnotationPresent(Rule.class)) continue;

            var rule = method.getAnnotation(Rule.class);
            var allConds = rule.all();
            var anyConds = rule.any();

            var allTrue = true;
            var anyTrue = anyConds.length == 0;

            for (var cond : allConds) {
                var found = facts
                        .stream()
                        .anyMatch(f -> f.matches(cond));
                if (!found) {
                    allTrue = false;
                    break;
                }
            }

            if (anyConds.length > 0) {
                for (var cond : anyConds) {
                    var found = facts
                            .stream()
                            .anyMatch(f -> f.matches(cond));
                    if (found) {
                        anyTrue = true;
                        break;
                    }
                }
            }

            if (allTrue && anyTrue) {
                method.invoke(knowledgeBase);
            }
        }
    }

    // --- Обратный вывод ---
    public <T extends KnowledgeBase> boolean backwardChain(T knowledgeBase, Collection<Fact> facts, String goal) throws InvocationTargetException, IllegalAccessException {
        System.out.println("🎯 Проверяем цель: " + goal);

        // Проверяем, может ли цель быть уже известным фактом
        var known = facts.stream().anyMatch(f -> f.name().equalsIgnoreCase(goal) || f.value().equalsIgnoreCase(goal));
        if (known) {
            System.out.println("✅ Цель " + goal + " уже известна из фактов.");
            return true;
        }

        // Ищем правила, чье заключение совпадает с goal
        for (Method method : knowledgeBase.getClass().getDeclaredMethods()) {
            if (!method.isAnnotationPresent(Rule.class)) continue;

            var conclusion = extractConclusionFromMethod(method);
            if (!conclusion.equalsIgnoreCase(goal)) continue;

            var rule = method.getAnnotation(Rule.class);
            var conditions = rule.all();

            System.out.println("📘 Найдено правило для " + goal + ": " + method.getName());

            var allSatisfied = true;
            for (var cond : conditions) {
                var factKnown = facts.stream().anyMatch(f -> f.matches(cond));
                if (!factKnown) {
                    System.out.println("🔍 Факта " + cond.name() + "=" + cond.value() + " нет. Пытаемся доказать...");
                    var proved = backwardChain(knowledgeBase, facts, cond.value());
                    if (!proved) {
                        allSatisfied = false;
                        break;
                    }
                }
            }

            if (allSatisfied) {
                System.out.println("✅ Условия для " + goal + " выполнены. Добавляем факт: " + goal);
                facts.add(new Fact("goal", goal));
                method.invoke(knowledgeBase);
                return true;
            }
        }

        System.out.println("❌ Не удалось доказать цель: " + goal);
        return false;
    }

    private String extractConclusionFromMethod(Method method) {
        var name = method.getName();
        if (name.startsWith("rule")) {
            return name.substring(4);
        }
        return name;
    }
}

