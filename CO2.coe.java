import java.util.Scanner;

class Record {
    int studentId;
    int attendance;

    Record(int studentId, int attendance) {
        this.studentId = studentId;
        this.attendance = attendance;
    }

    @Override
    public String toString() {
        return "[" + studentId + ", " + attendance + "%]";
    }
}

class BTreeNode {
    int t;
    int n;
    Record[] keys;
    BTreeNode[] children;
    boolean leaf;

    BTreeNode(int t, boolean leaf) {
        this.t = t;
        this.leaf = leaf;
        this.keys = new Record[2 * t - 1];
        this.children = new BTreeNode[2 * t];
        this.n = 0;
    }

    void traverse() {
        int i;
        for (i = 0; i < n; i++) {
            if (!leaf) {
                children[i].traverse();
            }
            System.out.print(keys[i].studentId + " ");
        }

        if (!leaf) {
            children[i].traverse();
        }
    }

    Record search(int id) {
        int i = 0;

        while (i < n && id > keys[i].studentId) {
            i++;
        }

        if (i < n && keys[i].studentId == id) {
            return keys[i];
        }

        if (leaf) {
            return null;
        }

        return children[i].search(id);
    }

    void insertNonFull(Record k) {
        int i = n - 1;

        if (leaf) {
            while (i >= 0 && keys[i].studentId > k.studentId) {
                keys[i + 1] = keys[i];
                i--;
            }

            keys[i + 1] = k;
            n++;
        } else {
            while (i >= 0 && keys[i].studentId > k.studentId) {
                i--;
            }

            if (children[i + 1].n == 2 * t - 1) {
                splitChild(i + 1, children[i + 1]);

                if (keys[i + 1].studentId < k.studentId) {
                    i++;
                }
            }

            children[i + 1].insertNonFull(k);
        }
    }

    void splitChild(int i, BTreeNode y) {
        BTreeNode z = new BTreeNode(y.t, y.leaf);
        z.n = t - 1;

        for (int j = 0; j < t - 1; j++) {
            z.keys[j] = y.keys[j + t];
        }

        if (!y.leaf) {
            for (int j = 0; j < t; j++) {
                z.children[j] = y.children[j + t];
            }
        }

        y.n = t - 1;

        for (int j = n; j >= i + 1; j--) {
            children[j + 1] = children[j];
        }

        children[i + 1] = z;

        for (int j = n - 1; j >= i; j--) {
            keys[j + 1] = keys[j];
        }

        keys[i] = y.keys[t - 1];
        n++;
    }
}

class BTree {
    BTreeNode root;
    int t;

    BTree(int t) {
        this.root = null;
        this.t = t;
    }

    void traverse() {
        if (root != null) {
            root.traverse();
        }
    }

    Record search(int id) {
        if (root == null) {
            return null;
        } else {
            return root.search(id);
        }
    }

    void insert(Record k) {
        if (root == null) {
            root = new BTreeNode(t, true);
            root.keys[0] = k;
            root.n = 1;
        } else {
            if (root.n == 2 * t - 1) {
                BTreeNode s = new BTreeNode(t, false);

                s.children[0] = root;
                s.splitChild(0, root);

                int i = 0;

                if (s.keys[0].studentId < k.studentId) {
                    i++;
                }

                s.children[i].insertNonFull(k);
                root = s;
            } else {
                root.insertNonFull(k);
            }
        }
    }
}

public class AttendanceIndexing {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        BTree tree = new BTree(3);

        System.out.println("Attendance Database Indexing Using B-Tree\n");

        Record[] records = {
            new Record(101, 92),
            new Record(102, 85),
            new Record(103, 96),
            new Record(104, 78),
            new Record(105, 88)
        };

        System.out.println("Inserting Records...");

        for (Record r : records) {
            tree.insert(r);
            System.out.println(r);
        }

        System.out.println("\nB-Tree Created Successfully.\n");

        System.out.println("Search Operation:");
        System.out.print("Enter Student ID: ");

        int id = sc.nextInt();

        Record result = tree.search(id);

        if (result != null) {
            System.out.println("\nRecord Found");
            System.out.println("--------------------------");
            System.out.println("Student ID : " + result.studentId);
            System.out.println("Attendance : " + result.attendance + "%");
            System.out.println("--------------------------");
        } else {
            System.out.println("\nRecord Not Found");
        }

        System.out.println("\nIndexed Student IDs:");
        tree.traverse();

        sc.close();
    }
}