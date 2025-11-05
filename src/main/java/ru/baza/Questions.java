package ru.baza;

import java.util.*;

public class Questions {

    public enum FactKey {
        COMPLEXITY("complexity"),
        BUDGET("budget"),
        TIME("time"),
        PERFORMANCE("performance"),
        SCALE("scale");

        private final String name;

        FactKey(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

    }

    public record Question(String text, List<String> options) {
        public Question {
            options = List.copyOf(options);
        }
    }

    public static final Map<Integer, Question> QUESTIONS_MAP;

    static {
        Map<Integer, Question> map = new LinkedHashMap<>();
        map.put(1, new Question("How complex should your website be?", List.of(
                "1 - Simple landing page",
                "2 - Medium company website",
                "3 - Complex multi-page web app"
        )));
        map.put(2, new Question("What is your budget level?", List.of(
                "1 - Low (under $500)",
                "2 - Medium ($500–2000)",
                "3 - High (over $2000)"
        )));
        map.put(3, new Question("How much development time do you have?", List.of(
                "1 - Short (1–2 weeks)",
                "2 - Average (1–2 months)",
                "3 - Long (3+ months)"
        )));
        map.put(4, new Question("What level of performance do you expect?", List.of(
                "1 - Low (basic site)",
                "2 - Medium (normal load)",
                "3 - High (many users, real-time)"
        )));
        map.put(5, new Question("What is your project scale?", List.of(
                "1 - Personal project",
                "2 - Small business website",
                "3 - Large company / enterprise"
        )));
        QUESTIONS_MAP = Collections.unmodifiableMap(map);
    }
}