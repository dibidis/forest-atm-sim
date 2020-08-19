package ATM;

/**
 * Models transactions for withdrawal.
 */
public class WithdrawalTransaction extends Transaction {

    private AccountHolder sender;
    private Account fromAccount;

    WithdrawalTransaction(double amount, AccountHolder sender, Account fromAccount) {
        super();
        this.sender = sender;
        this.fromAccount = fromAccount;
        this.amount = amount;
        this.id = numTransactions;
    }

    String getSenderUsername() {
        return sender.getUsername();
    }
    Account getFromAccount() {
        return fromAccount;
    }
    String getFromAccountId() {
        return fromAccount.getId();
    }

    @Override
    public String toString() {
        return "Withdrawal: $" + getAmount();
    }

    @Override
    public String getTransactionOwnerName() {
        return this.getSenderUsername();
    }
}
