package data;

import utils.Dijkstras;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Class representing directed weighted graphs, which can be read from
 * text files.
 * Each node is represented as a String.
 */
public class Graph {

    private Map<String, LinkedList<Neighbour>> adjMap;          // Adjacency list of nodes in graph
    private Map<String, Map<String, Path>> shortestPathMap;     // Computed shortest paths in map

    /**
     * Read in a graph from the text file given by filename.
     * data.Graph text files should follow the structure:
     *      First line: number of nodes (n)
     *      Next n lines: All node values, 1 per line
     *      Remaining lines: All edges in graph, 1 per line, where an edge is:
     *              Source_Node Dest_Node weight
     */
    public Graph(String filename) {
        adjMap = new HashMap<String, LinkedList<Neighbour>>();
        shortestPathMap = new HashMap<String, Map<String,Path>>();

        try {
            Scanner scan = new Scanner(new File(filename));

            // Read in size of graph (number of vertices)
            int graphSize = scan.nextInt();

            // Read in all vertices
            adjMap = new HashMap<String, LinkedList<Neighbour>>(graphSize);
            for (int i = 0; i < graphSize; i++) {
                adjMap.put(scan.next(), new LinkedList<Neighbour>());
            }

            // Read in all pairs of vertices
            while (scan.hasNext()) {
                addNeighbour(scan.next(), scan.next(), scan.nextInt());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Map<String, LinkedList<Neighbour>> getAdjMap() { return adjMap; }

    /**
     * Add a neighbour entry for the given source node
     * @param source Source node to add neighbour entry to
     * @param dest Destination node of the edge with the given source node
     * @param weight Weight between the two nodes
     */
    public void addNeighbour(String source, String dest, int weight) {
        if (adjMap.get(source) == null) {
            throw new RuntimeException("Source node not declared");
        }

        Neighbour newNeighbour = new Neighbour(dest, weight);
        if (!adjMap.get(source).contains(newNeighbour)) {
            adjMap.get(source).addFirst(newNeighbour);
        }
    }

    /**
     * Return the shortest distance between the source and destination nodes
     * @param source Source node
     * @param dest Destination node
     * @return The distance between the nodes, or Integer.MAX_VALUE if impossible
     */
    public int getShortestDistance(String source, String dest) {
        return determineShortestPath(source, dest).getDistance();
    }

    /**
     * Return the shortest path between the source and destination nodes
     * @param source Source node
     * @param dest Destination node
     * @return The path from source to dest that results in the shortest distance/weight,
     *         null if none exists
     */
    public List<String> getShortestPath(String source, String dest) {
        Path shortest = determineShortestPath(source, dest);
        return shortest == null ? null : shortest.getPath();
    }



    /**
     * Determine and return the Path object representing the shortest path
     * between the source and destination nodes, using Dijkstra's algorithm
     * @param source Source node
     * @param dest Destination node
     * @return The Path object representing the shortest path if exists, null otherwise
     */
    public Path determineShortestPath(String source, String dest) {
        if (adjMap.get(source) == null || adjMap.get(dest) == null) {
            return null;
        }

        // If shortest path is not yet computed, compute it
        if (shortestPathMap.get(source) == null) {
            shortestPathMap.put(source, Dijkstras.execute(this, source));
        }

        return shortestPathMap.get(source).get(dest);
    }


}
