package root;

public class Professor {
    private final int professorId;
    private final String professorName;
    String[][] timetable = new String[5][9];

    public Professor(int professorId, String professorName) {
        this.professorId = professorId;
        this.professorName = professorName;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                timetable[i][j]="";
            }
        }
    }

    public int getProfessorId() {
        return professorId;
    }

    public String getProfessorName() {
        return professorName;
    }

    public void addToProfessorsTimetable(int i, int j, String classRoom, String courseCode){
        timetable[i][j]=classRoom+", "+courseCode;
    }

    public String[][] getTimetable() {
        return timetable;
    }
}
