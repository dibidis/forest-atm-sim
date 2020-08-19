package ATM;

/**
 * An asset account from which the account holder may withdraw and make transfers.
 * One account is the primary chequing account to which deposits are made.
 */

public class Chequing extends Asset {

    // initial set up
    public Chequing(){
        super("Chequing");
        this.id = "Chequing" + numChequingAccounts;
        numChequingAccounts++;
    }

    @Override
    public boolean sufficientFundsToTransfer(double amount){
        return ((this.balance >= 0) && ((this.balance - amount) >= -100));
    }


}
