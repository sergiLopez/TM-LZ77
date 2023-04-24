public class Tuple {
  public final int length;
  public final int position;

  public Tuple(int length, int position) {
    this.length = length;
    this.position = position;
  }

  public int getLength() {
    return length;
  }

  public int getPosition() {
    return position;
  }

  @Override
  public String toString() {
    return "Tuple{" + "length=" + length + ", position=" + position + '}';
  }
}