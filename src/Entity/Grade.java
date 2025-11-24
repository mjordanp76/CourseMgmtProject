package Entity;

public class Grade {
    
    private int gradeID;
    private int asgmtID;
    private int accountID;
    private double score;

    public Grade() {
    }

    public void setGradeID(int gradeID) {
        this.gradeID = gradeID;
    }

    public int getGradeID() {
        return gradeID;
    }

    public void setAsgmtID(int asgmtID) {
        this.asgmtID = asgmtID;
    }

    public int getAsgmtID() {
        return asgmtID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getScore() {
        return score;
    }
}
