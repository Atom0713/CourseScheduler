package root;

public class Course {
    private final int courseId;
    private final String courseCode;
    private final String courseName;
    private final int courseCapacity;
    private final int courseHours;
    private final int professorId;
    private final String[] courseFaculty;

    public Course(int courseId, String courseCode, String courseName, int courseCapacity, int courseHours, int professorId, String[] courseFaculty) {
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.courseCapacity = courseCapacity;
        this.courseHours = courseHours;
        this.professorId = professorId;
        this.courseFaculty = courseFaculty;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCourseCapacity() {
        return courseCapacity;
    }

    public int getCourseHours() {
        return courseHours;
    }

    public int getProfessorId() {
        return professorId;
    }

    public String[] getCourseFaculty() {
        return courseFaculty;
    }

    public static int getCourseYearByCode(String courseCode){
        courseCode=courseCode.replaceAll("\\s","");
        for (int i = 0; i < courseCode.length(); i++) {
            if(courseCode.charAt(i)<58){
                return (int)(courseCode.charAt(i)-48);
            }
        }
        return -1;
    }

    public static boolean isFacDepYearClashing(int yearA, String[] facDepIdsA, int yearB, String[] facDepIdsB){
        if(yearA==yearB){
            for (int i = 0; i < facDepIdsA.length; i++) {
                for (int j = 0; j < facDepIdsB.length; j++) {
                    if(facDepIdsA[i].length()>facDepIdsB[j].length()){
                        if(facDepIdsA[i].startsWith(facDepIdsB[j]))
                            return true;
                    }
                    else{
                        if(facDepIdsB[j].startsWith(facDepIdsA[i]))
                            return true;
                    }
                }
            }
        }
        return false;
    }
}
