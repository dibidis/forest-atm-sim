package ATM;

import javafx.application.Application;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.Calendar;

public class Starter{

    /*
    This class is where program execution starts.
    Also, this class is responsible for handling time related functionality.
    */

    public static void main(String[] args) {

        IOManager ioManager = new IOManager();
        Bank bank = ioManager.readConfig();
        updateDateAndInterest(bank);

        while (true) {
            GreetingUI session = new GreetingUI(bank);
            session.displayGeneralOptions();
            ioManager.writeConfig(bank);
        }

    }

    private static void updateDateAndInterest(Bank bank){
        int lastUpdatedYear = bank.getLastUpdatedYear();
        int lastUpdatedMonth = bank.getLastUpdatedMonth();
        Calendar cal = bank.getCalendar();

        // Idea: use difference between two months, and then loop for however many differences, call the adjustSavings
        // method for each month difference. This is robust even if you shutdown and restart even after 6 years
        // If it is a new system, the lastUpdated time should just be set to the current time. No savings adjustments.
        if (lastUpdatedYear != 0) {
            int monthDiff = getMonthDiff(cal, lastUpdatedYear, lastUpdatedMonth);
            for (int i = 0; i < monthDiff; i++) {
                bank.adjustAllSavings();
            }
            if (monthDiff > 0) {
                System.out.println("Savings have been adjusted for " + monthDiff + " months.");
            }
        }

        // Below updates the date
        bank.setLastUpdatedDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH));

    }

    private static int getMonthDiff(Calendar cal, int lastUpdatedYear, int lastUpdatedMonth){
        int years = cal.get(Calendar.YEAR) - lastUpdatedYear;
        int months = cal.get(Calendar.MONTH) - lastUpdatedMonth;
        return years * 12 + months;
    }
}


