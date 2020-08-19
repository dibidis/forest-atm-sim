package ATM;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;

/**
 * An abstract superclass to Asset and Debt accounts and their children
 */

public abstract class Account implements Serializable {
    private String creationDate;
    private String type;
    private boolean isJoint = false;

    static int numCreditCardAccounts, numLineOfCreditAccounts, numChequingAccounts,
            numSavingsAccounts, numTreeAccounts = 0;
    String id;
    double balance;

    public Account(String type){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss");
        Date date = new Date();
        this.creationDate = dateFormat.format(date);
        this.balance = 0;
        this.type = type;
    }
    public Account(String type, boolean isJoint){
        this(type);
        this.isJoint = isJoint;
    }

    public String getType(){
        return type;
    }

    public void setBalance(double balance){ this.balance = balance; }
    double getBalance(){return this.balance;}

    public void increase(double amount){
        this.balance += amount;
    } //Increase balance by the amount specified
    public void decrease(double amount){
        this.balance -= amount;
    } //Decrease balance by the amount specified

    public void setId(String id){ this.id = id; }
    public String getId(){ return this.id; }

    String getCreationDate(){
        return this.creationDate;
    }

    boolean isJoint() { return this.isJoint; }

    void setJoint(boolean isJoint) {this.isJoint = isJoint; }

    public boolean sufficientFundsToTransfer(double amount){ return (this.balance >= amount);}

    void printAccountWithOptionNumber(int optionNumber){
        String jointString = "";
        if (this.isJoint()) jointString = " (Joint)";
        System.out.println("(" + optionNumber + ") " + this.getId() + jointString +
                " - Balance: $" + this.getBalance());
    }

    public String toString(){
        return this.id;
    }
}
