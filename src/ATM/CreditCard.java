package ATM;

/**
 * A debt account model that can only take in transfers and pay bills.
 */

public class CreditCard extends Debt {
    public CreditCard(){
        super("CreditCard");
        this.id = "CreditCard" + numCreditCardAccounts;
        numCreditCardAccounts++;
    }

    /**
     * This prevents the credit card model to transfer money out.
     */
    @Override
    public boolean sufficientFundsToTransfer(double amount) { return false; }
}
