package ru.baza.collector;

import ru.baza.model.Fact;

import java.util.Collection;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;

/**
 * Абстрактный базовый класс для компонентов, отвечающих за сбор фактов
 * на основе набора вопросов и ответов.
 *
 * <p>Используется в системах, где значение факта определяется через
 * интерактивное взаимодействие (например, интервью, анкета, ввод данных).
 *
 * <h3>Структура</h3>
 * <ul>
 *   <li>{@code questions} — словарь ключ → текст вопроса;
 *   <li>{@code answers} — словарь ключ → ответ (произвольного типа);
 *   <li>метод {@link #collectFacts()} — должен быть реализован наследником и
 *       возвращать факты, сформированные на основе вопросов/ответов.
 * </ul>
 *
 * <h3>Требования</h3>
 * <ul>
 *   <li>ключ {@code K} должен быть сравним (Comparable) — это позволяет
 *       переопределять порядок вопросов или использовать структуры, требующие
 *       упорядоченности ключей (например, TreeMap);</li>
 *   <li>{@code Question} и {@code Answer} не накладывают ограничений — типы
 *       выбираются реализацией;</li>
 *   <li>карты вопросов и ответов доступны только для чтения через
 *       {@link #getQuestions()} и {@link #getAnswers()}.</li>
 * </ul>
 *
 * @param <K>        тип ключа (должен реализовывать {@link Comparable})
 * @param <Question> тип вопроса (например, строка, объект DTO, модель UI)
 * @param <Answer>   тип ответа (строка, код, перечисление, объект и т.п.)
 */
public abstract class AbstractFactCollector<K extends Comparable<K>, Question, Answer> {

    /** Карта ключ → вопрос. */
    private final Map<K, Question> questions;

    /** Карта ключ → ответ. */
    private final Map<K, Answer> answers;

    /**
     * Создаёт базовый сборщик фактов.
     *
     * @param questions карта вопросов
     * @param answers   карта ответов
     */
    protected AbstractFactCollector(Map<K, Question> questions, Map<K, Answer> answers) {
        this.questions = questions;
        this.answers = answers;
    }

    /**
     * Возвращает неизменяемую карту вопросов.
     *
     * @return карта вопросов
     */
    protected Map<K, Question> getQuestions() {
        return unmodifiableMap(questions);
    }

    /**
     * Возвращает неизменяемую карту ответов.
     *
     * @return карта ответов
     */
    protected Map<K, Answer> getAnswers() {
        return unmodifiableMap(answers);
    }

    /**
     * Абстрактный метод, который должен быть реализован наследниками
     * и возвращать набор фактов, сформированных на основе заданных вопросов
     * и собранных ответов.
     *
     * @return коллекция фактов {@link Fact}, полученных из данных
     */
    protected abstract Collection<Fact> collectFacts();

    /**
     * Выводит в консоль вопрос и ответ, соответствующие указанному ключу.
     * <p>
     * Используется как вспомогательный метод для отладки или интерактивного режима.
     *
     * @param key ключ вопроса/ответа
     */
    protected void printQuestions(K key) {
        System.out.println(getQuestions().get(key));
        System.out.println(getAnswers().get(key));
    }
}
