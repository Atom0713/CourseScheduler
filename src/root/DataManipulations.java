package root;

import java.util.ArrayList;
import java.util.HashMap;

public class DataManipulations {
    private HashMap<String, ArrayList<Class>> completeSchedule;
    private int maxClassesAtSameTime;
    private Timetable timetable;
    private Class[] classes;
    private String[][] classesAsCodeTimeRoom;
    
    public DataManipulations(Timetable resultantTimetable) {
        this.timetable=resultantTimetable;
        completeSchedule = new HashMap<>();
        this.classes = resultantTimetable.getClasses();
        this.classesAsCodeTimeRoom = new String[classes.length][3];
        int max = 1;
        int classCounter=0;
        for (Class singleClass : classes) {
            //Preparing classesAsStringArray to print it later in pdf
            TimePeriod timePeriod = resultantTimetable.getTimePeriod(singleClass.getTimePeriodId());
            String classRoom = resultantTimetable.getClassRoom(singleClass.getClassRoomId()).getClassRoomName();
            String courseCode = resultantTimetable.getCourse(singleClass.getCourseId()).getCourseCode();
            classesAsCodeTimeRoom[classCounter][0] = courseCode;
            classesAsCodeTimeRoom[classCounter][1] = timePeriod.getTimePeriodAsString();
            classesAsCodeTimeRoom[classCounter][2] = classRoom;

            //Getting time periods of each class as an indexes
            int[][] timePeriodIndexes = timePeriod.getTimePeriodAsIndexes();
            for (int i = 0; i < timePeriodIndexes.length; i++) {
                String mergedIndex = "" + (char)(timePeriodIndexes[i][0]+48)+(char)(timePeriodIndexes[i][1]+48);
                ArrayList<Class> temp;
                if(!this.completeSchedule.containsKey(mergedIndex)){
                    temp = new ArrayList<>();
                    temp.add(singleClass);
                    this.completeSchedule.put(mergedIndex, temp);
                }
                else{
                    temp=this.completeSchedule.get(mergedIndex);
                    temp.add(singleClass);
                    if(temp.size()>max)
                        max = temp.size();
                    this.completeSchedule.replace(mergedIndex,temp);
                }
            }
            classCounter++;
        }
        this.maxClassesAtSameTime = max;
    }


    public String[][] getCompleteScheduleAs2dStringArray(){
        String[][] cmpltSchString=new String[5*this.maxClassesAtSameTime][9];
        int maxLength = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                String mergedIndex = "" + (char)(i+48)+(char)(j+48);
                if(!this.completeSchedule.containsKey(mergedIndex)){
                    for (int k = 0; k < this.maxClassesAtSameTime; k++) {
                        cmpltSchString[i*this.maxClassesAtSameTime+k][j]="";
                    }
                }
                else{
                    ArrayList<Class> temp = this.completeSchedule.get(mergedIndex);
                    for (int k = 0; k < this.maxClassesAtSameTime; k++) {
                        if(temp.size()>k){
                            String classInfo = "";
                            Class classA = temp.get(k);
                            classInfo+=this.timetable.getCourse(classA.getCourseId()).getCourseCode()+", ";
                            classInfo+=this.timetable.getClassRoom(classA.getClassRoomId()).getClassRoomName()+", ";
                            classInfo+=this.timetable.getProfessor(classA.getProfessorId()).getProfessorName();
                            if(classInfo.length()>maxLength)
                                maxLength=classInfo.length();
                            cmpltSchString[i*this.maxClassesAtSameTime+k][j]=classInfo;
                        }
                        else
                            cmpltSchString[i*this.maxClassesAtSameTime+k][j]="";
                    }
                }
            }
        }
        for (int i = 0; i < 5*this.maxClassesAtSameTime; i++) {
            for (int j = 0; j < 9; j++) {
                while (cmpltSchString[i][j].length()<maxLength){
                    cmpltSchString[i][j]+=" ";
                }
            }
        }
        return cmpltSchString;
    }

    /** This function return a String array, where each element is on type:
     *      ["CourseCode \t TimePeriod \t Classroom", ...]
     */
    public String[][] getClassesAsCodeTimeRoom(){
        return classesAsCodeTimeRoom;
    }

    public int getMaxClassesAtSameTime() {
        return maxClassesAtSameTime;
    }
}
