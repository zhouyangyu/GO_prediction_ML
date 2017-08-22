import java.util.*;
import java.io.*;

public class GGInteraction {
  public static void main(String[] args){
    Digraph<String> graph = readFile();
    System.out.println(graph.getSize());
    Map <String, Integer> map = getMap();
    System.out.println(map.size());
    getFeatures(map, graph);
  }
  
  private static void getFeatures(Map<String, Integer> map, Digraph<String> graph){
    BufferedReader br = null;
    FileWriter writer = null;
    String s = "";
    try{
      writer = new FileWriter("PPFeatures.txt");
      br = new BufferedReader(new FileReader("DataBaseT.txt"));
      while ((s = br.readLine()) != null){
        String[] line = s.split("\t");
        writer.write(getPos(map, graph, line[0]) + "\n");
      }
      writer.close();
    }
    catch (IOException e) {
      
    }
  }

  
  private static double getPos(Map<String, Integer> map, Digraph<String> graph, String gene){
    if (!graph.contains(gene)){
      return 0;
    } else {
      Set<String> neighbors = new HashSet<String>();
      neighbors.add(gene);
      int num = 0;
      Set<String> ns = graph.getNeighbors(gene);
      for (String g : ns){
        if (!neighbors.contains(g) && map.containsKey(g)  && map.get(g) == 1){
          neighbors.add(g);
          num++;
        }
//        Set<String> ng = graph.getNeighbors(g);
//        for (String n : ng){
//          if (!neighbors.contains(n) && map.containsKey(n) && map.get(n) == 1){
//            neighbors.add(n);
//            num++;
//          }
//        }
      }
      return (double) (num) / ns.size();
    }
  }
    
  private static Map<String, Integer> getMap(){
    Map<String, Integer> map = new HashMap<String, Integer>();
    BufferedReader br = null;
    FileWriter writer = null;
    String s = "";
    try{
      br = new BufferedReader(new FileReader("DataBaseT.txt"));
      while ((s = br.readLine()) != null){
        String[] line = s.split("\t");
        map.put(line[0], Integer.parseInt(line[line.length - 1]));  //leaf points to parents
      }
    }
    catch (IOException e) {
      
    }
    return map;
  }
  
  private static Digraph<String> readFile(){
    Digraph<String> graph = new Digraph<String>();
    BufferedReader br = null;
    String s = "";
    try{
      br = new BufferedReader(new FileReader("PPInteract2.txt"));
      while ((s = br.readLine()) != null){
        String[] line = s.split("\t");
        graph.add(line[0], line[1]);  //leaf points to parents
        graph.add(line[1], line[0]);  //leaf points to parents
      }
    }
    catch (IOException e) {
      
    }
    return graph;
  }
}