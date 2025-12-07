package ru.baza;

import java.util.*;

public class GAInteger extends GeneticAlgorithm<Integer>{
    static final int POP_SIZE = 50;
    static final int GENERATIONS = 100;
    static final double MUTATION_RATE = 0.1;
    static final int MIN_X = -100;
    static final int MAX_X = 100;

    public static void main(String[] args) {
        new GAInteger().execute();
    }

    public void execute() {
        var population = initPopulation(POP_SIZE, MAX_X, MIN_X);
        for (int gen = 0; gen < GENERATIONS; gen++) {
            population = evolve(population, POP_SIZE);
        }

        Integer best = population.stream()
                .min(Comparator.comparingDouble(super::fitness))
                .orElse(0);

        System.out.println("Лучший найденный x = " + best);
        System.out.println("f(x) = " + fitness(best));
    }

    @Override
    public List<Integer> initPopulation(int populationSize, Integer maxBound, Integer minBound) {
        var pop = new ArrayList<Integer>();
        for (var i = 0; i < populationSize; i++)
            pop.add(RANDOM.nextInt(maxBound - minBound + 1) + minBound);
        return pop;
    }

    @Override
    public Integer crossover(Integer p1, Integer p2) {
        return RANDOM.nextBoolean() ? p1 : p2;
    }

    @Override
    public Integer mutate(Integer x) {
        if (RANDOM.nextDouble() < MUTATION_RATE) {
            x += RANDOM.nextInt(3) - 1;
            if (x < MIN_X) x = MIN_X;
            if (x > MAX_X) x = MAX_X;
        }
        return x;
    }

}

