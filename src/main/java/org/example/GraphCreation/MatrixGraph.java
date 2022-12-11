package org.example.GraphCreation;/* *****************************************
 * CSCI 311 - Design and Analysis of Algorithms
 * Fall2022
 * Instructor: Prof. Brian King
 *
 * Group: 03
 * Date: 11/13/22
 * Time: 7:42 AM
 *
 * Project: CityConnections
 * Package: GraphCreation.GraphCreation.MatrixGraph
 * Class: GraphCreation.MatrixGraph
 *
 * Description: GraphCreation.Graph implementation where the graph is stored as a matrix, with values 0 in the case of no connection, or a positive float in the case of a connection
 *
 * *****************************************/

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

public class MatrixGraph extends Graph {

    /** Underlying 2D-Array for this graph - technically this is not a 2D array because we can't afford the consecutive memory, but it does have the instant access and same information as one */
    public final HashMap<Integer, HashMap<Integer, Double>> connections;

    /** List of all the edges */
    public final ArrayList<Edge> edges;

    /**
     * Constructor the graph which initializes its underlying array
     * @param fileName {@link String}
     */
    public MatrixGraph(String fileName){
        File file = new File(fileName);
        FileReader.getNodeCount(file);
        this.connections = FileReader.createMatrixGraph(file);
        this.edges = this.getEdges();
        this.minimumSpanningTree = new Edge[FileReader.getNumNodes()-1]; // number of nodes minus 1
    }

    /**
     * Overridden method to return this {@link Graph}'s set of {@link Edge}s
     * @return {@link TreeSet <GraphCreation.Edge>}
     */
    @Override
    public ArrayList<Edge> getEdges() {
        ArrayList<Edge> edges = new ArrayList<>();
        // traverse the table such that edges will be created in a non-repetitive way
        for (int i=0; i<this.connections.keySet().size()-1; i++){
            for (int j=i+1; j<this.connections.get(i).size(); j++){
                double edgeLength = this.connections.get(i).get(j);
                // is there a connection?
                if (edgeLength > 0) {
                    Edge edge = new Edge(i, j, edgeLength);
                    edges.add(edge);
                }
            }
        }

        // now sort
        edges.sort(Edge.EDGE_COMPARATOR);
        return edges;
    }

    /**
     * Overridden method to perform Prim's Algorithm to write to a file this {@link Graph}'s minimum spanning tree
     */
    @Override
    public void performPrimMST() {
        // first some initialization
        HashSet<Integer> visited = new HashSet<>();
        int[] numEdgesAdded = {0};
        TreeSet<Edge> potentialNextEdges = new TreeSet<>(Edge.EDGE_COMPARATOR);

        // get a starting node and add its edges
        for (int j=1; j<this.connections.get(0).size(); j++){
            double edgeLength = this.connections.get(0).get(j);
            if (edgeLength > 0){
                potentialNextEdges.add(new Edge(0, j, edgeLength));
            }
        }
        visited.add(0);

        // just keep adding edges from our vertices until we have added enough to create our spanning tree
        while (numEdgesAdded[0] < this.connections.keySet().size()-1){
            // find the next edge to add
            Edge newEdge = Graph.findNextEdge(this, potentialNextEdges, visited, numEdgesAdded);
            this.minimumSpanningTreeWeight += newEdge.getEdgeLength();

            // given the new edge, add all the newly reachable edges
            for (int j=0; j<this.connections.get(newEdge.getId2()).size(); j++) {
                int firstID = newEdge.getId2();
                int secondID = j;
                double length = this.connections.get(firstID).get(secondID);
                if (length > 0 && !visited.contains(secondID))
                    potentialNextEdges.add(new Edge(firstID, secondID, length));
            }

            // remove cycle-inducing edges
            Graph.removeCycleInducingEdges(potentialNextEdges, visited);

        }
    }

    /**
     * Overridden method to perform Kruskal's Algorithm to write to a file this {@link Graph}'s minimum spanning tree
     */
    @Override
    public void performKruskalMST() {
        Graph.kruskalMST(this, this.minimumSpanningTree, this.edges, this.minimumSpanningTree.length+1);
    }
}