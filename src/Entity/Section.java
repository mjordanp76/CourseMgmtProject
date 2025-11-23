package Entity;

public class Section {

    private int sectionID;
    private int courseID;
    private int profID;
    private int courseNum;
    private String courseName;
    private String sectionLetter;
    private String time;
    private String location;
    private String status;

    public Section() {
    }
    
    public void setSectionID(int sectionID) {
        this.sectionID = sectionID;
    }

    public int getSectionID() {
        return sectionID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setProfID(int profID) {
        this.profID = profID;
    }

    public int getProfID() {
        return profID;
    }

    public void setCourseNum(int courseNum) {
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

    public void setSectionLetter(String sectionLetter) {
        this.sectionLetter = sectionLetter;
    }

    public String getSectionLetter() {
        return sectionLetter;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
