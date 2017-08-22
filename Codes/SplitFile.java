import java.util.*;
import java.io.*;

public class SplitFile{

  
  public static void main(String[] args){
    BufferedReader br = null;
    String s = "";
    String current = "strI";
    List<String> toSave = new ArrayList<String>();
    try {
      br = new BufferedReader(new FileReader("26way.txt"));
      FileWriter writer = new FileWriter(current + ".txt"); 
      while ((s = br.readLine()) != null) {
        String[] line = s.split("\t");
        if (!line[0].equals(current)){
          writer.close();
          current = line[0];
          writer = new FileWriter(current + ".txt"); 
          System.out.println(s);
        }
        writer.write(line[1] + " " + line[3] + "\n");
      }
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