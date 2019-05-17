package root;

public class Class {
    private final int classId;
    private final int courseId;
    private int professorId;
    private int timePeriodId;
    private int classRoomId;

    public Class(int classId, int courseId) {
        this.classId = classId;
        this.courseId = courseId;
    }

    public void setProfessor(int professorId) {
        this.professorId = professorId;
    }

    public void setTimePeriod(int timePeriodId) {
        this.timePeriodId = timePeriodId;
    }

    public void setClassRoomId(int classRoomId) {
        this.classRoomId = classRoomId;
    }

    public int getClassId() {
        return classId;
    }

    public int getCourseId() {
        return courseId;
    }

    public int getProfessorId() {
        return professorId;
    }

    public int getTimePeriodId() {
        return timePeriodId;
    }

    public int getClassRoomId() {
        return classRoomId;
    }

}
