package ATM;

/*
Transaction class represents a single transaction made by a client.
 */

import java.io.Serializable;

public abstract class Transaction implements Serializable {

    double amount;
    int id;

    static int numTransactions = 0;

    Transaction(){
        numTransactions++;
    }

    /**
     * SETTERS AND GETTERS
     */

    public abstract String getTransactionOwnerName();

    public double getAmount() {
        return this.amount;
    }

    private int getId() {
        return this.id;
    }

    /**
     * EQUALS & TO STRING
     */

    @Override
    public abstract String toString();

    @Override
    public boolean equals(Object obj) {
        if (obj == null){
            return false;
        }
        else if (! (obj instanceof Transaction)){
            return false;
        }
        else{
            return this.getId() == ((Transaction)obj).getId();
        }
    }
}
