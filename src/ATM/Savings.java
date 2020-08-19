package ATM;

public class Savings extends Asset {
    public Savings() {
        super("Savings");
        this.id = "Savings" + numSavingsAccounts;
        numSavingsAccounts++;
    }
}
