import java.util.*;
import java.awt.*;

/**
 * Robbie Sollie - RobbieCritter.java - EGR226 - CBU - 3/15/18
 */
public class RobbieCritter extends Critter {
    private enum ColorMode {
        RAINBOW, CYCLE, AXIS, SHELL_CYCLE, SHELL
    }

    private static final ColorMode VISUALS = ColorMode.RAINBOW;
    private static final int COLOR_MULTIPLIER = 25;
    private static final boolean COORDINATED = true;
    private static HashMap<RobbieCritter, CritterInfo> swarm = new HashMap<>();
    private static boolean newCycle;
    private boolean amIFirst;
    private boolean hasMoved;
    private boolean movedThisRound;
    private int peaceTime;
    private static int peaceSum;
    private static boolean charge;
    private static int chargeDirection;
    private HashMap<Point, RobbieCritter> squadMap;
    private Point coords;
    private int squadBlue;
    private Color squadColor;
    private static RobbieCritter last;
    private int layer;
    private static int stepCounter = 0;


    public RobbieCritter() {
        movedThisRound = true;
        hasMoved = false;
        peaceTime = 0;
//        try {
//            Class c = Class.forName("StormageddonWasterOfTimeAndObfuscatorOfNames" +
//                    "NotToMentionTheAnnoyanceYouMustFeel" +
//                    "FromThisExcessivelyLongAndIncrediblyPointlessName");
//            Constructor thisMakesThings = c.getConstructors()[0];
//            Critter next = (Critter) thisMakesThings.newInstance();
//        } catch (Exception e) {
//            throw new IllegalArgumentException("brandon did it");
//        }
        if (last != null) {
            squadMap = last.addToMap(this);
            squadColor = last.squadColor;
            squadBlue = last.squadBlue;
        } else {
            squadMap = new HashMap<>();
            coords = new Point(0, 0);
            squadMap.put(coords, this);
            Random r = new Random();
            squadBlue = r.nextInt(255);
            squadColor = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
        }
    }


    @Override
    public Action getMove(CritterInfo info) {
        movedThisRound = true;
        last = this;
        if (COORDINATED) {
            swarm.put(this, info);
        } else {
            if (!hasMoved) {
                swarm.put(this, info);
                hasMoved = true;
            }
        }
        if (!newCycle) {
            stepCounter++;
            peaceSum = 0;
            amIFirst = true;
            newCycle = true;
        }
        peaceSum += peaceTime;
        peaceTime++;


//        if (charge && peaceTime > 500) {
//            if (info.getFront() == Neighbor.OTHER) {
//                peaceTime = 0;
//                return Action.INFECT;
//            } else if (info.getDirection() == Direction.NORTH && chargeDirection != 0) {
//                if (chargeDirection == 3) {
//                    return Action.LEFT;
//                } else {
//                    return Action.RIGHT;
//                }
//            } else if (info.getDirection() == Direction.EAST && chargeDirection != 1) {
//                if (chargeDirection == 0) {
//                    return Action.LEFT;
//                } else {
//                    return Action.RIGHT;
//                }
//            } else if (info.getDirection() == Direction.SOUTH && chargeDirection != 2) {
//                if (chargeDirection == 1) {
//                    return Action.LEFT;
//                } else {
//                    return Action.RIGHT;
//                }
//            } else if (info.getDirection() == Direction.WEST && chargeDirection != 3) {
//                if (chargeDirection == 2) {
//                    return Action.LEFT;
//                } else {
//                    return Action.RIGHT;
//                }
//            } else {
//                if (info.getFront() == Neighbor.SAME || info.getFront() == Neighbor.WALL)
//                    peaceTime = 0;
////                return Action.HOP;
//            }
//        }


        layer = 0;
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
            RobbieCritter north = squadMap.get(new Point(coords.x, coords.y + 1));
            RobbieCritter south = squadMap.get(new Point(coords.x, coords.y - 1));
            RobbieCritter east = squadMap.get(new Point(coords.x + 1, coords.y));
            RobbieCritter west = squadMap.get(new Point(coords.x - 1, coords.y));
            layer = 100;
            Action current = Action.INFECT;
            if (north != null && north.layer < layer) {
                current = turnToDirection(info, Direction.NORTH);
                layer = north.layer + 1;
            }
            if (south != null && south.layer < layer) {
                current = turnToDirection(info, Direction.SOUTH);
                layer = south.layer + 1;
            }
            if (east != null && east.layer < layer) {
                current = turnToDirection(info, Direction.EAST);
                layer = east.layer + 1;
            }
            if (west != null && west.layer < layer) {
                current = turnToDirection(info, Direction.WEST);
                layer = west.layer + 1;
            }
//            if (peaceTime > 10) {
//                double shortestDistance = 100;
//                RobbieCritter nearestFront = this;
//                for (RobbieCritter h : squadMap.values()) {
//                    if (h.peaceTime < 10 && distance(h) < shortestDistance) {
//                        shortestDistance = distance(h);
//                        nearestFront = h;
//                    }
//                }
//                if (Math.abs(nearestFront.coords.getX() - this.coords.getX()) > Math.abs(nearestFront.coords.getY() - this.coords.getY())) {
//                    if (nearestFront.coords.getX() < this.coords.getX()) {
//                        if (info.getDirection() != Direction.EAST) {
//                            current = turnToDirection(info, Direction.EAST);
//                        } else {
//                            current = Action.HOP;
//                        }
//                    } else {
//                        if (info.getDirection() != Direction.WEST) {
//                            current = turnToDirection(info, Direction.WEST);
//                        } else {
//                            current = Action.HOP;
//                        }
//                    }
//                } else {
//                    if (nearestFront.coords.getY() < this.coords.getY()) {
//                        if (info.getDirection() != Direction.NORTH) {
//                            current = turnToDirection(info, Direction.NORTH);
//                        } else {
//                            current = Action.HOP;
//                        }
//                    } else {
//                        if (info.getDirection() != Direction.SOUTH) {
//                            current = turnToDirection(info, Direction.SOUTH);
//                        } else {
//                            current = Action.HOP;
//                        }
//                    }
//                }
//
//            }
//            if (current == Action.HOP && info.getFront() == Neighbor.EMPTY) {
//                if (info.getDirection() == Direction.NORTH) {
//                    coords.setLocation(coords.getX(), coords.getY() + 1);
//                }
//                if (info.getDirection() == Direction.EAST) {
//                    coords.setLocation(coords.getX() + 1, coords.getY());
//                }
//                if (info.getDirection() == Direction.SOUTH) {
//                    coords.setLocation(coords.getX(), coords.getY() - 1);
//                }
//                if (info.getDirection() == Direction.WEST) {
//                    coords.setLocation(coords.getX() - 1, coords.getY());
//                }
//            }
            return current;
        }
    }

    private double distance(RobbieCritter h) {
        double aSquared = Math.pow(h.coords.getX() - this.coords.getX(), 2);
        double bSquared = Math.pow(h.coords.getY() - this.coords.getY(), 2);
        return Math.sqrt(aSquared + bSquared);
    }

    private Action turnToDirection(CritterInfo info, Direction d) {
        if (info.getDirection() == Direction.NORTH && d != Direction.NORTH) {
            if (d == Direction.WEST) {
                return Action.LEFT;
            } else {
                return Action.RIGHT;
            }
        } else if (info.getDirection() == Direction.EAST && d != Direction.EAST) {
            if (d == Direction.NORTH) {
                return Action.LEFT;
            } else {
                return Action.RIGHT;
            }
        } else if (info.getDirection() == Direction.SOUTH && d != Direction.SOUTH) {
            if (d == Direction.EAST) {
                return Action.LEFT;
            } else {
                return Action.RIGHT;
            }
        } else if (info.getDirection() == Direction.WEST && d != Direction.WEST) {
            if (d == Direction.SOUTH) {
                return Action.LEFT;
            } else {
                return Action.RIGHT;
            }
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
        if (convertCardinal(info.getDirection()) - maxDir == 0) {
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

    private HashMap<Point, RobbieCritter> addToMap(RobbieCritter hat) {
        CritterInfo mine = swarm.get(this);
        Point p = new Point();
        if (mine.getDirection() == Direction.NORTH) {
            p.setLocation(this.coords.x, this.coords.y + 1);
        } else if (mine.getDirection() == Direction.EAST) {
            p.setLocation(this.coords.x + 1, this.coords.y);
        } else if (mine.getDirection() == Direction.SOUTH) {
            p.setLocation(this.coords.x, this.coords.y - 1);
        } else if (mine.getDirection() == Direction.WEST) {
            p.setLocation(this.coords.x - 1, this.coords.y);
        }
        hat.coords = p;
        squadMap.put(p, hat);
        return squadMap;
    }

    @Override
    public String toString() {
//        return "þ";
//        if (VISUALS == ColorMode.SHELL_CYCLE) {
//            if (stepCounter % 200 < 100) {
//                return "" + layer;
//            }
//        }
        return "█";
//        return "" + layer;
    }

    public Color getColor() {
        newCycle = false;


        if (amIFirst) {
            killThemAll();
//            setSwarm();
            amIFirst = false;
            return Color.RED;
        }
        if (VISUALS == ColorMode.CYCLE) {
            if (stepCounter % 200 < 100) {
                return new Color(Math.abs(coords.x) * COLOR_MULTIPLIER > 254 ? 255 : Math.abs(coords.x) * COLOR_MULTIPLIER,
                        Math.abs(coords.y) * COLOR_MULTIPLIER > 254 ? 255 : Math.abs(coords.y) * COLOR_MULTIPLIER, squadBlue);
            }
        }
        if (VISUALS == ColorMode.AXIS || VISUALS == ColorMode.CYCLE) {
            if (coords.x == 0 && coords.y == 0) {
                return Color.BLACK;
            } else if (coords.x == 0 || coords.y == 0) {
                return Color.WHITE;
            }
        } else if (VISUALS == ColorMode.RAINBOW) {
            return new Color(Math.abs(coords.x) * COLOR_MULTIPLIER > 254 ? 255 : Math.abs(coords.x) * COLOR_MULTIPLIER,
                    Math.abs(coords.y) * COLOR_MULTIPLIER > 254 ? 255 : Math.abs(coords.y) * COLOR_MULTIPLIER, squadBlue);
        } else if (VISUALS == ColorMode.SHELL || VISUALS == ColorMode.SHELL_CYCLE) {
            int value = layer * COLOR_MULTIPLIER > 254 ? 0 : 255 - (layer * COLOR_MULTIPLIER);
            return new Color(value, value, value);
        }
        return squadColor;
    }

    private void killThemAll() {
        Set<RobbieCritter> hitList = new HashSet<>();
        for (RobbieCritter h : swarm.keySet()) {
            if (!h.movedThisRound) {
                hitList.add(h);
            }
            h.movedThisRound = false;
        }
        for (RobbieCritter h : hitList) {
            swarm.remove(h);
        }
    }

    private void setSwarm() {
        charge = false;
//        charge = peaceSum / (swarm.keySet().size() + 1) > 250;
        int[] directionCount = new int[4];
        for (CritterInfo i : swarm.values()) {
            for (Direction d : Direction.values()) {
                if (getNeighborCompass(i, d) == Neighbor.OTHER) {
                    iterateDirection(directionCount, d);
                }
            }
        }
        chargeDirection = 0;
        int max = directionCount[0];
        if (directionCount[1] > max) {
            chargeDirection = 1;
            max = directionCount[1];
        }
        if (directionCount[2] > max) {
            chargeDirection = 2;
            max = directionCount[2];
        }
        if (directionCount[3] > max) {
            chargeDirection = 3;
        }
    }

    private Critter.Neighbor getNeighborCompass(CritterInfo i, Direction compass) {
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

    private Critter.Direction getDirectionCompass(CritterInfo i, Direction compass) {
        int numDirection = convertCardinal(i.getDirection());
        int numSearch = convertCardinal(compass);

        if (numDirection == numSearch) {
            return i.getFrontDirection();
        } else if ((numDirection + 3) % 4 == numSearch) {
            return i.getLeftDirection();
        } else if ((numDirection + 1) % 4 == numSearch) {
            return i.getRightDirection();
        } else {
            return i.getBackDirection();
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

    private void mergeMaps(HashMap<Point, RobbieCritter> map, Point offset, RobbieCritter theFused) {

    }


}