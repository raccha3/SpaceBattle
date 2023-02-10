/* 
   Rachel Chalissery
   Mr. Stutler
   6/14/2022
   
   This program finds basic info about the ship and then alternates between using radar to scan for
   nearby objects, getting the results from the last radar scan and moving away from any objects that
   were found on the radar scan, and finally continuing to move in the environment

*/

import java.awt.Color;
import ihs.apcs.spacebattle.*;
import ihs.apcs.spacebattle.commands.*;
import java.util.*;

public class DragonsLair extends BasicSpaceship {

   // declare instance variables, including center
   private Point center;
   private int width;
   private int height;
   private int count;
   
   public static void main(String[] args) {
      TextClient.run("192.168.87.25", new DragonsLair());
   }

   @Override
   public RegistrationData registerShip(int numImages, int worldWidth, int worldHeight) {
      // declare variables
      center = new Point(worldWidth/2.0, worldHeight/2.0);
      this.width = worldWidth;
      this.height = worldHeight;
      count = 0;
   
      // parameters are ship name, color of ship text (RGB), and index of image
      return new RegistrationData("Rachel's Ship", new Color(255, 0, 255), 0);
   }

   @Override
   // every time getNextCommand() is called, return a command
   // all commands are subclasses of ShipCommand superclass
   public ShipCommand getNextCommand(BasicEnvironment env) {
      
      // find the basic information about the ship, it's position, and the game
      BasicGameInfo bgi = env.getGameInfo();
      ObjectStatus shipStatus = env.getShipStatus();
      Point shipPosition = shipStatus.getPosition();
           
      // find info about our ship
      int orientation = shipStatus.getOrientation();
      int angleTo = shipPosition.getAngleTo(center);
      int degrees = angleTo - orientation;
   
      System.out.println(shipPosition);
      System.out.println(orientation);
      System.out.println(angleTo);
      System.out.println(degrees);
   
      // these if statements allow the ship to alternate between thrusting away 
      // from objects and using radar to detect an object and then acting appropriately
      
      // first potential option is running a radar command 
      if (count % 3 == 0) {
         count++;
         return new RadarCommand(4);
      }
      
      // second potential option is getting the radar results
      if (count % 3 == 1) {
         count++;
         
         // put radar results into a list (gets each object by type and then uses a for loop to put them all into 
         // one large list)
         RadarResults radarList = new RadarResults();
         List<ObjectStatus> results = radarList.getByType("Ship");
         List<ObjectStatus> planets = radarList.getByType("Planet");
         List<ObjectStatus> asteroids = radarList.getByType("Asteroid");
         
         // adds the list of planets detected to the results list
         for (int i = 0; i < planets.size(); i++) {
            results.add(planets.get(i));
         }
         
         // adds the list of asteroids detected to the results list
         for (int i = 0; i < asteroids.size(); i++) {
            results.add(asteroids.get(i));
         }
         
         // if the list of results has any objects in it, the object is printed out
         // and my ship points away from the first object in the list and thrusts away from it
         if (results.size() != 0) {
            ObjectStatus object = results.get(0);
            System.out.println(object.toString());
            angleTo = shipPosition.getAngleTo(object.getPosition());
            degrees = angleTo - orientation;
            return new ThrustCommand('F', 0.4, 0.7);
         }
         
         // if the list of results has no objects in it the ship continues thrusting away from the center
         else {
            return new ThrustCommand('F', 0.4, 0.7);
         }
      }
      
      // last possibility is that the ship continues thrusting away from the center
      count++;
      return new ThrustCommand('F', 0.4, 0.7);
   }
}