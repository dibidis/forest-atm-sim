package ATM;

/**
 * Models transactions for funding the revolution for the grand leader.
 */

public class FundRevolutionTransaction extends Transaction {

    private GrandLeader grandLeader;

    FundRevolutionTransaction(double amount, GrandLeader grandLeader){
        this.amount = amount;
        this.grandLeader = grandLeader;
    }

    @Override
    public String getTransactionOwnerName() {
        return grandLeader.getUsername();
    }

    @Override
    public String toString() {
        return "Revolution Funded: $" + getAmount();
    }
}
