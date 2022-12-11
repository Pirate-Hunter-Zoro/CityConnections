package org.example.GraphCreation;/* *****************************************
 * CSCI311 - Design and Analysis of Algorithms
 * Fall2022
 * Instructor: Prof. Edward Talmage
 *
 * Group: 03
 * Date: 11/13/22
 * Time: 7:19 AM
 *
 * Project: CityConnections
 * Package: GraphCreation.GraphCreation.ConnectivityListGraph
 * Class: GraphCreation.NodeLengthPair
 *
 * Description: A class which will essentially stores an integer for the node which is connected to some other node (context in GraphCreation.ConnectivityListGraph), plus the length of the connection
 *
 * *****************************************/

import java.util.Comparator;
import java.util.Objects;

public class NodeLengthPair {

    /** Components of an edge connection */
    private int connectedID;
    private double edgeLength;

    /** {@link Comparator} for {@link NodeLengthPair}s */
    public static final Comparator<NodeLengthPair> NODE_LENGTH_PAIR_COMPARATOR = (e1, e2) -> {
                                                                                              if (e1.edgeLength < e2.edgeLength)
                                                                                                  return -1;
                                                                                              else if (e1.edgeLength > e2.edgeLength)
                                                                                                  return 1;
                                                                                              else
                                                                                                  return 0;
                                                                                            };

    /**
     * Simple constructor to initialize the attributes
     * @param connectedID {int}
     * @param edgeLength {double}
     */
    public NodeLengthPair(int connectedID, double edgeLength){
        this.connectedID = connectedID;
        this.edgeLength = edgeLength;
    }

    /**
     * Simple getter for the connected node's ID
     * @return int
     */
    public int getConnectedID() {
        return connectedID;
    }

    /**
     * Simple getter for the distance to the connected node's ID
     * @return double
     */
    public double getEdgeLength() {
        return edgeLength;
    }

    /**
     * Method to determine if two {@link NodeLengthPair} objects are equal
     * NOTE - while this does not consider where the edge came FROM, the {@link Node} class takes care of this
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof NodeLengthPair))
            return false;
        // compare the other GraphCreation.NodeLengthPair with 'this' one
        NodeLengthPair other = (NodeLengthPair) obj;
        return (this.connectedID == other.connectedID && (Double.compare(this.edgeLength, other.edgeLength) == 0));
    }

    /**
     * Method to return a hash code for a given {@link NodeLengthPair} instance
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.connectedID);
    }
}