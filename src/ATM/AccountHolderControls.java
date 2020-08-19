package ATM;

import java.util.*;

/**
 * Contains the control methods associated with the AccountHolder Interface.
 */

class AccountHolderControls extends UserUI {

    AccountHolderControls(Bank bank) { super(bank); }

    /**
     * Option map for classes that implement AccountHolder Interface.
     */
    @Override
    Map<String, Runnable> loadOptions(Map<String, Runnable> optionMap){
        optionMap.put("See your forest", this::displayTreeAccountForest);
        optionMap.put("Witness the forest exemplars", this::displayForestLeaders);
        optionMap.put("Make a deposit", this::depositMoney);
        optionMap.put("View accounts", this::displayAccounts);
        optionMap.put("Request new account", this::requestNewAccount);
        return optionMap;
    }

    /*
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                           Request New Accounts Methods
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
     */

    /**
     * Control logic for requesting new account; select type.
     */
    private void requestNewAccount(){
        System.out.println("\nWhat type of account would you like?");

        ArrayList<String> optionTypes = new ArrayList<>(Arrays.asList("Chequing", "Savings", "Tree Account",
                "Credit Card", "Line of Credit", "Go back"));

        int selection = printOptionsAndGetSelection(optionTypes);

        if (selection == optionTypes.size()-1) {
            return;
        }

        requestIndividualOrJointAccount(optionTypes.get(selection));
    }

    /**
     * Control logic for requesting new account; select type; select individual or joint.
     */
    private void requestIndividualOrJointAccount(String type){
        System.out.println("\nWould you like an individual or joint account?");
        ArrayList<String> optionTypes = new ArrayList<>(Arrays.asList("Individual", "Joint", "Go back"));

        int selection = printOptionsAndGetSelection(optionTypes);

        // Go back
        if (selection == optionTypes.size()-1) {
            return;
        }

        if (optionTypes.get(selection).equals("Individual")){
            requestAccountCreation(type);
            System.out.println("\nYour account request has been received. Expect a response in 1-3 business days.");
        }
        else if (optionTypes.get(selection).equals("Joint")){
            String username = getUsername("Enter username of other user for joint account.");
            requestAccountCreation(type, username);
            System.out.println("\nYour account request has been received. Expect a response in 1-3 business days.");
        }
    }

    /**
     * Add individual account request to the bank linked to this Control.
     */
    private void requestAccountCreation(String accountType){
        bank.addAccountRequest(bank.getUsername(), accountType);
    }

    /**
     * Add joint account request to the bank linked to this Control.
     */
    private void requestAccountCreation(String accountType, String username2){
        bank.addAccountRequest(bank.getUsername(), username2, accountType);
    }

    /*
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                               Display Accounts Methods
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
     */

    /**
     * Control logic for viewing accounts.
     */
    private void displayAccounts() {

        Map<String, Runnable> optionMap = new LinkedHashMap<>();
        optionMap.put("View asset accounts", this::viewAssetAccounts);
        optionMap.put("View debt accounts", this::viewDebtAccounts);
        optionMap.put("View transactions", this::viewTransactions);
        optionMap.put("Go back", this::goBack);

        ArrayList<String> options = new ArrayList<>(optionMap.keySet());

        while(running) {
            printDashes();
            System.out.println("Net total: $" + (bank.getAccountHolder()).calculateNetTotal() + "\n");
            int selection = printOptionsAndGetSelection(options);
            optionMap.get(options.get(selection)).run();
        }
        running = true;
    }

    /**
     * Control logic for viewing a particular kind of accounts.
     */
    private int viewAccountsByType(String type, int inc) {
        AccountHolder client = bank.getAccountHolder();

        System.out.println("\n" + type + " Accounts:");
        switch (type) {
            case "Asset":
                if (client.getAccountList(("Chequing")).size() != 0)
                    inc = viewAccountsByType("Chequing", inc);
                if (client.getAccountList(("Savings")).size() != 0)
                    inc = viewAccountsByType("Savings", inc);
                if (client.getAccountList(("TreeAccount")).size() != 0)
                    inc = viewAccountsByType("TreeAccount", inc);
                break;
            case "Debt":
                if (client.getAccountList(("CreditCard")).size() != 0)
                    inc = viewAccountsByType("CreditCard", inc);
                if (client.getAccountList(("LineOfCredit")).size() != 0)
                    inc = viewAccountsByType("LineOfCredit", inc);
                break;
            default:
                for (Account a : client.getAccountList(type)) {
                    a.printAccountWithOptionNumber(inc);
                    inc++;
                }
                break;
        }
        return inc;
    }

    /**
     * Control logic for viewing Asset accounts.
     */
    private void viewAssetAccounts() {
        AccountHolder client = bank.getAccountHolder();
        printDashes();
        int inc = viewAccountsByType("Asset", 1);
        List<Account> options = new ArrayList<>(client.getAccountList("Chequing"));
        options.addAll(client.getAccountList("Savings"));
        options.addAll(client.getAccountList("TreeAccount"));

        viewAssetOrDebtAccountsHelper(options, inc);
    }

    /**
     * Control logic for viewing Debt accounts.
     */
    private void viewDebtAccounts() {
        AccountHolder client = bank.getAccountHolder();
        printDashes();
        int inc = viewAccountsByType("Debt", 1);
        List<Account> options = new ArrayList<>(client.getAccountList("CreditCard"));
        options.addAll(client.getAccountList("LineOfCredit"));

        viewAssetOrDebtAccountsHelper(options, inc);
    }

    /**
     * Account selection helper.
     */
    private void viewAssetOrDebtAccountsHelper(List<Account> options, int inc){
        System.out.println("(0) Go back");

        int selection = promptForInt();
        while (!(selection >= 0 && selection < inc)) {
            selection = rePromptForInt();
        }

        if (selection != 0) {
            Account choice = options.get(selection - 1);
            displayAccount(choice);
        }
    }

    /**
     * Control logic for viewing options within a specific account.
     */
    private void displayAccount(Account account) {
        Map<String, Runnable> optionMap = new LinkedHashMap<>();

        if (account instanceof Asset) optionMap.put("Make a withdrawal", () -> withdrawMoney((Asset) account));
        if (!(account instanceof CreditCard)) optionMap.put("Make a transfer", () -> displayTransferOut(account));
        optionMap.put("Go back", this::goBack);

        ArrayList<String> options = new ArrayList<>(optionMap.keySet());

        while (running) {
            printDashes();
            System.out.println("\nAccount: " + account);
            System.out.println("Balance: $" + account.getBalance());

            printPleaseSelectOption();
            int selection = printOptionsAndGetSelection(options);
            optionMap.get(options.get(selection)).run();
        }
        running = true;
    }


    /*
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                               Transaction Methods
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
     */

    /**
     * Control logic for viewing transactions.
     */
    private void viewTransactions() {
        printDashes();

        System.out.println("\nTransactions: ");
        bank.getTransactionService().printTransactions();

        System.out.println("\n(0) Go back");
        int selection = promptForInt();
        while (selection != 0) {
            selection = rePromptForInt();
        }
    }

    /**
     * Control logic for displaying transfer options.
     */
    private void displayTransferOut(Account account){
        Map<String, Runnable> optionMap = new LinkedHashMap<>();

        optionMap.put("Transfer money to another of your accounts", () -> betweenAccountTransfer(account));
        optionMap.put("Transfer money to another of our bank members", () -> betweenUserTransfer(account));
        optionMap.put("Transfer money to someone not with our bank", () -> nonUserTransfer(account));
        optionMap.put("Go back", this::goBack);

        ArrayList<String> options = new ArrayList<>(optionMap.keySet());

        while (running) {
            printDashes();
            int selection = printOptionsAndGetSelection(options);
            optionMap.get(options.get(selection)).run();
        }
        running = true;
    }

    /**
     * Control logic for transfer between two accounts of the same user.
     */
    private void betweenAccountTransfer(Account account) {
        System.out.print("Select the account you'd like to transfer to: \n");
        Account toAccount = accountPicker();

        if (toAccount == null) {
            return;
        }

        double amount = InputUtility.getPositiveDollarInput("How much would you like to transfer out?");

        boolean successfulTransfer = bank.getTransactionService().withinUserTransfer(
                bank.getAccountHolder(), account, toAccount, amount);

        if (successfulTransfer) {
            payGrandMasterTreeFee(account);
            System.out.println("Successfully transferred $" + amount);

        } else {
            System.out.println("Insufficient funds.");
        }
    }

    /**
     * Control logic for transfer between two different users.
     */
    private void betweenUserTransfer(Account account) {
        String username = getUsername("Enter username of other user to transfer to.");

        if (username.equals("0")) {
            return;
        }

        AccountHolder receiver = bank.getAccountHolder(username);
        Account toAccount = receiver.getPrimaryAccount();

        double amount = InputUtility.getPositiveDollarInput("How much would you like to transfer out?");

        Transaction thisTransaction = bank.getTransactionService().betweenUsersTransfer(
                bank.getAccountHolder(), account, receiver, toAccount, amount);

        boolean successfulTransfer = (thisTransaction != null);

        if (successfulTransfer){
            payGrandMasterTreeFee(account);
            bank.addTransaction(receiver.getUsername(), thisTransaction);
            System.out.println("Successfully transferred $" + amount);
        }
        else {
            System.out.println("Transfer failed. Insufficient funds.");
        }
    }

    /**
     * Control logic for transfer to a non-user.
     */
    private void nonUserTransfer(Account account) {
        System.out.println("Enter name of receiver.");
        System.out.println("Enter here: ");
        String receiver = userInput.nextLine();

        double amount = InputUtility.getPositiveDollarInput("How much would you like to transfer out?");
        boolean successfulTransfer = bank.getTransactionService().toNonUserTransfer(
                bank.getAccountHolder(), account, receiver, amount);

        if (successfulTransfer) {
            System.out.println("Transfer successful.");
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    /**
     * Helper for picking accounts.
     */
    private Account accountPicker() {
        AccountHolder client = bank.getAccountHolder();
        System.out.println("Or enter (0) to cancel.");

        int inc = 1;
        for (Account a : client.getAccountList()) {
            a.printAccountWithOptionNumber(inc);
            inc++;
        }

        int selection = promptForInt();
        while (!(selection >= 0 && selection < inc)) {
            selection = rePromptForInt();
        }

        if (selection == 0) {
            return null;
        }

        return client.getAccountList().get(selection - 1);
    }

    /**
     * Control logic for deposit.
     */
    private void depositMoney() {
        AccountHolder client = bank.getAccountHolder();

        int amount = bank.getMoneyService().deposit();
        bank.getTransactionService().deposit(amount, client, client.getPrimaryAccount());
    }

    /**
     * Control logic for withdrawal; get desired amount.
     */
    private void withdrawMoney(Asset asset) {
        System.out.println("Valid withdraw amounts are denominations of 5.");
        boolean invalidAmount = true;
        while (invalidAmount) {
            int amount = InputUtility.getPositiveInteger();
            if (amount == 0){
                return;
            }
            if (amount % 5 == 0) {
                invalidAmount = !tryWithdrawal(amount, asset);
            } else {
                System.out.println("Invalid amount. Amount must be denomination of 5. Please try again.");
            }
        }
    }

    /**
     * Control logic for withdrawal; see if withdrawal is possible.
     */
    private boolean tryWithdrawal(int amount, Account account){
        AccountHolder client = (bank.getAccountHolder());
        MoneyService moneyService = bank.getMoneyService();
        boolean successfulWithdraw = false;

        if (account.sufficientFundsToTransfer(amount) && moneyService.sufficientMoney(amount)){
            bank.getTransactionService().withdraw(client, account, amount);
            bank.getMoneyService().withdraw(amount);
            successfulWithdraw = true;
            payGrandMasterTreeFee(account);
            System.out.println("Successfully withdrew $" + amount);
        }
        else {
            System.out.println("Insufficient funds.");
        }
        return successfulWithdraw;
    }

    /*
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                               Tree Methods
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
     */

    /**
     * Display forest.
     */
    private void displayTreeAccountForest(){
        TreeAccount currTreeAccount = bank.getAccountHolder().getTreeAccount();
        currTreeAccount.displayForest();
    }

    /**
     * Control logic for paying grandmaster when a transaction is made from a tree account.
     */
    private void payGrandMasterTreeFee(Account account){
        Account grandLeaderPrimary = bank.getGrandLeader().getPrimaryAccount();
        AccountHolder accountHolder = bank.getAccountHolder();
        if (bank.getAccountHolder() instanceof TreeAccount){
            bank.getTransactionService().withinUserTransfer(accountHolder, account, grandLeaderPrimary, 1);
        }
    }

    /**
     * Control logic for printing people with the greatest number of trees.
     */
    private void displayForestLeaders() {
        System.out.println();
        List<Client> clients = new ArrayList<>();
        for (AccountHolder a : bank.getAccountHolders()) {
            clients.add((Client) a);
        }
        Collections.sort(clients, Collections.reverseOrder());
        if (clients.size() > 5) {
            for (int i = 0; i < 5; i++) {
                Client c = clients.get(i);
                System.out.println(c.getUsername() + " has " + c.getTreeAccount().getTreesPlanted() + " trees in their" +
                        " forest.");
            }
        } else {
            for (Client c : clients) {
                System.out.println(c.getUsername() + " has " + c.getTreeAccount().getTreesPlanted() + " trees in their" +
                        " forest.");
            }
        }
        System.out.println("\nHow many trees do you have?\n\n");

        System.out.println("(0) Go back");
        int selection = promptForInt();
        while (!(selection == 0)) {
            selection = rePromptForInt();
        }
    }
}
