import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

/**
 * Panel 
 * @author jenniferluong
 *
 */
public class Panel extends JPanel implements MouseListener, MouseMotionListener, KeyListener {
   /**An array that represents cities*/
   private City[] city;
   /**An array that represents batteries*/
   private Battery[] battery;

   /**An Array List that represents missiles*/
   private ArrayList<Missile> missile;
   /**An Array List that represents explosions*/
   private ArrayList<Explosion> explosion;
   /**An Array List that represents which city/battery is alive*/
   private ArrayList<Rectangle> alive;

   /**X value of mouse*/
   private int crossX;
   /**Y value of mouse*/
   private int crossY;

   /**Level the user is on*/
   private int level;
   /**Amount of opponent missiles*/
   private int attack;
   /**Speed of opponent missiles*/
   private int opp_speed;
   
   /**Title font and size*/
   private Font title;
   /**Body font and size*/
   private Font body;

   
   /**
    * Panel initializes all private variables 
    * Creates cities, batteries, opponent/player missiles
    * Creates thread to run missiles
    */
   public Panel() {

      title = new Font("Arial", Font.PLAIN, 40);
      body = new Font("Arial", Font.PLAIN, 16);

      setBounds(225, 125, 710, 600); // x,y,w,h of window

      //Initializing
      missile = new ArrayList<Missile>();
      explosion = new ArrayList<Explosion>();
      alive = new ArrayList<Rectangle>();
      
      addMouseListener(this);
      addMouseMotionListener(this);
      addKeyListener(this);
      setFocusable(true);
      
      level = 0;
      attack = 20;
      opp_speed = 1;
      
      //Creating cities
      final int AMT_CITIES = 6;
      city = new City[AMT_CITIES];
      Point.Double[] locn = new Point.Double[AMT_CITIES];
      int city_location = 100;
      
      for (int i = 0; i < locn.length; i++) {
         
         locn[i] = new Point.Double(city_location, 530);
         city[i] = new City(locn[i], Color.DARK_GRAY);

         alive.add(city[i]);

         if (city_location == 250) {
            city_location += 150;
            
         } 
         else {
            city_location += 75;
         }
      }
      
      //Creating batteries
      final int AMT_BATTERY = 3;
      battery = new Battery[AMT_BATTERY];
      Point[] batLocn = new Point[AMT_BATTERY];
      int bat_location = 25;

      for (int i = 0; i < batLocn.length; i++) {
         
         batLocn[i] = new Point(bat_location, 500);
         
         battery[i] = new Battery(batLocn[i], Color.WHITE);
         
         alive.add(battery[i]);
         bat_location += 300;
      }

      Thread oppMiss = new Thread(new Runnable() {
         
         @Override
         public void run() {
            for (int i = 0; i < attack; i++) {
               
               int target_start = (int) (Math.random() * getWidth() + 1);
               
               Point start = new Point(target_start, 0);
               
               if (alive.size() != 0) {
                  int target_end = (int) (Math.random() * alive.size());
                  Point end = new Point(alive.get(target_end).x + 15, alive.get(target_end).y + 15);

                  missile.add(new Missile(start, end, opp_speed, 1, Color.RED));
               }
               
               try {
                  Thread.sleep(1500);
               } catch (InterruptedException e) {
               }
            }
         }
      });

      Thread playMiss = new Thread(new Runnable() {

         @Override
         public void run() {
            while (true) {
               //If missile is active, it will launch
               for (int i = 0; i < missile.size(); i++) {
                  if (missile.get(i).isActive()) {
                     missile.get(i).move();
                  }
               }
               
               //Checks collision detection between explosions and cities/batteries/other missiles
               for (int i = 0; i < explosion.size(); i++) {
                  if (explosion.get(i).isActive()) {
                     for (int j = 0; j < battery.length; j++) {
                        if (battery[j].isHit(explosion.get(i).getLocPoint())) {
                           alive.remove(battery[j]);
                        }
                     }
                     for (int k = 0; k < city.length; k++) {
                        if (city[k].isHit(explosion.get(i).getLocPoint())) {
                           alive.remove(city[k]);
                        }
                     }
                     for (int l = 0; l < missile.size(); l++) {
                        if (missile.get(l).isActive()) {
                           if (explosion.get(i).contains(missile.get(l).getLocPoint())) {
                              explosion.add(new Explosion(missile.get(l).getLocPoint()));
                              missile.remove(l);
                           }
                        }
                     }
                  }
               }

               try {
                  Thread.sleep(8);
                  repaint();

               } catch (InterruptedException e) {
               }
            }
         }
      });

      oppMiss.start();
      playMiss.start();
   }

   /**
    * Draws all components of the panel
    * Draws cities/batteries/missiles/explosions/crosshairs
    */
   public void paintComponent(Graphics g) {
      super.paintComponent(g);

      // CROSSHAIR MOUSE
      g.setColor(Color.WHITE);
      g.fillRect(crossX - 1, crossY - 14, 2, 28);
      g.fillRect(crossX - 14, crossY - 2, 28, 2);

      this.setBackground(Color.BLACK);

      if (level == 0) {
         g.setFont(title);
         g.setColor(Color.RED);
         try {
            BufferedImage titleName = ImageIO.read(new File ("Title.png"));
            g.drawImage(titleName, 200, 80, 320, 180, this);

         } catch (IOException e) {
            System.out.println("File not found");
         }
         g.setFont(body);
         g.setColor(Color.WHITE);
         g.drawString("By Jennifer Luong", 300, 280);
         g.drawString("Press ' Enter ' to begin", 285, 450);

         
      } 
      else if (level > 0){
         g.setColor(Color.BLACK);
         g.fillRect(39, 18, 140, 21);
         g.fillRect(900 - 201, 18, 175, 21);

         g.setColor(Color.WHITE);
         g.drawString("Battery 1: ", 525, 50);
         g.drawRect(600, 40, 78, 12);
         g.setColor(Color.RED);
         g.fillRect(602, 42, battery[0].getNumMissiles() * 5, 8);
         
         g.setColor(Color.WHITE);
         g.drawString("Battery 2: ", 525, 70);
         g.drawRect(600, 60,  78, 12);
         g.setColor(Color.RED);
         g.fillRect(602, 62, battery[1].getNumMissiles() * 5, 8);

         g.setColor(Color.WHITE);
         g.drawString("Battery 3: ", 525, 90);
         g.drawRect(600, 80, 78, 12);
         g.setColor(Color.RED);
         g.fillRect(602, 82, battery[2].getNumMissiles() * 5, 8);
        
         if (alive.size() > 0) {
               for (int i = 0; i < city.length; i++) {
                  city[i].draw(g);
               }

               for (int i = 0; i < battery.length; i++) {
                  battery[i].draw(g);
               }

                  for (int i = 0; i < missile.size(); i++) {
                     if (missile.get(i).isActive()) {
                        missile.get(i).draw(g);
                     } 
                     else {
                        explosion.add(new Explosion(missile.get(i).getLocPoint()));
                        missile.remove(i);
                        play("Boom.wav");
                     }
                  }
               
               for (int i = 0; i < explosion.size(); i++) {
                  if (explosion.get(i).move()) {
                     explosion.get(i).draw(g);
                  }
                  else {
                     explosion.remove(i);
                  }
               }
         }
         else  {
            level = -1;
         }
      }
      else {
         level = -1;
      }
    
      if (level == -1) {
         g.setFont(title);
         g.setColor(Color.WHITE);
         g.drawString("The End", 250, 200);
      }

   }

   /**
    * Allows user to play with keys
    */
   @Override
   public void keyPressed(KeyEvent e) {
      Point hitLoc = new Point(crossX, crossY);

      if (level == 0) {
         if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            level++;
         }
      }
      else if (level > 0) {
         if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            addMissile();
         } 
         else if (e.getKeyCode() == KeyEvent.VK_1) {
            if (hitLoc.getY() < battery[0].getLocPoint().getY()) {
               if (battery[0].getNumMissiles() != 0) {
                  Point start = new Point ((int) battery[0].getLocPoint().getX() + 35, (int) battery[0].getLocPoint().getY());
                  missile.add(new Missile(start, hitLoc, 6, 2, Color.GREEN));
                  battery[0].removeMissile();
               }
            }
         } 
         else if (e.getKeyCode() == KeyEvent.VK_2) {
            if (hitLoc.getY() < battery[0].getLocPoint().getY()) {
               if (battery[1].getNumMissiles() != 0) {
                  Point start = new Point ((int) battery[1].getLocPoint().getX() + 35, (int) battery[1].getLocPoint().getY());
                  missile.add(new Missile(start, hitLoc, 6, 2, Color.GREEN));
                  
                  battery[1].removeMissile();
               }
            }

         } 
         else if (e.getKeyCode() == KeyEvent.VK_3) {
            if (hitLoc.getY() < battery[0].getLocPoint().getY()) {
               if (battery[2].getNumMissiles() != 0) {
                  Point start = new Point ((int) battery[2].getLocPoint().getX() + 35, (int) battery[2].getLocPoint().getY());

                  missile.add(new Missile(start, hitLoc, 6, 2, Color.GREEN));
                  battery[2].removeMissile();
               }
            }
         }
      }
      play("Shoot.wav");

   }

   /**
    * Gets point when mouse moves
    */
   @Override
   public void mouseMoved(MouseEvent e) {
      crossX = e.getX();
      crossY = e.getY();
   }

   /**
    * Gets point when mouse clicks
    */
   @Override
   public void mouseClicked(MouseEvent e) {
      if (level > 0) {
         addMissile();
      }
      play("Shoot.wav");
   }

   /**
    * Adds missile according to the private variables of crosshairs
    */
   public void addMissile() {
      
      Point hitLoc = new Point(crossX, crossY);
      
      int parameter = getWidth() / 3;
      
      if (hitLoc.getY() < battery[0].getLocPoint().getY()) {
         if (crossX < parameter) {
            if (battery[0].getNumMissiles() != 0) {
               Point start = new Point ((int) battery[0].getLocPoint().getX() + 35, (int) battery[0].getLocPoint().getY());
               missile.add(new Missile(start, hitLoc, 6, 2, Color.GREEN));
               battery[0].removeMissile();
            }
         } 
         else if (crossX > parameter && crossX <= (parameter*2)) {
            if (battery[1].getNumMissiles() != 0) {
               Point start = new Point ((int) battery[1].getLocPoint().getX() + 35, (int) battery[1].getLocPoint().getY());
               missile.add(new Missile(start, hitLoc, 6, 2, Color.GREEN));
               battery[1].removeMissile();
            }
         } 
         else {
            if (battery[2].getNumMissiles() != 0) {
               Point start = new Point ((int) battery[2].getLocPoint().getX() + 35, (int) battery[2].getLocPoint().getY());

               missile.add(new Missile(start, hitLoc, 6, 2, Color.GREEN));
               battery[2].removeMissile();
            }
         }
      }
   }
   
   /**
    * Plays audio file
    * @param filename      Audio file to be played
    */
   public static void play(String filename) {
      try {
         Clip clip = AudioSystem.getClip();
         clip.open(AudioSystem.getAudioInputStream(new File(filename)));

         clip.start();
      } catch (LineUnavailableException e) {
         System.out.println("Audio Error");
      } catch (IOException e) {
         System.out.println("File not found");
      } catch (UnsupportedAudioFileException e) {
         System.out.println("Wrong file type");
      }
   }

   // unused
   @Override
   public void keyTyped(KeyEvent e) {
   }

   @Override
   public void keyReleased(KeyEvent e) {
   }

   @Override
   public void mouseDragged(MouseEvent e) {
   }

   @Override
   public void mousePressed(MouseEvent e) {
   }

   @Override
   public void mouseReleased(MouseEvent e) {
   }

   @Override
   public void mouseEntered(MouseEvent e) {
   }

   @Override
   public void mouseExited(MouseEvent e) {
   }

}
