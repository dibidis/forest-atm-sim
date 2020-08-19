package Forest;

public class Bear extends Animal {

    Bear(int height) {
        super(height);
        this.width = 12;
        turnAround();
    }

    public void leftAppearance(){
        this.appearance[0] = "  /\\   /\\  ";
        this.appearance[1] = "\\__'__, )\"";
        this.appearance[2] = "_, ____ ";

    }

    public void rightAppearance(){
        this.appearance[1] = "\"( ,__' _/";
        this.appearance[2] = "  ____ ,_  ";
    }

}
