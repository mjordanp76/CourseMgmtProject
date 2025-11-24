package Entity;

import java.util.ArrayList;

public class CourseList {
    
    private ArrayList<Course> courses;

    public CourseList() {
        courses = new ArrayList<>();
    }

    public void add(Course c) {
        courses.add(c);
    }

    public Course getCoursesByID(int id) {
        for (Course c : courses) {
            if (c.getCourseID() == id) {
                return c;
            }
        }
        return null;
    }

    public ArrayList<Course> getAll() {
        return courses;
    }

    public int size() {
        return courses.size();
    }
}
