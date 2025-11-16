package Entity;

public class Section {
    
    private int sectionID;
    private int courseID;
    private String sectionLetter;
    private String time;
    private String location;
    private String status;

    public Section(int sectionID, int courseID, String sectionLetter,  String time, String location, String status) {
        this.sectionID = sectionID;
        this.courseID = courseID;
        this.sectionLetter = sectionLetter;
        this.time = time;
        this.location = location;
        this.status = status;
    }

    public int getSectionID() {
        return sectionID;
    }

    public int getCourseID() {
        return courseID;
    }

    public String getSectionLetter() {
        return sectionLetter;
    }

    public String getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    public String getStatus() {
        return status;
    }
}
