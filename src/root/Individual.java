package root;

public class Individual {


    private int[] chromosome;
    private double fitness = -1;


    public Individual(Timetable timetable) {
        int numClasses = timetable.getNumClasses();
        // 1 gene for room, 1 for time
        int chromosomeLength = numClasses * 2;
        // Create random individual
        int newChromosome[] = new int[chromosomeLength];
        int chromosomeIndex = 0;
        // Loop through groups
        for(Course course:timetable.getCoursesAsArray()){
            int courseId = course.getCourseId();
            // Add random time
            int timePeriodId = timetable.getRandomTimePeriod(course.getCourseHours()).getTimePeriodId();
            newChromosome[chromosomeIndex] = timePeriodId;
            chromosomeIndex++;

            // Add random room
            int classRoomId = timetable.getRandomClassRoom().getClassRoomId();
            newChromosome[chromosomeIndex] = classRoomId;
            chromosomeIndex++;


        }
        this.chromosome = newChromosome;
    }


    public Individual(int chromosomeLength) {
        // Create random individual
        int[] individual = new int[chromosomeLength];


        for (int gene = 0; gene < chromosomeLength; gene++) {
            individual[gene] = gene;
        }

        this.chromosome = individual;
    }


    public Individual(int[] chromosome) {
        // Create individual chromosome
        this.chromosome = chromosome;
    }


    public int[] getChromosome() {
        return this.chromosome;
    }


    public int get_chromosome_length() {
        return this.chromosome.length;
    }


    public void set_gene(int offset, int gene) {
        this.chromosome[offset] = gene;
    }


    public int get_gene(int offset) {
        return this.chromosome[offset];
    }


    public void set_fitness(double fitness) {
        this.fitness = fitness;
    }


    public double get_fitness() {
        return this.fitness;
    }

    public String toString() {
        String output = "";
        for (int gene = 0; gene < this.chromosome.length; gene++) {
            output += this.chromosome[gene] + ",";
        }
        return output;
    }


    public boolean contains_gene(int gene) {
        for (int i = 0; i < this.chromosome.length; i++) {
            if (this.chromosome[i] == gene) {
                return true;
            }
        }
        return false;
    }



}
