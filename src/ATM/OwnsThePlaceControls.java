package ATM;

import java.util.Map;

/**
 * Contains the control methods associated with the OwnsThePlace Interface.
 */

class OwnsThePlaceControls extends UserUI {

    OwnsThePlaceControls(Bank bank) {
        super(bank);
    }

    @Override
    Map<String, Runnable> loadOptions(Map<String, Runnable> optionMap){
        optionMap.put("Fund the revolution", this::fundRevolution);
        optionMap.put("Burn a forest", this::burnForest);
        optionMap.put("Exile heretic", this::exileHeretic);
        return optionMap;
    }

    /**
     * Removes a user from the system and transfers their assets to the Grand Leader.
     */
    private void exileHeretic() {
        String username = getUsername("Who has displeased you and must be removed from the bank?");
        if (username.equals("0")) {
            return;
        }
        int fundsAcquired = 0;
        for (Account a : bank.getAccountHolder(username).getAccountList("Asset")) {
            fundsAcquired += a.getBalance();
            bank.getAccountHolder().getPrimaryAccount().increase(a.getBalance());
        }
        bank.removeUser(username);
        System.out.println(username + " is no more. You retrieved $" + fundsAcquired + " from their accounts.");
    }

    /**
     * Takes $25 from every AccountHolder whose net total is greater than $100.
     */
    private void fundRevolution(){
        int funding = 0;
        for (AccountHolder a : bank.getAccountHolders()) {
            if (!(a instanceof OwnsThePlace) && a.calculateNetTotal() >= 100) {
                a.getPrimaryAccount().setBalance(a.getPrimaryAccount().getBalance() - 25);
                funding = funding + 25;
            }
        }
        GrandLeader grandLeader = (GrandLeader) bank.getAccountHolder();
        Account leadersAccount = bank.getAccountHolder().getPrimaryAccount();
        leadersAccount.setBalance(leadersAccount.getBalance() + funding);
        System.out.println("You've received $" + funding + " in funding.");
        bank.getTransactionService().addToTransactionList(
                new FundRevolutionTransaction(funding, grandLeader));
    }

    /**
     * Sets an AccountHolder's tree count to 0.
     */
    private void burnForest(){
        System.out.println("\nWhose forest will you burn?");
        System.out.print("Enter username: ");
        String username = userInput.nextLine();

        while (!bank.hasAccountHolder(username)) {
            System.out.println("We're sorry, that username isn't in our system as an account holder. Please try again.");
            System.out.print("Enter username: ");
            username = userInput.nextLine();
        }

        bank.getAccountHolder(username).getTreeAccount().setTreesPlanted(0);

        System.out.println(username + "'s forest is no more.");
    }
}
