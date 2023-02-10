/*
   Rachel Chalissery
   Mr. Stutler
   5/19/2022

   This program calculates the midpoint of the universe based on its width and
   height.
   While the ship is over 250 away from the middle, it will thrust forward, when it is
   between 250 and 200 away from the middle it will idle, and when it is closer than
   200 away from the middle it will brake and slow down so that the ship will stop in
   the middle.
*/

import java.awt.Color;
import ihs.apcs.spacebattle.*;
import ihs.apcs.spacebattle.commands.*;

public class FindTheMiddleShip extends BasicSpaceship {

   // declare instance variables, including center
   private Point center;


   public static void main(String[] args) {
      // "10.40.30.98" is the IP address of Mr. Stutler's projector computer
      // "FindTheMiddleShip" is the name of the current class
      TextClient.run("192.168.86.38", new FindTheMiddleShip());
   }
   
   @Override
   public RegistrationData registerShip(int numImages, int worldWidth, int worldHeight) {
      center = new Point(worldWidth/2.0, worldHeight/2.0);
      // parameters are ship name, color of ship text (RGB), and index of image
      return new RegistrationData("Rachel's Ship", new Color(255, 0, 255), 0);
   }
 
   @Override
   public ShipCommand getNextCommand(BasicEnvironment env) {
      // find info about our ship
      ObjectStatus shipStatus = env.getShipStatus();
      Point shipPosition = shipStatus.getPosition();
      int orientation = shipStatus.getOrientation();
      int angleTo = shipPosition.getAngleTo(center);
      int degrees = angleTo - orientation;

      System.out.println(shipPosition);
      System.out.println(orientation);
      System.out.println(angleTo);
      System.out.println(degrees);

      // every time getNextCommand() is called, return a command
      // all commands are subclasses of ShipCommand superclass
      if (degrees != 0) {
         return new RotateCommand(degrees);
      }

      if (shipPosition.getDistanceTo(center) > 250) {
         return new ThrustCommand('B', 0.6, 0.82);
      }

      else if (shipPosition.getDistanceTo(center) > 200) {
         return new IdleCommand(0.4);
      }

      else {
         return new BrakeCommand(0);
      }

   }

}