package ru.baza.collector;

import ru.baza.questions.Condition;
import ru.baza.model.Fact;
import ru.baza.questions.QuestionsBase;

import java.util.*;

/**
 * Сборщик фактов по моделям вопросов/ответов, использующий перечисление
 * {@link Condition} для определения полного набора условий.
 *
 * <p>Данный класс реализует интерактивную логику: задаёт вопросы пользователю
 * через консоль, получает выбранные ответы и преобразует их в набор объектов
 * {@link Fact}, которые впоследствии используются экспертной системой.
 *
 * <h3>Особенности поведения</h3>
 * <ul>
 *     <li>Использует все элементы {@link Condition} через {@link EnumSet#allOf}.</li>
 *     <li>Для каждого условия выводится вопрос и доступные ответы.</li>
 *     <li>Пользователь вводит номер варианта ответа (целое число).</li>
 *     <li>Преобразование номера в текст ответа выполняется методом
 *         {@link QuestionsBase#getUserAnswer(Condition, int)}.</li>
 *     <li>Каждый выбранный ответ записывается как факт: name = имя Condition,
 *         value = текст выбранного ответа.</li>
 * </ul>
 *
 * <h3>Пример результата одной итерации:</h3>
 * <pre>
 * Condition: WEATHER
 * Question: "Какая сейчас погода?"
 * User input: 2
 * Recorded Fact: Fact("WEATHER", "Дождь")
 * </pre>
 *
 * <h3>Ошибки ввода</h3>
 * В текущей реализации не обрабатываются:
 * <ul>
 *   <li>нечисловой ввод;</li>
 *   <li>выход за диапазон вариантов ответа.</li>
 *
 * <p>При необходимости можно добавить валидацию перед {@code scanner.nextInt()}.
 *
 * <h3>Возвращаемые данные</h3>
 * Метод {@link #collectFacts()} всегда возвращает:
 * <ul>
 *     <li>{@code Set<Fact>} — по одному факту для каждого {@code Condition};</li>
 *     <li>множество всегда содержит ровно столько элементов, сколько значений в enum.</li>
 * </ul>
 */
public class FactCollector extends AbstractFactCollector<Condition, String, String> {

    /**
     * Создаёт коллектора фактов.
     *
     * @param questions карта условие → текст вопроса
     * @param answers   карта условие → текстовые варианты ответа
     */
    public FactCollector(Map<Condition, String> questions, Map<Condition, String> answers) {
        super(questions, answers);
    }

    /**
     * Проводит интерактивный сбор фактов.
     * <p>
     * Для каждого элемента {@link Condition} выводит вопрос и набор предложенных
     * ответов, ожидает числовой выбор пользователя и формирует {@link Fact}.
     *
     * @return множество собранных фактов
     */
    public Set<Fact> collectFacts() {
        System.out.println("Ответьте на следующие вопросы");

        var conditions = EnumSet.allOf(Condition.class);
        var userAnswers = new HashSet<Fact>();
        var scanner = new Scanner(System.in);

        for (var condition : conditions) {
            printQuestions(condition);

            System.out.print("Введите вариант предложенного ответа (цифра): ");
            var choice = scanner.nextInt();

            var answer = QuestionsBase.getUserAnswer(condition, choice);
            userAnswers.add(new Fact(condition.name(), answer.getText()));
        }

        return userAnswers;
    }
}
