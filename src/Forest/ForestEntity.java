package Forest;

import java.awt.*;

public abstract class ForestEntity {

    /** The font used to draw instances of this class. */
    private Font FONT = new Font("Monospaced", Font.PLAIN, 10);

    /** This Forest Entity's first coordinate, along the x-axis. c for column. */
    int c;
    /** This Forest Entity's second coordinate, along the y-axis. r for row */
    int r;

    /** The forest frame with which these entities live */
    Forest forest;

    /** Tree colour*/
    Color colour;

    /** The width of this forest entity */
    int width;

    /** The height of this forest entity */
    int height;

    /** The appearance of this animal, each value in the array is an ASCII line of the animal. */
    String[] appearance ;

    ForestEntity(int height){
        this.height = height;
        appearance = new String[height];
    }

    private boolean exists = true;


    abstract void update();

    void setLocation(int x, int y){
        this.c = x;
        this.r = y;
    }

    void setForest(Forest forest){this.forest = forest;}

    /**
     * Draws this tree.
     *
     * @param  g  the graphics context in which to draw this item.
     */
    void draw(Graphics g) {
            for (int i = 0; i < this.getHeight(); i++) {
                drawString(g, appearance[i], c, (-i + r));
            }
    }

    /**
     * Draws the given string in the given graphics context at
     * the given cursor location.
     *
     * @param  g  the graphics context in which to draw the string.
     * @param  s  the string to draw.
     * @param  x  the x-coordinate of the string's cursor location.
     * @param  y  the y-coordinate of the string's cursor location.
     */
    private void drawString(Graphics g, String s, int x, int y) {
        if (appearance[0] != null) {
            g.setColor(colour);
            g.setFont(FONT);
            FontMetrics fm = g.getFontMetrics(FONT);
            g.drawString(s, x * fm.charWidth('W'), y * fm.getAscent());
        }
    }

    int getHeight(){return this.height;}

    int getWidth() {return this.width;}

    int getX(){return this.c;}
    int getY(){return this.r;}
}