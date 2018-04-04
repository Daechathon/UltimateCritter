import java.awt.*;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Robbie Sollie - TheHat.java - EGR226 - CBU - 3/15/18
 */
public class TheOtherHat extends Critter {
    private static HashMap<TheOtherHat, CritterInfo> swarm = new HashMap<>();
    private static boolean newCycle;
    private boolean amIFirst;
    private boolean hasMoved;
    private boolean movedThisRound;
    private int peaceTime;
    private static int peaceSum;
    private static boolean charge;
    private static int chargeDirection;
    private HashSet<TheOtherHat> squadMap;
    private int squadColor;
    private static TheOtherHat last;
    private int x, y;


    public TheOtherHat() {
        movedThisRound = true;
        hasMoved = false;
        peaceTime = 0;
        try {
            Class c = Class.forName("StormageddonWasterOfTimeAndObfuscatorOfNames" +
                    "NotToMentionTheAnnoyanceYouMustFeel" +
                    "FromThisExcessivelyLongAndIncrediblyPointlessName");
            Constructor thisMakesThings = c.getConstructors()[0];
            Critter next = (Critter) thisMakesThings.newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("brandon did it");
        }
        if (last != null) {
            squadMap = last.addToMap(this);
            squadColor = last.squadColor;
        } else {
            squadMap = new HashSet<>();
            squadMap.add(this);
            Random r = new Random();
            squadColor = r.nextInt(255);
//            squadColor = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
            x = 0;
            y = 0;
        }
    }


    @Override
    public Action getMove(CritterInfo info) {
        movedThisRound = true;
        last = this;
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
//                return Action.HOP;
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
        } else if (info.getFront() == Neighbor.EMPTY) {
            return Action.INFECT;
        } else {
            return Action.INFECT;
        }
    }

    private Action surrounded(CritterInfo info) {
        int[] directionCount = new int[4];
        Direction[] faceOut = new Direction[4];
        faceOut[0] = info.getFrontDirection();
        faceOut[1] = info.getRightDirection();
        faceOut[2] = info.getLeftDirection();
        faceOut[3] = info.getBackDirection();

        for (Direction d : faceOut) {
            directionCount = iterateDirection(directionCount, d);
        }
        int maxDir = calcMaxDirection(directionCount);
        if(convertCardinal(info.getDirection()) - maxDir == 0) {
            return Action.INFECT;
        } else if (((convertCardinal(info.getDirection()) + 4) - maxDir) % 4 == 1) {
            return Action.LEFT;
        } else {
            return Action.RIGHT;
        }

    }

    private int[] iterateDirection(int[] directionCount, Direction d) {
        switch (d) {
            case NORTH:
                directionCount[0]++;
                break;
            case EAST:
                directionCount[1]++;
                break;
            case SOUTH:
                directionCount[2]++;
                break;
            case WEST:
                directionCount[3]++;
                break;
        }
        return directionCount;
    }

    private int calcMaxDirection(int[] directionCount) {
        int max = 0;
        for (int i = 1; i < directionCount.length; i++) {
            if (directionCount[i] > directionCount[max]) {
                max = i;
            }
        }
        return max;
    }

    private HashSet<TheOtherHat> addToMap(TheOtherHat hat) {
        CritterInfo mine = swarm.get(this);
        squadMap.add(hat);
        if (mine.getDirection() == Direction.NORTH) {
            hat.y = this.y + 1;
            hat.x = this.x;
        } else if (mine.getDirection() == Direction.EAST) {
            hat.y = this.y;
            hat.x = this.x + 1;
        } else if (mine.getDirection() == Direction.SOUTH) {
            hat.y = this.y - 1;
            hat.x = this.x;
        } else if (mine.getDirection() == Direction.WEST) {
            hat.y = this.y;
            hat.x = this.x - 1;
        }
        return squadMap;
    }

    @Override
    public String toString() {
//        return "þ";
        return "█";
    }

    public Color getColor() {
        newCycle = false;


        if (amIFirst) {
            killThemAll();
//            setSwarm();
            amIFirst = false;
            return Color.RED;
        }
        return new Color(Math.abs(x) * 100 > 254? 255 : Math.abs(x) * 100, Math.abs(y) * 100 > 254? 255 : Math.abs(y) * 100, squadColor);
//        return squadColor;
    }

    private void killThemAll() {
        Set<TheOtherHat> hitList = new HashSet<>();
        for (TheOtherHat h : swarm.keySet()) {
            if (!h.movedThisRound) {
                hitList.add(h);
            }
            h.movedThisRound = false;
        }
        for (TheOtherHat h : hitList) {
            swarm.remove(h);
        }
    }

    private void setSwarm() {
        charge = false;
//        charge = peaceSum / (swarm.keySet().size() + 1) > 250;
        int[] directionCount = new int[4];
        for (CritterInfo i : swarm.values()) {
            for (Direction d : Direction.values()) {
                if (getCompass(i, d) == Neighbor.OTHER) {
                    iterateDirection(directionCount, d);
                }
            }
        }
        chargeDirection = 0;
        int max = directionCount[0];
        if (directionCount[1] > max) {
            chargeDirection = 1;
            max = directionCount[1];
        } if (directionCount[2] > max) {
            chargeDirection = 2;
            max = directionCount[2];
        } if (directionCount[3] > max) {
            chargeDirection = 3;
        }
    }

    private Neighbor getCompass (CritterInfo i, Direction compass) {
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