package org.example.Application;/* *****************************************
 * CSCI311 - Design and Analysis of Algorithms
 * Fall2022
 * Instructor: Prof. Edward Talmage
 *
 * Group: 03
 * Date: 11/29/22
 * Time: 1:54 PM
 *
 * Project: CityConnections
 * Package: PACKAGE_NAME
 * Class: Application.Timer
 *
 * Description: Class which has static methods to time the results of various files
 *
 * *****************************************/

import org.example.GraphCreation.*;

public class Timer {

    /** List of files */
    public static final String[] FILES = {"Oldenburg.txt", "SanJoaquinCounty.txt", "California.txt", "NorthAmerica.txt", "SanFrancisco.txt"};

    /**
     * Number of Nodes: 6105
     * Number of Edges: 7029
     * Prim's Algorithm: 84ms
     * Kruskal's Algorithm: 15ms
     *
     * Number of Nodes: 18263
     * Number of Edges: 23797
     * Prim's Algorithm: 87ms
     * Kruskal's Algorithm: 13ms
     *
     * Number of Nodes: 21048
     * Number of Edges: 21693
     * Prim's Algorithm: 88ms
     * Kruskal's Algorithm: 9ms
     *
     * Number of Nodes: 175813
     * Number of Edges: 179102
     * Prim's Algorithm: 1045ms
     * Kruskal's Algorithm: 67ms
     *
     * Number of Nodes: 174956
     * Number of Edges: 221802
     * Prim's Algorithm: 2145ms
     * Kruskal's Algorithm: 42ms
     */

    /**
     * Create a {@link ConnectivityListGraph} for each input file and record the time it takes to perform the algorithms
     */
    public static void timeResults() {
        ConnectivityListGraph graph;
        for (String fileName : FILES){
            graph = new ConnectivityListGraph(fileName);

            // time Prim's algorithm
            long start = System.nanoTime();
            graph.performPrimMST();
            long end = System.nanoTime();
            long primTime = end - start;

            // time Kruskal's algorithm
            start = System.nanoTime();
            graph.performKruskalMST();
            end = System.nanoTime();
            long kruskalTime = end - start;

            // record the results
            System.out.println("Number of Nodes: " + FileReader.getNumNodes());
            System.out.println("Number of Edges: " + graph.edges.size());
            System.out.println("Prim's Algorithm: " + (primTime/1000000) + "ms");
            System.out.println("Kruskal's Algorithm: " + (kruskalTime/1000000) + "ms");
            System.out.println();
        }
    }

}