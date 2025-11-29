package projectFiles;

/**
 * AVL Tree implementation - a self-balancing Binary Search Tree.
 * Provides O(log n) time complexity for insert, search, and delete operations.
 * @param <K> The type of key (must be Comparable)
 * @param <V> The type of value/data stored
 */
public class AVLTree<K extends Comparable<K>, V> {
    private AVLNode<K, V> root;
    private int size;

    public AVLTree() {
        root = null;
        size = 0;
    }

    /**
     * Get the height of a node (null-safe)
     */
    private int height(AVLNode<K, V> node) {
        if (node == null) return 0;
        return node.height;
    }

    /**
     * Get the balance factor of a node
     */
    private int getBalance(AVLNode<K, V> node) {
        if (node == null) return 0;
        return height(node.left) - height(node.right);
    }

    /**
     * Update the height of a node based on its children
     */
    private void updateHeight(AVLNode<K, V> node) {
        if (node != null) {
            node.height = 1 + Math.max(height(node.left), height(node.right));
        }
    }

    /**
     * Right rotation for AVL balancing
     */
    private AVLNode<K, V> rightRotate(AVLNode<K, V> y) {
        AVLNode<K, V> x = y.left;
        AVLNode<K, V> T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        updateHeight(y);
        updateHeight(x);

        return x;
    }

    /**
     * Left rotation for AVL balancing
     */
    private AVLNode<K, V> leftRotate(AVLNode<K, V> x) {
        AVLNode<K, V> y = x.right;
        AVLNode<K, V> T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        updateHeight(x);
        updateHeight(y);

        return y;
    }

    /**
     * Insert a key-value pair into the AVL tree - O(log n)
     */
    public void insert(K key, V data) {
        root = insertRec(root, key, data);
    }

    private AVLNode<K, V> insertRec(AVLNode<K, V> node, K key, V data) {
        // Standard BST insertion
        if (node == null) {
            size++;
            return new AVLNode<K, V>(key, data);
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = insertRec(node.left, key, data);
        } else if (cmp > 0) {
            node.right = insertRec(node.right, key, data);
        } else {
            // Key already exists, update the data
            node.data = data;
            return node;
        }

        // Update height of this ancestor node
        updateHeight(node);

        // Get the balance factor to check if this node became unbalanced
        int balance = getBalance(node);

        // Left Left Case
        if (balance > 1 && key.compareTo(node.left.key) < 0) {
            return rightRotate(node);
        }

        // Right Right Case
        if (balance < -1 && key.compareTo(node.right.key) > 0) {
            return leftRotate(node);
        }

        // Left Right Case
        if (balance > 1 && key.compareTo(node.left.key) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && key.compareTo(node.right.key) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    /**
     * Search for a value by key - O(log n)
     */
    public V search(K key) {
        return searchRec(root, key);
    }

    private V searchRec(AVLNode<K, V> node, K key) {
        if (node == null) return null;

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return searchRec(node.left, key);
        } else if (cmp > 0) {
            return searchRec(node.right, key);
        } else {
            return node.data;
        }
    }

    /**
     * Check if a key exists in the tree - O(log n)
     */
    public boolean contains(K key) {
        return search(key) != null;
    }

    /**
     * Get the minimum key node
     */
    private AVLNode<K, V> minNode(AVLNode<K, V> node) {
        AVLNode<K, V> current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    /**
     * Delete a node by key - O(log n)
     */
    public void delete(K key) {
        root = deleteRec(root, key);
    }

    private AVLNode<K, V> deleteRec(AVLNode<K, V> node, K key) {
        if (node == null) return null;

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = deleteRec(node.left, key);
        } else if (cmp > 0) {
            node.right = deleteRec(node.right, key);
        } else {
            // Node to be deleted found

            // Node with only one child or no child
            if (node.left == null || node.right == null) {
                size--;
                AVLNode<K, V> temp = (node.left != null) ? node.left : node.right;
                if (temp == null) {
                    // No child case
                    node = null;
                } else {
                    // One child case
                    node = temp;
                }
            } else {
                // Node with two children: Get the inorder successor
                AVLNode<K, V> temp = minNode(node.right);
                node.key = temp.key;
                node.data = temp.data;
                // Recursively delete successor (size will be decremented there)
                node.right = deleteRec(node.right, temp.key);
            }
        }

        if (node == null) return null;

        // Update height
        updateHeight(node);

        // Get balance factor
        int balance = getBalance(node);

        // Left Left Case
        if (balance > 1 && getBalance(node.left) >= 0) {
            return rightRotate(node);
        }

        // Left Right Case
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Right Case
        if (balance < -1 && getBalance(node.right) <= 0) {
            return leftRotate(node);
        }

        // Right Left Case
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    /**
     * In-order traversal - returns elements in sorted order
     * Stores results in a LinkedList
     */
    public LinkedList<V> inOrderTraversal() {
        LinkedList<V> result = new LinkedList<V>();
        inOrderRec(root, result);
        return result;
    }

    private void inOrderRec(AVLNode<K, V> node, LinkedList<V> result) {
        if (node != null) {
            inOrderRec(node.left, result);
            // Add to end of list
            if (result.empty()) {
                result.insert(node.data);
            } else {
                result.findFirst();
                while (!result.last()) {
                    result.findNext();
                }
                result.insert(node.data);
            }
            inOrderRec(node.right, result);
        }
    }

    /**
     * Range query - find all values where key is between minKey and maxKey (inclusive)
     * Uses modified in-order traversal for efficiency - O(log n + k) where k is results count
     */
    public LinkedList<V> rangeQuery(K minKey, K maxKey) {
        LinkedList<V> result = new LinkedList<V>();
        rangeQueryRec(root, minKey, maxKey, result);
        return result;
    }

    private void rangeQueryRec(AVLNode<K, V> node, K minKey, K maxKey, LinkedList<V> result) {
        if (node == null) return;

        int cmpMin = minKey.compareTo(node.key);
        int cmpMax = maxKey.compareTo(node.key);

        // If node's key is greater than minKey, search left subtree
        if (cmpMin < 0) {
            rangeQueryRec(node.left, minKey, maxKey, result);
        }

        // If node's key is within range, add to result
        if (cmpMin <= 0 && cmpMax >= 0) {
            if (result.empty()) {
                result.insert(node.data);
            } else {
                result.findFirst();
                while (!result.last()) {
                    result.findNext();
                }
                result.insert(node.data);
            }
        }

        // If node's key is less than maxKey, search right subtree
        if (cmpMax > 0) {
            rangeQueryRec(node.right, minKey, maxKey, result);
        }
    }

    /**
     * Get minimum value
     */
    public V getMin() {
        if (root == null) return null;
        return minNode(root).data;
    }

    /**
     * Get maximum value
     */
    public V getMax() {
        if (root == null) return null;
        AVLNode<K, V> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.data;
    }

    /**
     * Check if tree is empty
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Get the size of the tree
     */
    public int size() {
        return size;
    }

    /**
     * Get the root node (for advanced traversals)
     */
    public AVLNode<K, V> getRoot() {
        return root;
    }

    /**
     * Update the value for an existing key - O(log n)
     * Returns true if key existed and was updated, false otherwise
     */
    public boolean update(K key, V newData) {
        AVLNode<K, V> node = findNode(root, key);
        if (node != null) {
            node.data = newData;
            return true;
        }
        return false;
    }

    private AVLNode<K, V> findNode(AVLNode<K, V> node, K key) {
        if (node == null) return null;
        
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return findNode(node.left, key);
        } else if (cmp > 0) {
            return findNode(node.right, key);
        } else {
            return node;
        }
    }
}
