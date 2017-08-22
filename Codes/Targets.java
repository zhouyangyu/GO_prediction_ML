import java.util.*;
import java.io.*;

public class Targets{

  
  public static void main(String[] args){
    Targets t = new Targets();
    String[] samples = t.getGenes("Genes.txt");
    Set<String> posSamples = t.getPos("posGenes.txt");

    t.getTarget(posSamples, samples);
  }

  private static void getTarget(Set<String> posSamples, String[] samples){
    try{
      FileWriter writer = new FileWriter("Targets.txt"); 
      for (int i = 0; i < samples.length; i++){
        String s = samples[i];
        String r;
        if (s.charAt(s.length() - 1) > 97 || s.charAt(s.length() - 1) < 97 + 25){
          String newS = s.substring(0, s.length() - 1);
          r = (posSamples.contains(newS) || posSamples.contains(s)) ? "1" : "0";
        } else{
          r = posSamples.contains(s) ? "1" : "0";
        }
        writer.write(r + "\n");
      }
      writer.close();
    }catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private static Set<String> getPos(String fileName){
    Set<String> name = new HashSet<String>();
    String s = "";
    BufferedReader br = null;
    try {
      br = new BufferedReader(new FileReader(fileName));
      while ((s = br.readLine()) != null) {
        String[] line = s.split(" ");
        name.add(line[0]);
        System.out.println(line[0]);
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (br != null)br.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
    return name;
  }
  
  private String[] getGenes(String fileName){
    List<String> name = new ArrayList<String>();
    String s = "";
    BufferedReader br = null;
    try {
      br = new BufferedReader(new FileReader(fileName));
      while ((s = br.readLine()) != null) {
        String[] line = s.split("\t");
        name.add(line[0]);
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (br != null)br.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
    String[] c2 = new String[name.size()];
    c2 = name.toArray(c2);
    return c2;
  }
}