package Entity;

public class Course {
    private String courseID;
    private String courseName;
    private String courseTime;
    private String courseLocation;

    public Course(String courseID, String courseName, String courseTime, String courseLocation) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.courseTime = courseTime;
        this.courseLocation = courseLocation;
    }

    public String getCourseID() {
        return courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public String getCourseLocation() {
        return courseLocation;
    }
}
