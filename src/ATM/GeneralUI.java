package ATM;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

/**
 * The highest level UI class. Contains utility methods.
 */

abstract class GeneralUI {

    Bank bank;
    Scanner userInput;

    GeneralUI(Bank bank) {
        this.bank = bank;
        userInput = new Scanner(System.in);
    }

    void printDashes() {
        System.out.print("\n-----------------------------------------------\n");
    }

    int printOptionsAndGetSelection(Collection<String> options) {
        for (int i = 0; i < options.size()-1; i++) {
            System.out.println("(" + (i+1) + ") " + ((ArrayList) options).get(i));
        }
        System.out.println("(0) " + ((ArrayList) options).get(options.size()-1));

        int selection = promptForInt();

        while (!(selection >= 0 && selection < options.size())) {
            selection = rePromptForInt();
        }

        if (selection == 0) selection = (options.size());

        return selection-1;
    }

    int promptForInt(){
        System.out.print("Enter here: ");
        int input = -1;
        while (input <0) {
            try {
                input = Integer.parseInt(userInput.nextLine());
                if (input < 0){
                    System.out.println("Invalid option. Please try again.");
                    System.out.print("Enter here: ");
                }
            } catch (Exception e) {
                System.out.println("Invalid option. Please try again.");
                System.out.print("Enter here: ");
            }
        }
        return input;
    }

    String promptForString(){
        System.out.print("Enter here: ");
        return userInput.nextLine();
    }

    int rePromptForInt() {
        System.out.println("Invalid option. Please try again.");
        return promptForInt();
    }

    String rePromptForString() {
        System.out.println("Invalid option. Please try again.");
        System.out.print("Enter here: ");
        return userInput.nextLine();
    }

    String getUsername(String info) {
        System.out.println(info);
        System.out.println("Or enter (0) to cancel.");
        System.out.println("Enter here:");
        String username = userInput.nextLine();


        while ((!bank.hasUser(username) && !username.equals("0"))
                || username.equals(bank.getUsername())) {
            username = rePromptForString();
        }

        return username;
    }

    abstract void displayGeneralOptions();
}
