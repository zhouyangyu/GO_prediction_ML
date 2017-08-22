import java.util.*;
import java.io.*;

public class GOScore {

  public static void main(String[] args){
    Map<String, String> names = getNames();
    Map<String, String> results = getResults();
    getGenes(names, results);
    
  }
  
  
  private static Map<String, String> getResults(){
    Map<String, String> results = new HashMap<String, String>();
    BufferedReader br = null;
    String s = "";
    try{
      br = new BufferedReader(new FileReader("GOScore.txt"));
      while ((s = br.readLine()) != null){
        String[] line = s.split("\t");
        results.put(line[0], line[1] + "\t" + line[2]);
      }
    } catch (IOException e) {
      
    }
    return results;
  }
  
  private static void getGenes(Map<String, String> names, Map<String, String> results){
    BufferedReader br = null;
    FileWriter writer = null;
    String s = "";
    try{
      br = new BufferedReader(new FileReader("DataBaseT.txt"));
      writer = new FileWriter("GOFeatures.txt");
      while ((s = br.readLine()) != null){
        String[] line = s.split("\t");
        String n = line[0];
        String alter = "@@";
        if (n.charAt(n.length() - 1) > 97 || n.charAt(n.length() - 1) < 97 + 25){
          alter = n.substring(0, n.length() - 1);
        }

        if (names.containsKey(n) || names.containsKey(alter)){
          String f = names.containsKey(n) ? names.get(n) : names.get(alter);
          if (results.containsKey(f)){
            writer.write(results.get(f) + "\n");
          } else {
            writer.write("Null\t Null\n");
          }
        }
      }
      writer.close();
    } catch (IOException e) {
      
    }
  }
  
  
  private static Map<String, String> getNames(){
    Map<String, String> name = new HashMap<String, String>();
    BufferedReader br = null;
    String s = "";
    try{
      br = new BufferedReader(new FileReader("nameMap.txt"));
      while ((s = br.readLine()) != null){
        if (!s.equals("")){
          String[] line = s.split("\t");
          if (line[0].equals("@")){
            line[0] = line[1];
          }
          name.put(line[1], line[0]);
        }
      }
    } catch (IOException e) {
      
    }
    return name;
  }
}