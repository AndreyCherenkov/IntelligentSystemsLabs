package ru.baza;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

/**
 * Абстрактный класс, реализующий основу генетического алгоритма (ГА).
 *
 * <p>Генетический алгоритм работает с популяцией объектов типа {@code T},
 * где {@code T} — числовой тип, расширяющий {@link Number}.
 * Класс определяет общую логику:
 *
 * <ul>
 *     <li>оценка пригодности (fitness-функция);</li>
 *     <li>турнирный отбор;</li>
 *     <li>скрещивание (crossover);</li>
 *     <li>мутация (mutation);</li>
 *     <li>эволюция популяции;</li>
 *     <li>инициализация популяции — реализуется в подклассах;</li>
 * </ul>
 *
 * <p>Для конкретного типа чисел (целых, вещественных и т.д.)
 * необходимо реализовать методы:
 * <ul>
 *     <li>{@link #initPopulation(int, Number, Number)}</li>
 *     <li>{@link #crossover(Number, Number)}</li>
 *     <li>{@link #mutate(Number)}</li>
 * </ul>
 *
 * @param <T> тип хромосомы, должен быть наследником {@link Number}
 */
public abstract class GeneticAlgorithm<T extends Number> {

    /** Генератор случайных чисел, используемый во всех операциях ГА. */
    protected static final Random RANDOM = new Random();

    /**
     * Fitness-функция, определяющая пригодность хромосомы.
     *
     * <p>По умолчанию используется квадратичная функция:
     * <pre>f(x) = x² + 4</pre>
     * Задача алгоритма — минимизировать fitness.
     *
     * <p>Подклассы могут переопределить этот метод для других задач оптимизации.
     *
     * @param x хромосома
     * @return значение fitness-функции
     */
    protected double fitness(T x) {
        return x.doubleValue() * x.doubleValue() + 4;
    }

    /**
     * Инициализация начальной популяции.
     *
     * @param populationSize размер популяции
     * @param maxBound верхняя граница диапазона
     * @param minBound нижняя граница диапазона
     * @return коллекция начальных хромосом
     */
    protected abstract Collection<T> initPopulation(int populationSize, T maxBound, T minBound);

    /**
     * Турнирный отбор: выбирает лучшего из двух случайно выбранных членов популяции.
     *
     * @param population популяция
     * @param fitnessFunction функция оценки пригодности
     * @return лучшая из двух случайных хромосом
     */
    protected T tournamentSelection(List<T> population, Function<T, Double> fitnessFunction) {
        var a = population.get(RANDOM.nextInt(population.size()));
        var b = population.get(RANDOM.nextInt(population.size()));
        return fitnessFunction.apply(a) < fitnessFunction.apply(b) ? a : b;
    }

    /**
     * Операция скрещивания двух родителей.
     *
     * @param p1 родитель 1
     * @param p2 родитель 2
     * @return новая хромосома
     */
    protected abstract T crossover(T p1, T p2);

    /**
     * Операция мутации хромосомы.
     *
     * @param x исходная хромосома
     * @return мутированная хромосома
     */
    protected abstract T mutate(T x);

    /**
     * Выполняет одно поколение эволюции.
     *
     * <p>Для каждого нового потомка:
     * <ol>
     *     <li>выбираются два родителя с помощью турнирного отбора;</li>
     *     <li>выполняется скрещивание;</li>
     *     <li>к полученной хромосоме применяется мутация;</li>
     *     <li>результат добавляется в новую популяцию.</li>
     * </ol>
     *
     * @param population текущая популяция
     * @param populationSize желаемый размер новой популяции
     * @return новая популяция после эволюции
     */
    protected List<T> evolve(List<T> population, int populationSize) {
        var newPopulation = new ArrayList<T>();
        for (var i = 0; i < populationSize; i++) {
            var p1 = tournamentSelection(population, this::fitness);
            var p2 = tournamentSelection(population, this::fitness);
            var child = crossover(p1, p2);
            child = mutate(child);
            newPopulation.add(child);
        }
        return newPopulation;
    }
}
