// EGR222 Homework 5 (Critters)
// Authors: Stuart Reges and Marty Stepp
//          modified by Kyle Thayer
//
// CritterMain provides the main method for a simple simulation program.  Alter
// the number of each critter added to the simulation if you want to experiment
// with different scenarios.  You can also alter the width and height passed to
// the CritterFrame constructor.

public class CritterMain {
    public static void main(String[] args) {
        CritterFrame frame = new CritterFrame(120, 100);

        /*
         * For Looping
         */
        frame.add(1250, TheHat.class);
        frame.add(1250, Husky.class);

        // uncomment each of these lines as you complete these classes
//        frame.add(30, Bear.class);
//        frame.add(30, Lion.class);
//        frame.add(30, Giant.class);
//        frame.add(1250, TheHat.class);
//        frame.add(30, TheHat.class);
//        frame.add(30, FlyTrap.class);
//        frame.add(1250, Husky.class);
//        frame.add(30, Husky.class);
//        frame.add(30, Haxxx.class);
//        frame.add(3000, TheHat.class);
//        frame.add(3000, TheOtherHat.class);
//
////        frame.add(5790, Food.class);
////        frame.add(500, Food.class);
        frame.start();
        int i = 0;
        while (true) {
            if (frame.isFinished()) {
                System.out.println("Threading is weird");
                frame = frame.newFrame(65, 105);
                frame.add(100, Giant.class);
                frame.add(100,Bear.class);
                frame.add(100, Lion.class);
                frame.add(100, FlyTrap.class);
                frame.add(100, Food.class);
                frame.start();
                TheHat newGame = new TheHat(0);
                frame.immediateRun();
            }
            System.out.print("");
        }
    }
}
