package data;

/**
 * Class representing an adjacency list entry for a Graph,
 * containing the destination node and the weight of the path
 * to that node.
 */
public class Neighbour {
    private String dest;
    private int weight = -1;

    public Neighbour(String dest, int weight) {
        this.dest = dest;
        this.weight = weight;
    }

    public String getDest() { return dest; }

    public int getWeight() { return weight; }

    @Override
    public int hashCode() {
        return 31 +
                ((dest == null) ? 0 : dest.hashCode()) +
                weight;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Neighbour other = (Neighbour)obj;
        if (dest == null) {
            if (other.getDest() != null)
                return false;
        } else if (!dest.equals(other.getDest())) {
            return false;
        }
        if (weight != other.getWeight())
            return false;

        return true;
    }
}