```java id="5h2kq9"
import java.util.*;

// -----------------------------
// Product Class
// -----------------------------
class Product {
    int productId;
    int quantity;
    int sales;

    Product(int productId, int quantity, int sales) {
        this.productId = productId;
        this.quantity = quantity;
        this.sales = sales;
    }
}

// -----------------------------
// Segment Tree for Range Queries
// -----------------------------
class SegmentTree {

    int[] tree;
    int n;

    SegmentTree(int[] arr) {
        n = arr.length;
        tree = new int[4 * n];
        build(arr, 0, 0, n - 1);
    }

    // Build Segment Tree
    void build(int[] arr, int node, int start, int end) {

        if (start == end) {
            tree[node] = arr[start];
        } else {

            int mid = (start + end) / 2;

            build(arr, 2 * node + 1, start, mid);
            build(arr, 2 * node + 2, mid + 1, end);

            tree[node] =
                    tree[2 * node + 1] + tree[2 * node + 2];
        }
    }

    // Range Sum Query
    int query(int node, int start, int end, int left, int right) {

        // No overlap
        if (right < start || end < left) {
            return 0;
        }

        // Complete overlap
        if (left <= start && end <= right) {
            return tree[node];
        }

        // Partial overlap
        int mid = (start + end) / 2;

        int p1 = query(2 * node + 1, start, mid, left, right);
        int p2 = query(2 * node + 2, mid + 1, end, left, right);

        return p1 + p2;
    }

    // Update value
    void update(int node, int start, int end,
                int index, int value) {

        if (start == end) {
            tree[node] = value;
        } else {

            int mid = (start + end) / 2;

            if (index <= mid) {
                update(2 * node + 1, start, mid, index, value);
            } else {
                update(2 * node + 2, mid + 1, end, index, value);
            }

            tree[node] =
                    tree[2 * node + 1] + tree[2 * node + 2];
        }
    }

    int rangeSum(int left, int right) {
        return query(0, 0, n - 1, left, right);
    }
}

// -----------------------------
// Fenwick Tree (Binary Indexed Tree)
// -----------------------------
class FenwickTree {

    int[] bit;
    int n;

    FenwickTree(int size) {
        n = size;
        bit = new int[n + 1];
    }

    // Update Fenwick Tree
    void update(int index, int value) {

        while (index <= n) {
            bit[index] += value;
            index += index & (-index);
        }
    }

    // Prefix Sum Query
    int prefixSum(int index) {

        int sum = 0;

        while (index > 0) {
            sum += bit[index];
            index -= index & (-index);
        }

        return sum;
    }

    // Range Sum Query
    int rangeSum(int left, int right) {
        return prefixSum(right) - prefixSum(left - 1);
    }
}

// -----------------------------
// Main Warehouse Management System
// -----------------------------
public class WarehouseManagementSystem {

    public static void main(String[] args) {

        // Sample Warehouse Dataset
        Product[] products = {
                new Product(101, 50, 200),
                new Product(102, 40, 150),
                new Product(103, 70, 300),
                new Product(104, 30, 120),
                new Product(105, 90, 500)
        };

        System.out.println("WAREHOUSE DATASET");
        System.out.println("------------------------------");

        for (Product p : products) {
            System.out.println(
                    "Product ID: " + p.productId +
                    ", Quantity: " + p.quantity +
                    ", Sales: " + p.sales);
        }

        // -----------------------------
        // Segment Tree Operations
        // -----------------------------

        int[] quantities = new int[products.length];

        for (int i = 0; i < products.length; i++) {
            quantities[i] = products[i].quantity;
        }

        SegmentTree segmentTree = new SegmentTree(quantities);

        System.out.println("\nSEGMENT TREE OPERATIONS");
        System.out.println("------------------------------");

        // Query total stock from index 1 to 3
        int totalStock =
                segmentTree.rangeSum(1, 3);

        System.out.println(
                "Total Stock from Product 102 to 104: "
                        + totalStock);

        // Update stock
        segmentTree.update(0, 0,
                quantities.length - 1,
                2, 100);

        System.out.println(
                "Updated Product 103 Quantity to 100");

        int updatedStock =
                segmentTree.rangeSum(1, 3);

        System.out.println(
                "Updated Total Stock from Product 102 to 104: "
                        + updatedStock);

        // -----------------------------
        // Fenwick Tree Operations
        // -----------------------------

        FenwickTree fenwickTree =
                new FenwickTree(products.length);

        for (int i = 0; i < products.length; i++) {
            fenwickTree.update(i + 1, products[i].sales);
        }

        System.out.println("\nFENWICK TREE OPERATIONS");
        System.out.println("------------------------------");

        // Prefix sum query
        int cumulativeSales =
                fenwickTree.prefixSum(3);

        System.out.println(
                "Cumulative Sales till Product 103: "
                        + cumulativeSales);

        // Range sales query
        int salesRange =
                fenwickTree.rangeSum(2, 5);

        System.out.println(
                "Sales from Product 102 to 105: "
                        + salesRange);

        // Update sales
        fenwickTree.update(3, 100);

        System.out.println(
                "Added 100 Sales to Product 103");

        int updatedSales =
                fenwickTree.prefixSum(5);

        System.out.println(
                "Updated Total Sales: "
                        + updatedSales);

        // -----------------------------
        // Complexity Analysis
        // -----------------------------

        System.out.println("\nTIME COMPLEXITY ANALYSIS");
        System.out.println("------------------------------");

        System.out.println(
                "Segment Tree Query Complexity: O(log n)");

        System.out.println(
                "Segment Tree Update Complexity: O(log n)");

        System.out.println(
                "Fenwick Tree Prefix Sum Complexity: O(log n)");

        System.out.println(
                "Fenwick Tree Update Complexity: O(log n)");

        System.out.println(
                "B-Tree/B+ Tree Indexing Complexity: O(log n)");
    }
}
```
