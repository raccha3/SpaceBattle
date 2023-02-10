/* 
   Rachel Chalissery
   Mr. Stutler
   6/14/2022
   
   This program finds the nearest bauble and moves towards it to collect it.
   It also keeps track of the golden bauble and collects it if it is nearby.

*/

import java.awt.Color;
import ihs.apcs.spacebattle.*;
import ihs.apcs.spacebattle.commands.*;

public class HungryHungryBaubles extends BasicSpaceship {

   // declare instance variables, including center
   private Point center;
   private int width;
   private int height;
   
   public static void main(String[] args) {
      TextClient.run("192.168.87.24", new HungryHungryBaubles());
   }

   @Override
   public RegistrationData registerShip(int numImages, int worldWidth, int worldHeight) {
      // declare variables
      center = new Point(worldWidth/2.0, worldHeight/2.0);
      this.width = worldWidth;
      this.height = worldHeight;

      // parameters are ship name, color of ship text (RGB), and index of image
      return new RegistrationData("Rachel's Ship", new Color(255, 0, 255), 0);
   }

   @Override
   // every time getNextCommand() is called, return a command
   // all commands are subclasses of ShipCommand superclass
   public ShipCommand getNextCommand(BasicEnvironment env) {
      
      // find the location of the golden bauble and print it out
      BasicGameInfo bgi = env.getGameInfo();
      Point myBauble = bgi.getObjectiveLocation();
      System.out.println("Golden Bauble: " + myBauble);
      
      // get ship's position and use that to find the closest bauble
      ObjectStatus shipStatus = env.getShipStatus();
      Point shipPosition = shipStatus.getPosition();
      Point closestBauble = shipPosition.getClosestMappedPoint(myBauble, this.width, this.height);
      System.out.println("Closest Bauble: " + closestBauble);
      
      // find info about our ship
      int orientation = shipStatus.getOrientation();
      int angleTo = shipPosition.getAngleTo(closestBauble);
      int degrees = angleTo - orientation;

      System.out.println(shipPosition);
      System.out.println(orientation);
      System.out.println(angleTo);
      System.out.println(degrees);

      // move towards nearest bauble to collect it
      if (degrees != 0) {
         return new RotateCommand(degrees);
      }

      if (shipPosition.getDistanceTo(closestBauble) > 100) {
         return new ThrustCommand('B', 0.75, 0.7);
      }
      
      else if (shipPosition.getDistanceTo(closestBauble) > 0) {
         return new ThrustCommand('B', 0.3, 0.3);
      }

      return new BrakeCommand(0);
   }
}





