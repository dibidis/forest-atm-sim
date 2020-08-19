package ATM;

class TransactionServiceFactory {

    /**
     * Creates a new Transaction Service with the given owner username.
     */

    TransactionService getTransactionService(String username){
        if(username == null){
            return null;
        } else{
            return new TransactionManager(username);
        }
    }
}
