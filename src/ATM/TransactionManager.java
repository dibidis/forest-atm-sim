package ATM;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/*
TransactionManager class keeps track of all the transactions made by a client.
 */

class TransactionManager implements TransactionService, Serializable {

    private String ownerUsername;
    private List<Transaction> transactionList;


    TransactionManager(String ownerUsername) {
        this.ownerUsername = ownerUsername;
        this.transactionList = new ArrayList<>();
    }


    /**
     * SETTERS & GETTERS
     */

    public List<Transaction> getTransactionList() {
        return this.transactionList;
    }

    public void addToTransactionList(Transaction t){
        this.transactionList.add(t);
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    /**
     * Transfer from one account to another of Account Holder
     */
    public boolean withinUserTransfer(AccountHolder client, Account fromAccount, Account toAccount, double amount) {
        if (fromAccount.sufficientFundsToTransfer(amount)) {
            Transaction t = new WithinUserTransaction(client, fromAccount, toAccount, amount);
            fromAccount.decrease(amount);
            toAccount.increase(amount);
            addToTransactionList(t);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Transfer from one AccountHolder's account to another's account.
     */
    public Transaction betweenUsersTransfer(AccountHolder client, Account fromAccount,
                                            AccountHolder receiver, Account toAccount, double amount) {
        if (fromAccount.sufficientFundsToTransfer(amount)) {
            Transaction t = new BetweenUserTransaction(client, fromAccount, receiver, toAccount, amount);
            fromAccount.decrease(amount);
            toAccount.increase(amount);
            addToTransactionList(t);
            return t;
        } else {
            return null;
        }
    }

    /**
     * Transfer to someone who does not have an account.
     */
    public boolean toNonUserTransfer(AccountHolder client, Account fromAccount, String receiver, double amount) {
        IOManager ioManager = new IOManager();
        if (fromAccount.sufficientFundsToTransfer(amount)) {
            Transaction t = new ToNonUserTransaction(client, fromAccount, receiver, amount);
            fromAccount.decrease(amount);
            addToTransactionList(t);
            ioManager.sendOutgoing(client.toString(), receiver, amount);
            return true;
        } else { return false;
        }
    }

    /**
     * Make a deposit to an account of Account Holder.
     */
    public void deposit(double amount, AccountHolder receiver, Account toAccount){
        Transaction t = new DepositTransaction(receiver, toAccount, amount);
        addToTransactionList(t);
        toAccount.increase(amount);
        if (amount != 0){
        System.out.println("\nDeposited $" + amount + " to "+ toAccount.toString());
        }
    }

    /**
     * Withdraw money from an account of Account Holder.
     */
    public boolean withdraw(AccountHolder sender, Account fromAccount, double amount) {
        if (fromAccount.sufficientFundsToTransfer(amount)){
            Transaction t = new WithdrawalTransaction(amount, sender, fromAccount);
            addToTransactionList(t);
            fromAccount.decrease(amount);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Undo the given transaction.
     */

    public void undoTransaction(UndoableTransaction transaction) {
        Account fromAccount = transaction.getFromAccount();
        Account toAccount = transaction.getToAccount();

        double amount = transaction.getAmount();

        fromAccount.increase(amount);
        toAccount.decrease(amount);
        deleteTransaction(transaction);
    }

    public void deleteTransaction(Transaction latestTransaction) {
        int latestTransactionIndex = 0;
        for (int i = 0; i < transactionList.size(); i++) {
            if (transactionList.get(i).equals(latestTransaction)) {
                latestTransactionIndex = i;
            }
        }
        transactionList.remove(latestTransactionIndex);
    }

    /**
     * Print all the transactions contained in this Manager
     */
    public void printTransactions() {
        for (Transaction t : transactionList){
            System.out.println(t);
        }
    }
}