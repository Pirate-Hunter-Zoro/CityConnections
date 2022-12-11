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
 * Package: GraphCreation.GraphCreation.ConnectivityListGraph
 * Class: GraphCreation.ConnectivityListGraph
 *
 * Description: GraphCreation.Graph implementation where the graph is stored as a list of Nodes, each containing pointers to the IDs of the nodes which are directly connected with said node
 *
 * *****************************************/

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

public class ConnectivityListGraph extends Graph {

    /** Underlying list of linked nodes */
    public final Node[] connections;

    /** List of all the edges */
    public final ArrayList<Edge> edges;

    /**
     * Constructor the graph which initializes its underlying array
     * @param fileName {@link String}
     */
    public ConnectivityListGraph(String fileName){
        File file = new File(fileName);
        FileReader.getNodeCount(file);
        this.connections = FileReader.createConnectivityListGraph(file);
        this.edges = this.getEdges();
        this.minimumSpanningTree = new Edge[FileReader.getNumNodes()-1]; // number of nodes minus 1
    }

    /**
     * Overridden method to return this {@link Graph}'s set of {@link Edge}s
     * @return {@link TreeSet< Edge >}
     */
    @Override
    public ArrayList<Edge> getEdges() {
        ArrayList<Edge> edges = new ArrayList<>();
        HashSet<Edge> edgesHashSet = new HashSet<>();

        // look at every vertex except the last one because all of its connections will be repetitive
        for (int i=0; i<this.connections.length-1; i++){
            Node vertex = this.connections[i];
            // look at everything connected to this vertex
            for (NodeLengthPair connection : vertex.getConnected()){
                // create an edge out of the connection and add it to our set of edges
                Edge newEdge = new Edge(Math.min(vertex.getID(), connection.getConnectedID()), Math.max(vertex.getID(), connection.getConnectedID()), connection.getEdgeLength());
                edgesHashSet.add(newEdge);
            }
        }

        // now put into an array and sort
        for (Edge edge : edgesHashSet){
            edges.add(edge);
        }
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
        for (NodeLengthPair connection : this.connections[0].getConnected()){
            potentialNextEdges.add(new Edge(0, connection.getConnectedID(), connection.getEdgeLength()));
        }
        visited.add(0);

        // just keep adding edges from our vertices until we have added enough to create our spanning tree
        while (numEdgesAdded[0] < this.connections.length-1){
            // find the next edge to add
            Edge newEdge = Graph.findNextEdge(this, potentialNextEdges, visited, numEdgesAdded);
            this.minimumSpanningTreeWeight += newEdge.getEdgeLength();

            // given the new edge, add all the newly reachable edges
            for (NodeLengthPair connection : this.connections[newEdge.getId2()].getConnected()) {
                int firstID = newEdge.getId2();
                int secondID = connection.getConnectedID();
                double length = connection.getEdgeLength();
                if (!visited.contains(secondID))
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