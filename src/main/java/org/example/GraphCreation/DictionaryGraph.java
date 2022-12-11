package org.example.GraphCreation;/* *****************************************
 * CSCI205 -Software Engineering and Design
 * Spring2022
 * Instructor: Prof. Brian King*
 * Name: Nishant Shrestha
 * Section: 10 am
 * Date: 22/11/2022
 * Time: 15:45
 *
 * Project: csci311-final
 * Package: GraphCreation.GraphCreation.DictionaryGraph
 * Class: GraphCreation.DictionaryGraph
 *
 * Description:
 *
 * ****************************************
 */

import java.io.File;
import java.util.*;

public class DictionaryGraph extends Graph {

    /** How this {@link Graph} stores its connections */
    public HashMap<Integer, HashMap<Integer, Double>> connections;

    public final ArrayList<Edge> edges;

    /**
     * Constructor
     * @param fileName
     */
    public DictionaryGraph(String fileName) {
        File file = new File(fileName);
        FileReader.getNodeCount(file);
        this.connections = FileReader.createDictionaryGraph(file);
        this.minimumSpanningTree = new Edge[FileReader.getNumNodes()-1]; // number of nodes minus 1
        this.edges = getEdges();
    }

    /**
     * Method to obtain an {@link ArrayList< Edge >} representing this {@link Graph}
     * @return {@link ArrayList< Edge >}
     */
    @Override
    public ArrayList<Edge> getEdges() {
        // to store all of our edges
        ArrayList<Edge> edges = new ArrayList<>();

        // at first, throw edges into here to avoid repeating edges
        HashSet<Edge> edgeHashSet = new HashSet<>();

        // go through every key and its respective HashMap
        for (int p : this.connections.keySet()) {
            for (int k : this.connections.get(p).keySet()) {
                Edge node = new Edge(p, k, this.connections.get(p).get(k));
                edgeHashSet.add(node);
            }
        }
        for ( Edge nodes : edgeHashSet){
            edges.add(nodes);
        }
        edges.sort(Edge.EDGE_COMPARATOR);
        return edges;
    }

    /**
     * Perform Prim's Algorithm to create a minimum spanning tree out of this {@link DictionaryGraph}
     */
    @Override
    public void performPrimMST() {
        HashSet<Integer> visited = new HashSet<>();
        int[] numEdgesAdded = {0};

        TreeSet<Edge> potentialNextEdges = new TreeSet<>(Edge.EDGE_COMPARATOR);

        // add initial set of edges to add, as well as the first node which has now been visited
        for (Integer connectionID :this.connections.get(0).keySet()){
            potentialNextEdges.add(new Edge(0, connectionID, this.connections.get(0).get(connectionID)));
        }
        visited.add(0);

        while (numEdgesAdded[0] < this.connections.size()-1) {

            // pick the lowest possible edge, and then continue
            Edge newEdge = Graph.findNextEdge(this, potentialNextEdges, visited, numEdgesAdded);
            this.minimumSpanningTreeWeight += newEdge.getEdgeLength();

            // once the new edge is found, a new node offers new edges to potentially explore
            for (Integer connectionID : this.connections.get(newEdge.getId2()).keySet()) {
                Integer firstID = newEdge.getId2();
                Integer secondID = connectionID;
                Double length = this.connections.get(newEdge.getId2()).get(connectionID);

                if (!visited.contains(secondID)) {
                    potentialNextEdges.add(new Edge(firstID, secondID, length));
                }

            }

            // remove any edges which could create a cycle
            Graph.removeCycleInducingEdges(potentialNextEdges, visited);

        }



    }

    /**
     * Simple implementation for Kruskal's Algorithm to produce a minimum spanning tree
     */
    @Override
    public void performKruskalMST() {
        Graph.kruskalMST(this, this.minimumSpanningTree, this.edges, this.minimumSpanningTree.length + 1);

    }
}
