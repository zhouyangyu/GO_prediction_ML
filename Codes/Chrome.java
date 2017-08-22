import java.util.*;
import java.io.*;

public class Chrome{
  
  public static void main(String[] args){
    
    String[] chromes = {"chrI", "chrII", "chrIII", "chrIV", "chrM", 
    "chrV", "chrX"};
    BufferedReader br = null;
    String s = "";
    try {
      br = new BufferedReader(new FileReader("DataBaseT.txt"));
      FileWriter writer = new FileWriter("ChromeFeature.txt"); 
      while ((s = br.readLine()) != null) {
        String[] line = s.split("\t");
        String n = line[1];
        for (int i = 0; i < chromes.length; i++){
          writer.write("\t" + (chromes[i].equals(n) ? 1 : 0));
        }
        writer.write("\n");
      }
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (br != null)br.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }
  
}