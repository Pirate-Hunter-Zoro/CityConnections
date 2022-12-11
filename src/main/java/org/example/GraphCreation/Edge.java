package org.example.GraphCreation;/* *****************************************
 * CSCI311 - Design and Analysis of Algorithms
 * Fall2022
 * Instructor: Prof. Edward Talmage
 *
 * Group: 03
 * Date: 11/14/22
 * Time: 7:44 PM
 *
 * Project: CityConnections
 * Package: GraphCreation
 * Class: GraphCreation.Edge
 *
 * Description: Implementation of an GraphCreation.Edge class which represents an edge in some graph
 *
 * *****************************************/

import java.util.Comparator;
import java.util.Objects;

public class Edge {

    /** three components of an {@link Edge} */
    private final int id1;
    private final int id2;
    private final double edgeLength;

    /** static {@link Comparator} for {@link Edge}s */
    public static final Comparator<Edge> EDGE_COMPARATOR = (e1, e2) -> {
                                                                        if (e1.getEdgeLength() < e2.getEdgeLength()){
                                                                            return -1;
                                                                        } else if (e1.getEdgeLength() > e2.getEdgeLength()){
                                                                            return 1;
                                                                        } else {
                                                                            // same distance, so go by ID
                                                                            if (e1.getId1() == e2.getId1())
                                                                                return e1.getId2() - e2.getId2();
                                                                            else
                                                                                return e1.getId1() - e2.getId1();
                                                                            // this returns 0 IF and ONLY IF e1 and e2 are the SAME damn edge
                                                                        }
                                                                    };

    /**
     * Constructor for an {@link Edge}
     * @param id1
     * @param id2
     * @param edgeLength
     */
    public Edge(int id1, int id2, double edgeLength){
        this.id1 = id1;
        this.id2 = id2;
        this.edgeLength = edgeLength;
    }

    /**
     * Getter for the edge length
     * @return double
     */
    public double getEdgeLength() {
        return edgeLength;
    }

    /**
     * Simple getter for the id of the "first" node
     * @return int
     */
    public int getId1() {
        return id1;
    }

    /**
     * Simple getter for the id of the "second" node
     * @return int
     */
    public int getId2() {
        return id2;
    }

    /**
     * Overridden method to determine if two {@link Edge}s are equal
     * @param obj
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Edge)){
            return false;
        }
        // otherwise
        Edge other = (Edge) obj;
        return (
                (this.id1 == other.id1 && this.id2 == other.id2)
                ||
                (this.id1 == other.id2 && this.id2 == other.id1)
        ) && (this.edgeLength == other.edgeLength);
    }

    /**
     * Method to produce a hash code for a given {@link Edge}
     * @return int
     */
    @Override
    public int hashCode() {
        // we care about the two node IDs; not the length of the edge
        return Objects.hash(Math.max(this.id1, this.id2), Math.min(this.id1, this.id2), this.edgeLength);
        // this way, order on the two node IDs does not matter
    }

    /**
     * {@link String} representation overrider for {@link Edge}s
     * @return {@link String}
     */
    @Override
    public String toString() {
        return String.format("%d %d %f\n", this.id1, this.id2, this.edgeLength);
    }
}