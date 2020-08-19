package ATM;

public abstract class Debt extends Account {
    /**
     * Debt accounts have different behaviours for balance display and transactions.
     * Abstract class for classes like CreditCard and LineofCredit
     */
    private Integer maxDebt = 500;
    Debt(String type){
        super(type);
    }

    public void setMaxDebt(Integer maxDebtAmount){
        this.maxDebt = maxDebtAmount;
    }

    @Override
    // increase and decrease in debt accounts mean different things.
    public void increase(double amount) { this.balance -= amount;}

    @Override
    public void decrease(double amount) { this.balance += amount;}

    @Override
    public boolean sufficientFundsToTransfer(double amount) {
        return (this.balance + amount <= maxDebt);
    }
}
