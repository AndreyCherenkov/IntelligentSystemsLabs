package ru.baza;


import java.util.*;

import static ru.baza.KnowledgeBase.QUESTIONS;
import static ru.baza.KnowledgeBase.ANSWERS;

public class ExpertSystem {

    public boolean backwardChaining(
            String goal,
            Map<Condition, Answer> facts,
            List<Rule> rules
    ) {

        if (facts.containsValue(Answer.valueOf(goal))) {
            return true;
        }

        return false;
    }

    private static Map<Condition, Answer> collectAnswers() {
        System.out.println("Ответьте на следующие вопросы");

        var conditions = EnumSet.allOf(Condition.class);
        var userAnswers = new HashMap<Condition, Answer>();
        var scanner = new Scanner(System.in);
        for (var condition : conditions) {
            printQuestions(condition);

            System.out.print("Введите вариант предложенного ответа (цифра): ");
            var answer = KnowledgeBase.getUserAnswer(condition, scanner.nextInt());
            userAnswers.put(condition, answer);
        }

        return userAnswers;
    }

    private static void printQuestions(Condition condition) {
        System.out.println(QUESTIONS.get(condition));
        System.out.println(ANSWERS.get(condition));
    }

    public static void main(String[] args) {
        RulesLoader.RULES.forEach(it -> System.out.println(it.conclusion + " | " + it.conditions));
        new ExpertSystem().backwardChaining("WordPress", collectAnswers(), RulesLoader.RULES);
    }

}
