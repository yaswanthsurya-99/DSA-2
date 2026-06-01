import java.util.*;

// Edge class for graph representation
class Edge {
    int src, dest, weight;

    Edge(int s, int d, int w) {
        src = s;
        dest = d;
        weight = w;
    }
}

// Graph class
class Graph {
    int vertices;
    List<List<Edge>> adjList;
    List<Edge> allEdges;

    Graph(int v) {
        vertices = v;
        adjList = new ArrayList<>();
        allEdges = new ArrayList<>();

        for (int i = 0; i < v; i++) {
            adjList.add(new ArrayList<>());
        }
    }

    // Add undirected weighted edge
    void addEdge(int src, int dest, int weight) {
        Edge edge = new Edge(src, dest, weight);

        adjList.get(src).add(new Edge(src, dest, weight));
        adjList.get(dest).add(new Edge(dest, src, weight));

        allEdges.add(edge);
    }

    // BFS Traversal
    void bfs(int start) {
        boolean[] visited = new boolean[vertices];
        Queue<Integer> queue = new LinkedList<>();

        visited[start] = true;
        queue.add(start);

        System.out.println("BFS Traversal:");

        while (!queue.isEmpty()) {
            int node = queue.poll();
            System.out.print(node + " ");

            for (Edge edge : adjList.get(node)) {
                int neighbor = edge.dest;

                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }
        System.out.println();
    }

    // DFS Traversal
    void dfs(int start) {
        boolean[] visited = new boolean[vertices];

        System.out.println("DFS Traversal:");
        dfsUtil(start, visited);
        System.out.println();
    }

    void dfsUtil(int node, boolean[] visited) {
        visited[node] = true;
        System.out.print(node + " ");

        for (Edge edge : adjList.get(node)) {
            int neighbor = edge.dest;

            if (!visited[neighbor]) {
                dfsUtil(neighbor, visited);
            }
        }
    }

    // Kruskal's Algorithm
    void kruskalMST() {
        Collections.sort(allEdges, Comparator.comparingInt(e -> e.weight));

        int[] parent = new int[vertices];

        for (int i = 0; i < vertices; i++) {
            parent[i] = i;
        }

        int totalCost = 0;

        System.out.println("\nKruskal MST:");

        for (Edge edge : allEdges) {
            int root1 = find(parent, edge.src);
            int root2 = find(parent, edge.dest);

            if (root1 != root2) {
                System.out.println(
                        edge.src + " - " + edge.dest + " : " + edge.weight);

                totalCost += edge.weight;
                union(parent, root1, root2);
            }
        }

        System.out.println("Total Minimum Cost: " + totalCost);
    }

    int find(int[] parent, int node) {
        if (parent[node] != node) {
            parent[node] = find(parent, parent[node]);
        }
        return parent[node];
    }

    void union(int[] parent, int x, int y) {
        parent[x] = y;
    }

    // Prim's Algorithm
    void primMST() {
        boolean[] visited = new boolean[vertices];

        PriorityQueue<Edge> pq =
                new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));

        visited[0] = true;

        for (Edge edge : adjList.get(0)) {
            pq.add(edge);
        }

        int totalCost = 0;

        System.out.println("\nPrim MST:");

        while (!pq.isEmpty()) {
            Edge edge = pq.poll();

            if (visited[edge.dest]) {
                continue;
            }

            visited[edge.dest] = true;

            System.out.println(
                    edge.src + " - " + edge.dest + " : " + edge.weight);

            totalCost += edge.weight;

            for (Edge next : adjList.get(edge.dest)) {
                if (!visited[next.dest]) {
                    pq.add(next);
                }
            }
        }

        System.out.println("Total Minimum Cost: " + totalCost);
    }
}

// Main class
public class SmartGridSystem {
    public static void main(String[] args) {

        Graph graph = new Graph(6);

        // Adding infrastructure connections
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 3);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 2);
        graph.addEdge(2, 3, 4);
        graph.addEdge(3, 4, 2);
        graph.addEdge(4, 5, 6);

        // BFS Traversal
        graph.bfs(0);

        // DFS Traversal
        graph.dfs(0);

        // Kruskal MST
        graph.kruskalMST();

        // Prim MST
        graph.primMST();
    }
}