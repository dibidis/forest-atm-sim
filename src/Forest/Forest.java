package Forest;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * A forest simulation.
 */
public class Forest {

    /**
     * The width and height of a character
     */
    private final int charWidth = 6;

    private final int charHeight = 10;

    /**
     * The width and height (in pixels) of the frame
     */
    private final int frameWidth = 680;

    private final int frameHeight = 600;

    /**
     * The height and width (in entities) of the whole forest
     */
    private final int height = frameHeight/charHeight;

    private final int width = frameWidth/charWidth;

    /** Array of entities */ //TODO Better description?

    private ForestEntity[][] entities =
            new ForestEntity[width][height];

    /** the last updated entity*/
    private ForestEntity lastUpdated;


    private boolean running = true;

    /** GETTERS */

    public int getWidth() { return width; }

    public int getHeight() { return height; }

    ForestEntity getEntity(int x, int y) {
        return entities[x][y];
    }

    public void setLastUpdated(ForestEntity entity){
        this.lastUpdated = entity;
    }

    public ForestEntity getlastUpdated(){
        return this.lastUpdated;
    }

    /**ADDING ENTITIES TO FOREST*/

    private void tryAddTrees(int numTrees){
        int maxTreeCapacity = 900;
        if (numTrees >= maxTreeCapacity){
            System.out.println("Good job! Your digital forest is full! Cannot add anymore trees." +
                    "Showing full forest");
            addTrees(maxTreeCapacity);
        } else {
            addTrees(numTrees);
            System.out.println("Trees successfully planted! Enjoy your forest!");
        }
    }

    private void tryAddAnimals(int numAnimals){
        int maxAnimalCapacity = 5;
        if (numAnimals >= maxAnimalCapacity){
            System.out.println("Too many animals! Overcrowding your forest! Showing forest at full animal capacity.");
            addAnimal(maxAnimalCapacity);
        } else {
            addAnimal(numAnimals);
            System.out.println("Animals successfully birthed! Enjoy your forest!");
        }
    }

    private void addTrees(int numTrees) {

        //for every 5 medium trees (25 small trees), grow a big tree instead.
        while (numTrees >= 25) {
            int bigTreeHeight = 10;
            BigTree newBigTree = new BigTree(bigTreeHeight);
            newBigTree.setForest(this);
            insertBigEntityInEmptySpot(newBigTree);
            numTrees -= 25;
        }
        //for every 10 small trees, grow a medium tree instead.
        while (numTrees >= 10 && numTrees < 25) {
            int mediumTreeHeight = 7;
            MediumTree newMedTree = new MediumTree(mediumTreeHeight);
            newMedTree.setForest(this);
            insertEntityInEmptySpot(newMedTree);
            numTrees -= 10;
        }
        while (numTrees >= 1 && numTrees < 10) {
            int smallTreeHeight = 4;
            SmallTree newSmallTree = new SmallTree(smallTreeHeight);
            newSmallTree.setForest(this);
            insertEntityInEmptySpot(newSmallTree);
            numTrees--;
        }
    }

    private void addAnimal(int numBears){
        int bearHeight = 3;
        for (int i = 0; i < numBears; i++) {
            Bear newBear = new Bear(bearHeight);
            newBear.setForest(this);
            insertEntityInEmptySpot(newBear);
        }
    }

    /** ADDING ENTITIES TO FOREST-ENTITY ARRAY */

    private void insertEntityInEmptySpot(ForestEntity entity) {
        boolean unsuccessfulInsert = true;
        while (unsuccessfulInsert) {
            //randomly find a spot in the forest to plant a tree
            int i = (int) (Math.random() * (width - (entity.getWidth() + 1)));
            int j = (int) (Math.random() * (height - (entity.getHeight() + 1)));
            //make sure the spot is empty
            if (checkIfSurroundingEmpty(entity, i, j)) {
                entities[i][j] = entity;
                entity.setLocation(i, j + (entity.getHeight() - 1));
                unsuccessfulInsert = false;
            }
        }
    }

    private void insertBigEntityInEmptySpot(ForestEntity entity){
        //systematically find a spot to plant a big tree
        //ints start at 1 so trees aren't planted on the edges of the forest
        int spaceBetweenTreesH = 5;
        int spaceBetweenTreesV = 7;
        for (int x = 1; x < (width - entity.getWidth()); x += spaceBetweenTreesH){
            for (int y = 1; y < (height - entity.getHeight() + 1); y += entity.getHeight()){
                if (checkIfSurroundingEmpty(entity, x, y)) {
                    entities[x][y] = entity;
                    entity.setLocation(x, y + spaceBetweenTreesV);
                    return;
                }
            }
        }
    }

    private boolean checkIfSurroundingEmpty(ForestEntity entity, int i, int j){
        boolean notOccupied = true;
        for (int h = 0; h < entity.getHeight(); h++){
            for (int w = 0; w < 2; w++){
                if (j != width - 3){
                if (entities[i + h][j + w] != null) {
                    notOccupied = false;
                }
                }
            }
        } return notOccupied;
    }


    /** DISPLAY THE FOREST*/

    public void run(int numTrees, int numAnimals) {
        // The window in which the forest exists.

        ForestFrame f = new ForestFrame(height, width, this);
        if (f.isAlwaysOnTopSupported()){
            f.setAlwaysOnTop(true);
        }
        f.addWindowListener(new WindowAdapter() {
                                @Override
                                public void windowClosing(WindowEvent e) {
                                    f.setVisible(false);
                                    f.dispose();
                                    f.forest.running = false;
                                }
                            });

        tryAddTrees(numTrees);
        tryAddAnimals(numAnimals);

        // Show it all!
        f.setSize(frameWidth, frameHeight);
        f.setLocation(10, 10);
        f.setVisible(true);

        // Every .3 seconds, tell each item in the forest to update.
        while (running) {

            for (int x = 0; x < (width - 1); x++) {
                for (int y = 0; y < (height - 1); y++) {
                    ForestEntity e = entities[x][y];
                    if (e != null) {
                        if (e != getlastUpdated()) {
                            entities[x][y].update();
                            setLastUpdated(e);
                            entities[x][y] = null;
                            entities[e.getX()][e.getY()] = e;
                        }
                    }
                }
            }

            // Tell the forest to redraw itself.
            f.repaint();

            // Wait .3 seconds before redoing the queue.
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                //not a big deal
            }
        }

    }
}
