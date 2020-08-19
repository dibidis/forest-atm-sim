package ATM;

import java.util.*;
import java.util.concurrent.Callable;

/**
 * Models a client in the bank.
 */

public class Client extends User implements AccountHolder, Comparable<Client> {
    private List<Account> accountList;
    private Chequing primaryAccount;

    public Client(String username, String password) {
        super(username, password);
        this.accountList = new ArrayList<>();
        primaryAccount = new Chequing();
        accountList.add(primaryAccount);
        accountList.add(new TreeAccount());
    }

    /*
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                               Getters
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
     */

    /**
     * Get this Client model's primary account.
     */
    public Account getPrimaryAccount() {
        return primaryAccount;
    }
    /**
     * Get this Client model's tree account.
     */
    public TreeAccount getTreeAccount(){
        for (Account a : accountList) {
            if (a instanceof TreeAccount) {
                return (TreeAccount) a;
            }
        }
        return new TreeAccount();
    }

    /**
     * Get this Client model's list of accounts.
     */
    public List<Account> getAccountList() {
        return this.accountList;
    }

    /**
     * Get this Client model's list of accounts by type.
     */
    public List<Account> getAccountList(String type) {
        Map<String, Callable<List<Account>>> typeMap = new LinkedHashMap<>();
        typeMap.put("Savings", this::getSavingsList);
        typeMap.put("Chequing", this::getChequingList);
        typeMap.put("TreeAccount", this::getTreeAccountList);
        typeMap.put("CreditCard", this::getCreditCardList);
        typeMap.put("LineOfCredit", this::getLineOfCreditList);
        typeMap.put("Asset", this::getAssetList);
        typeMap.put("Debt", this::getDebtList);
        List<Account> accounts = null;
        try {
            accounts = typeMap.get(type).call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accounts;
    }

    /**
     * Get this Client model's list of Savings accounts.
     */
    private List<Account> getSavingsList() {
        List<Account> SavingsList = new ArrayList<>();
        for (Account currAccount : accountList) {
            if (currAccount instanceof Savings) {
                SavingsList.add(currAccount);
            }
        }
        return SavingsList;
    }

    /**
     * Get this Client model's list of Chequing accounts.
     */
    private List<Account> getChequingList() {
        List<Account> ChequingList = new ArrayList<>();
        for (Account currAccount : accountList) {
            if (currAccount instanceof Chequing) {
                ChequingList.add(currAccount);
            }
        }
        return ChequingList;
    }

    /**
     * Get this Client model's list of Tree accounts.
     */
    private List<Account> getTreeAccountList() {
        List<Account> TreeAccountList = new ArrayList<>();
        for (Account currAccount : accountList) {
            if (currAccount instanceof TreeAccount) {
                TreeAccountList.add(currAccount);
            }
        }
        return TreeAccountList;
    }

    /**
     * Get this Client model's list of Line of Credit accounts.
     */
    private List<Account> getLineOfCreditList() {
        List<Account> LineOfCreditList = new ArrayList<>();
        for (Account currAccount : accountList) {
            if (currAccount instanceof LineOfCredit) {
                LineOfCreditList.add(currAccount);
            }
        }
        return LineOfCreditList;
    }

    /**
     * Get this Client model's list of Credit Card accounts.
     */
    private List<Account> getCreditCardList() {
        List<Account> CreditCardList = new ArrayList<>();
        for (Account currAccount : accountList) {
            if (currAccount instanceof CreditCard) {
                CreditCardList.add(currAccount);
            }
        }
        return CreditCardList;
    }

    /**
     * Get this Client model's list of Asset accounts.
     */
    private List<Account> getAssetList() {
        List<Account> AssetList = new ArrayList<>(getChequingList());
        AssetList.addAll(getSavingsList());
        AssetList.addAll(getTreeAccountList());
        return AssetList;
    }

    /**
     * Get this Client model's list of Debt accounts.
     */
    private List<Account> getDebtList() {
        List<Account> debtList = new ArrayList<>(getCreditCardList());
        debtList.addAll(getLineOfCreditList());
        return debtList;
    }


    /*
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                           Calculate net total methods
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
     */

    /**
     * Calculate net total of all of this models accounts.
     */
    public double calculateNetTotal() {
        return calculateTotalAssets() - calculateTotalDebt();
    }

    /**
     * Calculate net total of this models Debt accounts.
     */
    private double calculateTotalDebt() {
        double totalDebt = 0;

        for (Account currDebtAccount : getDebtList()) {
            totalDebt = totalDebt + currDebtAccount.getBalance();
        }
        return totalDebt;
    }

    /**
     * Calculate net total of this models Asset accounts.
     */
    private double calculateTotalAssets() {
        double totalAssets = 0;

        for (Account currDebtAccount : getAssetList()) {
            totalAssets = totalAssets + currDebtAccount.getBalance();
        }
        return totalAssets;
    }

    /*
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                                     Helpers
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
     */

    /**
     * Adjust all the savings accounts for this client model.
     */
    public void adjustSavingsAccounts() {
        double amount = 0.001;
        for (Account currAccount : accountList) {
            if (currAccount instanceof Savings) {
                if (currAccount.isJoint()){
                    currAccount.balance *= java.lang.Math.sqrt(1 + amount);
                }
                else {
                    currAccount.balance *= 1 + amount;
                }
            }
        }
    }

    /**
     * Find an account with accountId.
     */
    public Account findAccount(String accountId) {
        for (Account account : accountList) {
            if (account.getId().equals(accountId)) {
                return account;
            }
        }
        return getPrimaryAccount();
    }

    /**
     * Return if this client model has an account with accountId.
     */
    public boolean hasAccount(String accountId) {
        for (Account account : accountList) {
            if (accountId.equals(account.getId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Compare two client models based on the number of their trees.
     */
    public int compareTo(Client c) {
        return getTreeAccount().getTreesPlanted().compareTo(c.getTreeAccount().getTreesPlanted());
    }

    /**
     * Add a new account to this client model's list of accounts.
     */
    public void addAccount(Account newAccount) {
        accountList.add(newAccount);
    }
}