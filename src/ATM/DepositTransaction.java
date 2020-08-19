package ATM;

/**
 * Models transactions for deposits.
 */

public class DepositTransaction extends Transaction {
    private AccountHolder receiver;
    private Account fromAccount;

    DepositTransaction(AccountHolder receiver, Account fromAccount, double amount){
        super();
        this.receiver = receiver;
        this.fromAccount = fromAccount;
        this.amount = amount;
        this.id = numTransactions;
    }

    private String getReceiverUsername() {
        return receiver.getUsername();
    }

    @Override
    public String toString() {
        return "Deposit: $" + getAmount();
    }

    @Override
    public String getTransactionOwnerName() {
        return this.getReceiverUsername();
    }
}
