package ru.baza;

import ru.baza.collector.FactCollector;
import ru.baza.engine.RuleEngine;
import ru.baza.knowledges.WebKnowledgeBase;
import ru.baza.questions.QuestionsBase;

public class Main {
    public static void main(String[] args) {
        var engine = new RuleEngine();
        var kb = new WebKnowledgeBase();

        var facts = new FactCollector(QuestionsBase.QUESTIONS, QuestionsBase.ANSWERS).collectFacts();
        System.out.println(facts);
        boolean result = engine.backwardChain(kb, facts, "AngularSpringMySQL");
        System.out.println(result);

    }
}
