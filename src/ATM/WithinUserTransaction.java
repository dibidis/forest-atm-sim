package ATM;

/**
 * Models transactions between one users different accounts.
 */
public class WithinUserTransaction extends UndoableTransaction{

    WithinUserTransaction(AccountHolder senderAndReceiver, Account fromAccount, Account toAccount, double amount){
        super();
        this.sender = senderAndReceiver;
        this.fromAccount = fromAccount;
        this.receiver = senderAndReceiver;
        this.toAccount = toAccount;
        this.amount = amount;
        this.id = numTransactions;
    }

    @Override
    public String toString() {
        return "Between Account Transfer: $" + getAmount() + " transferred from "
                + getFromAccountId() + " to " + getToAccountId();
    }
}
