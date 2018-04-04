import java.awt.*;
import java.lang.reflect.Field;

/**
 * Created by Brandon Aldridge on 3/28/2018.
 */
public class Haxxx extends Critter{

    private boolean amIFirst;
    private static boolean newCycle = false;
    private static boolean hacked = false;

    public Haxxx() {

        amIFirst = false;
    }

    @Override
    public Critter.Action getMove(CritterInfo info) {

        if (!newCycle) {

            amIFirst = true;
            newCycle = true;
        }

        if (info.getFront() == Neighbor.OTHER) {

            if(hacked){
                hackInfect();
            }
            return Action.INFECT;
        } else if (info.getFront() == Neighbor.EMPTY) {
            return Action.HOP;
        } else {
            return Action.RIGHT;
        }
    }

    @Override
    public String toString() {

        return "▒";
    }

    public Color getColor() {

        newCycle = false;

        if (amIFirst) {

            if(!hacked){

                initiateHack();
            }

            amIFirst = false;
            return Color.RED;
        }

        return Color.GREEN;
    }

    public void initiateHack(){

        hacked = true;

        try {
            Class model = Class.forName("CritterModel");
            Field grid = model.getDeclaredField("grid");
            Field info = model.getDeclaredField("info");
            boolean gridAccessible = grid.isAccessible();
            boolean infoAccessible = info.isAccessible();
            info.setAccessible(true);
            grid.setAccessible(true);



            info.setAccessible(infoAccessible);
            grid.setAccessible(gridAccessible);
        }catch(Exception e){System.out.println("" + e);}
    }

    public void hackInfect(){


    }
}
