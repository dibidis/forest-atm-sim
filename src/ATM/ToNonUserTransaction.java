package ATM;

/**
 * Models transactions to non users.
 */

public class ToNonUserTransaction extends Transaction{
    private String nonUserReceiver;
    private AccountHolder sender;
    private Account fromAccount;

    ToNonUserTransaction(AccountHolder sender, Account fromAccount, String nonUserReceiver, double amount){
        super();
        this.sender = sender;
        this.fromAccount = fromAccount;
        this.nonUserReceiver = nonUserReceiver;
        this.amount = amount;
        this.id = numTransactions;
    }

    private String getNonUserReceiver() {
        return this.nonUserReceiver;
    }
    private String getSenderUsername() {
        return sender.getUsername();
    }
    Account getFromAccount() {
        return fromAccount;
    }
    private String getFromAccountId() {
        return fromAccount.getId();
    }

    @Override
    public String toString() {
        return "ToNonUserTransfer: $" + getAmount() + " transferred from " + getFromAccountId()
                + " to " + getNonUserReceiver();
    }

    @Override
    public String getTransactionOwnerName() {
        return this.getSenderUsername();
    }
}
