package ru.baza;

import java.util.*;

public class GAReal extends GeneticAlgorithm<Double>{
    static final int POP_SIZE = 50;
    static final int GENERATIONS = 100;
    static final double MUTATION_RATE = 0.1;
    static final double MIN_X = -10;
    static final double MAX_X = 10;

    public static void main(String[] args) {
        new GAReal().execute();
    }

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

    @Override
    public List<Double> initPopulation(int populationSize, Double maxBound, Double minBound) {
        var pop = new ArrayList<Double>();
        for (var i = 0; i < POP_SIZE; i++) {
            pop.add(MIN_X + (MAX_X - MIN_X) * RANDOM.nextDouble());
        }
        return pop;
    }

    @Override
    public Double crossover(Double p1, Double p2) {
        return (p1 + p2) / 2.0;
    }

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

