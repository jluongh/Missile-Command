import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Batteries represent each battery that contains a certain about of missiles
 * Defender must protect
 * 
 * @author jenniferluong
 *
 */
public class Battery extends Rectangle {
   /** Amount of missiles */
   private int numMissiles;
   /** Color of city */
   private Color color;
   
   /** Width of battery */
   private static final int WIDTH = 70;
   /** Height of battery */
   private static final int HEIGHT = 200;
   
   /** Battery image */
   private BufferedImage img;
   
   /**
    * Creates a battery according to parameters
    * 
    * @param loc        location of battery
    * @param c          color of battery
    */
   public Battery (Point loc, Color c) {
      super ((int) loc.getX(), (int) loc.getY(), WIDTH, HEIGHT);

      color = c;
      numMissiles = 15;
      
      try {
         img  = ImageIO.read(new File("Battery.png"));
      }
      catch (IOException e) {
         System.out.println("File not found");
      }
   }
   
   /**
    * Draws the battery
    * @param g       Used to draw
    */
   public void draw (Graphics g) {
      if (numMissiles != 0) {
         g.drawImage(img, (int) this.getX(),(int) this.getY(), (int) getWidth(), (int) getHeight(), null);
         //g.setColor(color);
         //g.fillRect((int) this.getX(),(int) this.getY(), (int) getWidth(), (int) getHeight());
      }
   }
   
   /**
    * Gets number of missiles in battery
    * @return        Number of missiles
    */
   public int getNumMissiles() {
      return numMissiles;
   }
   
   /**
    * Removes a missile from battery
    */
   public void removeMissile() {
      if (numMissiles != 0) {
         numMissiles--;
      }
      else {
         numMissiles = 0;
      }
   }

   /**
    * Checks if battery is hit
    * 
    * @param p       Point to check
    * @return        True if point is within battery location
    *                False, otherwise
    */
   public boolean isHit(Point.Double p) {
      if (this.contains(p)) {
         numMissiles = 0;
         return true;
      }
      return false;
   }
   
   /**
    * Gets location point of battery
    * @return        Current location
    */
   public Point getLocPoint() {
      return new Point ((int)this.getX(), (int) this.getY());
   }
   

}
