package ATM;

import java.io.Serializable;
import java.util.*;

/**
 * The primary model class. It contains all data that must be serialized.
 */

class Bank implements Serializable {

    /**
     * Money service of this bank.
     */
    private MoneyService moneyService;

    /**
     * Hash map of username's to their user objects.
     */
    private HashMap<String, User> userHashMap;

    /**
     * Hash map of username's to their transaction services.
     */
    private HashMap<String, TransactionService> transactionServiceHashMap;

    /**
     * List of account requests.
     */
    private List<AccountRequest> newAccountRequests;

    /**
     * Current user's username.
     */
    private String currentUsername;

    /**
     * Last updated month.
     */
    private int lastUpdatedMonth; //from 0 to 11

    /**
     * Last updated year.
     */
    private int lastUpdatedYear;

    /**
     * This banks calendar.
     */
    private Calendar cal = Calendar.getInstance();

    Bank(HashMap<String, User> userHashMap, HashMap<String, TransactionService> thm, MoneyManager mm) {
        this.userHashMap = userHashMap;
        this.transactionServiceHashMap = thm;
        this.moneyService = mm;
        this.newAccountRequests = new ArrayList<>();
    }

    /*
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                                   Setters
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
     */

    /**
     * Set current username to username.
     */
    void setCurrentUser(String username) {
        currentUsername = username;
    }

    /**
     * Set current users password to newPassword.
     */
    public void setPassword(String newPassword) {
        userHashMap.get(currentUsername).setPassword(newPassword);
    }

    /**
     * Set last updated year to year.
     */
    private void setLastUpdatedYear(int year) {
        lastUpdatedYear = year;
    }

    /**
     * Set last updated month to month.
     */
    private void setLastUpdatedMonth(int month) {
        lastUpdatedMonth = month;
    }

    /**
     * Set last updated date to given year and month.
     */
    void setLastUpdatedDate(int year, int month) {
        setLastUpdatedYear(year);
        setLastUpdatedMonth(month);
    }

    /*
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                                   Getters
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
     */

    /**
     * Get current username.
     */
    public String getUsername() {
        return currentUsername;
    }

    /**
     * Get current user's associated account holder object.
     */
    AccountHolder getAccountHolder() {
        return (AccountHolder) userHashMap.get(currentUsername);
    }

    /**
     * Get current user's associated transaction service object.
     */
    TransactionService getTransactionService() {
        return transactionServiceHashMap.get(currentUsername);
    }

    /**
     * Get current user's associated User object.
     */
    public User getUser() {
        return userHashMap.get(currentUsername);
    }

    /**
     * Get current user's password.
     */
    public String getPassword() {
        return userHashMap.get(currentUsername).getPassword();
    }

    /**
     * Get money service of this bank.
     */
    MoneyService getMoneyService() {
        return moneyService;
    }

    /**
     * Get account holder object of username.
     */
    AccountHolder getAccountHolder(String username) {
        return (AccountHolder) userHashMap.get(username);
    }

    /**
     * Get User object of username.
     */
    private User getUser(String username) {
        return userHashMap.get(username);
    }

    /**
     * Get transaction service of username.
     */
    TransactionService getTransactionService(String username) {
        return transactionServiceHashMap.get(username);
    }

    /**
     * Get list of new account requests in this bank.
     */
    List<AccountRequest> getNewAccountRequests() {
        return this.newAccountRequests;
    }

    /**
     * Get list of account holders in this bank.
     */
    List<AccountHolder> getAccountHolders() {
        List<AccountHolder> accountHolders = new ArrayList<>();
        for (User u : userHashMap.values()) {
            if (u instanceof AccountHolder) {
                accountHolders.add((AccountHolder) u);
            }
        }
        return accountHolders;
    }

    /**
     * Get grand leader of this bank.
     */
    GrandLeader getGrandLeader(){
        GrandLeader grandLeader = new GrandLeader("0","0");
        for (User u : userHashMap.values()) {
            if (u instanceof GrandLeader) {
                grandLeader = (GrandLeader) u;
            }
        }
        return grandLeader;
    }

    /**
     * Get this banks last updated month.
     */
    int getLastUpdatedMonth() {
        return lastUpdatedMonth;
    }

    /**
     * Get this banks last updated year.
     */
    int getLastUpdatedYear() {
        return lastUpdatedYear;
    }

    /**
     * Get this banks calendar.
     */
    Calendar getCalendar() {
        return cal;
    }

    /*
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                                   Adders
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
     */

    /**
     * Add user to this bank.
     */
    void addUser(User user) {
        userHashMap.put(user.getUsername(), user);
    }

    /**
     * Add new account request for username of accountType.
     */
    void addAccountRequest(String username, String accountType){
        newAccountRequests.add(new AccountRequest(username, accountType));
    }

    /**
     * Add new joint account request for username1 and username2 of accountType.
     */
    void addAccountRequest(String username1, String username2, String accountType){
        newAccountRequests.add(new AccountRequest(username1, username2, accountType));
    }

    /**
     * Add transactionService to this bank.
     */
    void addTransactionService(TransactionService transactionService) {
        this.transactionServiceHashMap.put(transactionService.getOwnerUsername(), transactionService);
    }

    /**
     * Add account to username's account list.
     */
    void addAccount(String username, Account account){
        getAccountHolder(username).addAccount(account);
    }

    /**
     * Add account to username1 and username2's account list.
     */
    void addAccount(String username1, String username2, Account account){
        account.setJoint(true);
        addAccount(username1, account);
        addAccount(username2, account);
    }

    /**
     * Add thisTransaction to receiverUsername's transaction list.
     */
    void addTransaction(String receiverUsername, Transaction thisTransaction) {
        transactionServiceHashMap.get(receiverUsername).addToTransactionList(thisTransaction);
    }


    /*
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                                   Checkers
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
     */

    /**
     * Return if username has password.
     */
    boolean isValidPassword(String username, String password) {
        return getUser(username).getPassword().equals(password);
    }

    /**
     * Return if bank has user.
     */
    boolean hasUser(String username) {
        return userHashMap.containsKey(username);
    }

    /**
     * Return if bank has account holder.
     */
    boolean hasAccountHolder(String username) {
        return !userHashMap.containsKey(username) || (!(userHashMap.get(username) instanceof AccountHolder));
    }

    /**
     * Return if this username is an account holder.
     */
    private boolean hasAccounts(String username) {
        return userHashMap.get(username) instanceof AccountHolder;
    }


    /*
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                                   Helpers
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
     */

    /**
     * Removes this user.
     */
    void removeUser(String username) {
        userHashMap.remove(username);
        transactionServiceHashMap.remove(username);
        List<AccountRequest> accountRequestsToRemove = new ArrayList<>();
        for (AccountRequest a : newAccountRequests) {
            if (a.getUsername().equals(username)) {
                accountRequestsToRemove.add(a);
            }
        }
        for (AccountRequest a : accountRequestsToRemove) {
            newAccountRequests.remove(a);
        }
    }

    /**
     * Adjusts all savings in this bank.
     */
    void adjustAllSavings() {
        for (String username : userHashMap.keySet()) {
            if (hasAccounts(username)) {
                ((AccountHolder) userHashMap.get(username)).adjustSavingsAccounts();
            }
        }
    }

    void removeTransaction(String currentUsername, UndoableTransaction transaction) {
        transactionServiceHashMap.get(currentUsername).deleteTransaction(transaction);
    }
}