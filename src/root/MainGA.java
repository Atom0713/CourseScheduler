package root;

import UI.MainFrame;
import IO.InputEXCEL;

import java.io.IOException;
import java.util.ArrayList;

public class MainGA {
    public static void main(String[] args) {
        MainFrame.mainFrame();

    }





    public static Timetable initializeTimetable(String path)  throws IOException{
        // Create timetable
        Timetable timetable = new Timetable();
        InputEXCEL input = new InputEXCEL();

        String classrooms[][]= input.readExcelFile(path, "Classrooms");
        for (int i=1; i < classrooms.length; i++){
                timetable.addClassRoom(i, classrooms[i][0], Integer.parseInt(classrooms[i][1])); // ID выставляем сами
        }

        String professors[][] = input.readExcelFile(path,"Professors");
        for (int i=1; i < professors.length; i++){
                timetable.addProfessor(Integer.parseInt(professors[i][0]), professors[i][1]);
        }

        ArrayList<ArrayList<String>> courses = input.getAllTimePeriodsOfCourses(path, "Courses");//multi-dimensional ArrayList<ArrayList<String>>, each inner list
        // holds information about a course, its code, subject, capacity and duration in hours
        for (int i = 0; i< courses.size(); i++){
            String[] facultyDepartmentIds = splitStringByComa(courses.get(i).get(5));
            timetable.addCourse(i, courses.get(i).get(0), courses.get(i).get(1), Integer.parseInt(courses.get(i).get(2)), Integer.parseInt(courses.get(i).get(3)), Integer.parseInt(courses.get(i).get(4)), facultyDepartmentIds);
        }
        // Adding time periods
        for (int i = 10; i < 60; i+=10) {
            for (int j = 1; j < 10; j++) {
                timetable.addTimePeriod(i+j,TimePeriod.timePeriodIdToString(i+j));
                if(j<9){
                    int tempId = (i+j)*100+(i+j+1);
                    timetable.addTimePeriod(tempId, TimePeriod.timePeriodIdToString(tempId));
                }
                if(j<8){
                    int tempId = (i+j)*10000+(i+j+1)*100+(i+j+2);
                    timetable.addTimePeriod(tempId, TimePeriod.timePeriodIdToString(tempId));
                }
            }
        }
        String preferences[][] = InputEXCEL.readExcelFile(path, "Preferences");
        for (int i = 1;i < preferences.length; i++){
            int[][] preference = new int[5][3];
            preference[0][0] = Integer.parseInt(preferences[i][1]);
            preference[0][1] = Integer.parseInt(preferences[i][2]);
            preference[0][2] = Integer.parseInt(preferences[i][3]);

            preference[1][0] = Integer.parseInt(preferences[i][4]);
            preference[1][1] = Integer.parseInt(preferences[i][5]);
            preference[1][2] = Integer.parseInt(preferences[i][6]);

            preference[2][0] = Integer.parseInt(preferences[i][7]);
            preference[2][1] = Integer.parseInt(preferences[i][8]);
            preference[2][2] = Integer.parseInt(preferences[i][9]);

            preference[3][0] = Integer.parseInt(preferences[i][10]);
            preference[3][1] = Integer.parseInt(preferences[i][11]);
            preference[3][2] = Integer.parseInt(preferences[i][12]);

            preference[4][0] = Integer.parseInt(preferences[i][13]);
            preference[4][1] = Integer.parseInt(preferences[i][14]);
            preference[4][2] = Integer.parseInt(preferences[i][15]);

            timetable.addPreference(Integer.parseInt(preferences[i][0]), Integer.parseInt(preferences[i][0]), preference);
        }
        return timetable;
    }

    private static String[] splitStringByComa(String s){
        s=s.replaceAll("\\s","");
        return s.split(",");
    }
}