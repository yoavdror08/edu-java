package com.shaharyi.corona.recursive;

import java.util.*;

class Main {
  static Random rand = new Random();
  static Scanner reader = new Scanner(System.in);

  public static void main(String[] args) {
    int[][] m = new int[10][10];
    infect(m, 4, 5, 1);
  }

  public static void infect(int[][] m, int i, int j, int time) {  
    if (i>=0 && i<m.length && j>=0 && j<m[0].length && m[i][j]==0) {
      m[i][j] = time;
      //String s = reader.nextLine();
      for (int r=-1; r<=1; r++) 
        for (int c=-1; c<=1; c++)
          if (c!=0 || r!=0)
            if (Math.random() < 0.3)
              infect(m, i+r, j+c, time+1);                         
    }
    print(m);
    System.out.println("stage="+time);
  }
  public static void print(int[][] m) {
    int rows = m.length;
    int cols = m[0].length;
    int total = 0;
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        System.out.printf("%2d ", m[i][j]);
        if (m[i][j] > 0)
          total++;
      }
      System.out.println();
    }
    double percent = (double)total*100 / (rows*cols);
    System.out.println("Total = " + total+" = " + percent+"%");
  }  
}
