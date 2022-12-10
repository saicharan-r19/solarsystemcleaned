package sim.solar.planet;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.io.FileReader;  
import java.io.IOException;  
import java.util.HashMap; 
import com.opencsv.CSVReader; 

public class PlanetLoader {

    private final String planetConfigFile = "solarconfig.csv";
    
    
    
    //TODO: use the row value to select just one row of the solarconfig.csv to be used for each display 
    
    public List<PlanetInterface> Produce(int row) {
        
        List<PlanetInterface> planetList = new ArrayList<PlanetInterface>();
        Map<Integer,List<Integer>> planetConfigMap = new  HashMap<>();
        List<Integer> planetConfig = new ArrayList<Integer>(); 
        
        
        //TODO: call the ReadPlanetConfig method and have it return a collection of the data it found
        
         planetConfigMap = ReadPlanetConfig(planetConfigFile);
        //TODO: use the 'row' selection to utilize only the selected row from the CSV file
        System.out.println("row"+row);
        planetConfig = planetConfigMap.get(row);
        System.out.println("planetConfig"+planetConfig);
        
        //TODO: store the data from solarconfig.csv into these eleven variables:
        //int numplanets = 90; 
       // int numplanets =planetConfig.get(0);   
        // int orbit      = 240;  
//         int increment  = 1;    
//         int angleinc   = 5;    
//         int planetsize = 10;   
//         int redbase    = 240;  
//         int greenbase  = 240;  
//         int bluebase   = 60;   
//         int redinc     = -2;   
//         int greeninc   = -2;   
//         int blueinc    = 2;    
         int numplanets =planetConfig.get(0); 
         int orbit      = planetConfig.get(1);  
        int increment  = planetConfig.get(2);    
        int angleinc   = planetConfig.get(3);    
        int planetsize = planetConfig.get(4);   
        int redbase    = planetConfig.get(5);  
        int greenbase  = planetConfig.get(6);  
        int bluebase   = planetConfig.get(7);   
        int redinc     = planetConfig.get(8);   
        int greeninc   = planetConfig.get(9);   
        int blueinc    = planetConfig.get(10);  
        
 
        int angle     = 0;
        int red ;
        int green;
        int blue; 
        
        for (int i=0; i<numplanets; i++) {
            angle    = angle + angleinc;   // controls offset between planets
            red      = redbase   + redinc*i;   // planet color 
            green    = greenbase + greeninc*i;   // planet color
            blue     = bluebase  + blueinc*i;   // planet color
            if (((0 <= red) && (red <= 250)) && ((0 <= green) && (green <= 250)) && ((0 <= blue) && (red <= blue))) {
            planetList.add(new Planet (angle, orbit, increment, planetsize, red, green, blue));
            }else {
            break;
            }
         }
         
        return planetList; 
   }
      
   
   
   //TODO: upgrade this method as necessary to return the data in a form that can be used by Produce method
   
   private  Map<Integer,List<Integer>>  ReadPlanetConfig(String fileName)  {  
      CSVReader reader = null; 
     
      Map<Integer,List<Integer>> planetConfigMap = new  HashMap<>(); 
       

      try {  

         reader = new CSVReader(new FileReader(fileName));
         String [] nextLine;  
         nextLine = reader.readNext();  // read the header line 
                  
         //TODO: figure out what to do with the header information if its needed
         for ( String token : nextLine)  {  
            //TODO: figure out what to do with the header information if its needed
            
         }

         //TODO: take each token and copy them into some type of collection storage, ArrayList, HashMap, etc 
         int i=0;
         while ((nextLine = reader.readNext()) != null) { 
         
          List<Integer> planetconfigList = new ArrayList<>(); 
            for ( String token : nextLine)  {  
               //TODO: take each token and copy them into some type of collection storage, ArrayList, HashMap, etc 
               planetconfigList.add(Integer.parseInt(token));
               // System.out.println(token);
            }
          planetConfigMap.put(i, planetconfigList);
         // System.out.println(planetConfigMap);
      i++;
         }
         reader.close(); 
        
      }  
      catch (Exception e) {  
         e.printStackTrace();  
      } 
       return planetConfigMap; 
   } 
}

