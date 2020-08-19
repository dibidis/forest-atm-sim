package ATM;

public abstract class UndoableTransaction extends Transaction {
    AccountHolder sender, receiver;
    Account fromAccount, toAccount;

    String getSenderUsername() {
        return sender.getUsername();
    }

    String getReceiverUsername() {
        return receiver.getUsername();
    }

    Account getFromAccount() {
        return fromAccount;
    }

    String getFromAccountId() {
        return fromAccount.getId();
    }

    Account getToAccount() {
        return toAccount;
    }

    String getToAccountId() {
        return toAccount.getId();
    }

    public String getTransactionOwner(){ return getReceiverUsername(); }

    @Override
    public String getTransactionOwnerName() {
        return this.getSenderUsername();
    }
}
