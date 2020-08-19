package ATM;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

class UserUI extends GeneralUI {
    boolean running = true;

    UserUI(Bank bank) {
        super(bank);
        this.bank = bank;
    }

    @Override
    void displayGeneralOptions() {
        User user = bank.getUser();

        Map<String, Runnable> optionMap = getOptionMap(user);

        ArrayList<String> options = new ArrayList<>(optionMap.keySet());

        while (running) {
            greeting(user);
            int selection = printOptionsAndGetSelection(options);
            optionMap.get(options.get(selection)).run();
        }
        running = true;
    }

    Map<String, Runnable> loadOptions(Map<String, Runnable> optionMap){
        optionMap.put("Change your password", this::changePassword);
        optionMap.put("Log out", this::logout);
        return optionMap;
    }

    private void changePassword() {
        printDashes();
        System.out.println("Current password: " + bank.getPassword());
        System.out.print("Please enter a new password: ");
        String newPassword = userInput.nextLine();
        while (newPassword.equals(bank.getPassword())) {
            System.out.println("Entered password is the same as the old password. Try again.");
            System.out.print("Please enter a new password: ");
            newPassword = userInput.nextLine();
        }
        bank.setPassword(newPassword);
    }

    private void logout() {
        System.out.println("Have a great day!");
        printDashes();
        this.running = false;
    }

    void goBack() {
        this.running = false;
    }

    private Map<String, Runnable> getOptionMap(User user) {
        Map<String, Runnable> optionMap = new LinkedHashMap<>();

        if (user instanceof WorksHere) {
            WorksHereControls worksHereControls = new WorksHereControls(bank);
            optionMap = worksHereControls.loadOptions(optionMap);
        }
        if (user instanceof CreatesUsers) {
            CreatesUsersControls createsUsersControls = new CreatesUsersControls(bank);
            optionMap = createsUsersControls.loadOptions(optionMap);
        }
        if (user instanceof AccountHolder) {
            AccountHolderControls accountHolderControls = new AccountHolderControls(bank);
            optionMap = accountHolderControls.loadOptions(optionMap);
        }
        if (user instanceof OwnsThePlace) {
            OwnsThePlaceControls ownsThePlaceControls = new OwnsThePlaceControls(bank);
            optionMap = ownsThePlaceControls.loadOptions(optionMap);
        }

        optionMap = loadOptions(optionMap);

        return optionMap;
    }

    private void greeting(User user) {
        printDashes();
        System.out.println("\nWelcome, " + bank.getUsername() + "!");

        if (user instanceof WorksHere) {
            new WorksHereControls(bank).printNotifications();
        }

        printPleaseSelectOption();
    }

    String getClientUsername(String question){
        System.out.println(question);
        System.out.println("Or enter (0) to go back.");
        System.out.print("Enter username: ");
        String username = userInput.nextLine();


        while (bank.hasAccountHolder(username) && !username.equals("0")) {
            System.out.println("We're sorry, that username isn't in our system as a client. Please try again.");
            System.out.println("Or enter (0) to go back.");
            System.out.print("Enter username: ");
            username = userInput.nextLine();
        }
        return username;
    }

    void printPleaseSelectOption(){
        System.out.println("\nPlease select one of the following options:");
    }

}
