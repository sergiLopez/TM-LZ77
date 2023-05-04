public class LDNode {

    private final int position;
    private final int offset;

    public LDNode(int offset, int position) {
        this.position = position;
        this.offset = offset;
    }

    public int getPosition() {
        return position;
    }

    public int getOffset() {
        return offset;
    }

    @Override
    public String toString() {
        return "(" + offset + ", " + position + ")";
    }
}
