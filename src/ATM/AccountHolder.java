package ATM;

import java.util.List;

/**
 * The interface for Users that have accounts.
 */

public interface AccountHolder {

    Account getPrimaryAccount();

    TreeAccount getTreeAccount();

    String getUsername();

    List<Account> getAccountList();

    List<Account> getAccountList(String type);

    double calculateNetTotal();

    boolean hasAccount(String fromAccountId);

    Account findAccount(String fromAccountId);

    void adjustSavingsAccounts();

    void addAccount(Account newAccount);

    String getPassword();

    void setPassword(String newPassword);
}
