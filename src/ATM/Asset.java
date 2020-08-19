package ATM;

/**
 * An abstract superclass to asset accounts like Chequing and Savings.
 */

public abstract class Asset extends Account {
    Asset(String type){
        super(type);
    }
}
