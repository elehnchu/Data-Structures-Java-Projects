import javax.swing.*;

/**
 * Class that implements an AVL tree which implements the MyMap interface.
 * @author El Chu Hc3294
 * @version 1.0 November 6, 2022
 */
public class AVLTreeMap<K extends Comparable<K>, V> extends BSTMap<K, V>
        implements MyMap<K, V> {
    private static final int ALLOWED_IMBALANCE = 1;

    /**
     * Creates an empty AVL tree map.
     */
    public AVLTreeMap() { }

    public AVLTreeMap(Pair<K, V>[] elements) {
        insertElements(elements);
    }

    /**
     * Creates a AVL tree map of the given key-value pairs. If
     * sorted is true, a balanced tree will be created via a divide-and-conquer
     * approach. If sorted is false, the pairs will be inserted in the order
     * they are received, and the tree will be rotated to maintain the AVL tree
     * balance property.
     * @param elements an array of key-value pairs
     */
    public AVLTreeMap(Pair<K, V>[] elements, boolean sorted) {
        if (!sorted) {
            insertElements(elements);
        } else {
            root = createBST(elements, 0, elements.length - 1);
        }
    }

    /**
     * Recursively constructs a balanced binary search tree by inserting the
     * elements via a divide-snd-conquer approach. The middle element in the
     * array becomes the root. The middle of the left half becomes the root's
     * left child. The middle element of the right half becomes the root's right
     * child. This process continues until low > high, at which point the
     * method returns a null Node.
     * @param pairs an array of <K, V> pairs sorted by key
     * @param low   the low index of the array of elements
     * @param high  the high index of the array of elements
     * @return      the root of the balanced tree of pairs
     */
    protected Node<K, V> createBST(Pair<K, V>[] pairs, int low, int high) {
        if (low > high) {
            return null;
        }
        int mid = low + (high - low) / 2;
        Pair<K, V> pair = pairs[mid];
        Node<K, V> parent = new Node<>(pair.key, pair.value);
        size++;
        parent.left = createBST(pairs, low, mid - 1);
        if (parent.left != null) {
            parent.left.parent = parent;
        }
        parent.right = createBST(pairs, mid + 1, high);
        if (parent.right != null) {
            parent.right.parent = parent;
        }
        // This line is critical for being able to add additional nodes or to
        // remove nodes. Forgetting this line leads to incorrectly balanced
        // trees.
        parent.height =
                Math.max(avlHeight(parent.left), avlHeight(parent.right)) + 1;
        return parent;
    }

    /**
     * Associates the specified value with the specified key in this map. If the
     * map previously contained a mapping for the key, the old value is replaced
     * by the specified value.
     * @param key   the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     * @return the previous value associated with key, or null if there was no
     *         mapping for key
     */
    @Override
    public V put(K key, V value) {
        //nvp is the parent of whatever you're going to add
        NodeOldValuePair nvp = new NodeOldValuePair(null, null);
        nvp = insertAndBalance(key, value, root, nvp);
        return nvp.oldValue;
    }

    /**
     * Removes the mapping for a key from this map if it is present.
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with key, or null if there was no
     *         mapping for key
     */
    public V remove(K key) {
        // Replace the line with the code required for proper removal from an
        // AVL tree. This task is extra credit.
        //exact same as BST remove
        // and then check if balanced (method) call
        NodeOldValuePair nvp = new NodeOldValuePair(null, null);
        root = helperremove(root, key, nvp);
        return nvp.oldValue;
    }

    private Node<K, V> helperremove(Node<K, V> root, K key, NodeOldValuePair nvp) {

        int comparison = key.compareTo( root.key);
        if (comparison < 0) {

            root.left = helperremove( root.left, key, nvp);
            if (root.left!= null) {
                root.left.parent = root;
            }

        } else if (comparison > 0) {

            root.right = helperremove( root.right, key, nvp);
            if (root.right!= null) {
                root.right.parent = root;
            }

        } else {
            if (nvp.oldValue== null) {
                nvp.oldValue = root.value;
                size--;
            }
            if ((root.left == null) || (root.right == null)) {
                Node<K, V> t = null;
                if (root.left == null){
                    t = root.right;
                }
                else{
                     t = root.left;
                }

                if (t != null) {
                    t.parent = root.parent;
                }

                if (t == null){
                    root = null;
                }
                else {
                    root = t;
                }
            } else {
                Node<K, V> t = treeMinimum( root.right);
                root.key = t.key;
                root.value = t.value;
                root.right = helperremove( root.right, t.key, nvp);

                if (root.right != null) {
                    root.right.parent = root;
                }
            }
        }
        if (root== null) {
            return null;
        }
        root.height = Math.max(height(root.left), height(root.right)) + 1;
        return balance(root);
    }


    private NodeOldValuePair insertAndBalance(
            //t is pointer to root
            K key, V value, Node<K, V> t, NodeOldValuePair nvp) {
        //if t = null then makes nvp as new node w/root at nvp.node
        if (t == null) {
            size++;
            nvp.node = new Node<K, V>(key, value);
            if (root == null) {
                root = nvp.node;
            }
            return nvp;
        }
        //compares key wishing to place in with current key of t
        int comparison = key.compareTo(t.key);
        // TODO
        if (comparison < 0){
            nvp = insertAndBalance(key, value, t.left, nvp);
            t.left = nvp.node;
            nvp.node.parent = t;
        }
        else if (comparison > 0) {
            nvp = insertAndBalance(key, value, t.right, nvp);
            t.right = nvp.node;
            nvp.node.parent = t;
        }
        else {
            nvp.node = t;
            nvp.oldValue = t.value;
            t.value = value;
            return nvp;
        }
        nvp.node = t;

        t.height = Math.max(height(t.left), height (t.right))+1;
        //Brian code here
        Node<K, V> n = balance(t);
        nvp.node = n;
        return nvp;
    }


    private Node<K, V> balance(Node<K, V> t) {
        // TODO
        //when to know which rotation to call
        if (t == null) {
            return t;
        }
        if (height(t.left)- height(t.right) > 1) {
            //what specifically diagram wise is this comparing/ how do we get the height
            if (height(t.left.left) >= height(t.left.right)){
                t = rotateWithLeftChild(t);
            }
            else{
                t = doubleWithLeftChild(t);
            }
        }
        else if (height(t.right)- height(t.left) > 1){
            if (height(t.right.right) >= height(t.right.left)){
                t = rotateWithRightChild(t);
            }
            else {
                t = doubleWithRightChild(t);
            }
        }
        //+1 to include current node
        t.height = Math.max(height(t.left), height (t.right))+1;
        return t;
    }

    private int avlHeight(Node<K, V> t) {
        return t == null ? -1 : t.height;
    }

    private Node<K, V> rotateWithLeftChild(Node<K, V> k2) {
        // TODO
        Node<K,V> newroot = k2.left;

        if (root == k2) {
            root = newroot;
            root.parent = null;
        }

        k2.left = newroot.right;
        if (newroot.right != null){
            newroot.right.parent = k2;
        }
        newroot.right = k2;
        k2.parent = newroot;

        k2.height = Math.max( height(k2.left), height(k2.right))+1;
        newroot.height = Math.max(height(newroot.left), k2.height) +1;
        return newroot;
    }
    //
    private Node<K, V> rotateWithRightChild(Node<K, V> k1) {
        // TODO
        //change left to right and vice versa
        Node<K,V> newroot = k1.right;

        if (root == k1) {
            root = newroot;
            root.parent = null;
        }
        k1.right = newroot.left;
        if (newroot.left != null){
            newroot.left.parent = k1;
        }
        newroot.left = k1;
        k1.parent = newroot;
        k1.height = Math.max( height(k1.left), height(k1.right))+1;
        newroot.height = Math.max( height(newroot.left), k1.height) +1;
        return newroot;
    }

    private Node<K, V> doubleWithLeftChild(Node<K, V> k3) {
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }

    private Node<K, V> doubleWithRightChild(Node<K, V> k3) {
        k3.right = rotateWithLeftChild(k3.right);
        return rotateWithRightChild(k3);
    }

    private class NodeOldValuePair {
        Node<K, V> node;
        V oldValue;

        NodeOldValuePair(Node<K, V> n, V oldValue) {
            this.node = n;
            this.oldValue = oldValue;
        }
    }

    public static void main(String[] args) {
        boolean usingInts = true;
        if (args.length > 0) {
            try {
                Integer.parseInt(args[0]);
            } catch (NumberFormatException nfe) {
                usingInts = false;
            }
        }

        AVLTreeMap avlTree;
        if (usingInts) {
            @SuppressWarnings("unchecked")
            Pair<Integer, Integer>[] pairs = new Pair[args.length];
            for (int i = 0; i < args.length; i++) {
                try {
                    int val = Integer.parseInt(args[i]);
                    pairs[i] = new Pair<>(val, val);
                } catch (NumberFormatException nfe) {
                    System.err.println("Error: Invalid integer '" + args[i]
                            + "' found at index " + i + ".");
                    System.exit(1);
                }
            }
            avlTree = new AVLTreeMap<Integer, Integer>(pairs);
        } else {
            @SuppressWarnings("unchecked")
            Pair<String, String>[] pairs = new Pair[args.length];
            for (int i = 0; i < args.length; i++) {
                pairs[i] = new Pair<>(args[i], args[i]);
            }
            avlTree = new AVLTreeMap<String, String>(pairs);
        }

        System.out.println(avlTree.toAsciiDrawing());
        System.out.println();
        System.out.println("Height:                   " + avlTree.height());
        System.out.println("Total nodes:              " + avlTree.size());
        System.out.printf("Successful search cost:   %.3f\n",
                avlTree.successfulSearchCost());
        System.out.printf("Unsuccessful search cost: %.3f\n",
                avlTree.unsuccessfulSearchCost());
        avlTree.printTraversal(PREORDER);
        avlTree.printTraversal(INORDER);
        avlTree.printTraversal(POSTORDER);
    }
}
