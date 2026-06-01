import java.util.*;

// Product Node for AVL Tree
class ProductNode {
    int productId;
    String productName;
    double price;
    int stock;
    int height;

    ProductNode left, right;

    ProductNode(int id, String name, double price, int stock) {
        this.productId = id;
        this.productName = name;
        this.price = price;
        this.stock = stock;
        this.height = 1;
    }
}

// AVL Tree Class
class AVLTree {

    // Get height
    int height(ProductNode node) {
        if (node == null)
            return 0;
        return node.height;
    }

    // Get maximum
    int max(int a, int b) {
        return Math.max(a, b);
    }

    // Right Rotation (LL Rotation)
    ProductNode rightRotate(ProductNode y) {
        ProductNode x = y.left;
        ProductNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;

        return x;
    }

    // Left Rotation (RR Rotation)
    ProductNode leftRotate(ProductNode x) {
        ProductNode y = x.right;
        ProductNode T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;

        return y;
    }

    // Balance Factor
    int getBalance(ProductNode node) {
        if (node == null)
            return 0;

        return height(node.left) - height(node.right);
    }

    // Insert Product
    ProductNode insert(ProductNode node, int id, String name,
                       double price, int stock) {

        // BST Insertion
        if (node == null)
            return new ProductNode(id, name, price, stock);

        if (id < node.productId)
            node.left = insert(node.left, id, name, price, stock);

        else if (id > node.productId)
            node.right = insert(node.right, id, name, price, stock);

        else
            return node;

        // Update height
        node.height = 1 + max(height(node.left), height(node.right));

        // Check balance
        int balance = getBalance(node);

        // LL Rotation
        if (balance > 1 && id < node.left.productId)
            return rightRotate(node);

        // RR Rotation
        if (balance < -1 && id > node.right.productId)
            return leftRotate(node);

        // LR Rotation
        if (balance > 1 && id > node.left.productId) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // RL Rotation
        if (balance < -1 && id < node.right.productId) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // Find minimum node
    ProductNode minValueNode(ProductNode node) {
        ProductNode current = node;

        while (current.left != null)
            current = current.left;

        return current;
    }

    // Delete Product
    ProductNode delete(ProductNode root, int id) {

        if (root == null)
            return root;

        // BST Deletion
        if (id < root.productId)
            root.left = delete(root.left, id);

        else if (id > root.productId)
            root.right = delete(root.right, id);

        else {

            if ((root.left == null) || (root.right == null)) {

                ProductNode temp;

                if (root.left != null)
                    temp = root.left;
                else
                    temp = root.right;

                if (temp == null) {
                    temp = root;
                    root = null;
                } else
                    root = temp;

            } else {

                ProductNode temp = minValueNode(root.right);

                root.productId = temp.productId;
                root.productName = temp.productName;
                root.price = temp.price;
                root.stock = temp.stock;

                root.right = delete(root.right, temp.productId);
            }
        }

        if (root == null)
            return root;

        // Update height
        root.height = max(height(root.left), height(root.right)) + 1;

        // Balance factor
        int balance = getBalance(root);

        // LL Rotation
        if (balance > 1 && getBalance(root.left) >= 0)
            return rightRotate(root);

        // LR Rotation
        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // RR Rotation
        if (balance < -1 && getBalance(root.right) <= 0)
            return leftRotate(root);

        // RL Rotation
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    // Search Product
    ProductNode search(ProductNode root, int id) {

        if (root == null || root.productId == id)
            return root;

        if (id < root.productId)
            return search(root.left, id);

        return search(root.right, id);
    }

    // Inorder Traversal
    void inorder(ProductNode root) {

        if (root != null) {

            inorder(root.left);

            System.out.println(
                    "Product ID: " + root.productId +
                    ", Name: " + root.productName +
                    ", Price: " + root.price +
                    ", Stock: " + root.stock);

            inorder(root.right);
        }
    }
}

// Main Class
public class RetailMindSystem {

    public static void main(String[] args) {

        AVLTree tree = new AVLTree();
        ProductNode root = null;

        // Insert Products
        root = tree.insert(root, 101, "Laptop", 55000, 10);
        root = tree.insert(root, 105, "Mobile", 25000, 20);
        root = tree.insert(root, 102, "Keyboard", 1500, 50);
        root = tree.insert(root, 110, "Mouse", 800, 40);
        root = tree.insert(root, 108, "Monitor", 12000, 15);

        System.out.println("Product Inventory (Inorder Traversal):");
        tree.inorder(root);

        // Search Product
        int searchId = 105;

        ProductNode result = tree.search(root, searchId);

        if (result != null) {
            System.out.println("\nProduct Found:");
            System.out.println("ID: " + result.productId +
                               ", Name: " + result.productName +
                               ", Price: " + result.price +
                               ", Stock: " + result.stock);
        } else {
            System.out.println("\nProduct Not Found");
        }

        // Delete Product
        System.out.println("\nDeleting Product ID 102...");
        root = tree.delete(root, 102);

        System.out.println("\nUpdated Inventory:");
        tree.inorder(root);
    }
}