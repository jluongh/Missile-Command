import java.awt.*;

/**
 * Creates missiles to launch for player and opponent
 * @author jenniferluong
 *
 */
public class Missile {

   /** Start location of missile*/
   private Point start;
   /** Current location of missile */
   private Point.Double location;
   /** End location of missile */
   private Point end;
   /** Speed of missile */
   private int speed;
   /** Color of missile*/
   private Color color;
   /** Type: 1 = Opponent 2 = Player */
   private int type;
   /** Active missile*/
   private boolean active;
   /** Amount of X value moved */
   private double moveAmtX;
   /** Amount of Y value moved*/
   private double moveAmtY;

   /**
    * Creates a missile according to parameters
    * 
    * @param s       start location
    * @param e       end location
    * @param sp      speed of missile
    * @param t       type of missile
    * @param c       color of missile
    */
   public Missile (Point s, Point e, int sp, int t, Color c) {
      start = s;
      location = new Point.Double (s.x, s.y);
      end = e;
      speed = sp;
      type = t;
      color = c;
      active = true;
   }

   /**
    * Moves missile closer to end location
    * Sets location to new location
    */
   public void move() {
      
      double dx = end.x - start.x;
      double dy = end.y - start.y;
      double magnitude = (int) Math.sqrt(dx * dx + dy * dy);

      moveAmtX = dx * speed / magnitude;
      moveAmtY = dy * speed / magnitude;

      location.setLocation(location.x + moveAmtX, location.y + moveAmtY);
   }

   /**
    * Draws the missile depending on type of missile
    * @param g       Used to draw
    */
   public void draw(Graphics g) {
      if (active) {
         if (type == 1) {
            if (location.y < end.y) {
               g.setColor(Color.WHITE);
               g.drawLine((int) start.getX(), (int) start.getY(), (int) location.getX(), (int) location.getY()); 
               g.setColor(color);
               g.fillRect((int) location.x - 3, (int) location.y - 3, 6, 6);
            }
            else {
               active = false;
            }
         } 
         else {
            if (location.y > end.y) {
               g.setColor(Color.WHITE);
               g.drawLine((int) start.getX(), (int) start.getY(), (int) location.getX(), (int) location.getY()); 
               g.setColor(color);
               g.fillRect((int) location.x - 3, (int) location.y - 2, 6, 6);
            }
            else {
               active = false;
            }
         }
      }
   }

   /**
    * Checks if missile is active
    * @return        True if missile is active
    *                False, otherwise
    */
   public boolean isActive() {
      return active;
   }

   /**
    * Gets location point of missile
    * @return        Current location
    */
   public Point.Double getLocPoint() {
      return location;
   }
   
   /**
    * Checks if missile is of type player
    * @return        True if missile is of type player
    *                False, otherwise
    */
   public boolean isPlayer() {
      if (type == 1) {
         return false;
      }
      else {
         return true;
      }
   }
}
