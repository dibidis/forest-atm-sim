package ATM;

public interface MoneyService {
    int deposit();

    int[] getMoney();

    void changeAmountBills(int[] billsToIncrease, boolean b);

    String representBills();

    void deleteAlerts();

    boolean withdraw(int amount);

    boolean sufficientMoney(int amount);

    void sendAlertIfLowOnMoney();
}
