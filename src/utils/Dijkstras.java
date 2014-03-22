package utils;

import java.util.*;
import data.Graph;
import data.Neighbour;
import data.Path;

/**
 * Utility class providing the ability to run Dijsktra's
 * algorithm on a given Graph and a source node in that
 * graph.
 */
public class Dijkstras {

    // Collections used in Dijkstra's algorithm
    static Set<String> fringe;
    static Map<String, Integer> distances;
    static Map<String, String> predecessors;

    /**
     * Execute Dijkstra's algorithm on the graph, determining and storing
     * all shortest paths starting at the source node given
     * @param source Source node of path
     */
    public static Map<String, Path> execute(Graph graph, String source) {
        fringe = new HashSet<String>();
        distances = new HashMap<String, Integer>();
        predecessors = new HashMap<String, String>();

        Map<String, Path> destPaths = new HashMap<String, Path>();

        // Determine all shortest paths starting at source
        distances.put(source, 0);
        fringe.add(source);
        while (fringe.size() > 0) {
            String node = getShortest(fringe);
            destPaths.put(node, getPath(node));
            fringe.remove(node);
            findShortestDistances(graph, node);
        }

        return destPaths;
    }

    /**
     * Given a set of nodes, return the node with the shortest
     * distance from the current source node, as computed so far
     * @param nodes Nodes to examine
     * @return The node with the shortest distance
     */
    private static String getShortest(Set<String> nodes) {
        String closest = null;
        for (String node : nodes) {
            if (closest == null) {
                closest = node;
            } else {
                if (getDistance(node) < getDistance(closest)) {
                    closest = node;
                }
            }
        }
        return closest;
    }

    /**
     * Return the currently computed distance from the current
     * source node to the given destination node
     * @param node Destination node
     * @return Currently computed distance, or Integer.MAX_VALUE if not
     *         yet determined
     */
    private static int getDistance(String node) {
        Integer distance = distances.get(node);

        return distance == null ? Integer.MAX_VALUE : distance;
    }

    /**
     * Update and store (if needed) the shortest distances to all nodes that are
     * neighbours of the given source node
     * @param source Source node
     */
    private static void findShortestDistances(Graph graph, String source) {
        for (Neighbour next : graph.getAdjMap().get(source)) {
            String dest = next.getDest();
            if (getDistance(source) + next.getWeight() < getDistance(dest)) {
                distances.put(dest, getDistance(source) + next.getWeight());
                predecessors.put(dest, source);
                fringe.add(dest);
            }
        }
    }

    /**
     * Return the Path representing the shortest path between the
     * current source node and the given node
     * @param node
     * @return
     */
    private static Path getPath(String node) {
        LinkedList<String> path = new LinkedList<String>();
        String current = node;
        path.addFirst(node);
        while (predecessors.get(current) != null) {
            current = predecessors.get(current);
            path.addFirst(current);
        }

        return new Path(path, getDistance(node));
    }
}
