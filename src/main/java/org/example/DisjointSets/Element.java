package org.example.DisjointSets;/* *****************************************
 * CSCI311 - Design and Analysis of Algorithms
 * Fall2022
 * Instructor: Prof. Edward Talmage
 *
 * Group: 03
 * Date: 11/14/22
 * Time: 8:38 PM
 *
 * Project: CityConnections
 * Package: GraphCreation.Edges
 * Class: DisjointSets.Element
 *
 * Description: Implementation of a class which will be an integer element in a set
 *
 * *****************************************/

import java.util.Objects;

public class Element {

    /**
     * Fields for every {@link Element}
     */
    private final int value;
    private int rank = 0;
    private Element parent;

    /**
     * Constructor for an {@link Element}
     * @param value
     */
    public Element(int value){
        this.value = value;
        this.parent = null;
    }

    /**
     * Overrider for the 'equals()' method - we only care about the value of the {@link Element}
     * @param obj
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Element))
            return false;
        // otherwise
        Element other = (Element) obj;
        return this.value == other.value;
    }

    /**
     * Overrider for an {@link Element}'s hash code - only a result of the {@link Element}'s value
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    /**
     * Method to set the parent pointer of an {@link Element}
     * @param parent
     */
    public void setParent(Element parent) {
        this.parent = parent;
    }

    /**
     * Simple getter for the parent of an {@link Element}
     * @return
     */
    public Element getParent() {
        return parent;
    }

    /**
     * Path compression algorithm to return the representative pointer of a node
     * @return {@link Element}
     */
    public Element findSet() {
        if (this.parent == null)
            return this;
        else {
            Element rep = this.parent.findSet();
            this.parent = rep;
            return rep;
        }
        // while height is potentially shortened for the rep DisjointSets.Element,  we do not need to worry about changing the rank
    }

    /**
     * Method to return the rank of an {@link Element}, which is only applicable for those elements which are representatives of sets
     * @return
     */
    public int getRank() {
        return rank;
    }

    /**
     * Method which will be necessary when two sets, with representatives of equal length, are unioned
     */
    public void increaseRank() {
        this.rank = this.rank + 1;
    }
}