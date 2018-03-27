import org.omg.PortableInterceptor.DISCARDING;

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
    private static int chargeDirection;


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
            } else if (info.getDirection() == Direction.NORTH && chargeDirection != 0) {
                if (chargeDirection == 3) {
                    return Action.LEFT;
                } else {
                    return Action.RIGHT;
                }
            } else if (info.getDirection() == Direction.EAST && chargeDirection != 1) {
                if (chargeDirection == 0) {
                    return Action.LEFT;
                } else {
                    return Action.RIGHT;
                }
            } else if (info.getDirection() == Direction.SOUTH && chargeDirection != 2) {
                if (chargeDirection == 1) {
                    return Action.LEFT;
                } else {
                    return Action.RIGHT;
                }
            } else if (info.getDirection() == Direction.WEST && chargeDirection != 3) {
                if (chargeDirection == 2) {
                    return Action.LEFT;
                } else {
                    return Action.RIGHT;
                }
            } else {
                if (info.getFront() == Neighbor.SAME || info.getFront() == Neighbor.WALL)
                    peaceTime = 0;
                return Action.HOP;
            }
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
            killThemAll();
            setSwarm();
            amIFirst = false;
            return Color.RED;
        }
        return Color.ORANGE;
    }

    private void killThemAll() {
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
    }

    private void setSwarm() {
        charge = false;
        charge = peaceSum / (swarm.keySet().size() + 1) > 250;
        int north = 0, south = 0, east = 0, west = 0;
        for (CritterInfo i : swarm.values()) {
            for (Direction d : Direction.values()) {
                if (getCompass(i, d) == Neighbor.EMPTY) {
                    switch (d) {
                        case NORTH:
                            north++;
                            break;
                        case SOUTH:
                            south++;
                            break;
                        case EAST:
                            east++;
                            break;
                        case WEST:
                            west++;
                            break;
                    }
                }
            }
        }
        chargeDirection = 0;
        int max = north;
        if (east > max) {
            chargeDirection = 1;
            max = east;
        } if (south > max) {
            chargeDirection = 2;
            max = south;
        } if (west > max) {
            chargeDirection = 3;
        }
    }

    private Critter.Neighbor getCompass (CritterInfo i, Direction compass) {
        int numDirection = convertCardinal(i.getDirection());
        int numSearch = convertCardinal(compass);

        if (numDirection == numSearch) {
            return i.getFront();
        } else if ((numDirection + 3) % 4 == numSearch) {
            return i.getLeft();
        } else if ((numDirection + 1) % 4 == numSearch) {
            return i.getRight();
        } else {
            return i.getBack();
        }
    }

    private int convertCardinal(Direction compass) {
        if (compass == Direction.NORTH)
            return 0;
        if (compass == Direction.EAST)
            return 1;
        if (compass == Direction.SOUTH)
            return 2;
        return 3;
    }

}