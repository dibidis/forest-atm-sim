package Forest;

class BigTree extends Tree {

    BigTree(int height){
        super(height);
        this.width = 12;
        setAppearance();
    }

    private void setAppearance(){
        for (int i = 0; i < 3; i++){ this.appearance[i] = "     }}{      "; }
        this.appearance[3] = " \\#\\o |{ ##  ";
        this.appearance[4] = "####\\ | /#_/o#";
        this.appearance[5] = " #o# \\| / ### ";
        this.appearance[6] = "  #\\ #\\/##o  ";
        this.appearance[7] = "   ##\\ #o#   ";
        this.appearance[8] = "    ####   ";
        this.appearance[9] = "     o#    ";
    }
}
