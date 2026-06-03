import java.util.*;

public class RoutingSystem  {

    static class Node implements Comparable<Node> {
        String router;
        int cost;

        Node(String router, int cost) {
            this.router = router;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node other) {
            return this.cost - other.cost;
        }
    }

    private Map<String, List<Node>> graph = new HashMap<>();

    // Add connection between routers
    void addEdge(String src, String dest, int cost) {
        graph.putIfAbsent(src, new ArrayList<>());
        graph.putIfAbsent(dest, new ArrayList<>());

        graph.get(src).add(new Node(dest, cost));
        graph.get(dest).add(new Node(src, cost)); // Undirected network
    }

    // Dijkstra Algorithm
    void shortestPath(String source, String destination) {

        Map<String, Integer> distance = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        PriorityQueue<Node> pq = new PriorityQueue<>();

        for (String router : graph.keySet()) {
            distance.put(router, Integer.MAX_VALUE);
        }

        distance.put(source, 0);
        pq.add(new Node(source, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            for (Node neighbor : graph.get(current.router)) {

                int newCost =
                        distance.get(current.router) + neighbor.cost;

                if (newCost < distance.get(neighbor.router)) {
                    distance.put(neighbor.router, newCost);
                    previous.put(neighbor.router, current.router);

                    pq.add(new Node(neighbor.router, newCost));
                }
            }
        }

        // Reconstruct Path
        List<String> path = new ArrayList<>();

        String current = destination;

        while (current != null) {
            path.add(current);
            current = previous.get(current);
        }

        Collections.reverse(path);

        System.out.println("=== COMMUNICATION ROUTING SYSTEM ===\n");

        System.out.println("Source Router      : " + source);
        System.out.println("Destination Router : " + destination);

        System.out.println("\nShortest Path:");

        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i));

            if (i != path.size() - 1) {
                System.out.print(" -> ");
            }
        }

        System.out.println("\n");
        System.out.println("Minimum Cost : "
                + distance.get(destination));

        System.out.println("\nPacket Delivered Successfully");

        System.out.println("\nPerformance Summary:");
        System.out.println("Dijkstra Time : O((V+E) log V)");

        System.out.println("\nProgram Executed Successfully.");
    }

    public static void main(String[] args) {

        RoutingSystem network = new RoutingSystem();

        // Network Topology
        network.addEdge("R1", "R2", 4);
        network.addEdge("R1", "R3", 2);
        network.addEdge("R2", "R4", 5);
        network.addEdge("R3", "R5", 4);
        network.addEdge("R4", "R6", 8);
        network.addEdge("R5", "R6", 6);

        // Find shortest route
        network.shortestPath("R1", "R6");
    }
}