package ATM;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Contains the control methods associated with the CreatesUsers Interface
 */

class CreatesUsersControls extends UserUI{

    CreatesUsersControls(Bank bank) {
        super(bank);
    }

    /**
     * Option map for classes that implement CreatesUsers Interface.
     */
    @Override
    Map<String, Runnable> loadOptions(Map<String, Runnable> optionMap){
        optionMap.put("Create a new user", this::createNewUser);
        return optionMap;
    }

    /*
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
                                               Create new user methods
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
      ////////////////////////////////////////////////////////////////////////////////////////////////////////////
     */

    /**
     * Control logic for creating new user.
     */
    private void createNewUser() {
        Map<String, Runnable> optionMap = new LinkedHashMap<>();
        optionMap.put("Create Client account", this::createClient);
        optionMap.put("Create Employee account", this::createEmployee);
        optionMap.put("Create Grand Leader", this::createGrandLeader);
        optionMap.put("Go back", this::goBack);

        ArrayList<String> options = new ArrayList<>(optionMap.keySet());

        while(running) {
            printDashes();
            int selection = printOptionsAndGetSelection(options);
            optionMap.get(options.get(selection)).run();
        }
        running = true;
    }

    /**
     * Helper for creating Grand Leader.
     */
    private void createGrandLeader() {
        createNewUser("GrandLeader");
    }

    /**
     * Helper for creating Client.
     */
    private void createClient() {
        createNewUser("Client");
    }

    /**
     * Helper for creating Employee.
     */
    private void createEmployee() {
        createNewUser("Employee");
    }

    /**
     * Control logic for creating new user by using a User Factory.
     */
    private void createNewUser(String type) {
        System.out.println("At any time, enter (0) to cancel user creation.");
        System.out.println("Please enter a username.");
        String username = promptForString();
        while (bank.hasUser(username)) {
            System.out.println("That username is taken. Please enter another.");
            username = promptForString();
        }

        if (username.equals("0")) {
            return;
        }

        System.out.println("Please enter a password.");
        String password = promptForString();

        if (password.equals("0")) {
            return;
        }

        UserFactory userFactory = new UserFactory();
        bank.addUser(userFactory.getUser(type, username, password));

        TransactionServiceFactory transactionServiceFactory = new TransactionServiceFactory();
        bank.addTransactionService(transactionServiceFactory.getTransactionService(username));

        System.out.println(username + " now has an account.");
    }
}
