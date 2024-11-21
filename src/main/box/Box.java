public class Box<T> {

    private T item;

    public Box(T param) {
      item = param;
    }

    public void setItem(T t) {
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
