import java.util.*;
import java.io.*;

//update all samples
public class ParseGO{
  
  private static final String source = "GO:0031103";
  
  
  public static void main(String[] args){
    Digraph<String> graph = readFile();
    System.out.println(graph.getSize());
    Set<String> geneNames = readGeneName();
    Map<String, List<String>> map = GOMap(geneNames);
    getScore(map, graph);
  }
  
  private static void getScore(Map<String, List<String>> map, Digraph<String> graph){
    try{
      FileWriter writer = new FileWriter("GOScore.txt");
      for (String gene : map.keySet()){
        int sum = 0;
        int min = 10000;
        int i = 0;
        List<String> onts = map.get(gene);
        for (String ont : onts){
          if (graph.contains(ont)){
            int score = graph.shortestDistance(ont, source);
            if (score < min){
              min = score;
            }
            sum += score;
            i++;
          }
        }
        writer.write(gene + "\t" + (float) sum / i + "\t" + min + "\n");
      }
      writer.close();
    } catch (IOException e){
    }
  }
  
  
  private static Map<String, List<String>> GOMap(Set<String> geneNames){
    BufferedReader br = null;
    String s = "";
    Map<String, List<String>> map = new HashMap<String, List<String>>();
    try{
      br = new BufferedReader(new FileReader("genOnto1.txt"));
      while ((s = br.readLine()) != null){
        String[] line = s.split("\t");
        if (geneNames.contains(line[0])){
          if (!map.keySet().contains(line[0])){
            map.put(line[0], new LinkedList<String>());
          }
          map.get(line[0]).add(line[1]);
        }
      }
    } catch (IOException e) {
      
    }
    return map;
  }
  
  
  public static Set<String> readGeneName(){
    Set<String> labels = new TreeSet<String>();
    labels.add("is_a:"); labels.add("intersection_of"); 
    labels.add("relationship:");
    BufferedReader br = null;
    String s = "";
    try{
      br = new BufferedReader(new FileReader("AllSamples.txt"));
      while ((s = br.readLine()) != null){
        labels.add(s);
      }
    } catch (IOException e) {
      
    }
    return labels;
  }
  
  
  
  private static Digraph<String> readFile(){
    Digraph<String> graph = new Digraph<String>();
    Set<String> labels = new TreeSet<String>();
    labels.add("is_a:"); labels.add("intersection_of"); 
    labels.add("relationship:");
    BufferedReader br = null;
    String s = "";
    try{
      String cur = "GO:-1";
      br = new BufferedReader(new FileReader("ALLGOterms.txt"));
      while ((s = br.readLine()) != null){
        if (s.equals("[Term]")){
          cur = br.readLine().split(" ")[1];
        } else {
          String[] line = s.split(" ");
          if (labels.contains(line[0])){
            int i = 1;
            while (i < line.length){
              if (line[i].substring(0,2).equals("GO")){
                graph.add(cur, line[i]);  //leaf points to parents
                graph.add(line[i], cur);
                break;
              }
              i++;
            }
          }
        }
      }
    } catch (IOException e) {
      
    }
    return graph;
  }

}