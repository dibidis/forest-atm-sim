package ATM;

import java.io.Serializable;

/**
 * Bundles the data required for account approval by bank employees.
 */

public class AccountRequest implements Serializable {

    private String username, accountType;
    private String username2;

    AccountRequest(String username, String accountType){
        this.username = username;
        this.accountType = accountType;
        this.username2 = "";
    }
    AccountRequest(String username1, String username2, String accountType){
        this(username1, accountType);
        this.username2 = username2;
    }

    public String getUsername() {
        return username;
    }

    String getUsername2() {return username2;}

    boolean isJointAccountRequest() {
        String secondUser = this.getUsername2();
        return !secondUser.equals("");
    }

    public String[] getUsernames(){return new String[]{username, username2};}

    String getAccountType(){
        return accountType;
    }

    @Override
    public String toString() {
        if (username2.equals("")){
            return username + " " + accountType;
        }
        else{
            return username + " " + username2 + " " + accountType;
        }
    }
}
