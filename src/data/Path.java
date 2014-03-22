package data;

import java.util.LinkedList;

/**
 * Class representing a path in a Graph, where each path
 * contains the nodes in that path (in order of traversal)
 * as well as the total distance from the source to the destination
 */
public class Path {
    private LinkedList<String> path;
    private int distance;

    public Path() {
        this.path = new LinkedList<String>();
    }

    public Path(LinkedList<String> path, int distance) {
        this.path = path;
        this.distance = distance;
    }

    public void addNode(String node) {
        path.add(node);
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public LinkedList<String> getPath() { return path; }

    public int getDistance() { return distance; }
}
