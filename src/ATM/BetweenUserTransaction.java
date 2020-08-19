package ATM;

/**
 * Models transactions between AccountHolders within our bank.
 */

public class BetweenUserTransaction extends UndoableTransaction {

    BetweenUserTransaction(AccountHolder sender, Account fromAccount, AccountHolder receiver, Account toAccount, double amount){
        super();
        this.sender = sender;
        this.fromAccount = fromAccount;
        this.receiver = receiver;
        this.toAccount = toAccount;
        this.amount = amount;
        this.id = numTransactions;
    }

    @Override
    public String toString() {
        return "Between Client Transfer: $" + getAmount() + " transferred from "
                + getFromAccountId() + " to " + getReceiverUsername();
    }
}
