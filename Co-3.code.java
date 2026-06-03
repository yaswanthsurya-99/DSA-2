import java.util.*;

class Edge implements Comparable<Edge> {
    String src, dest;
    int weight;

    Edge(String src, String dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge other) {
        return this.weight - other.weight;
    }
}

class DisjointSet {
    private Map<String, String> parent = new HashMap<>();

    void makeSet(String node) {
        parent.put(node, node);
    }

    String find(String node) {
        if (!parent.get(node).equals(node)) {
            parent.put(node, find(parent.get(node)));
        }
        return parent.get(node);
    }

    void union(String a, String b) {
        String rootA = find(a);
        String rootB = find(b);

        if (!rootA.equals(rootB)) {
            parent.put(rootA, rootB);
        }
    }
}

public class HealthcareMST {

    public static void main(String[] args) {

        System.out.println("=== SMART HEALTHCARE CONNECTIVITY ===\n");

        String[] hospitals = {"H1", "H2", "H3", "H4", "H5"};

        System.out.println("Hospitals:");
        for (String h : hospitals) {
            System.out.print(h + " ");
        }

        System.out.println("\n");

        List<Edge> edges = new ArrayList<>();

        edges.add(new Edge("H1", "H2", 4));
        edges.add(new Edge("H1", "H3", 6));
        edges.add(new Edge("H2", "H3", 2));
        edges.add(new Edge("H2", "H4", 7));
        edges.add(new Edge("H3", "H4", 3));
        edges.add(new Edge("H4", "H5", 5));

        Collections.sort(edges);

        DisjointSet ds = new DisjointSet();

        for (String hospital : hospitals) {
            ds.makeSet(hospital);
        }

        int totalCost = 0;

        System.out.println("MST Connections:");

        for (Edge edge : edges) {

            String root1 = ds.find(edge.src);
            String root2 = ds.find(edge.dest);

            if (!root1.equals(root2)) {

                ds.union(edge.src, edge.dest);

                System.out.println(
                    edge.src + " - " +
                    edge.dest + " : " +
                    edge.weight
                );

                totalCost += edge.weight;
            }
        }

        System.out.println("\nTotal Communication Cost = " + totalCost);

        System.out.println("\nMST Generated Successfully");

        System.out.println("\nPerformance Summary:");
        System.out.println("Kruskal : O(E log E)");
        System.out.println("Prim    : O(E log V)");

        System.out.println("\nProgram Executed Successfully.");
    }
}