package sim.solar; 

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.util.List; 
import sim.solar.planet.PlanetLoader ;
import sim.solar.planet.PlanetInterface;
import java.io.BufferedReader;
import java.io.FileReader;  

class Simulation extends JPanel implements Runnable {
  
   
   
   //TODO: These five variables should be initialized from values in a config file instead of being hardcoded:  
 //   private static final int screenSize = 600;         // screen size both x and y
//    private static final int frameDelay = 10;          // millisec delay to slow down animation speed 
//    private static final int maxSolarCount = 3;        // cannot exceed data rows solarconfig.csv 
//    private static final int cyclesPerSolarSystem = 3*360;   // degrees of rotation allotted to each solar system 
//    private static final int pauseDelay = 400;              // millisec delay between solar systems 

  private static  int screenSize;         // screen size both x and y
   private static  int frameDelay;          // millisec delay to slow down animation speed 
   private static  int maxSolarCount;        // cannot exceed data rows solarconfig.csv 
   private static  int cyclesPerSolarSystem;   // degrees of rotation allotted to each solar system 
   private static  int pauseDelay;
   
   
   
   
   //These variables remain initialized here in the code, dont move them to config file :
   private static final Color colorBlack = new Color(0,0,0);
   private static final Color colorGreen = new Color(30,120,30); 
   private int cycleCount = 1;       // must start at one
   private int solarCounter = 0;   // must start at zero
 //  private static int screenMid; // mid-screen location 
  private static int screenMid;
   
   private PlanetLoader planetLoader = new  PlanetLoader();  
   private SolarSystem solarSystem  ;

   public Simulation() {  
   System.out.println("before"+screenSize+" "+frameDelay+" "+maxSolarCount+" "+cyclesPerSolarSystem+" "+pauseDelay+" ");
      LoadGraphics();
System.out.println("After"+screenSize+" "+frameDelay+" "+maxSolarCount+" "+cyclesPerSolarSystem+" "+pauseDelay+" ");
screenMid = screenSize/2;
       List<PlanetInterface> planetList = planetLoader.Produce(solarCounter); 
       solarSystem = new SolarSystem(planetList); 
       setBackground(colorBlack);
       setPreferredSize( new Dimension(screenSize, screenSize) );
       final Thread t = new Thread (this);
       t.start ();
  }
  
  private void NextSolarSystemRun() {
       try {
         // this will slow down display animation
         Thread.sleep(frameDelay);   
         
         // switch to next solar system
         if ((cycleCount % cyclesPerSolarSystem) == 0) {
             solarCounter++; 
             if (solarCounter > maxSolarCount) {
                solarCounter=0; // rollover to first planetary config
             }
             // get next set of planets to view 
             List<PlanetInterface> planetList = planetLoader.Produce(solarCounter);
             solarSystem = new SolarSystem(planetList); 
             Thread.sleep(pauseDelay);  // pause between change to next solar system
         }
      } 
    catch (InterruptedException e) {
     e.printStackTrace();
    }

  }
    
  public void run() {
    while(true)  {
      solarSystem.run(); // calculations for next animation 
      repaint();
      cycleCount++;
      NextSolarSystemRun(); 
    }
  }
  
  public void paintComponent(final Graphics g)  {
    // clear out previous frame of drawings
    g.setColor( colorBlack ); 
    g.fillRect(0, 0, screenSize, screenSize);
    
    // paint planets in the solar system 
    solarSystem.paint(g, screenMid); 

    // repaint x-y axis lines using dark green
    g.setColor( colorGreen );
    g.drawLine( screenMid, 0, screenMid, screenSize); 
    g.drawLine( 0, screenMid, screenSize, screenMid); 
   }


public void LoadGraphics() {
 String line;  
 int lineCount=0;
  try {
 BufferedReader br = new BufferedReader(new FileReader("C:/Users/19sai/OneDrive/Desktop/Fitchburg docs/Assignments/PCP_BMackay/SolarSystemCleaned/SolarSystemCleaned/classes/graphics.txt")); 
 
 while ((line = br.readLine()) != null)   {  
 System.out.println("line"+line);
               String[] tokens = line.split("\\|"); 
               System.out.println("tokens"+ tokens[0]);
               if (lineCount == 0) {  
                  screenSize         = Integer.parseInt(tokens[0]);         
                  frameDelay         =  Integer.parseInt(tokens[1]);          
                  maxSolarCount       = Integer.parseInt(tokens[2]);     
                  cyclesPerSolarSystem  =  Integer.parseInt(tokens[3]); 
                  pauseDelay             = Integer.parseInt(tokens[4]); 
               }
               lineCount++; 
            }  
            
            }  
      catch (Exception e) {  
         e.printStackTrace();  
      }
}

}