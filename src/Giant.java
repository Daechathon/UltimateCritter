import java.awt.*;


public class Giant extends Critter {
    private int stepCounter;

    public Giant() {
        stepCounter = 0;
    }

    @Override
    public Action getMove(CritterInfo info) {
        stepCounter++;

        if (info.getFront() == Neighbor.OTHER) {
            return Action.INFECT;
        } else if (info.getFront() == Neighbor.EMPTY) {
            return Action.HOP;
        } else {
            return Action.RIGHT;
        }
    }

    @Override
    public Color getColor() {
        return Color.GRAY;
    }

    @Override
    public String toString() {
        if ((stepCounter % 18) / 6 == 0) {
            return "fee";
        } else if ((stepCounter % 18) / 6 == 1) {
            return "fie";
        } else {
            return "fum";
        }
    }
}
