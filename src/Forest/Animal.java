package Forest;
import java.awt.*;
import java.util.ArrayList;

/**
 * A forest animal.
 */
public abstract class Animal extends ForestEntity {

    /** Indicates whether this animal is moving right. */
    boolean goingRight;

    /** Different appearances of Animal*/
    abstract void rightAppearance();

    abstract void leftAppearance();

    /**
     * Constructs a new animal.
     */

    Animal(int height) {
        super(height);
        colour = Color.orange.darker();
        goingRight = true;
    }

    /**
     * Turns this animal around, causing it to reverse direction.
     */
    void turnAround() {
        goingRight = !goingRight;
        if (goingRight) {
            rightAppearance();
        } else {
            leftAppearance();
        }
    }

    private void move() {
        // Move one spot to the right or left.
        if (goingRight) {
            if ((c < (forest.getWidth() - width)) && (forest.getEntity(c + 1, r) == null)) {
                c = c + 1;
            } else {
                turnAround();
            }
        } else {
            if (c > 0 && forest.getEntity(c - 1, r) == null) {
                c -= 1;
            } else {
                turnAround();
            }
        }
    }

    private void tryChangingMovement(double randomInt) {
        //try to move up or down
        if (randomInt < 0.1) {
            //check if can move vertically up
            if (r < (forest.getHeight() - height) && (forest.getEntity(c, r + 1) == null)) {
                r += 1;
            }

        } else if (0.1 < randomInt && randomInt < 0.2) {
            //check if can move up
            if (r > 0 && (forest.getEntity(c, r - 1) == null)) {
                r -= 1;
            }
         //try to turn around
        } else if ( 0.2 < randomInt && randomInt < 0.3) {
            turnAround();
        }
    }

        /**
         * Causes this item to take its turn in the forest simulation.
         */
        public void update() {

            double d;
            d = Math.random();
            move();
            tryChangingMovement(d);

        }
}
