package root;

public class GA {

    private int max_pop_size;
    private double mut_prob;
    private double crossover_prob;
    private int number_of_elite_individuals;
    protected int tournament_size;

    public GA(int max_pop_size, double mut_prob, double crossover_prob, int number_of_elite_individuals,
              int tournament_size) {

        this.max_pop_size = max_pop_size;
        this.mut_prob = mut_prob;
        this.crossover_prob = crossover_prob;
        this.number_of_elite_individuals = number_of_elite_individuals;
        this.tournament_size = tournament_size;
    }


    // Initialize population
    public Population initialize_population(Timetable timetable) {
        return new Population(this.max_pop_size, timetable);
    }


    public boolean is_termination_condition_met(int generations_counter, int generations_max) {
        return (generations_counter > generations_max);
    }


    public boolean is_termination_condition_met(Population population, int non_progressing_generations_counter) {
        return population.get_fittest(0).get_fitness() >= 1.0 && non_progressing_generations_counter>=50;
    }



    public double calculate_fitness(Individual individual, Timetable timetable) {


        Timetable cloned_timetable = new Timetable(timetable);
        cloned_timetable.createClasses(individual);

        // Calculate fitness
        int clashes = cloned_timetable.calculate_clashes();
        double fitness = 1 / (double) (clashes + 1);

        double preference_rate = cloned_timetable.calculate_preference_rate()/100000;
        fitness+=preference_rate;

        individual.set_fitness(fitness);

        return fitness;
    }


    public void evaluate_population(Population population, Timetable timetable) {
        double population_fitness = 0;

        // Loop over population evaluating individuals and summing population
        // fitness
        for (Individual individual : population.get_individuals()) {
            population_fitness += this.calculate_fitness(individual, timetable);
        }

        population.set_population_fitness(population_fitness);
    }


    public Individual select_parent(Population population) {

        Population tournament_population = new Population(this.tournament_size);

        population.shuffle();
        for (int i = 0; i < this.tournament_size; i++) {
            Individual tournament_individual = population.get_individual(i);
            tournament_population.set_individual(i, tournament_individual);
        }

        // Return the best
        return tournament_population.get_fittest(0);
    }



    public Population apply_mutation(Population population, Timetable timetable) {

        Population mutated_population = new Population(this.max_pop_size);

        for (int i = 0; i < population.size(); i++) {
            Individual individual = population.get_fittest(i);

            Individual random_individual = new Individual(timetable);

            for (int j = 0; j < individual.get_chromosome_length(); j++) {

                if (i > this.number_of_elite_individuals) {

                    if (this.mut_prob > Math.random()) {

                        individual.set_gene(j, random_individual.get_gene(j));
                    }
                }
            }

            mutated_population.set_individual(i, individual);
        }

        return mutated_population;
    }


    public Population crossover(Population population) {

        Population new_population = new Population(population.size());

        for (int i = 0; i < population.size(); i++) {
            Individual parent1 = population.get_fittest(i);

            if (this.crossover_prob > Math.random() && i >= this.number_of_elite_individuals) {
                // Initialize child
                Individual child = new Individual(parent1.get_chromosome_length());

                // Find second parent
                Individual parent2 = select_parent(population);

                // Loop over chromosome
                for (int j = 0; j < parent1.get_chromosome_length(); j++) {

                    if (0.5 > Math.random()) {
                        child.set_gene(j, parent1.get_gene(j));
                    } else {
                        child.set_gene(j, parent2.get_gene(j));
                    }
                }

                // Add child to new population
                new_population.set_individual(i, child);
            } else {
                //add elite without crossover
                new_population.set_individual(i, parent1);
            }
        }

        return new_population;
    }



}