class Pi {
    public static void main(String[] args) {
        System.out.println(sum(3));
        System.out.println("start");
        double r = 4*calcPI(0, 0, 0.0001);
        System.out.println(r);
        r = 4*PI(0, 0.0001);
        System.out.println(r);
    }
  
    public static double calcPI(int n, double c, double precision) {
        double e = 1.0 / (2*n+1);
        if (e < precision)
            return c;
        int sign = - n%2;
        if (sign == 0)
            sign = 1;
        return calcPI(n+1, c + sign*e, precision);
    }
  
    public static double PI(int n, double precision) {
        int sign = - n%2;
        if (sign == 0)
            sign = 1;
        double e = (double)sign / (2*n+1);
        //System.out.println(e);
        if (Math.abs(e) < precision)
            return 0;
        return e + PI(n+1, precision);
    }
  
    // page 26 q.11
    public static double sum(int n) {
        if (n == 1)
            return 1;
        if (n%2 == 0)
            return -Math.sqrt(2*n-1) + sum(n-1);
        return n*2-1 + sum(n-1);
    }
}
