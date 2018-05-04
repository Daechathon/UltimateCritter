import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Brandon Aldridge on 3/26/2018.
 */
public class StormageddonWasterOfTimeAndObfuscatorOfNamesNotToMentionTheAnnoyanceYouMustFeelFromThisExcessivelyLongAndIncrediblyPointlessName extends Critter {

    private static HashMap<StormageddonWasterOfTimeAndObfuscatorOfNamesNotToMentionTheAnnoyanceYouMustFeelFromThisExcessivelyLongAndIncrediblyPointlessName, CritterInfo> swarm = new HashMap<>();
    private static boolean newCycle;
    private boolean isFirst;
    private boolean hasMoved;
    private boolean movedThisRound;
    private int peaceTime;
    private static int peaceSum;
    private static boolean charge;
    private Squad squad;

    private static Class overlord;
    private static String[] hierarchy = {"RobbieCritter", "StormageddonWasterOfTimeAndObfuscatorOfNamesNotToMentionTheAnnoyanceYouMustFeelFromThisExcessivelyLongAndIncrediblyPointlessName"};
    

    public StormageddonWasterOfTimeAndObfuscatorOfNamesNotToMentionTheAnnoyanceYouMustFeelFromThisExcessivelyLongAndIncrediblyPointlessName() {

        movedThisRound = true;
        hasMoved = false;
        peaceTime = 0;
        squad = new Squad(this);


//        String className = "Foo";
//        String packageName = getClass().getPackage().getName();
//
//        try {
//            Class<?> clazz = Class.forName(packageName + "." + className);
//
//        } catch(Exception e){
//            e.printStackTrace();
//        }

    }

    @Override
    public Critter.Action getMove(CritterInfo info) {
        movedThisRound = true;
        if (!hasMoved) {
            swarm.put(this, info);
            hasMoved = true;
        }
        if (!newCycle) {
            peaceSum = 0;
            isFirst = true;
            newCycle = true;
        }
        peaceSum += peaceTime;
        peaceTime++;

        if (charge && peaceTime > 500) {
            if (info.getFront()  == Critter.Neighbor.OTHER) {
                peaceTime = 0;
                return Critter.Action.INFECT;
            } else if (info.getFront() == Critter.Neighbor.SAME || info.getFront() == Critter.Neighbor.WALL){
                peaceTime = 0;
            }
            return Critter.Action.HOP;
        }

        if (info.getFront() == Critter.Neighbor.OTHER) {
            peaceTime = 0;
            return Critter.Action.INFECT;
        } else if (info.getRight() == Critter.Neighbor.EMPTY || info.getRight() == Critter.Neighbor.OTHER) {
            return Critter.Action.RIGHT;
        } else if (info.getLeft() == Critter.Neighbor.EMPTY || info.getLeft() == Critter.Neighbor.OTHER) {
            return Critter.Action.LEFT;
        } else if (info.getBack() == Critter.Neighbor.EMPTY || info.getBack() == Critter.Neighbor.OTHER) {
            return Critter.Action.RIGHT;
        } else {
            return Critter.Action.INFECT;
        }
    }

    @Override
    public String toString() {

        return "þ";
    }

    public Color getColor() {
        newCycle = false;

        if (isFirst) {
            setSwarm();
            isFirst = false;
            return Color.RED;
        }
        return Color.GREEN;
    }

    private void setSwarm() {
        Set<StormageddonWasterOfTimeAndObfuscatorOfNamesNotToMentionTheAnnoyanceYouMustFeelFromThisExcessivelyLongAndIncrediblyPointlessName> hitList = new HashSet<>();
        for (StormageddonWasterOfTimeAndObfuscatorOfNamesNotToMentionTheAnnoyanceYouMustFeelFromThisExcessivelyLongAndIncrediblyPointlessName h : swarm.keySet()) {
            if (!h.movedThisRound) {
                hitList.add(h);

            }
            h.movedThisRound = false;
        }
        for (StormageddonWasterOfTimeAndObfuscatorOfNamesNotToMentionTheAnnoyanceYouMustFeelFromThisExcessivelyLongAndIncrediblyPointlessName h : hitList) {
            swarm.remove(h);
        }
        charge = false;
        if (peaceSum / (swarm.keySet().size() + 1) > 250) {
            charge = true;
        }
    }

    public class Squad{

        public CritterGridState[][] map;
        public Map<Critter, Point> critterPointMap;

        public Squad(Critter c){

            map = new CritterGridState[5][5];
            critterPointMap = new HashMap<>();
            critterPointMap.put(c, new Point(0, 0));
        }

        public void resize(int xSize, int ySize, int xOffset, int yOffset){

            //todo implement
        }

        public void update(Critter c){

            update((int) critterPointMap.get(c).getX(), (int) critterPointMap.get(c).getY());
        }

        public void update(int x, int y){

            //todo implement
        }


    }

    private static class CritterGridState{

        public enum gridState {UNKNOWN, ALLY, ENEMY, FLOOR, WALL}
        public enum gridDirection {NORTH, SOUTH, EAST, WEST}
    }
}

//stuff to look at for overlord implementation--------------------------------------------------------------------------------------------------------

/*
import java.lang.reflect.Method;

public class ReflectionCalls {
    public static void main(String[] args) {
        new ReflectionCalls();
    }

    public ReflectionCalls() {
        callMethod(true);
        callMethod(false);
    }

    private void callMethod(boolean isInstanceMethod) {

        String className = "DiscoveredClass";
        String staticMethodName = "methodStatic";
        String instanceMethodName = "methodInstance";
        Class<?>[] formalParameters = { int.class, String.class };
        Object[] effectiveParameters = new Object[] { 5, "hello" };
        String packageName = getClass().getPackage().getName();

        try {
            Class<?> clazz = Class.forName(packageName + "." + className);

            if (!isInstanceMethod) {
                Method method = clazz.getMethod(staticMethodName, formalParameters);
                method.invoke(null, effectiveParameters);
            }

            else {
                Method method = clazz.getMethod(instanceMethodName, formalParameters);
                Object newInstance = clazz.newInstance();
                method.invoke(newInstance, effectiveParameters);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package reflectionexp;

    public class DiscoveredClass {

        public static void methodStatic(int x, String string) {
            System.out.println("static method with " + x + " and " + string);
        }

        public void methodInstance(int x, String string) {
            System.out.println("instance method with " + x + " and " + string);
        }

    }
 */
