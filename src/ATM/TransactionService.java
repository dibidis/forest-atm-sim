package ATM;

import java.util.List;

/**
 * Interface for transaction management.
 */

interface TransactionService {

    boolean withinUserTransfer(AccountHolder client, Account fromAccount, Account thisAccount, double amount);

    Transaction betweenUsersTransfer(AccountHolder client, Account thisAccount, AccountHolder receiver, Account toAccount, double amount);

    boolean toNonUserTransfer(AccountHolder client, Account thisAccount, String receiver, double amount);

    void printTransactions();

    boolean withdraw(AccountHolder client, Account account, double amount);

    void deposit(double amount, AccountHolder accountHolder, Account toAccount);

    List<Transaction> getTransactionList();

    void undoTransaction(UndoableTransaction toUndo);

    String getOwnerUsername();

    void addToTransactionList(Transaction thisTransaction);

    void deleteTransaction(Transaction transaction);
}
