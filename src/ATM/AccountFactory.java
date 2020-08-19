package ATM;

class AccountFactory {

    /**
     * Creates a new Account with the given type.
     */

    Account getAccount(String accountType){
        if(accountType == null){
            return null;
        }

        //ReplaceAll method needed to deal with spaces in "Credit Card", "Line of Credit", and "Tree Account"
        if(accountType.replaceAll(" ", "").equalsIgnoreCase("Chequing")){
            return new Chequing();

        } else if(accountType.replaceAll(" ", "").equalsIgnoreCase("Savings")){
            return new Savings();

        } else if(accountType.replaceAll(" ", "").equalsIgnoreCase("CreditCard")){
            return new CreditCard();

        } else if(accountType.replaceAll(" ", "").equalsIgnoreCase("LineOfCredit")){
            return new LineOfCredit();

        } else if(accountType.replaceAll(" ", "").equalsIgnoreCase("TreeAccount")) {
            return new TreeAccount();
        }
        return null;
    }
}
