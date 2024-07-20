import java.io.Serializable;

public class Node implements Serializable {
    private int n;
    private long key[];
    private String value[];
    private Node child[];
    private boolean leaf = true;

    Node(int T, int n, boolean leaf) {
        this.n = n;
        this.leaf = leaf;
        key = new long[2 * T - 1];
        value = new String[2 * T - 1];
        child = new Node[2 * T - 1];
    }

    public int Find(long k) {
        for (int i = 0; i < this.n; i++) {
            if (this.key[i] == k) {
                return i;
            }
        }
        return -1;
    }


    public int getN() {
        return n;
    }

    public long[] getKey() {
        return key;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public String[] getValue() {
        return value;
    }

    public Node[] getChild() {
        return child;
    }
    public void setValueId(int i, String val) {
        value[i] = val;
    }
    public void setKeyId(int i, long val) {
        key[i] = val;
    }
    public String getValueId(int i) {
        return value[i];
    }
    public long getKeyId(int i) {
        return key[i];
    }
    public void setChildId(int i, Node a) {
        child[i] = a;
    }
    public Node getChildId(int i) {
        return child[i];
    }
    public void setN(int n) {
        this.n = n;
    }
}


