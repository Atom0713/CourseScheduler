package GUI;

import IO.OutputEXCEL;
import root.*;
import root.Class;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainFrame {
    public static String path=null;//will hold path to the EXCEL input file
    public  static void mainFrame(){
        //GUI..start
        //create Jframe object
        JFrame frame = new JFrame("Set up");
        //create labels and buttons objects
        JLabel chooseFileLabel,loadingLable;
        JButton runGA, run1,run2, chooseFileButton;
        //set sizes and initialize labels and buttons objects
        chooseFileButton = new JButton("Open");
        chooseFileLabel = new JLabel("Choose File");
        chooseFileButton.setBounds(80,10,80,30);
        chooseFileLabel.setBounds(10, 10, 90,30);

        chooseFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser chooseFile = new JFileChooser();
                //invoke the showSaveDialog function to show the save dialog
                int r =  chooseFile.showSaveDialog(null);

                //if the user selects a file
                if(r == JFileChooser.APPROVE_OPTION)
                {
                    //set the path to the path of the selected file
                    path = chooseFile.getSelectedFile().getAbsolutePath();
                }
            }
        });


        runGA = new JButton("Run using Genetic Algorithm");
        run1 = new JButton("...");
        run2 = new JButton("...");
        loadingLable = new JLabel("file was not choosen ");
        runGA.setBounds(100,200,200,30);
        run1.setBounds(100,250,200,30);
        run2.setBounds(100,300,200,30);
        runGA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                loadingLable.setBounds(150,50,250,30);

                try{
                    //main processes
                    System.out.println("Initialization of root.Timetable started...");
                    Timetable timetable = MainGA.initializeTimetable(path);
                    System.out.println("Initialization of root.Timetable Finished\n");

                    System.out.println("Initialization of Genetic Algorithm started...");
                    GA ga = new GA(200, 0.02, 0.9, 2, 5);
                    System.out.println("Initialization of Genetic Algorithm finished...\n");

                    System.out.println("Initialization of root.Population started...");
                    Population population = ga.initialize_population(timetable);
                    System.out.println("Initialization of root.Population finished.\n");

                    System.out.println("First population is being evaluated...");
                    ga.evaluate_population(population, timetable);
                    System.out.println("First population is evaluated.\n");
                    // Keep track of current generation
                    int generation = 1;
                    // Start evolution loop
                    int nonProgressingGenerationsCount = 0;
                    double previousFitness = population.get_fittest(0).get_fitness();
                    while (!ga.is_termination_condition_met(generation, 50000) && !ga.is_termination_condition_met(population,nonProgressingGenerationsCount)) {
                        if(population.get_fittest(0).get_fitness()>previousFitness){
                            previousFitness=population.get_fittest(0).get_fitness();
                            nonProgressingGenerationsCount=0;
                        }
                        System.out.println("Generation number: "+ generation);
                        // Print fitness
                        System.out.println("G" + generation + " Best fitness: " + population.get_fittest(0).get_fitness());

                        // Apply crossover
                        population = ga.crossover(population);

                        // Apply mutation
                        population = ga.apply_mutation(population, timetable);

                        // Evaluate population
                        ga.evaluate_population(population, timetable);
                        // Increment the current generation
                        if(previousFitness>=1.0) {
                            nonProgressingGenerationsCount++;
                        }
                        generation++;
                    }
                    // Print fitness
                    timetable.createClasses(population.get_fittest(0));
                    //System.out.println();
                    //System.out.println("Solution found in " + generation + " generations");

                    //System.out.println("Final solution fitness: " + population.get_fittest(0).get_fitness());
                    //System.out.println("Clashes: " + timetable.calculate_clashes());


                    /** Здесь выводится результат, здесь же заполняются расписания преподов и кабинетов
                     *
                     */
                    //System.out.println();
                    Class[] classes = timetable.getClasses();
                    int classIndex = 1;
                    for (Class bestClass : classes) {
                        Course course = timetable.getCourse(bestClass.getCourseId());
                        ClassRoom classRoom = timetable.getClassRoom(bestClass.getClassRoomId());
                        Professor professor = timetable.getProfessor(bestClass.getProfessorId());
                        TimePeriod timePeriod = timetable.getTimePeriod(bestClass.getTimePeriodId());

                        //Filling timetables of Professors and ClassRooms
                        int[][] timePeriodIndexes = timePeriod.getTimePeriodAsIndexes();
                        for (int i = 0; i < timePeriodIndexes.length; i++) {
                            professor.addToProfessorsTimetable(timePeriodIndexes[i][0],timePeriodIndexes[i][1],classRoom.getClassRoomName(),course.getCourseCode());
                            classRoom.addToClassRoomsTimetable(timePeriodIndexes[i][0],timePeriodIndexes[i][1],course.getCourseCode());
                        }
//                        System.out.println("Class " + classIndex + ":");
//                        System.out.println("Course: " + course.getCourseName());
//                        System.out.println("ClassRoom: " + classRoom.getClassRoomName());
//                        System.out.println("Professor: " + professor.getProfessorName());
//                        System.out.println("TimePeriod: " + timePeriod.getTimePeriod());
//                        System.out.println("-----");
                        classIndex++;
                    }


                    /** @allProfTimetables содержит в себе расписания каждого профессора
                     * @allProfNames содержит имена всех преподов в том же порядке что и @allProfTimetables
                     */

                    ArrayList<String[][]> allProfTimetables = new ArrayList<>();
                    ArrayList<String> allProfNames = new ArrayList<>();
                    Professor[] professors = timetable.getProfessorsAsArray();
                    for (int i = 0; i < professors.length; i++) {
                        allProfTimetables.add(professors[i].getTimetable());
                        allProfNames.add(professors[i].getProfessorName());
                    }


                    /** allClassRoomsTimetables содержит в себе расписания каждого кабинета
                     * @allClassRoomsNames содержит названия всех кабинетов в том же порядке что и @allProfTimetables
                     */

                    ArrayList<String[][]> allClassRoomsTimetables = new ArrayList<>();
                    ArrayList<String> allClassRoomsNames = new ArrayList<>();
                    ClassRoom[] classRooms = timetable.getClassRoomsAsArray();
                    for (int i = 0; i < classRooms.length; i++) {
                        allClassRoomsTimetables.add(classRooms[i].getTimetable());
                        allClassRoomsNames.add(classRooms[i].getClassRoomName());
                    }

                    //OutputFrame constructor gets all the timetable and other information necessary as arguments to produce output frame.
                    OutputFrame outputFrame = new OutputFrame(generation, population.get_fittest(0).get_fitness(), timetable.calculate_clashes(), allProfNames,allProfTimetables, allClassRoomsNames,allClassRoomsTimetables);
                    OutputEXCEL outputExcel = new OutputEXCEL(timetable);
                    //main output frame
                    outputFrame.outputFrame();
                }catch (Exception ex){
                    System.out.println(ex);
                }
            }
        });
        //adding labels buttons to the frame. Setting its size and layout and default close operation.
        frame.add(loadingLable);
        frame.add(chooseFileLabel);
        frame.add(chooseFileButton);
        frame.add(runGA);
        frame.add(run1);
        frame.add(run2);
        frame.setSize(400,400);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        //GUI...end
    }
}
