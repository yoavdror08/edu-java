public class Pair<K, V> {
  private K v1;
  private V v2;

  public Pair(K x, V y) {
    v1 = x;
    v2 = y;
  }
  public K getV1() {
    return v1;
  }
  public V getV2() {
    return v2;
  }

  public String toString() {
      String s = "My props:\n";
      s += "v1: " + v1 + "\n";
      s += "v2: " + v2;
      return s;
}
