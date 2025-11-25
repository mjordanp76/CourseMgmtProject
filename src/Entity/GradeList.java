package Entity;

public class GradeList {

    private int studentID;
    private String studentName; // fname + lname from Account

    // These map to specific assignments in the section
    private Double assignment1;
    private Double midterm;
    private Double assignment2;
    private Double finalExam;

    public GradeList(int studentID, String studentName) {
        this.studentID = studentID;
        this.studentName = studentName;
    }

    // getters & setters required by PropertyValueFactory

    public int getStudentID() {
        return studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public Double getAsgmt1() {
        return assignment1;
    }

    public void setAsgmt1(Double assignment1) {
        this.assignment1 = assignment1;
    }

    public Double getMidterm() {
        return midterm;
    }

    public void setMidterm(Double midterm) {
        this.midterm = midterm;
    }

    public Double getAsgmt2() {
        return assignment2;
    }

    public void setAsgmt2(Double assignment2) {
        this.assignment2 = assignment2;
    }

    public Double getFinal() {
        return finalExam;
    }

    public void setFinal(Double finalExam) {
        this.finalExam = finalExam;
    }

    public double getTotal() {
        double sum = 0;
        int count = 0;

        if (assignment1 != null) { sum += assignment1; count++; }
        if (midterm != null)    { sum += midterm; count++; }
        if (assignment2 != null) { sum += assignment2; count++; }
        if (finalExam != null)  { sum += finalExam; count++; }

        if (count == 0) return 0;

        double average = sum / count;

        return Math.round(average * 100.0) / 100.0;
    }
}
