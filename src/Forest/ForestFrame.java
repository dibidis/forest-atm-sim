package Forest;
import java.awt.*;
import javax.swing.JFrame;

/**
 * Displays the fish tank.
 */
public class ForestFrame extends JFrame {

    /** The forest frame with which these entities live */
    public Forest forest;

    /** Height of frame in entities */
    private int height;

    /** Width of frame in entities */
    private int width;


    ForestFrame(int height, int width, Forest forest){
        this.height = height;
        this.width = width;
        this.forest = forest;
    }

    /**
     * Paints this forest.
     *
     * @param  g  the graphics context to use for painting.
     */
    public void paint(Graphics g) {

        // Get my width and height.
        int w = getBounds().width;
        int h = getBounds().height;

        // Paint the window white.
        g.setColor(Color.green.darker().darker().darker().darker());
        g.fillRect(0, 0, w, h);

        // Tell all the Forest Frame items to draw themselves.
        for (int a = 0; a != (int) (width); a++) {
            for (int b = 0; b != (int) (height); b++) {
                if (forest.getEntity(a, b) != null) {
                    forest.getEntity(a, b).draw(g);
                    }
                }
            }
        }

    }
