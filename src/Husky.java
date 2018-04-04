
/**
 * Brandon Aldridge:
 *      this will be used exclusively for testing purposes
 */

/*

Sam Hopkins

CSE 142 AD

Dan Grossman

TA: Yuki Liang

Assignment 9



This Husky starts the simulation with a flytrap-like

behavior, modified not to waste turns facing inward.

Once a certain percentage of Huskies have not

had any contact with other creatures for some number

of moves, they all swarm in a pack, first east, then

west, then north, then south.

*/


import java.awt.*;

import java.util.*;



public class Husky extends Critter{



    //a static field to keep track of all the instances of Husky

    public static ArrayList<Husky> huskyList = new ArrayList<Husky>();



    //declare fields

    private Neighbor[] sides = new Neighbor[4];

    private int movesSinceOther;

    private boolean headedLeft;

    private int numSidesClosed;

    private int movesSinceSwarm;

    private boolean swarm;

    private int countClosed;

    private CritterInfo info;

    private int moveCount;

    private int rand1;

    private int rand2;



    //declare constants

    private static final double FRAC_HUSKIES_TO_SWARM = 0.9;



    public Husky() {

        Random r = new Random();

        rand1 = r.nextInt(90);

        rand2 = r.nextInt(70);

        headedLeft = true;

        huskyList.add(this);

        movesSinceOther = 0;

        numSidesClosed = 0;

        movesSinceSwarm = huskyList.get(0).getMovesSinceSwarm();

        swarm = huskyList.get(0).getSwarm();

        countClosed = 0;

        moveCount = huskyList.get(0).getMoveCount();

    }



    //return a color to represent Husky

    public Color getColor(){

        return Color.BLUE;

    }



    //return a string to represent Husky

    public String toString(){

        return "H";

    }



    //refresh the state and test for swarming behavior.

    //Return the appropriate action for the behavior mode.

    public Action getMove(CritterInfo info){

        refreshState(info);

        if (swarm) {

            movesSinceSwarm++;

            return swarmBehavior();

        } else {

            return normalBehavior();

        }

    }



    //determine the appropriate swarming behavior based on the

    //number of moves since the beginning of the swarm.

    private Action swarmBehavior(){

        Direction d = info.getDirection();

        if (sides[0] == Neighbor.OTHER){

            return Action.INFECT;

        } else if (sides[1] == Neighbor.OTHER) {

            return Action.LEFT;

        } else if (sides[3] == Neighbor.OTHER) {

            return Action.RIGHT;

        } else if (movesSinceSwarm < 100 && !(d == Direction.EAST)) {

            return Action.LEFT;

        } else if (movesSinceSwarm > 100 && movesSinceSwarm < 200 &&

                !(d == Direction.WEST)) {

            return Action.RIGHT;

        } else if (movesSinceSwarm > 200 && movesSinceSwarm < rand1 + 200 &&

                !(d == Direction.EAST)) {

            return Action.LEFT;

        } else if (movesSinceSwarm > rand1 + 200 && movesSinceSwarm < 350 &&

                !(d == Direction.NORTH)){

            return Action.RIGHT;

        } else if (movesSinceSwarm > 350 && movesSinceSwarm < 450 &&

                !(d == Direction.SOUTH)) {

            return Action.RIGHT;

        } else if (movesSinceSwarm > 450 && movesSinceSwarm < rand2 + 450 &&

                !(d == Direction.NORTH)) {

            return Action.LEFT;

        } else if (movesSinceSwarm > rand2 + 450 && movesSinceSwarm < 510) {

            return normalBehavior();

        } else if (movesSinceSwarm < 510) {

            return Action.HOP;

        } else {

            movesSinceSwarm = 0;

            return Action.HOP;

        }

    }



    //determine the flytrap-esque behavior

    //based on surroundings.

    private Action normalBehavior(){

        if (sides[0] == Neighbor.OTHER){

            return Action.INFECT;

        } else if (countClosed == 3){

            return sideBehavior();

        } else if (countClosed == 2 && isOpen(sides[0]) == isOpen(sides[2])){

            return sandwichBehavior();

        } else if (countClosed == 2) {

            return cornerBehavior();

        } else if (countClosed == 1){

            return threeSideSpinBehavior();

        } else {

            return Action.RIGHT;

        }

    }



    //return the proper action for a Husky in normal

    //mode touching another Husky or a wall on one side.

    private Action threeSideSpinBehavior(){

        if (headedLeft && isOpen(sides[1])) {

            return Action.LEFT;

        } else if (headedLeft) {

            headedLeft = false;

            return Action.RIGHT;

        } else if (!headedLeft && isOpen(sides[3])) {

            return Action.RIGHT;

        } else {

            headedLeft = true;

        }

        return Action.LEFT;

    }



    //return the proper action for a Husky on the corner

    //of a cluster.

    private Action cornerBehavior(){

        if (isOpen(sides[1])){

            return Action.LEFT;

        } else {

            return Action.RIGHT;

        }

    }



    //return the proper action for a Husky in normal

    //mode with a Husky or a Wall on two, opposite sides.

    private Action sandwichBehavior(){

        if (!isOpen(sides[0])) {

            return Action.RIGHT;

        } else {

            return Action.INFECT;

        }

    }



    //return the proper action for a Husky in normal

    //mode on the side of a cluster.

    private Action sideBehavior(){

        if (isOpen(sides[0])){

            return Action.INFECT;

        } else if (isOpen(sides[1])) {

            return Action.LEFT;

        } else {

            return Action.RIGHT;

        }

    }



    //return whether a Neighbor is a Husky

    //or a wall.

    private boolean isOpen(Neighbor side){

        return !(side == Neighbor.SAME || side == Neighbor.WALL);

    }



    //getter for movesSinceOther

    public int getMovesSinceOther(){

        return movesSinceOther;

    }



    //read in data from info, update fields

    private void refreshState(CritterInfo info){

        purgeList();

        moveCount++;



        //determine the surroundings

        sides[0] = info.getFront();

        sides[1] = info.getLeft();

        sides[2] = info.getBack();

        sides[3] = info.getRight();



        //find out how many other creatures are around, and where they might appear

        int others = 0;

        countClosed = 0;

        for (int i = 0; i < sides.length; i++) {

            if (sides[i] == Neighbor.OTHER) {

                others++;

            }

            if (sides[i] == Neighbor.SAME || sides[i] == Neighbor.WALL){

                countClosed++;

            }

        }



        if (others == 0){

            movesSinceOther++;

        } else {

            movesSinceOther = 0;

        }



        //find out how many other huskies have seen other creatures recently

        int countNoContact = 0;

        for (int i = 0; i < huskyList.size(); i++){

            if (huskyList.get(i).getMovesSinceOther() > 150){

                countNoContact++;

            }

        }

        if (countNoContact > huskyList.size()*FRAC_HUSKIES_TO_SWARM){

            swarm = true;

        }

        if (movesSinceSwarm > 0){

            swarm = true;

        }



        //make CritterInfo easily accessible by all methods

        this.info = info;

    }



    //getter for moveCount

    public int getMoveCount(){

        return moveCount;

    }



    //getter for movesSinceSwarm

    public int getMovesSinceSwarm() {

        return movesSinceSwarm;

    }



    //getter for swarm

    public boolean getSwarm(){

        return swarm;

    }



    //remove all dead huskies from huskyList

    private void purgeList() {

        for (int i = 0; i < huskyList.size(); i++) {

            if (huskyList.get(i).getMoveCount() + 5 < moveCount) {

                huskyList.remove(i);

            }

        }

    }

}