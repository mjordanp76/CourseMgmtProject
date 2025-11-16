package Entity;

public class GradeList {

    private int gradeID;
    private int asgmtID;
    private int accountID;
    private double score;

    public GradeList(int gradeID, int asgmtID, int accountID, double score) {
        this.gradeID = gradeID;
        this.asgmtID = asgmtID;
        this.accountID = accountID;
        this.score = score;
    }

    public int getGradeID() {
        return gradeID;
    }

    public int getAsgmtID() {
        return asgmtID;
    }

    public int getAccoundID() {
        return accountID;
    }

    public double getScore() {
        return score;
    }
    
}
