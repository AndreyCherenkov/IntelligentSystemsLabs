package ru.baza;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ExpertSystemInteractive {

    private final ExpertSystem expertSystem;

    public ExpertSystemInteractive(List<Rule> rules) {
        this.expertSystem = new ExpertSystem(rules);
    }

    public void run() {
        Map<String, String> facts = askUserQuestions();
        List<String> recommendations = expertSystem.getRecommendations(facts);
        System.out.println("💡 Recommended solutions: " + recommendations);
    }

    /**
     * Опрос пользователя через консоль на основе класса Questions
     */
    private Map<String, String> askUserQuestions() {
        Map<String, String> facts = new HashMap<>();
        Scanner scanner = new Scanner(System.in);

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
                    if (choice < 1 || choice > options.size()) {
                        System.out.println("❌ Invalid choice, try again.");
                    }
                } else {
                    System.out.println("❌ Invalid input, enter a number.");
                    scanner.next(); // пропускаем некорректный ввод
                }
            }

            // Преобразуем выбранный вариант в ключ для фактов
            String factKey = getFactKeyByQuestionNumber(qNumber);
            String factValue = mapAnswerToOption(factKey, choice);
            facts.put(factKey, factValue);
        }

        return facts;
    }

    /**
     * Связывает номер вопроса с ключом факта
     */
    private String getFactKeyByQuestionNumber(int qNumber) {
        return switch (qNumber) {
            case 1 -> Questions.FactKey.COMPLEXITY.getName();
            case 2 -> Questions.FactKey.BUDGET.getName();
            case 3 -> Questions.FactKey.TIME.getName();
            case 4 -> Questions.FactKey.PERFORMANCE.getName();
            case 5 -> Questions.FactKey.SCALE.getName();
            default -> throw new IllegalArgumentException("Unknown question number: " + qNumber);
        };
    }

    /**
     * Преобразует номер выбранного варианта в значение для факта
     */
    private String mapAnswerToOption(String factKey, int choice) {
        return switch (factKey) {
            case "complexity", "performance", "budget" -> switch (choice) {
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
            case "scale" -> switch (choice) {
                case 1 -> SelectionOptions.PERSONAL.text;
                case 2 -> SelectionOptions.SMALL_BUSINESS.text;
                case 3 -> SelectionOptions.LARGE_PROJECT.text;
                default -> "";
            };
            default -> "";
        };
    }

    public static void main(String[] args) {
        ExpertSystemInteractive interactiveSystem = new ExpertSystemInteractive(RulesLoader.RULES);
        interactiveSystem.run();
    }
}
