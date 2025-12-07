package ru.baza.collector;

import ru.baza.model.Fact;

import java.util.Collection;
import java.util.Map;

/**
 * Абстрактный сборщик фактов на основе вопросов и ответов.
 * @param <K> тип ключа (должен быть сравним)
 * @param <Question> тип вопроса
 * @param <Answer> тип ответа
 */
public abstract class AbstractFactCollector<K extends Comparable<K>, Question, Answer> {

    private final Map<K, Question> questions;
    private final Map<K, Answer> answers;

    protected AbstractFactCollector(Map<K, Question> questions, Map<K, Answer> answers) {
        this.questions = questions;
        this.answers = answers;
    }

    protected Map<K, Question> getQuestions() {
        return questions;
    }

    protected Map<K, Answer> getAnswers() {
        return answers;
    }

    /**
     * Собирает факты на основе предоставленных данных.
     * @return коллекция найденных фактов
     */
    protected abstract Collection<Fact> collectFacts();

    protected void printQuestions(K condition) {
        System.out.println(getQuestions().get(condition));
        System.out.println(getAnswers().get(condition));
    }
}
