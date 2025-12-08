package ru.baza;

import java.util.*;

/**
 * Реализация генетического алгоритма для оптимизации вещественного значения.
 *
 * <p>Класс наследует {@link GeneticAlgorithm} и предоставляет конкретные
 * реализации методов для работы с хромосомами типа {@code Double}.
 *
 * <p>Особенности:
 * <ul>
 *     <li>Инициализация случайных значений в диапазоне [-10, 10].</li>
 *     <li>Кроссовер — усреднение двух родителей.</li>
 *     <li>Мутация — добавление гауссовского шума.</li>
 * </ul>
 *
 * <p>Цель алгоритма — минимизация функции {@link #fitness(Double)},
 * определённой в родительском классе.
 */
public class GAReal extends GeneticAlgorithm<Double> {

    /** Размер популяции. */
    static final int POP_SIZE = 50;

    /** Количество поколений. */
    static final int GENERATIONS = 100;

    /** Вероятность мутации. */
    static final double MUTATION_RATE = 0.1;

    /** Минимально возможное значение хромосомы. */
    static final double MIN_X = -10;

    /** Максимально возможное значение хромосомы. */
    static final double MAX_X = 10;

    /**
     * Точка входа: запускает пример оптимизации вещественной переменной.
     */
    public static void main(String[] args) {
        new GAReal().execute();
    }

    /**
     * Основной цикл выполнения генетического алгоритма:
     * <ol>
     *     <li>Генерация начальной популяции.</li>
     *     <li>Эволюция популяции через {@link #GENERATIONS} поколений.</li>
     *     <li>Нахождение лучшего решения.</li>
     * </ol>
     */
    public void execute() {
        List<Double> population = initPopulation(POP_SIZE, MAX_X, MIN_X);

        for (int gen = 0; gen < GENERATIONS; gen++) {
            population = evolve(population, POP_SIZE);
        }

        Double best = population.stream()
                .min(Comparator.comparingDouble(super::fitness))
                .orElse(0.0);

        System.out.println("Лучший найденный x = " + best);
        System.out.println("f(x) = " + fitness(best));
    }

    /**
     * Создаёт начальную популяцию случайных вещественных значений
     * внутри заданного диапазона.
     *
     * @param populationSize размер популяции
     * @param maxBound верхняя граница диапазона
     * @param minBound нижняя граница диапазона
     * @return список случайных значений
     */
    @Override
    public List<Double> initPopulation(int populationSize, Double maxBound, Double minBound) {
        var pop = new ArrayList<Double>();
        for (var i = 0; i < populationSize; i++) {
            pop.add(MIN_X + (MAX_X - MIN_X) * RANDOM.nextDouble());
        }
        return pop;
    }

    /**
     * Кроссовер: при скрещивании двух вещественных значений
     * используется их среднее арифметическое.
     *
     * @param p1 родитель 1
     * @param p2 родитель 2
     * @return потомок (среднее двух родителей)
     */
    @Override
    public Double crossover(Double p1, Double p2) {
        return (p1 + p2) / 2.0;
    }

    /**
     * Мутация вещественного значения.
     * <p>С вероятностью {@link #MUTATION_RATE} добавляет гауссовский шум
     * с коэффициентом 0.5 и ограничивает значение в пределах [MIN_X, MAX_X].
     *
     * @param x исходное значение
     * @return мутированное или исходное значение
     */
    @Override
    public Double mutate(Double x) {
        if (RANDOM.nextDouble() < MUTATION_RATE) {
            x += RANDOM.nextGaussian() * 0.5;
            if (x < MIN_X) x = MIN_X;
            if (x > MAX_X) x = MAX_X;
        }
        return x;
    }
}
