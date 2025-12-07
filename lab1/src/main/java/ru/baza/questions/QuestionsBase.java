package ru.baza.questions;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static ru.baza.questions.Answer.*;
import static ru.baza.questions.Condition.*;

public class QuestionsBase {

    public static final Map<Condition, String> QUESTIONS;
    public static final Map<Condition, String> ANSWERS;

    private QuestionsBase() {}

    static {
        var questions = new LinkedHashMap<Condition, String>();
        questions.put(COMPLEXITY, "How complex should your website be?");
        questions.put(BUDGET, "What is your budget level?");
        questions.put(TIME, "How much development time do you have?");
        questions.put(PERFORMANCE, "What level of performance do you expect?");
        questions.put(SCALE, "What is your project scale?");
        QUESTIONS = Collections.unmodifiableMap(questions);

        var answers = new LinkedHashMap<Condition, String>();
        answers.put(COMPLEXITY, """
                1 - Simple landing page
                2 - Medium company website
                3 - Complex multi-page web app
                """);
        answers.put(BUDGET, """
                1 - Low (under $500)
                2 - Medium ($500–2000)
                3 - High (over $2000)
                """);
        answers.put(TIME, """
                1 - Short (1–2 weeks)
                2 - Average (1–2 months)
                3 - Long (3+ months)
                """);
        answers.put(PERFORMANCE, """
                1 - Low (basic site)
                2 - Medium (normal load)
                3 - High (many users, real-time)
                """);
        answers.put(SCALE, """
                1 - Personal project
                2 - Small business website
                3 - Large company / enterprise
                """);
        ANSWERS = Collections.unmodifiableMap(answers);
    }

    public static Answer getUserAnswer(Condition condition, int answerNumber) {
        return switch (condition) {
            case COMPLEXITY, BUDGET, PERFORMANCE -> switch (answerNumber) {
                case 1 -> LOW;
                case 2 -> MEDIUM;
                case 3 -> HIGH;
                default -> UNCERTAINTY;
            };
            case TIME -> switch (answerNumber) {
                case 1 -> SHORT;
                case 2 -> MEDIUM;
                case 3 -> HIGH;
                default -> UNCERTAINTY;
            };
            case SCALE -> switch (answerNumber) {
                case 1 -> PERSONAL;
                case 2 -> SMALL_BUSINESS;
                case 3 -> LARGE_PROJECT;
                default -> UNCERTAINTY;
            };
        };
    }
}

