package ru.baza;

import ru.baza.collector.FactCollector;
import ru.baza.engine.RuleEngine;
import ru.baza.knowledges.WebKnowledgeBase;
import ru.baza.questions.QuestionsBase;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) {
        RuleEngine engine = new RuleEngine();
        WebKnowledgeBase kb = new WebKnowledgeBase();

        var facts = new FactCollector(QuestionsBase.QUESTIONS, QuestionsBase.ANSWERS).collectFacts();

        // Проверяем, можем ли доказать WordPress
        try {
            boolean result = engine.backwardChain(kb, facts,"Angular + Spring Boot + MySQL");
            boolean result1 = engine.backwardChain(kb, facts,"React + Django + PostgreSQL");
            System.out.println(result);
            System.out.println(result1);

        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
