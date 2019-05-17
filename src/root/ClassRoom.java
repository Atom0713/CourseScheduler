package root;

public class ClassRoom {
    private final int classRoomId;
    private final String classRoomName;
    private final int classRoomCapacity;
    String[][] timetable = new String[5][9];

    public ClassRoom(int classRoomId, String classRoomName, int classRoomCapacity) {
        this.classRoomId = classRoomId;
        this.classRoomName = classRoomName;
        this.classRoomCapacity = classRoomCapacity;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                timetable[i][j]="";
            }
        }
    }

    public int getClassRoomId() {
        return classRoomId;
    }

    public String getClassRoomName() {
        return classRoomName;
    }

    public int getClassRoomCapacity() {
        return classRoomCapacity;
    }

    public String[][] getTimetable() {
        return timetable;
    }

    public void addToClassRoomsTimetable(int i, int j, String courseCode){
        timetable[i][j] = courseCode;
    }
}
