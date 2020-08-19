package Forest;

class MediumTree extends Tree{

    MediumTree(int height){
        super(height);
        this.width = 4;
        setAppearance();
    }

    private void setAppearance(){
        for (int i = 0; i < 2; i++){ this.appearance[i] = "   }|{   "; }
        this.appearance[2] = "  #}|{#  ";
        this.appearance[3] = " ##\\|/o# ";
        this.appearance[4] = "#o\\#|#/##";
        this.appearance[5] = "  ###o#  ";
        this.appearance[6] = "   ###   ";
    }

}
