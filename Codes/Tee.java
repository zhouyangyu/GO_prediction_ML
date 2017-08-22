import java.util.*;
import java.io.*;

public class Tee{

  
  public static void main(String[] args){
    BufferedReader br = null;
    String s = "";
    try {
      Set<String> pos = new HashSet<String>();
      FileWriter writer = new FileWriter("NegGenes.txt");
      br = new BufferedReader(new FileReader("posGenes.txt"));
      while ((s = br.readLine()) != null) {
        if (s.split(" ").length > 1){
          pos.add(s.split(" ")[1]);
        } else{
          pos.add(s.split(" ")[0]);
        }
      }
      br = new BufferedReader(new FileReader("AllSamples.txt"));
      while ((s = br.readLine()) != null) {
        if (!pos.contains(s)){
          writer.write(s + "\n");
        }
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