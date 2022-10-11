package com.shaharyi.corona;

class Matrix {
  private int[][] m;
  private int rows;
  private int cols;
  private double rFactor;
  private int startContagious;
  private int endContagious;
  private double pCatchPerDay;

  public Matrix(int r, int c, double rFactor, int startContagious, int endContagious) {
    // In Java, arrays start with all zeros!
    m = new int[r][c];
    rows = r;
    cols = c;
    int numDays = endContagious - startContagious + 1;
    double pTotalYes = rFactor / 8;
    double pNotCatchPerDay = Math.pow(1 - pTotalYes, 1.0 / numDays);
    this.pCatchPerDay = 1 - pNotCatchPerDay;
    System.out.printf("pCatchPerDay = %.2f \n\n", pCatchPerDay);
    this.rFactor = rFactor;
    this.startContagious = startContagious;
    this.endContagious = endContagious;
  }

  public int get(int i, int j) {
    return m[i][j];
  }

  public void set(int i, int j, int value) {
    m[i][j] = value;
  }

  public void calcNext() {
    int[][] mNew = new int[rows][cols];
    for (int i = 1; i < rows - 1; i++)
      for (int j = 1; j < cols - 1; j++)
        mNew[i][j] = calcNextCell(i, j);
    m = mNew;
  }

  // חישוב הדור הבא בתא הנוכחי
  public int calcNextCell(int r, int c) {
    int v = m[r][c];
    if (v > 0 && v <= endContagious)
      return v + 1;
    if (v == endContagious + 1)
      return v;

    if (v != 0)
      System.out.println("BUGGGGGG");

    // Scan neighbors with "for" inside "for"
    for (int i = r - 1; i <= r + 1; i++)
      for (int j = c - 1; j <= c + 1; j++)
        if (m[i][j] >= startContagious && m[i][j] <= endContagious)
          if (Math.random() < pCatchPerDay)
            return 1;
    return 0;
  }

  public void print() {
    int total = 0;
    for (int i = 1; i < rows - 1; i++) {
      for (int j = 1; j < cols - 1; j++) {
        System.out.printf("%2d ", m[i][j]);
        if (m[i][j] > 0)
          total++;
      }
      System.out.println();
    }
    System.out.println("Total = " + total);
  }
}


