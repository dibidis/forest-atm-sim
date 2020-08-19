package ATM;

/**
 * A debt account model that allows transfers in and out.
 */

public class LineOfCredit extends Debt {

    public LineOfCredit(){
        super("LineOfCredit");
        this.id = "LineOfCredit" + numLineOfCreditAccounts;
        numLineOfCreditAccounts ++;
    }

    public void setBalance(double balance){ this.balance = balance; }
}
