import javax.swing.JFrame;
/**
 * Frame creates an instance of panel
 * The main method location
 * 
 * @author jenniferluong
 *
 */
public class Frame extends JFrame {

   /** Panel of the game */
   private Panel p;
   
   /**
    * Frame of the panel/game
    */
   public Frame() {
      setBounds(325, 125, 710, 600);//x,y,w,h of window
      
      p = new Panel();
      
      setTitle("Missile Command");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      getContentPane().add(p);
      
      setVisible(true);
   }
   
   /**
    * Location of the main method
    * Calls frame
    * @param args
    */
   public static void main (String [] args) {
      new Frame();
   }
}
