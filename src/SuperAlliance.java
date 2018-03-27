import java.awt.*;

/**
 * Created by Brandon Aldridge on 3/16/2018.
 */
public class SuperAlliance extends Critter {

    public SuperAlliance(){

    }

    public Action getMove(CritterInfo info){

        return Action.INFECT;
    }

    public Color getColor(){

        return Color.CYAN;
    }
}
