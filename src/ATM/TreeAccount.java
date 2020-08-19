package ATM;

import Forest.*;

public class TreeAccount extends Asset{
    private Integer treesPlanted = 0;

    // initial set up
    public TreeAccount(){
        super("TreeAccount");
        this.id = "TreeAccount" + numTreeAccounts;
        numTreeAccounts++;
    }

    Integer getTreesPlanted(){ return this.treesPlanted;}

    void setTreesPlanted(Integer treesPlanted) {
        this.treesPlanted = treesPlanted;
    }

    @Override
    public boolean sufficientFundsToTransfer(double amount){
        if (this.balance >= (amount)) {
            //plant a tree with every successful transaction
            plantTree();
            return true;
        } else {
            return false;
        }
    }
    private void plantTree(){
        treesPlanted++;
        //cost of a tree is $1
        this.balance -= 1;
    }

    void displayForest(){
        Forest myForest = new Forest();
        //put 2 bears in the forest
        int numBears = 2;
        myForest.run(treesPlanted, numBears);
    }

}
