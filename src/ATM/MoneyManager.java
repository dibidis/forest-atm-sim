package ATM;

import java.io.Serializable;

class MoneyManager implements Serializable, MoneyService {

    private int[] money;

    MoneyManager(){
        this.money = new int[4];
    }

    /**
     * SETTERS AND GETTERS
     */
    void setBills(int[] bills){
        System.arraycopy(bills, 0, this.money, 0, bills.length);
    }

    public int[] getMoney() {
        return money;
    }

    private int getTotalMoney(){
        return (money[0] * 50 + money[1] * 20 + money[2] * 10 + money[3] * 5);
    }

    /**
     * Update denominations based on deposits.txt, and
     * return the amount deposited.
     */
    public int deposit(){
        IOManager ioManager = new IOManager();

        int[] toIncrease = ioManager.readDeposit();
        changeAmountBills(toIncrease, true);
        ioManager.deleteDeposits();
        return toIncrease[0] * 50 + toIncrease[1] * 20 + toIncrease[2] * 10 + toIncrease[3] * 5;
    }

    /**
     * Update denominations based on desired withdrawal amount, and
     * return whether withdrawal was successful.
     *
     * Send an alert to manager if ATM is low on money.
     */
    public boolean withdraw(int amount) {
        //Precondition, amount % 5 == 0.

        // This result says whether there are sufficient funds or no
        boolean result;

        if (sufficientMoney(amount)){
                changeAmountBills(bestCombinationOfBills(amount, getMoney()), false);
                result = true;
        } else{
            result = false;
        }
        sendAlertIfLowOnMoney();

        return result;
    }

    public void sendAlertIfLowOnMoney(){
        if (lowOnMoney()){
            IOManager ioManager = new IOManager();
            ioManager.sendAlert(money);
        }
    }

    /**
     * Return the number of bills,
     * with the maximum number of higher denominations,
     * in an array.
     */
    private int[] bestCombinationOfBills(int amount, int[] money) {
        int fifties = 0;
        int twenties = 0;
        int tens = 0;
        int fives = 0;

        while (amount >= 50 && fifties < money[0]) {
            fifties ++;
            amount -= 50;
        }
        while (amount >= 20 && twenties < money[1]) {
            twenties ++;
            amount -= 20;
        }
        while (amount >= 10 && tens < money[2]) {
            tens ++;
            amount -= 10;
        }
        while (amount >= 5 && fives < money[3]) {
            fives ++;
            amount -= 5;
        }

        return new int[]{fifties, twenties, tens, fives};
    }

    /**
     * Increase or decrease the amount of bills by the amountBills
     * @param amountBills the number of new bills to increase or to decrease
     * @param increase whether we wanna increase or decrease
     */
    public void changeAmountBills(int[] amountBills, boolean increase) {
        int [] newBills = new int [amountBills.length];
        int [] previousBills = getMoney();
        for (int i = 0; i < amountBills.length; i++ ){
            if (increase){
                newBills[i] = previousBills[i] + amountBills[i];
            } else{
                newBills[i] = previousBills[i] - amountBills[i];
            }
        }
        setBills(newBills);
    }

    /**
     * Return if the number of a denomination is lower than 20.
     */
    private boolean lowOnMoney(){
        for (int billNumber : money){
            if (billNumber < 20){
                return true;
            }
        }
        return false;
    }

    /**
     * @param amount desired amount
     * @return if ATM has enough bills to afford this amount
     */
    public boolean sufficientMoney(int amount){
        return amount <= getTotalMoney();
    }

    /**
     * Print number of each bill to notify Bank Manager
     */
    public String representBills(){
        return "$50: " + this.money[0] + "\n" +
                "$20: " + this.money[1] + "\n" +
                "$10: " + this.money[2] + "\n" +
                "$5: " + this.money[3];
    }

    /**
     * delete alerts.txt file after Bank Manager loads money
     */
    public void deleteAlerts() {
        IOManager ioManager = new IOManager();
        ioManager.deleteAlerts();
    }
}