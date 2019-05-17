package root;

import java.util.ArrayList;
import java.util.HashMap;

public class DataManipulations {
    private HashMap<String, ArrayList<Class>> completeSchedule;
    private int maxClassesAtSameTime;
    private Timetable timetable;
    
    public DataManipulations(Timetable resultantTimetable) {
        this.timetable=resultantTimetable;
        completeSchedule = new HashMap<>();
        Class[] classes = resultantTimetable.getClasses();
        int max = 1;
        for (Class singleClass : classes) {
            //Getting time periods of each class as an indexes
            TimePeriod timePeriod = resultantTimetable.getTimePeriod(singleClass.getTimePeriodId());
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

    public int getMaxClassesAtSameTime() {
        return maxClassesAtSameTime;
    }
}
