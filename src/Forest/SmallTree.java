package Forest;

class SmallTree extends Tree{

    SmallTree(int height){
        super(height);
        this.width = 2;
        setAppearance();
    }

    private void setAppearance(){
        this.appearance[0] = " }{ ";
        this.appearance[1] = " ## ";
        this.appearance[2] = "#o##";
        this.appearance[3] = " ## ";
    }
}
