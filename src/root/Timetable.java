package root;

import java.util.HashMap;

public class Timetable{
    private final HashMap<Integer, ClassRoom> classRooms;
    private final HashMap<Integer, Professor> professors;
    private final HashMap<Integer, Course> courses;
    private final HashMap<Integer, TimePeriod> timePeriods;
    private final HashMap<Integer, Preference> preferences;
    private Class[] classes;
    private int numClasses = 0;


    public Timetable() {
        this.classRooms = new HashMap<Integer, ClassRoom>();
        this.professors = new HashMap<Integer, Professor>();
        this.courses = new HashMap<Integer, Course>();
        this.timePeriods = new HashMap<Integer, TimePeriod>();
        this.preferences = new HashMap<Integer, Preference>();
    }

    public Timetable(Timetable cloneable) {
        this.classRooms = cloneable.getClassRooms();
        this.professors = cloneable.getProfessors();
        this.courses = cloneable.getCourses();
        this.timePeriods = cloneable.getTimePeriods();
        this.preferences = cloneable.getPreferences();
    }

    public HashMap<Integer, ClassRoom> getClassRooms() {
        return this.classRooms;
    }

    public HashMap<Integer, Professor> getProfessors() {
        return this.professors;
    }

    public HashMap<Integer, Course> getCourses() {
        return this.courses;
    }

    public HashMap<Integer, TimePeriod> getTimePeriods() {
        return this.timePeriods;
    }

    public HashMap<Integer, Preference> getPreferences() {
        return this.preferences;
    }

    public Class[] getClasses() {
        return this.classes;
    }

    public void addClassRoom(int classRoomId, String classRoomName, int classRoomCapacity){
        this.classRooms.put(classRoomId, new ClassRoom(classRoomId, classRoomName, classRoomCapacity));
    }

    public void addProfessor(int professorId, String professorName){
        this.professors.put(professorId, new Professor(professorId, professorName));
    }

    public void addCourse(int courseId, String courseCode, String courseName, int courseCapacity, int courseHours, int professorId, String[] courseFaculty){
        this.courses.put(courseId, new Course(courseId, courseCode, courseName, courseCapacity, courseHours, professorId, courseFaculty));
    }

    public void addTimePeriod(int timeId, String time) {
        this.timePeriods.put(timeId, new TimePeriod(timeId, time));
    }

    public void addPreference(int preferenceId, int professorId, int[][] preferences){
        this.preferences.put(preferenceId, new Preference(preferenceId, professorId, preferences));
    }

    public void createClasses(Individual individual){
        Class[] classes = new Class[this.getNumClasses()];

        int[] chromosome = individual.getChromosome();
        int chromosomePos = 0;
        int classIndex = 0;


        for(Course course:this.getCoursesAsArray()){
            int courseId = course.getCourseId();

            classes[classIndex] = new Class(classIndex, courseId);

            //Add timeperiod
            classes[classIndex].setTimePeriod(chromosome[chromosomePos]);
            chromosomePos++;


            //Add classroom
            classes[classIndex].setClassRoomId(chromosome[chromosomePos]);
            chromosomePos++;

            classes[classIndex].setProfessor(course.getProfessorId());

            classIndex++;
        }
        this.classes=classes;
    }

    public ClassRoom getClassRoom(int classRoomId) {
        if (!this.classRooms.containsKey(classRoomId)) {
            //System.out.println("classRooms doesn't contain key " + classRoomId);
        }
        return (ClassRoom) this.classRooms.get(classRoomId);
    }

    public ClassRoom getRandomClassRoom() {
        Object[] classRoomsArray = this.classRooms.values().toArray();
        ClassRoom classRoom = (ClassRoom) classRoomsArray[(int) (classRoomsArray.length * Math.random())];
        return classRoom;
    }

    public Professor getProfessor(int professorId) {
        return (Professor) this.professors.get(professorId);
    }

    public Course getCourse(int courseId) {
        return (Course) this.courses.get(courseId);
    }

    public TimePeriod getTimePeriod(int timePeriodId) {
        return (TimePeriod) this.timePeriods.get(timePeriodId);
    }

    public TimePeriod getRandomTimePeriod(int duration) {
        Object[] timePeriodArray = this.timePeriods.values().toArray();
        TimePeriod timePeriod;
        while(true) {
            timePeriod = (TimePeriod) timePeriodArray[(int)
                    (timePeriodArray.length * Math.random())];
            int timePeriodId = timePeriod.getTimePeriodId();
            if(timePeriodId >= 100000 && duration == 3){
                break;
            }
            else if(timePeriodId >= 1000 && duration == 2 && timePeriodId<10000){
                break;
            }
            else if(timePeriodId >= 10 && duration == 1 && timePeriodId<100){
                break;
            }
        }
        return timePeriod;
    }

    /**
     * Get number of classes that need scheduling
     *
     * @return numClasses
     */
    public int getNumClasses(){
        if (this.numClasses > 0) {
            return this.numClasses;
        }
        int numClasses = 0;
        for(Course course:this.getCoursesAsArray()){
            numClasses ++;
        }
        this.numClasses = numClasses;
        return this.numClasses;
    }


    /**
     * Calculate the number of clashes
     *
     * @return numClashes
     */
    public int calculate_clashes() {
        int clashes = 0;
        for (Class classA : this.classes) {
            // Check classRoom capacity
            int roomCapacity = this.getClassRoom(classA.getClassRoomId()).getClassRoomCapacity();
            int courseCapacity = this.getCourse(classA.getCourseId()).getCourseCapacity();
            if (roomCapacity < courseCapacity) {
                clashes++;
            }
            // Check if classRoom is taken
            for (Class classB : this.classes) {
                if (classA.getClassRoomId() == classB.getClassRoomId() && classA.getClassId() != classB.getClassId()){
                    int time1 = classA.getTimePeriodId();
                    int time2 = classB.getTimePeriodId();
                    if(TimePeriod.isTimeClashing(time1,time2)) {
                        clashes++;
                        break;
                    }
                }
            }
            // Check if professor is available
            for (Class classB : this.classes) {
                if (classA.getProfessorId() == classB.getProfessorId() && classA.getClassId() != classB.getClassId()) {
                    int time1 = classA.getTimePeriodId();
                    int time2 = classB.getTimePeriodId();
                    if(TimePeriod.isTimeClashing(time1,time2)) {
                        clashes++;
                        break;
                    }
                }
            }

            //Check if faculty&department clashes
            int yearA = Course.getCourseYearByCode(this.getCourse(classA.getCourseId()).getCourseCode());
            String[] facDepIdsA = this.getCourse(classA.getCourseId()).getCourseFaculty();
            int timeA = classA.getTimePeriodId();
            for(Class classB : this.classes){
                int timeB = classB.getTimePeriodId();
                if(timeA==timeB && classA.getClassId() != classB.getClassId()) {
                    int yearB = Course.getCourseYearByCode(this.getCourse(classB.getCourseId()).getCourseCode());
                    String[] facDepIdsB = this.getCourse(classB.getCourseId()).getCourseFaculty();
                    if (Course.isFacDepYearClashing(yearA, facDepIdsA, yearB, facDepIdsB)) {
                        clashes++;
                        break;
                    }
                }
            }
        }
        return clashes;
    }

    public double calculate_preference_rate(){
        int preferencesRate = 0;
        Preference[] preferences = getPreferencesAsArray();
        for (Preference preference: preferences) {
            for(Class classA : this.classes){
                int professorId1 = classA.getProfessorId();
                int professorId2 = preference.getProfessorId();
                if(professorId1==professorId2){
                    int timePeriodId = classA.getTimePeriodId();
                    int[][] preferencesTable = preference.getPreferences();
                    while(timePeriodId!=0){
                        int i = timePeriodId%100/10 - 1;
                        int j = timePeriodId%10 - 1;
                        j/=3;
                        preferencesRate+=preferencesTable[i][j];
                        timePeriodId/=100;
                    }
                }
            }
        }
        return preferencesRate;
    }

    public Course[] getCoursesAsArray(){
        return (Course[]) this.courses.values().toArray(new Course[this.courses.size()]);
    }

    public Professor[] getProfessorsAsArray(){
        return (Professor[]) this.professors.values().toArray(new Professor[this.professors.size()]);
    }

    public Preference[] getPreferencesAsArray(){
        return (Preference[]) this.preferences.values().toArray(new Preference[this.preferences.size()]);
    }

    public ClassRoom[] getClassRoomsAsArray(){
        return (ClassRoom[]) this.classRooms.values().toArray(new ClassRoom[this.classRooms.size()]);
    }

    public static String timeIndexToString(int timeIndex){
        int time = timeIndex+9;
        String timeString = "";
        while (time!=0){
            timeString=(char)(time%10+48)+timeString;
            time/=10;
        }
        return timeString+":00";
    }
}
