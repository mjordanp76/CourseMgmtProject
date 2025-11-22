package Entity;

public class Account {
    
    private int accountID;
    private String usn;
    private String fName;
    private String lName;
    private String dept;
    private String role;
    private String pswd;

    public Account() {
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setUsn(String usn) {
        this.usn = usn;
    }

    public String getUsn() {
        return usn;
    }

    public void setFirstName(String fName) {
        this.fName = fName;
    }

    public String getFirstName() {
        return fName;
    }

    public void setLastName(String lName) {
        this.lName = lName;
    }

    public String getLastName() {
        return lName;
    }

    public String getFullName() {
        return fName + " " + lName;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getDept() {
        return dept;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setPwdHash(String pswd) {
        this.pswd = pswd;
    }

    public String getPwdHash() {
        return pswd;
    }

    public void display() {
        System.out.println("Account ID: " + accountID);
        System.out.println("Email: " + usn);
        System.out.println("First name: " + fName);
        System.out.println("Last name: " + lName);
        System.out.println("Department: " + dept);
        System.out.println("Role: " + role);
    }
}
