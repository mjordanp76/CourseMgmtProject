package Entity;

import java.util.ArrayList;

public class GradeList {
    
    private ArrayList<Grade> grades;

    public GradeList() {
        grades = new ArrayList<>();
    }

    public void add(Grade g) {
        grades.add(g);
    }

    public Grade getgradesByID(int id) {
        for (Grade g : grades) {
            if (g.getGradeID() == id) {
                return g;
            }
        }
        return null;
    }

    public ArrayList<Grade> getAll() {
        return grades;
    }

    public int size() {
        return grades.size();
    }
}
