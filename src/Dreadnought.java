import java.awt.*;

/**
 * Created by Brandon Aldridge on 3/9/2018.
 */
public class Dreadnought extends Critter {

    private int stepCount;

    public Dreadnought(){

        stepCount = 0;
    }

    public Action getMove(CritterInfo info){

        stepCount++;

        return Action.INFECT;
    }

    public Color getColor(){

        switch(stepCount % 6){

            case 0:
                return Color.red;
            case 1:
                return Color.orange;
            case 2:
                return Color.yellow;
            case 3:
                return Color.green;
            case 4:
                return Color.blue;
            case 5:
                return new Color(148,0,211);
            default:
                return Color.black;
        }
    }
}
