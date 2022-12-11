package org.example.GraphCreation;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

import org.example.DisjointSets.*;

/**
 * Requires certain graph behavior
 */
public abstract class Graph {

    /** All {@link Graph}s write their minimum spanning trees to a specified file */
    public static final String OUTPUT_PATH_MATRIX_GRAPH = "resultingMatrixMST.txt";
    public static final String OUTPUT_PATH_LIST_GRAPH = "resultingListMST.txt";
    public static final String OUTPUT_DICTIONARY_GRAPH = "resultingDictionaryMST.txt";

    /** All {@link Graph} implementations have an array which stores the edges making up a minimum spanning tree of said graph */
    public Edge[] minimumSpanningTree = new Edge[0];

    /** Stores the total weight of this {@link Graph}'s minimum spanning tree */
    protected double minimumSpanningTreeWeight = 0.0;

    /** All {@link Graph} implementations must define this method */
    public abstract ArrayList<Edge> getEdges();

    /** All {@link Graph} implementations must define this method for a minimum spanning tree
     * Note - there is no shared prim implementation, because we are building out from the nodes, which depends on how the graph is constructed
     * */
    public abstract void performPrimMST();

    /** All {@link Graph} implementations must define this method for a minimum spanning tree */
    public abstract void performKruskalMST();

    /**
     * All Graphs have this algorithmic implementation
     * @param minimumSpanningTree
     * @param edges
     * @param numNodes
     */
    protected static void kruskalMST(Graph graph, Edge[] minimumSpanningTree, ArrayList<Edge> edges, int numNodes) {
        // when picking edges, we need to make sure we do not create a cycle
        DisjointSets.reset();

        // this algorithm works by picking edges of the smallest available length until we have picked |V|-1 edges
        int numEdges = 0;
        for (Edge edge: edges){
            // have we found enough edges?
            if (numEdges == numNodes-1) {
                break;
            }
            // otherwise
            int edgeId1 = edge.getId1();
            int edgeId2 = edge.getId2();
            if (!DisjointSets.sameSet(edgeId1, edgeId2)){
                // then we will NOT create a cycle with this edge

                // add these two nodes to our Disjoint Sets collection - if they are already present then nothing changes
                DisjointSets.makeSet(edgeId1);
                DisjointSets.makeSet(edgeId2);

                // adding the edge means these two nodes are connected
                DisjointSets.union(edgeId1, edgeId2);
                // update this graph's array of MST edges
                minimumSpanningTree[numEdges] = edge;
                numEdges++;
                graph.minimumSpanningTreeWeight += edge.getEdgeLength();
            }
        }

        // refresh the disjoint sets
        DisjointSets.reset();
    }

    /** All {@link Graph} implementations have access to this method which writes edges to a file
     * Source for how to implement file writing: https://www.w3schools.com/java/java_files_create.asp
     * */
    public static void writeUp(Edge[] minimumSpanningTree, String outputPath){

        int numEdgesWritten = 0;
        try (FileWriter writer = new FileWriter(outputPath)){
            for (Edge edge : minimumSpanningTree){
                if (edge != null) {
                    writer.write(numEdgesWritten + " " + edge.toString());
                    numEdgesWritten++;
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Helper method to find the next edge to add in Prim's algorithm, usable by both {@link ConnectivityListGraph} and {@link MatrixGraph}
     * @param potentialNextEdges
     * @param visited
     * @param numEdgesAdded
     */
    protected static Edge findNextEdge(Graph someGraph, TreeSet<Edge> potentialNextEdges, HashSet<Integer> visited, int[] numEdgesAdded){
        Iterator<Edge> edgeIterator = potentialNextEdges.iterator();
        // we know that potentialNextEdges is a TreeSet, so whichever first valid edge shows up is the one we want to add

        // we have edges to potentially add
        Edge newEdge = edgeIterator.next();
        while (visited.contains(newEdge.getId2())) {
            // find another edge - this one creates a cycle
            newEdge = edgeIterator.next();
        }

        // now we have found the newEdge to add
        someGraph.minimumSpanningTree[numEdgesAdded[0]] = newEdge;
        numEdgesAdded[0]++;
        // adding this edge opened up some new edge possibilities
        visited.add(newEdge.getId2());

        // return the GraphCreation.Edge
        return newEdge;
    }

    /**
     * Given a set of visited nodes and a set of edges, remove all the edges which would create a cycle during the construction of a MST
     * @param potentialNextEdges
     * @param visited
     */
    protected static void removeCycleInducingEdges(TreeSet<Edge> potentialNextEdges, HashSet<Integer> visited) {
        // now filter out the edges which will create cycles
        ArrayList<Edge> cycleEdges = new ArrayList<>();
        for (Edge edge : potentialNextEdges) {
            if (visited.contains(edge.getId2())) {
                cycleEdges.add(edge);
            }
        }
        for (Edge edge : cycleEdges) {
            potentialNextEdges.remove(edge);
        }
    }

    /**
     * Simple getter for the weight of this {@link Graph}'s minimum spanning tree
     */
    public double getMinimumSpanningTreeWeight(){
        return this.minimumSpanningTreeWeight;
    }

}
