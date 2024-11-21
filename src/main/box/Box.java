public class Box<T> {

    private T t;

    public Box(T param) {
      t = param;
    }

    public void set(T t) {
      this.t = t; 
    }
    
    public T get() {
      return t; 
    }
    
    public String toString() {
      String s = "t: ";
      s = s + t.toString();
      return s;
    }
}
