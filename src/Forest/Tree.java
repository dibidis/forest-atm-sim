package Forest;
import java.awt.*;

/**
 * Tree.
 */

public abstract class Tree extends ForestEntity {

  /** Lets the tree shimmy*/
  boolean shift;

  /**
   * Constructs a new tree item.
   */
  Tree(int height) {
    super(height);
    colour  = Color.green.darker();
    shift = true;
  }

  /**
   * Causes this item to take its turn in the fish-tank simulation.
   */
  public void update() {
    //Trees don't move!
    return;
  }

}
