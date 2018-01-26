import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Explosions are triggered after a missile reaches its end point
 * They destroy the opponent's missiles
 * 
 * @author jenniferluong
 *
 */
public class Explosion extends Rectangle {

   /** Flag that checks if explosion is expanding */
   private boolean expanding;
   /** Flag that checks if explosion is active*/
   private boolean active;
   /** Current location of missile */
   private Point.Double location;
   
   /**
    * Creates an explosion according to parameters
    * 
    * @param p       location of explosion
    */
   public Explosion (Point.Double p) {

      active = true;
      expanding = true;
      width = 5;
      location = p;
      
      this.setBounds((int) location.x, (int) location.y, width, width);
   }
   
   /**
    * Draws the explosion
    * @param g          Used to draw
    */
   public void draw (Graphics g) {
      g.setColor(Color.YELLOW);

      if (active) {
         g.fillOval((int) location.x - width / 2, (int) location.y - width / 2, width, width);
         this.setBounds((int) location.x - width / 2, (int) location.y - width / 2, width, width);
      }
      else {
         g.fillOval((int) location.x - width / 2, (int) location.y - width / 2, width, width);
         this.setBounds((int) location.x - width / 2, (int) location.y - width / 2, width, width);
      }
   }
   
   /**
    * Explosion expands until a desired radius is achieved
    * 
    * @return        True if still active
    *                False, otherwise
    */
   public boolean move() {
      if (expanding) {
         if (width < 75) {
            width++;
         }
         else {
            expanding = false;
         }
      }
      else {
         if (width > 0) {
            width--;
         }
         else {
            active = false;
         }
      }
      return active;
   }
   
   /**
    * Checks if explosion is active
    * @return        True if explosion is active
    *                False, otherwise
    */
   public boolean isActive() {
      return active;
   }
   
   /**
    * Checks if point is found within explosion
    * 
    * @return        True if point is within location
    *                False, otherwise
    */
   public boolean contains (Point p) {
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
      return location;
   }
   
   
}
