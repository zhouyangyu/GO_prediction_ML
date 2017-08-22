import java.util.*;
import java.io.*;

public class TrimDataBase{

  
  public static void main(String[] args){
    BufferedReader br = null;
    Set<String> samples = getSampleName();
    String s = "";
    try {
      br = new BufferedReader(new FileReader("DataBase.txt"));
      FileWriter writer = new FileWriter("DataBaseTT.txt"); 
      while ((s = br.readLine()) != null) {
        String[] line = s.split("\t");
        String n = line[0];
        String alter = "@@";
        if (n.charAt(n.length() - 1) > 97 || n.charAt(n.length() - 1) < 97 + 25){
          alter = n.substring(0, n.length() - 1);
        }
        if (samples.contains(n) || samples.contains(alter)){
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
  
  private static Set<String> getSampleName(){
    BufferedReader br = null;
    Set<String> set = new HashSet<String>();
    String s = "";
    try {
      br = new BufferedReader(new FileReader("AllSamplesMN.txt"));
      while ((s = br.readLine()) != null) {
        set.add(s);
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
    return set;
  }
}