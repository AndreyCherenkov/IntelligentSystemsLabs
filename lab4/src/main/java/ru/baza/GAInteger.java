package ru.baza;

import java.util.*;

/**
 * Реализация генетического алгоритма для поиска оптимального значения целочисленной переменной.
 *
 * <p>Класс расширяет {@link GeneticAlgorithm} и определяет конкретные операции
 * над хромосомами, представленными целыми числами.
 *
 * <p>Поддерживает:
 * <ul>
 *     <li>Случайную инициализацию популяции в заданном диапазоне.</li>
 *     <li>Одноточечный "ленивый" кроссовер — выбор одного из родителей.</li>
 *     <li>Мутацию числового значения на ±1.</li>
 * </ul>
 *
 * <p>Оптимизация выполняется путём минимизации функции {@code fitness(x)},
 * определённой в родительском классе.
 */
public class GAInteger extends GeneticAlgorithm<Integer> {

    /** Размер популяции. */
    static final int POP_SIZE = 50;

    /** Количество поколений. */
    static final int GENERATIONS = 100;

    /** Вероятность мутации. */
    static final double MUTATION_RATE = 0.1;

    /** Минимально возможное значение переменной. */
    static final int MIN_X = -100;

    /** Максимально возможное значение переменной. */
    static final int MAX_X = 100;

    /**
     * Точка входа: запускает пример работы ГА.
     */
    public static void main(String[] args) {
        new GAInteger().execute();
    }

    /**
     * Полный цикл работы генетического алгоритма:
     * <ol>
     *     <li>Инициализация популяции.</li>
     *     <li>Эволюция в течение {@link #GENERATIONS} поколений.</li>
     *     <li>Выбор лучшего результата.</li>
     * </ol>
     */
    public void execute() {
        var population = initPopulation(POP_SIZE, MAX_X, MIN_X);

        for (int gen = 0; gen < GENERATIONS; gen++) {
            population = evolve(population, POP_SIZE);
        }

        // Поиск лучшего решения
        Integer best = population.stream()
                .min(Comparator.comparingDouble(super::fitness))
                .orElse(0);

        System.out.println("Лучший найденный x = " + best);
        System.out.println("f(x) = " + fitness(best));
    }

    /**
     * Инициализирует популяцию случайными значениями в диапазоне [minBound, maxBound].
     *
     * @param populationSize количество особей
     * @param maxBound верхняя граница значений
     * @param minBound нижняя граница значений
     * @return список случайных целых чисел
     */
    @Override
    public List<Integer> initPopulation(int populationSize, Integer maxBound, Integer minBound) {
        var pop = new ArrayList<Integer>();
        for (var i = 0; i < populationSize; i++)
            pop.add(RANDOM.nextInt(maxBound - minBound + 1) + minBound);
        return pop;
    }

    /**
     * Кроссовер для чисел: случайно выбирает одного из родителей.
     *
     * @param p1 родитель 1
     * @param p2 родитель 2
     * @return потомок
     */
    @Override
    public Integer crossover(Integer p1, Integer p2) {
        return RANDOM.nextBoolean() ? p1 : p2;
    }

    /**
     * Мутация: с вероятностью {@link #MUTATION_RATE} изменяет x на -1, 0 или +1.
     * Гарантирует, что x остаётся в диапазоне [MIN_X, MAX_X].
     *
     * @param x значение для мутации
     * @return мутированное (или исходное) значение
     */
    @Override
    public Integer mutate(Integer x) {
        if (RANDOM.nextDouble() < MUTATION_RATE) {
            x += RANDOM.nextInt(3) - 1; // -1, 0 или +1
            if (x < MIN_X) x = MIN_X;
            if (x > MAX_X) x = MAX_X;
        }
        return x;
    }
}
