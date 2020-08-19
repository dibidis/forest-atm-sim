package ATM;

import java.util.Scanner;

/**
 * A static class with input utility methods.
 */

class InputUtility {

    /**
     * Used to pause to show messages to user
     * @param milliseconds
     */
    static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Used to get a valid integer from the user
     */
    static int getPositiveInteger() {
        Scanner userInput = new Scanner(System.in);
        int amount;
        while (true) {
            System.out.println("How much would you like to withdraw?");
            System.out.println("Enter 0 to go back.");
            System.out.print("Enter here: $");
            String option = userInput.nextLine();
            if (AllNumeric(option)) {
                if (option.length() > 9) {
                    System.out.println("Amount must be lower than 1000000000");
                } else {
                    amount = Integer.valueOf(option);
                    if (amount == 0){
                        return amount;
                    }
                    if (amount > 0) {
                        return amount;
                    } else {
                        System.out.println("Amount must be positive. Please try again.");
                    }
                }
            } else {
                System.out.println("Invalid amount. Please try again.");
            }
        }
    }

    private static boolean AllNumeric(String option) {
        boolean result = true;
        for (int i = 0; i < option.length(); i++) {
            if (!Character.isDigit(option.charAt(i))) {
                result = false;
            }
        }
        return result;
    }

    /**
     * Used to get a valid positive number from the user
     */
    static double getPositiveDollarInput(String question) {
        Scanner userInput = new Scanner(System.in);
        double amount = 0;
        while (amount <= 0) {
            System.out.println(question);
            System.out.print("Enter here: $");
            if (userInput.hasNextDouble()) {
                amount = userInput.nextDouble();
                // The below is needed to consume the \n character created by hitting enter following the
                // value inputted by the user.
                userInput.nextLine();
            } else {
                System.out.println("Invalid amount. Please try again.");
                userInput.nextLine();
                //Skip the below if
                continue;
            }
            if (amount <= 0) {
                System.out.println("Amount must be greater than 0. Please try again.");
            }
        }
        return amount;
    }

}
