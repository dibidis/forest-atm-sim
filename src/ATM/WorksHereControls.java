package ATM;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Contains the control methods associated with the WorksHere Interface.
 */

class WorksHereControls extends UserUI {

    WorksHereControls(Bank bank) {
        super(bank);
    }

    @Override
    Map<String, Runnable> loadOptions(Map<String, Runnable> optionMap){
        optionMap.put("Restock the ATM", this::restockATM);
        optionMap.put("View new account requests or create a new account", this::createNewAccount);
        optionMap.put("Undo client transactions", this::undoTransaction);
        optionMap.put("Reset client password", this::resetAccountHolderPassword);

        return optionMap;
    }


    /*
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                              Notifications
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
     */

    /**
     * Consolidates notification view methods.
     */
    void printNotifications() {
        System.out.println("\n----------------NOTIFICATIONS--------------\n");
        restockNotification();
        printDashes();
        shortNewAccountRequestNotification();
        printDashes();
    }

    /**
     * View full notification details.
     */
    private void longNewAccountRequestNotification() {
        List<AccountRequest> accountRequests = bank.getNewAccountRequests();

        if (!(accountRequests.size() == 0)) {
            System.out.println("We have " + accountRequests.size() + " new account requests: \n");

            for (int i = 0; i < accountRequests.size(); i++) {
                AccountRequest request = accountRequests.get(i);
                String jointString = "";
                String withUserString = "";
                if (request.isJointAccountRequest()){
                    jointString = "joint ";
                    withUserString = " with user " + request.getUsername2();
                }
                System.out.println(i + ") User " + request.getUsername() + " wants a new " + jointString
                        + request.getAccountType() + " account" + withUserString + ".");
            }
        } else {
            System.out.println("There are no new account requests.");
        }
    }

    /**
     * View abbreviated notification details.
     */
    private void shortNewAccountRequestNotification() {
        if (!(bank.getNewAccountRequests().size() == 0)) {
            System.out.println("We have " + bank.getNewAccountRequests().size() + " new account request(s).");
        } else {
            System.out.println("\nThere are no new account requests.");
        }
    }

    /**
     * Alert the bank employees that the ATM needs bills.
     */
    private void restockNotification() {
        MoneyService moneyManager = bank.getMoneyService();
        moneyManager.sendAlertIfLowOnMoney();

        String directory = (System.getProperty("user.dir") + "/phase2/src/IO_Files/");
        File tmpDir = new File(directory + "alerts.txt");
        boolean exists = tmpDir.exists();

        if (exists) {
            System.out.println("The ATM is running low.");
        } else {
            System.out.println("The ATM is well-stocked.");
        }
    }

    /*
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                              Set User Password
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
     */

    /**
     * Changes an AccountHolder password.
     */
    private void resetAccountHolderPassword() {
        System.out.println("\nEnter the username of the user whose password you will reset.");
        System.out.println("Or enter (0) to go back.");
        System.out.print("Enter username: ");
        String username = userInput.nextLine();

        while (bank.hasAccountHolder(username) && !username.equals("0")) {
            System.out.println("We're sorry, that username isn't in our system as a client. Please try again.");
            System.out.println("Enter (0) to go back.");
            System.out.print("Enter username: ");
            username = userInput.nextLine();
        }

        if (username.equals("0")){
            return;
        }

        System.out.print("Please enter a new password: ");
        String newPassword = userInput.nextLine();
        while (newPassword.equals(bank.getAccountHolder(username).getPassword())) {
            System.out.println("Entered password is the same as the old password. Try again.");
            System.out.print("Please enter a new password: ");
            newPassword = userInput.nextLine();
        }

        bank.getAccountHolder(username).setPassword(newPassword);
    }

    /*
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                              Restock ATM
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
     */

    /**
     * Controls ATM restocking.
     */
    private void restockATM() {
        MoneyService moneyManager = bank.getMoneyService();
        IOManager ioManager = new IOManager();
        boolean[] isLow = ioManager.getIsLow();
        int[] numBills = moneyManager.getMoney();

        int[] billsToIncrease = getBillsToIncrease(isLow, numBills);

        moneyManager.changeAmountBills(billsToIncrease, true);
        System.out.println("Operation completed.");
        System.out.println("New amount of bills: ");
        System.out.println(moneyManager.representBills());
        moneyManager.deleteAlerts();
        InputUtility.sleep(800);
    }

    /**
     * Controls number of bills input to ATM
     * @param isLow an array indicating which bill denominations are low
     * @param numBills an array indicating the counts for each bill denomination
     * @return an array of the updated bill counts
     */
    private int[] getBillsToIncrease(boolean[] isLow, int[] numBills) {
        int[] billsToIncrease = new int[4];
        for (int i = 0; i < isLow.length; i++) {
            if (isLow[i]) {
                String billName = indexToDollars(i);
                System.out.println("Please enter the amount by which you want to increase " + billName + ".");
                System.out.println("Current amount: " + numBills[i]);
                System.out.println("This increase should make the total number of bills above 20.");
                System.out.println("Enter here: ");
                String option = userInput.nextLine();

                // If manager does not enter a number.
                while (notAllNumeric(option) || Integer.valueOf(option) < 0) {
                    option = rePromptForString();
                }

                billsToIncrease[i] = Integer.valueOf(option);
            }
        }
        return billsToIncrease;
    }

   /*
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                              New Accounts
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
     */

    /**
     * Controls new account creation.
     */
    private void createNewAccount() {
        longNewAccountRequestNotification();

        Map<String, Runnable> optionMap = new LinkedHashMap<>();
        optionMap.put("Automatically approve all requests", this::approveAllNewAccountRequests);
        optionMap.put("Approve requests one by one", this::approveIndividualAccountRequests);
        optionMap.put("Create new a account for a user", this::createNewAccountManually);
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
     * Approve all requested accounts
     */
    private void approveAllNewAccountRequests() {

        if (bank.getNewAccountRequests().size() == 0){
            System.out.println("There are no requests to approve.");
        }

        for (AccountRequest request : bank.getNewAccountRequests()) {
            createNewAccount(request);
        }
        System.out.println("All requests have been approved.");
        bank.getNewAccountRequests().clear();
    }

    /**
     * Approved requested accounts one by one.
     */
    private void approveIndividualAccountRequests() {

        if (bank.getNewAccountRequests().size() == 0){
            System.out.println("There are no requests to approve.");
            return;
        }

        AccountRequest toApprove = getAccountRequestToApprove();

        if (toApprove == null) {
            return;
        }

        System.out.println("A new " + toApprove.getAccountType()
                + " account has been created for " + toApprove.getUsername());

        createNewAccount(toApprove);

        exitQuestionForIndividualRequestApproval();
    }

    /**
     * Helper method for selecting specific account requests for approval
     * @return the chosen AccountRequest
     */
    private AccountRequest getAccountRequestToApprove(){
        printAccountRequestsForApproval();
        int selection = promptForInt();
        while(!(selection >= 0 && selection <= bank.getNewAccountRequests().size())){
            selection = rePromptForInt();
        }
        if (selection == 0){
            return null;
        } else {
            AccountRequest toApprove = bank.getNewAccountRequests().get(selection-1);
            bank.getNewAccountRequests().remove(selection-1);
            return toApprove;
        }
    }

    /**
     * View method for AccountRequests
     */
    private void printAccountRequestsForApproval(){
        int inc = 1;
        for (AccountRequest a : bank.getNewAccountRequests()) {
            System.out.println("(" + inc++ + ") " + a);
        }
        System.out.println("(0) Go back.");
    }

    /**
     * Controls manual account creation.
     */
    private void createNewAccountManually() {

        String username = getUsername("Please type in the username of the user for whom you'd like to create" +
                " an account");

        if (username.equals("0")) {
            return;
        }

        String accountType = getAccountType();

        if (accountType.equals("0")) {
            return;
        }

        createNewAccount(username, accountType);
        System.out.println("An account of type " + accountType + " has been created for " + username + ".");

        exitQuestionForManualAccountCreation();
    }

    /**
     * Helper method for new account creation from AccountRequest object
     * @param request the AccountRequest containing the relevant account info.
     */
    private void createNewAccount(AccountRequest request) {
        String secondUser = request.getUsername2();
        if (request.isJointAccountRequest()) {
            createNewAccount(request.getUsername(), secondUser, request.getAccountType());
        }
        else{
            createNewAccount(request.getUsername(), request.getAccountType());
        }
    }

    /**
     * Helper method for new account creation from employee input.
     * @param username the username of the AccountHolder who wants the account
     * @param accountType the type of Account to create
     */
    private void createNewAccount(String username, String accountType){
        AccountFactory accountFactory = new AccountFactory();
        Account newAccount = accountFactory.getAccount(accountType);
        bank.addAccount(username, newAccount);
    }

    /**
     * Helper method that creates joint accounts
     * @param username1 one of the AccountHolder's usernames
     * @param username2 the second AccountHolder's username
     * @param accountType the desired account type
     */
    private void createNewAccount(String username1, String username2, String accountType){
        AccountFactory accountFactory = new AccountFactory();
        Account newAccount = accountFactory.getAccount(accountType);
        bank.addAccount(username1, username2, newAccount);
    }

    /**
     * Helper method for after approving an account request
     */
    private void exitQuestionForIndividualRequestApproval() {

        System.out.println("(1) Approve another account.");
        System.out.println("(0) Go back.");
        int selection = promptForInt();
        while (!(selection == 0 || selection == 1)) {
            selection = rePromptForInt();
        }
        if (selection == 1) {
            approveIndividualAccountRequests();
        }
    }

    /**
     * Helper method for after manually creating an account
     */
    private void exitQuestionForManualAccountCreation() {

        System.out.println("(1) Create another account.");
        System.out.println("(0) Go back.");
        int selection = promptForInt();
        while (!(selection == 0 || selection == 1)) {
            selection = rePromptForInt();
        }
        if (selection == 1) {
            createNewAccountManually();
        }
    }

    /**
     * Helper method for getting account type
     * @return String representing the desired type
     */
    private String getAccountType() {
        String[] accountTypes = {"Chequing", "Savings", "CreditCard", "LineOfCredit"};
        System.out.println("What type of account would you like to create?");
        int inc = 1;
        for (String type : accountTypes) {
            System.out.println("(" + inc + ") " + type);
            inc++;
        }
        System.out.println("(0) Go back.");
        int selection = promptForInt();
        while (!(selection >= 0 && selection < inc)) {
            selection = rePromptForInt();
        }
        if (selection == 0) {
            return "0";
        }
        return accountTypes[selection-1];
    }


    /*
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                              Undo Transaction
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
     */

    /**
     * Controls the undoing of transactions
     */
    private void undoTransaction() {
        String username = getClientUsername("Enter the username of the client whose transaction you'd like to undo: ");

        if (username.equals("0")){
            return;
        }
        TransactionService transactionService = bank.getTransactionService(username);
        if (transactionService.getTransactionList().size() == 0){
            System.out.println("That client has made no transactions.");
        } else {
            UndoableTransaction toUndo = getTransactionToUndo(username);
            if (!(toUndo == null)) {
                transactionService.undoTransaction(toUndo);
                removeForOddParty(username, toUndo);
                System.out.println("Transaction undone.");
            }
        }
        exitQuestionForUndo();
    }

    /**
     * Helper that ensures both parties of a betweenUserTransaction are undone
     * @param username the user on the other end of the transaction
     * @param transaction the transaction to undo
     */
    private void removeForOddParty(String username, UndoableTransaction transaction) {
        //Odd party is receiver
        if (transaction.getSenderUsername().equals(username)){
            bank.removeTransaction(transaction.getReceiverUsername(), transaction);
        } else { // Odd party is the sender
            bank.removeTransaction(transaction.getSenderUsername(), transaction);
        }
    }

    /**
     * View and select a transaction
     * @param username the user whose transactions are viewed
     * @return the chosen transaction
     */
    private UndoableTransaction getTransactionToUndo(String username){
        List<UndoableTransaction> undoableTransactions = printTransactionsForSelection(username);
        int selection = promptForInt();
        while(!(selection >= 0 && selection <= undoableTransactions.size())){
            selection = rePromptForInt();
        }
        if (selection == 0){
            return null;
        } else {
            return undoableTransactions.get(selection-1);
        }
    }

    private List<UndoableTransaction> printTransactionsForSelection(String username){
        System.out.println("Here are " + username + "'s undoable transactions.");
        List<Transaction> transactions = bank.getTransactionService(username).getTransactionList();
        List<UndoableTransaction> undoableTransactions = new ArrayList<>();

        for (Transaction t : transactions) {
            if (t instanceof UndoableTransaction) {
                undoableTransactions.add((UndoableTransaction) t);
            }
        }

        int inc = 1;
        for (Transaction t : undoableTransactions) {
            System.out.println("(" + inc + ") " + t);
            inc++;
        }
        System.out.println("(0) Go back.");
        return undoableTransactions;
    }

    private void exitQuestionForUndo() {
        System.out.println("(1) Undo another transaction.");
        System.out.println("(0) Go back.");
        int selection = promptForInt();
        while (!(selection == 0 || selection == 1)) {
            selection = rePromptForInt();
        }
        if (selection == 1) {
            undoTransaction();
        }
    }

    /**
     * HELPERS
     */

    private boolean notAllNumeric(String option) {
        boolean result = false;
        for (int i = 0; i < option.length(); i++) {
            if (!Character.isDigit(option.charAt(i))) {
                result = true;
            }
        }
        return result;
    }

    private String indexToDollars(int i) {
        if (i == 0) {
            return "$50";
        } else if (i == 1) {
            return "$20";
        } else if (i == 2) {
            return "$10";
        } else {
            return "$5";
        }
    }

    private String getUserName() {
        System.out.println("Enter here: ");
        String userName = userInput.nextLine();

        while (!bank.hasUser(userName) && !userName.equals("0")) {
            userName = rePromptForString();
        }
        return userName;
    }
}
