package projectFiles;

/**
 * AVL Tree Node class for self-balancing Binary Search Tree implementation.
 * Each node contains a key for ordering, data for the stored value, and height for balancing.
 * @param <K> The type of key (must be Comparable)
 * @param <V> The type of value/data stored
 */
public class AVLNode<K extends Comparable<K>, V> {
    K key;
    V data;
    AVLNode<K, V> left;
    AVLNode<K, V> right;
    int height;

    public AVLNode(K key, V data) {
        this.key = key;
        this.data = data;
        this.left = null;
        this.right = null;
        this.height = 1; // New node is initially at height 1
    }
}
