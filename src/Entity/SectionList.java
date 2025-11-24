package Entity;

import java.util.ArrayList;

public class SectionList {
    
    private ArrayList<Section> sections;

    public SectionList() {
        sections = new ArrayList<>();
    }

    public void add(Section s) {
        sections.add(s);
    }

    public Section getSectionsByID(int id) {
        for (Section s : sections) {
            if (s.getSectionID() == id) {
                return s;
            }
        }
        return null;
    }

    public ArrayList<Section> getAll() {
        return sections;
    }

    public int size() {
        return sections.size();
    }
}
