package ru.baza;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public abstract class GeneticAlgorithm<T extends Number> {

    protected static final Random RANDOM = new Random();

    protected double fitness(T x) {
        return x.doubleValue() * x.doubleValue() + 4;
    }

    /**
     * Метод для инициализации популяции
     * **/
    protected abstract Collection<T> initPopulation(int populationSize, T maxBound, T minBound);

    protected T tournamentSelection(List<T> population, Function<T, Double> fitnessFunction) {
        var a = population.get(RANDOM.nextInt(population.size()));
        var b = population.get(RANDOM.nextInt(population.size()));

        return fitnessFunction.apply(a) < fitnessFunction.apply(b) ? a : b;
    }

    protected abstract T crossover(T p1, T p2);
    protected abstract T mutate(T x);

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
