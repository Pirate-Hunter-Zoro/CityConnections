package org.example.GraphCreation;/* *****************************************
 * CSCI311 - Design and Analysis of Algorithms
 * Fall2022
 * Instructor: Prof. Edward Talmage
 *
 * Group: 3
 * Date: 11/13/22
 * Time: 5:56 AM
 *
 * Project: CityConnections
 * Package: GraphCreation
 * Class: GraphCreation.Node
 *
 * Description: A class which will essentially stores an integer, and a reference to an array of integers representing all of the nodes this node is directly connected with
 *
 * *****************************************/

import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;

public class Node {

    /** ID associated with this node in a graph */
    private int ID = -1;

    /** HashSet of node IDs (integers) which correspond to the nodes this {@link Node} is directly connected with */
    private final HashSet<NodeLengthPair> connected;

    /** Class comparator */
    public static final Comparator<Node> NODE_COMPARATOR = (n1, n2) -> {
                                                                   return n1.ID - n2.ID;
                                                                };

    /**
     * Simple constructor for a GraphCreation.Node, initializing its ID and the IDs of the nodes which it is connected to
     * @param ID
     * @param connected
     */
    public Node(int ID, HashSet<NodeLengthPair> connected){
        this.ID = ID;
        this.connected = connected;
    }

    /**
     * Simple getter for the ID
     * @return int
     */
    public int getID() {
        return ID;
    }

    /**
     * Simple getter for the number of connected nodes
     * @return int
     */
    public int getNumConnected() {
        return connected.size();
    }

    /**
     * Simple getter for the (final) set of connections
     * @return {@link HashSet<NodeLengthPair>}
     */
    public HashSet<NodeLengthPair> getConnected() {
        return connected;
    }

    /**
     * Simple boolean to check if two {@link Node}s are equal
     * @param obj
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        else if (!(obj instanceof Node))
            return false;
        // compare the two nodes
        Node other = (Node) obj;
        return this.ID == other.ID;
    }

    /**
     * Method to generate a hash code for a {@link Node} instance
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(this.ID);
    }

}