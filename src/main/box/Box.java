public class Box<T> {

    private T item;

    public Box(T x) {
      item = x;
    }

    public void setItem(T item) {
      this.item = item; 
    }
    
    public T getItem() {
      return item; 
    }
    
    public String toString() {
      String s = "item: ";
      s = s + item.toString();
      return s;
    }
}
