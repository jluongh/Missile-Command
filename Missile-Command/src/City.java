import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Cities represent each city that the defender must protect
 * 
 * @author jenniferluong
 *
 */
public class City extends Rectangle {
   /** Flag that checks if city is active */
   private boolean active;
   /** Color of city */
   private Color color;
   
   /** Width of city */
   private final static int  WIDTH = 70;
   /** Height of city */
   private final static int HEIGHT = 75;
   
   /** City image */
   private BufferedImage img;
   
   /**
    * Creates a city according to parameters
    * 
    * @param loc        location of city
    * @param c          color of city
    */
   public City (Point.Double loc, Color c) {
      super((int) loc.getX(), (int) loc.getY(), WIDTH, HEIGHT);
      color = c;
      active = true;
      
      try {
         img  = ImageIO.read(new File("Building.png"));
      }
      catch (IOException e) {
         System.out.println("File not found");
      }
   }
   
   /**
    * Draws the city
    * @param g       Used to draw
    */
   public void draw (Graphics g) {
      if (active) {
         g.setColor(color);
         g.drawImage(img, (int) this.getX(), (int) this.getY(), (int) getWidth(), (int) getHeight(), null);
         //g.fillRect((int) this.getX(), (int) this.getY(), (int) getWidth(), (int) getHeight());
      }
   }
   
   /**
    * Checks if city is active
    * @return        True if city is active
    *                False, otherwise
    */
   public boolean isActive() {
      return active;
   }
   
   /**
    * Checks if city is hit
    * 
    * @param p       Point to check
    * @return        True if point is within city location
    *                False, otherwise
    */
   public boolean isHit(Point.Double p) {
      if (this.contains(p)) {
         active = false;
      }
      return this.contains(p);
   }
   
   /**
    * Gets location point of missile
    * @return        Current location
    */
   public Point.Double getLocPoint() {
      return new Point.Double ((int) this.getX(), (int) this.getY());
   }
}
