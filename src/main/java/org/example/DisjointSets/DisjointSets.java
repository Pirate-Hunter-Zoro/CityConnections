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
 * Class: DisjointEdgeSets
 *
 * Description: Implementation of Disjoint Sets for the purposes of Kruskal's Algorithm
 *
 * *****************************************/

import java.util.HashMap;

public class DisjointSets {

    /** Global collection of {@link Element}s */
    private static HashMap<Integer, Element> elements = new HashMap<>();

    /** Clear out the collection of {@link Element}s */
    public static void reset(){
        elements = new HashMap<>();
    }

    /**
     * Method to determine if an element is inserted or not
     */
    public static boolean isPresent(int i){
        return elements.containsKey(i);
    }

    /**
     * Method to create a new set, which means we have one {@link Element} with no parent pointer
     * @param i
     */
    public static void makeSet(int i){
        // only allow this if the element is not already present in the set of all elements
        if (!isPresent(i)){
            elements.put(i, new Element(i));
        }
    }

    /**
     * Method to conjoin two sets by giving all elements the same representative pointer
     * @param i
     * @param j
     */
    public static void union(int i, int j){
        if (elements.containsKey(i) && elements.containsKey(j)){
            Element firstElement = elements.get(i);
            Element rep1 = firstElement.findSet();

            Element secondElement = elements.get(j);
            Element rep2 = secondElement.findSet();

            // we can compare representatives by their 'equals()' method or by memory
            if (rep1 != rep2){
                // now combine the two trees, using rank as a heuristic
                if (rep1.getRank() == rep2.getRank()){
                    rep2.setParent(rep1);
                    rep1.increaseRank();
                // greater rank becomes the parent, and no rank is changed
                } else if (rep1.getRank() > rep2.getRank()){
                    rep2.setParent(rep1);
                } else{
                    rep1.setParent(rep2);
                }
            }
        }
    }

    /**
     * Method to determine if two {@link Element}s are in the same set
     * @param i
     * @param j
     * @return boolean
     */
    public static boolean sameSet(int i, int j){
        // in the entire collection of Elements?
        if (!(elements.containsKey(i) && elements.containsKey(j)))
            return false;

        // get the elements
        Element first = elements.get(i);
        Element second = elements.get(j);

        // compare their representative elements
        return first.findSet() == second.findSet();
    }

}