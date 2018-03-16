import java.awt.*;


public class Bear extends Critter {

    private final boolean polar;
    private int stepCounter;

    // Constructor
    // Start off the step counter at zero and initialise polar
    public Bear(boolean polar) {
        this.polar = polar;
        stepCounter = 0;
    }

    // Handles the moves for the bear based on the given spec
    @Override
    public Action getMove(CritterInfo info) {
        stepCounter++;

        if (info.getFront() == Neighbor.OTHER){
            // Attack if enemy is in front
            return Action.INFECT;
        } else if (info.getFront() == Neighbor.EMPTY) {
            // Hop if the way ahead is not blocked
            return Action.HOP;
        } else {
            // Turn left if the tile ahead is blocked
            return Action.LEFT;
        }
    }

    // Returns WHITE if the bear is a polar bear, otherwise returns BLACK
    @Override
    public Color getColor() {
        if (polar) {
            return Color.WHITE;
        } else {
            return Color.BLACK;
        }
    }

    // Returns slash or backslash, depending on the value of stepCounter
    @Override
    public String toString() {
        if (stepCounter % 2 == 0) {
            return "/";
        } else {
            return "\\";
        }
    }
}
