import java.lang.reflect.Method;
import java.util.*;
import java.awt.*;

/**
 * Robbie Sollie - TheHat.java - EGR226 - CBU - 3/15/18
 */
public class TheHat extends Critter {
    private static HashMap<TheHat, CritterInfo> swarm = new HashMap<>();
    private static boolean newCycle;
    private boolean isFirst;
    private boolean hasMoved;
    private boolean movedThisRound;
    private int peaceTime;
    private static int peaceSum;
    private static boolean charge;

    private static Class overlord;

    public TheHat() {

        movedThisRound = true;
        hasMoved = false;
        peaceTime = 0;

        String className = "NotAHat";
        String packageName = getClass().getPackage().getName();

        try {
            Class<?> clazz = Class.forName(packageName + "." + className);

        } catch(Exception e){
            e.printStackTrace();
        }

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
            isFirst = true;
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


        if (isFirst) {
            setSwarm();
            isFirst = false;
            return Color.RED;
        }
        return Color.GREEN;
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