package sim.solar;

import javax.swing.JFrame;

public class DisplayFrame extends JFrame {
  public void Activate ()  {
  
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    
    Simulation sim = new Simulation(); 
    getContentPane().add(sim); 
     
    pack();
    setVisible(true);
  }
}
