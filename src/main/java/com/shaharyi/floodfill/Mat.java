package com.shaharyi.floodfill;

import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;

public class Mat {
  private char[][] mat;
  private int width;
  private int height;
  private char fillChar;
  private char borderChar;
  private Scanner scan = new Scanner(System.in);
  private int counter;

  public Mat(int width, int height) {
    this.width = width;
    this.height = height;
    mat = new char[height][width];
  }

  public Mat(String[] pic) {
    width = pic[0].length();
    height = pic.length;
    mat = new char[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        mat[i][j] = pic[i].charAt(j);
      }
    }
  }

  public void setFillChar(char c) {
    fillChar = c;
  }

  public void fill(char c) {
    for (int i = 0; i < mat.length; i++) {
      for (int j = 0; j < mat[0].length; j++) {
        mat[i][j] = c;
      }
    }
  }

  public void set(int x, int y, char c) {
    if (x >= 0 && x < width && y >= 0 && y < height)
      mat[y][x] = c;
  }

  public char get(int x, int y) {
    if (x >= 0 && x < mat[0].length && y >= 0 && y < mat.length)
      return mat[y][x];
    return '\0';
  }

  public void print() {
    for (int i = 0; i < mat.length; i++) {
      for (int j = 0; j < mat[0].length; j++) {
        System.out.print(mat[i][j]);
      }
      System.out.println();
    }
  }

  public int[] findChar(char target) {
    int[] pos = { -1, -1 };
    for (int i = 0; i < mat.length; i++) {
      for (int j = 0; j < mat[0].length; j++) {
        if (mat[i][j] == target) {
          pos[1] = i;
          pos[0] = j;
        }
      }
    }
    return pos;
  }

  public void floodFill(int x, int y, char borderChar, char fillChar) {
    this.borderChar = borderChar;
    this.fillChar = fillChar;
    counter = 0;
    floodFillQueue(x, y);
    System.out.println("Counter=" + counter);
  }

  public void floodFillStack(int x, int y) {
    Stack<int[]> stack = new Stack<int[]>();
    stack.push(new int[] { x, y });
    while (!stack.isEmpty()) {
      counter++;
      int[] pos = stack.pop();
      x = pos[0];
      y = pos[1];
      int[][] n = { { x + 1, y }, { x - 1, y }, { x, y + 1 }, { x, y - 1 } };
      for (int i = 0; i < n.length; i++) {
        x = n[i][0];
        y = n[i][1];
        if (x >= 0 && x < width && y >= 0 && y < height)
          if (mat[y][x] != fillChar && mat[y][x] != borderChar) {
            mat[y][x] = fillChar;
            stack.push(new int[] { x, y });
          }
      }
    }
  }

  public void floodFillQueue(int x, int y) {
    Queue<int[]> queue = new LinkedList<int[]>();
    queue.add(new int[] { x, y });
    while (!queue.isEmpty()) {
      counter++;
      int[] pos = queue.remove();
      x = pos[0];
      y = pos[1];
      int[][] n = { { x + 1, y }, { x - 1, y }, { x, y + 1 }, { x, y - 1 } };
      for (int i = 0; i < n.length; i++) {
        x = n[i][0];
        y = n[i][1];
        if (x >= 0 && x < width && y >= 0 && y < height)
          if (mat[y][x] != fillChar && mat[y][x] != borderChar) {
            mat[y][x] = fillChar;
            queue.add(new int[] { x, y });
          }
      }
      //print();
      //scan.nextLine();
    }
  }

  public void floodFillScanLine(int x, int y, char oldVal, char newVal) {
    boolean openUp, openDown;
    Point cur = new Point(x, y);
    Stack<Point> stack = new Stack<Point>();
    stack.push(cur);
    while (!stack.isEmpty()) {
      cur = stack.pop();
      x = cur.x;
      y = cur.y;
      openUp = false;
      openDown = false;
      // move to leftmost pixel
      while (mat[x][y] == oldVal)
        x--;

      while (mat[x][y] == oldVal) {
        mat[x][y] = newVal;
        if (!openUp)
          if (mat[x][y + 1] == oldVal) {
            stack.push(new Point(x, y + 1));
            openUp = true;
          } else if (mat[x][y + 1] != oldVal)
            openUp = false;
        if (!openDown) {
          if (mat[x][y - 1] == oldVal) {
            stack.push(new Point(x, y - 1));
            openDown = true;
          }
        } else if (mat[x][y - 1] != oldVal)
          openDown = false;
        x++;
      }
    }
  }

  public void floodFillRecurse(int x, int y) {
    counter++;
    if (x < 0 || x >= mat[0].length)
      return;
    if (y < 0 || y >= mat.length)
      return;
    if (mat[y][x] != fillChar && mat[y][x] != borderChar) {
      mat[y][x] = fillChar;
      floodFillRecurse(x + 1, y);
      floodFillRecurse(x, y + 1);
      floodFillRecurse(x - 1, y);
      floodFillRecurse(x, y - 1);
    }
  }
}

