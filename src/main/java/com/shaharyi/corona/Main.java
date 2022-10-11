package com.shaharyi.corona;

import java.util.*;

class Main {
  public static Scanner reader = new Scanner(System.in);
  
  public static void main(String[] args) {
    
    final int ROWS = 10;
    final int COLS = 10;
    
    Matrix m = new Matrix(ROWS+2, COLS+2, 2.0, 3, 9);

    //put patient zero
    int x = (int)(Math.random()*ROWS) + 1;
    int y = (int)(Math.random()*COLS) + 1;
    m.set(x, y, 1);
    
    String s = "";
    while (!s.equals("q"))
    {
      m.print();
      m.calcNext();  
      s = reader.nextLine();
    }
  }
}
