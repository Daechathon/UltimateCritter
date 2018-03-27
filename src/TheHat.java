import java.util.*;
import java.awt.*;

/**
 * Robbie Sollie - TheHat.java - EGR226 - CBU - 3/15/18
 */
public class TheHat extends Critter {
    private static HashMap<TheHat, CritterInfo> swarm = new HashMap<>();
    private static boolean newCycle;
    private boolean amIFirst;
    private boolean hasMoved;
    private boolean movedThisRound;
    private int peaceTime;
    private static int peaceSum;
    private static boolean charge;


    public TheHat() {
        movedThisRound = true;
        hasMoved = false;
        peaceTime = 0;
    }

    @Override
    public Action getMove(CritterInfo info) {
        movedThisRound = true;
        if (!hasMoved) {
            swarm.put(this, info);
            hasMoved = true;
        }
        if (!newCycle) {
            peaceSum = 0;
            amIFirst = true;
            newCycle = true;
        }
        peaceSum += peaceTime;
        peaceTime++;

        if (charge && peaceTime > 500) {
            if (info.getFront()  == Neighbor.OTHER) {
                peaceTime = 0;
                return Action.INFECT;
            } else if (info.getFront() == Neighbor.SAME || info.getFront() == Neighbor.WALL){
                peaceTime = 0;
            }
            return Action.HOP;
        }

        if (info.getFront() == Neighbor.OTHER) {
            peaceTime = 0;
            return Action.INFECT;
        } else if (info.getRight() == Neighbor.EMPTY || info.getRight() == Neighbor.OTHER) {
            return Action.RIGHT;
        } else if (info.getLeft() == Neighbor.EMPTY || info.getLeft() == Neighbor.OTHER) {
            return Action.LEFT;
        } else if (info.getBack() == Neighbor.EMPTY || info.getBack() == Neighbor.OTHER) {
            return Action.RIGHT;
        } else {
            return Action.INFECT;
        }
    }

    @Override
    public String toString() {
        return "þ";
    }

    public Color getColor() {
        newCycle = false;


        if (amIFirst) {
            setSwarm();
            amIFirst = false;
            return Color.RED;
        }
        return Color.ORANGE;
    }

    private void setSwarm() {
        Set<TheHat> hitList = new HashSet<>();
        for (TheHat h : swarm.keySet()) {
            if (!h.movedThisRound) {
                hitList.add(h);

            }
            h.movedThisRound = false;
        }
        for (TheHat h : hitList) {
            swarm.remove(h);
        }
        charge = false;
        if (peaceSum / (swarm.keySet().size() + 1) > 250) {
            charge = true;
        }
    }

}
