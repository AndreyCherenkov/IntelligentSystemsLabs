package ru.baza;

import java.util.*;

public class ExpertSystemExplained {

    private final List<Rule> rules;

    public ExpertSystemExplained(List<Rule> rules) {
        this.rules = rules;
    }

    /**
     * Основная функция запуска системы с объяснениями
     */
    public void run(Map<String, String> facts) {
        for (Rule rule : rules) {
            Explanation explanation = new Explanation();
            boolean result = backwardChaining(rule.conclusion(), facts, new HashSet<>(), explanation);
            if (result) {
                System.out.println("💡 Recommendation: " + rule.conclusion());
                System.out.println("   Reasoning:");
                explanation.print();
                System.out.println();
            }
        }
    }

    /**
     * Backward chaining с записью объяснений
     */
    private boolean backwardChaining(String goal, Map<String, String> facts, Set<String> visited, Explanation explanation) {
        if (facts.containsValue(goal)) {
            explanation.addLine("Goal '" + goal + "' already satisfied by known facts.");
            return true;
        }

        if (visited.contains(goal)) {
            explanation.addLine("Goal '" + goal + "' already visited. Preventing cycles.");
            return false;
        }

        visited.add(goal);

        for (Rule rule : rules) {
            if (!rule.conclusion().equalsIgnoreCase(goal)) continue;

            explanation.addLine("Checking rule: IF " + rule.conditions() + " THEN " + rule.conclusion());

            boolean conditionsMet = true;
            for (Map.Entry<String, String> condition : rule.conditions().entrySet()) {
                String factValue = facts.get(condition.getKey());
                double similarity = (factValue != null) ? FuzzyLogic.similarity(factValue, condition.getValue()) : 0.0;

                if (similarity >= 0.5) {
                    explanation.addLine("  ✔ Condition '" + condition.getKey() + "' matches: '" + factValue + "' ~ '" + condition.getValue() + "' (similarity=" + similarity + ")");
                } else {
                    explanation.addLine("  ❌ Condition '" + condition.getKey() + "' does NOT match: '" + factValue + "' ~ '" + condition.getValue() + "' (similarity=" + similarity + ")");
                    conditionsMet = false;
                    break;
                }
            }

            if (conditionsMet) {
                explanation.addLine("=> All conditions satisfied for goal '" + goal + "'.");
                facts.put("goal:" + goal, goal); // кешируем цель
                return true;
            }
        }

        explanation.addLine("=> No rules could satisfy goal '" + goal + "'.");
        return false;
    }

    /**
     * Вспомогательный класс для накопления объяснений
     */
    static class Explanation {
        private final List<String> lines = new ArrayList<>();

        void addLine(String line) {
            lines.add(line);
        }

        void print() {
            for (String line : lines) {
                System.out.println(line);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, String> facts = new HashMap<>();

        // Сбор фактов через Questions
        for (var entry : Questions.QUESTIONS_MAP.entrySet()) {
            int qNumber = entry.getKey();
            Questions.Question question = entry.getValue();

            System.out.println(question.text());
            List<String> options = question.options();
            for (String option : options) {
                System.out.println(option);
            }

            int choice = -1;
            while (choice < 1 || choice > options.size()) {
                System.out.print("Enter your choice (1-" + options.size() + "): ");
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                } else {
                    scanner.next(); // пропускаем некорректный ввод
                }
            }

            String factKey = switch (qNumber) {
                case 1 -> Questions.FactKey.COMPLEXITY.getName();
                case 2 -> Questions.FactKey.BUDGET.getName();
                case 3 -> Questions.FactKey.TIME.getName();
                case 4 -> Questions.FactKey.PERFORMANCE.getName();
                case 5 -> Questions.FactKey.SCALE.getName();
                default -> throw new IllegalArgumentException("Unknown question number: " + qNumber);
            };

            String factValue = switch (factKey) {
                case "complexity" -> switch (choice) {
                    case 1 -> SelectionOptions.LOW.text;
                    case 2 -> SelectionOptions.MEDIUM.text;
                    case 3 -> SelectionOptions.HIGH.text;
                    default -> "";
                };
                case "budget" -> switch (choice) {
                    case 1 -> SelectionOptions.LOW.text;
                    case 2 -> SelectionOptions.MEDIUM.text;
                    case 3 -> SelectionOptions.HIGH.text;
                    default -> "";
                };
                case "time" -> switch (choice) {
                    case 1 -> SelectionOptions.SHORT.text;
                    case 2 -> SelectionOptions.MEDIUM.text;
                    case 3 -> SelectionOptions.LONG.text;
                    default -> "";
                };
                case "performance" -> switch (choice) {
                    case 1 -> SelectionOptions.LOW.text;
                    case 2 -> SelectionOptions.MEDIUM.text;
                    case 3 -> SelectionOptions.HIGH.text;
                    default -> "";
                };
                case "scale" -> switch (choice) {
                    case 1 -> SelectionOptions.PERSONAL.text;
                    case 2 -> SelectionOptions.SMALL_BUSINESS.text;
                    case 3 -> SelectionOptions.LARGE_PROJECT.text;
                    default -> "";
                };
                default -> "";
            };

            facts.put(factKey, factValue);
        }

        ExpertSystemExplained system = new ExpertSystemExplained(RulesLoader.RULES);
        system.run(facts);
    }
}
