package ATM;

class GreetingUI extends GeneralUI {

    GreetingUI(Bank bank) {
        super(bank);
    }

    @Override
    void displayGeneralOptions(){
        printLogo();
        System.out.println("Welcome to TreeBank!");
        String username = login();

        bank.setCurrentUser(username);
        new UserUI(bank).displayGeneralOptions();
    }

    private String login() {

        System.out.println("\nPlease provide your username and password.");
        System.out.print("Enter username: ");
        String username = userInput.nextLine();

        if (username.equals("SetDate")){
            displaySetDateOptions();
            // This just has to be something that isn't an existing username
            System.out.println("\nPlease provide your username and password.");
            System.out.print("Enter username: ");
            username = userInput.nextLine();
        }

        while (!bank.hasUser(username)) {
            System.out.println("We're sorry, that username isn't in our system. Please try again.");
            System.out.print("Enter username: ");
            username = userInput.nextLine();
        }

        System.out.print("Enter password: ");
        String password = userInput.nextLine();

        while (!bank.isValidPassword(username, password)) {
            System.out.println("That password doesn't match the username. Please try again.");
            System.out.print("Enter password: ");
            password = userInput.nextLine();
        }

        return username;
    }

    private void printLogo(){
        System.out.println(" /$$$$$$$$                            /$$$$$$$                      /$$      ");
        System.out.println("|__  $$__/                           | $$__  $$                    | $$      ");
        System.out.println("   | $$  /$$$$$$   /$$$$$$   /$$$$$$ | $$  \\ $$  /$$$$$$  /$$$$$$$ | $$   /$$");
        System.out.println("   | $$ /$$__  $$ /$$__  $$ /$$__  $$| $$$$$$$  |____  $$| $$__  $$| $$  /$$/");
        System.out.println("   | $$| $$  \\__/| $$$$$$$$| $$$$$$$$| $$__  $$  /$$$$$$$| $$  \\ $$| $$$$$$/ ");
        System.out.println("   | $$| $$      | $$_____/| $$_____/| $$  \\ $$ /$$__  $$| $$  | $$| $$_  $$ ");
        System.out.println("   | $$| $$      |  $$$$$$$|  $$$$$$$| $$$$$$$/|  $$$$$$$| $$  | $$| $$ \\  $$");
        System.out.println("   |__/|__/       \\_______/ \\_______/|_______/  \\_______/|__/  |__/|__/  \\__/");
        System.out.println("\n");
    }

    private void displaySetDateOptions(){
        System.out.println("Set Year: ");
        int year = promptForInt();
        while (!(year >= 0)) {
            year = rePromptForInt();
        }
        System.out.println("Set Month (Number from 1 to 12): ");
        int month = promptForInt();
        while (!(month >= 1 && month <= 12)) {
            month = rePromptForInt();
        }
        // month-1 for 0 based indexing
        bank.setLastUpdatedDate(year, month-1);

        // update as soon as happened, so that you can restart program immediately.
        IOManager ioManager = new IOManager();
        ioManager.writeConfig(bank);
    }

}
