import java.io.*;
import java.util.*;


public class Histone {
  
  
  private final double thresh = 0.15; //length of string we look ahead
  private String[] hists = {"HistoneI", "HistoneII", "HistoneIII", "HistoneIV", "HistoneM", 
    "HistoneV", "HistoneX"};
  private String[] chromes = {"chrI", "chrII", "chrIII", "chrIV", "chrM", 
    "chrV", "chrX"};
  
  
  public static void main(String[] args){
    Histone c = new Histone();
    Integer[][] genes = c.getGenes("Genes.txt");
    Integer[] start = genes[0];
    Integer[] length = genes[1];
    Integer[] chromess = genes[2];
    int[][] starts = c.getUpstream(start, length, chromess);
    c.getSDV(starts);
  }
  
  
  private int[][] getUpstream(Integer[] start, Integer[] length, Integer[] chromes){
    int[][] starts = new int[start.length][3];
    for (int i = 0; i < start.length; i++){
      starts[i][0] = start[i] - (int) (length[i] * thresh);
      starts[i][1] = (int) (length[i] * thresh);
      starts[i][2] = chromes[i];
    }
    return starts;//The Result must be sorted in Chrome order and ascending order
  }
  
  
  public void getAverage(int[][] starts){
    int chromeIndex = 0;
    int index = 0;
    System.out.println(1);
    String[] chrome = getChrome(hists[chromeIndex] + ".txt");
    System.out.println(chrome.length);
    int begin = Integer.parseInt(chrome[0].split(" ")[0]);
    try{
      FileWriter writer = new FileWriter("histoneAverage.txt"); 
      while (index < starts.length){
        if (chromeIndex != starts[index][2]){
          chromeIndex++;
          chrome = getChrome(hists[chromeIndex] + ".txt");
          System.out.println(chrome.length);
          begin = Integer.parseInt(chrome[0].split(" ")[0]);
        }
        double conservation = getAverage(chrome, begin, starts[index]);
        writer.write(conservation + "\n");
        index++;
      }
      writer.close();
    }catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  
  public void getSDV(int[][] starts){
    int chromeIndex = 0;
    int index = 0;
    System.out.println(1);
    String[] chrome = getChrome(hists[chromeIndex] + ".txt");
    System.out.println(chrome.length);
    int begin = Integer.parseInt(chrome[0].split(" ")[0]);
    try{
      FileWriter writer = new FileWriter("histoneSDV.txt"); 
      while (index < starts.length){
        if (chromeIndex != starts[index][2]){
          chromeIndex++;
          chrome = getChrome(hists[chromeIndex] + ".txt");
          System.out.println(chrome.length);
          begin = Integer.parseInt(chrome[0].split(" ")[0]);
        }
        double conservation = getSDV(chrome, begin, starts[index]);
        writer.write(conservation + "\n");
        index++;
      }
      writer.close();
    }catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private double getAverage(String[] chrome, int begin, int[] gene){
    int start = gene[0] - begin;
    int length = gene[1];
    int range = Math.abs(length);
    int dir = 1;
    double sum = 0;
    if (length < 0){
      dir = -1;
    }
    try{
      for (int i = 0; i < Math.abs(length); i++){
        double conserv = Double.parseDouble(chrome[start + dir * i].split(" ")[1]);
        if ( conserv >= 0){
          sum += conserv;
        } else {
          range--;
        }
      }
    } catch (ArrayIndexOutOfBoundsException e){
      System.out.println("Sup");
      return -1;
    }
    return sum/range;
  }
  
  private double getSDV(String[] chrome, int begin, int[] gene){
    int start = gene[0] - begin;
    int length = gene[1];
    int range = Math.abs(length);
    int dir = 1;
    List<Double> ele = new LinkedList<Double>();
    double sum = 0;
    if (length < 0){
      dir = -1;
    }
    try{
      for (int i = 0; i < Math.abs(length); i++){
        double conserv = Double.parseDouble(chrome[start + dir * i].split(" ")[1]);
        ele.add(conserv);
        if ( conserv >= 0){
          sum += conserv;
        } else {
          range--;
        }
      }
    } catch (ArrayIndexOutOfBoundsException e){
      System.out.println("Sup");
      return -1;
    }
    double mean = sum/range;
    double sdv = 0;
    for (double d : ele){
      sdv += (d - mean) * (d - mean) / range;
    }
    return Math.sqrt(sdv);
  }
  
  
  private Integer[][] getGenes(String fileName){
    List<String> chromeList = new ArrayList<String>(Arrays.asList(chromes));
    List<Integer> begin = new ArrayList<Integer>();
    List<Integer> length = new ArrayList<Integer>();
    List<Integer> chrome = new ArrayList<Integer>();
    String s = "";
    BufferedReader br = null;
    try {
      br = new BufferedReader(new FileReader(fileName));
      while ((s = br.readLine()) != null) {
        String[] line = s.split("\t");
        chrome.add(chromeList.indexOf(line[1]));
        if (Integer.parseInt(line[2]) > 0){
          begin.add(Integer.parseInt(line[3]));
        } else{
          begin.add(Integer.parseInt(line[4]));
        }
        length.add((Integer.parseInt(line[4]) - Integer.parseInt(line[3]))
                     * Integer.parseInt(line[2]));
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
    Integer[] c2 = new Integer[begin.size()];
    c2 = begin.toArray(c2);
    Integer[] c3 = new Integer[length.size()];
    c3 = length.toArray(c3);
    Integer[] c4 = new Integer[chrome.size()];
    c4 = chrome.toArray(c4);
    Integer[][] result = new Integer[3][c2.length];
    result[0] = c2;
    result[1] = c3;
    result[2] = c4;
    return result;
  }
  
  private String[] getChrome(String fileName){
    List<String> c = new ArrayList<String>();
    String s = "";
    BufferedReader br = null;
    try {
      br = new BufferedReader(new FileReader(fileName));
      int last = 0;
      while ((s = br.readLine()) != null) {
        String[] line = s.split("\t");
        int temp = Integer.parseInt(line[1]);
        while (temp != last){
          String l = c.get(c.size() - 1).split(" ")[1];
          c.add(last + " " + l);
          last++;
        } 
        c.add(line[1] + " " + line[3]);
        last++;
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
    String[] r = new String[c.size()];
    return c.toArray(r);
  }
}