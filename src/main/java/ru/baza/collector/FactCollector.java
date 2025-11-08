package ru.baza.collector;

import ru.baza.questions.Condition;
import ru.baza.model.Fact;
import ru.baza.questions.QuestionsBase;

import java.util.*;

public class FactCollector extends AbstractFactCollector<Condition, String, String> {

    public FactCollector(Map<Condition, String> questions, Map<Condition, String> answers) {
        super(questions, answers);
    }

    public Set<Fact> collectFacts() {
        System.out.println("Ответьте на следующие вопросы");

        var conditions = EnumSet.allOf(Condition.class);
        var userAnswers = new HashSet<Fact>();
        var scanner = new Scanner(System.in);
        for (var condition : conditions) {
            printQuestions(condition);

            System.out.print("Введите вариант предложенного ответа (цифра): ");
            var answer = QuestionsBase.getUserAnswer(condition, scanner.nextInt());
            userAnswers.add(new Fact(condition.name(), answer.name()));
        }

        return userAnswers;
    }
}
