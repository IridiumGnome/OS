import java.io.*;

public class BTree implements Serializable {

    private int T;
    private Node root;
    private static final long serialVersionUID = 1L;
    BTree(int t) {
        T = t;
        root = new Node(T, 0, true);
    }

    private String Get(Node x, long key) {
        int xSize = x.getN();
        long[] xKeys = x.getKey();
        String[] xValues = x.getValue();
        Node[] xChildren = x.getChild();
        int i = 0;
        for ( ; i < xSize; i++) {
            if (key < xKeys[i]) {
                break;
            }
            if (key == xKeys[i]) {
                return xValues[i];
            }
        }
        if (x.isLeaf()) {
            return null;
        }
        else {
            return Get(xChildren[i], key);
        }
    }

    private void Change(Node x, long k, String v) {
        int i = 0;
        int xSize = x.getN();
        long[] xKeys = x.getKey();
        Node[] xChildren = x.getChild();
        //if (x != null) {
            for (i = 0; i < xSize; i++) {
                if (k < xKeys[i]) {
                    break;
                }
                if (k == xKeys[i]) {
                    x.setValueId(i, v);
                }
            }
        //}
        if(!x.isLeaf())
            Change(xChildren[i], k, v);
    }

    // Split function
    private void Split(Node x, int pos, Node y) {
        Node z = new Node(T, T - 1, y.isLeaf());

        for (int j = 0; j < T - 1; j++) {
            z.setKeyId(j, y.getKeyId(j + T));
            z.setValueId(j, y.getValueId(j + T));
        }
        if (!y.isLeaf()) {
            for (int j = 0; j < T; j++) {
                z.setChildId(j, y.getChildId(j + T));
            }
        }
        y.setN(T - 1);
        int xN = x.getN();
        for (int j = xN; j >= pos + 1; j--) {
            x.setChildId(j + 1, x.getChildId(j));
        }
        x.setChildId(pos + 1, z);

        for (int j = xN; j >= pos; j--) {
            x.setKeyId(j + 1, x.getKeyId(j));
            x.setValueId(j + 1, x.getValueId(j));
        }
        x.setKeyId(pos, y.getKeyId(T - 1));
        x.setValueId(pos, y.getValueId(T - 1));
        x.setN(xN + 1);
    }

    // Insert the key
    public void Insert(final long key, String v) {
        Node r = root;
        if (r.getN() == 2 * T - 1) {
            Node s = new Node(T, 0, false);
            root = s;
            s.setChildId(0, r);
            Split(s, 0, r);
            _Insert(s, key, v);
        }
        else {
            _Insert(r, key, v);
        }
    }

    // Insert the node
    private void _Insert(Node x, long k, String v) {
        if (x.isLeaf()) {
            int i = 0;
            for (i = x.getN() - 1; i >= 0 && k < x.getKeyId(i); i--) {
                x.setKeyId(i + 1, x.getKeyId(i));
                x.setValueId(i + 1, x.getValueId(i));
            }
            x.setKeyId(i + 1, k);
            x.setValueId(i + 1, v);
            x.setN(x.getN() + 1);
        }
        else {
            int i = 0;
            for (i = x.getN() - 1; i >= 0 && k < x.getKeyId(i); i--) {};
            i++;
            Node tmp = x.getChildId(i);
            if (tmp.getN() == 2 * T - 1) {
                Split(x, i, tmp);
                if (k > x.getKeyId(i)) {
                    i++;
                }
            }
            _Insert(x.getChildId(i), k, v);
        }
    }

    public Node Search(Node x, long key) {
        int i = 0;
        if (x == null)
            return x;
        for (i = 0; i < x.getN(); i++) {
            if (key < x.getKeyId(i)) {
                break;
            }
            if (key == x.getKeyId(i)) {
                return x;
            }
        }
        if (x.isLeaf()) {
            return null;
        }
        else {
            return Search(x.getChildId(i), key);
        }
    }
    public void Show() {
        Show(root);
    }

    public void Change(long k, String v) {
        if (Search(root, k) != null) {
            Change(root, k, v);
        }
    }

    public String Get(long k) {
        String tmp = this.Get(root, k);
        if (tmp != null) {
            return tmp;
        }
        else {
            return "EMPTY";
        }
    }

    public boolean Contain(long k) {
        if (this.Search(root, k) != null) {
            return true;
        }
        else {
            return false;
        }
    }

    // Show the node
    private void Show(Node x) {
        assert (x == null);
        for (int i = 0; i < x.getN(); i++) {
            System.out.println(x.getKeyId(i) + " " + x.getValueId(i));
        }
        if (!x.isLeaf()) {
            for (int i = 0; i < x.getN() + 1; i++) {
                Show(x.getChildId(i));
            }
        }
    }

//    static int iMax = 1;
//
//    public static void RewriteCurrent(BTree b) {
//        try {
//            FileOutputStream fileOut = new FileOutputStream("/storage/" + iMax + ".tmp");
//            ObjectOutputStream out = new ObjectOutputStream(fileOut);
//            out.writeObject(b);
//            out.close();
//            fileOut.close();
//            System.out.printf("Serialized data is saved in storage");
//        } catch (IOException i) {
//            i.printStackTrace();
//        }
//    }
//
//    public static boolean CheckPrev(long key, String val) {
//
//        int i = 1;
//        while(true) {
//            BTree bH = null;
//            try {
//                FileInputStream fileIn = new FileInputStream("/storage/" + i + ".tmp");
//                ObjectInputStream in = new ObjectInputStream(fileIn);
//                bH = (BTree) in.readObject();
//                in.close();
//                fileIn.close();
//                if(bH.Contain(key)) {
//                    bH.Change(key, val);
//                    return true;
//                }
//                else {
//                    i++;
//                    bH = null;
//                }
//            }
//            catch (IOException e) {
//                //i.printStackTrace();
//                //System.exit(0);
//                return false;
//            }
//            catch (ClassNotFoundException c) {
//                System.out.println("No storage");
//                iMax = i;
//                //c.printStackTrace();
//                return false;
//            }
//        }
//    }

}
