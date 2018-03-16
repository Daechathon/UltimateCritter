import java.awt.*;
import java.util.Random;


public class Lion extends Critter {

    private int stepCounter;
    private static final Random r = new Random();
    private Color color;

    public Lion() {
        stepCounter = 0;
        setColor();
    }

    @Override
    public Action getMove(CritterInfo info) {
        stepCounter++;
        // Change color every three moves
        if (stepCounter % 3 == 0) {
            setColor();
        }

        if (info.getFront() == Neighbor.OTHER){
            // Attack if enemy is in front
            return Action.INFECT;
        } else if (info.getFront() == Neighbor.WALL || info.getRight() == Neighbor.WALL) {
            // Turn left if there is a wall ahead or to the right
            return Action.LEFT;
        } else if (info.getFront() == Neighbor.SAME) {
            // Turn right if there is a Lion ahead
            return Action.RIGHT;
        } else {
            return Action.HOP;
        }
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "L";
    }

    private void setColor() {
        int n = r.nextInt() % 3;
        if (n == 0) {
            color = Color.RED;
        } else if (n == 1) {
            color = Color.GREEN;
        } else {
            color = Color.BLUE;
        }
    }
}
