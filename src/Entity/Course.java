package Entity;

public class Course {

    private int courseID;
    private int courseNum;
    private String courseName;
    private String dept;
    private int prereq;

    public Course() {
    }
    
    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseNum(int courseNum)  {
        this.courseNum = courseNum;
    }
    
    public int getCourseNum() {
        return courseNum;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseName() {
        return courseName;
    }
    
    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getDept() {
        return dept;
    }

    public void setPrereq(int prereq) {
        this.prereq = prereq;
    }

    public int getPrereq() {
        return prereq;
    }
    
    public String getDeptAndName() {
        return dept + " " + courseNum;
    }
}