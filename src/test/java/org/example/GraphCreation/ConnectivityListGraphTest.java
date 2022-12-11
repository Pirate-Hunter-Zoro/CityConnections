package org.example.GraphCreation;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class ConnectivityListGraphTest {

    /** Make sure its underlying array of Nodes is what's expected */
    private static final String TEST_FILE_NAME = "testGraph.txt";

    /** Test nodes */
    private static Node node0;
    private static HashSet<NodeLengthPair> connected0;
    private static Node node1;
    private static HashSet<NodeLengthPair> connected1;
    private static Node node2;
    private static HashSet<NodeLengthPair> connected2;
    private static Node node3;
    private static HashSet<NodeLengthPair> connected3;
    private static Node node4;
    private static HashSet<NodeLengthPair> connected4;
    private static Node node5;
    private static HashSet<NodeLengthPair> connected5;

    /**
     * Test to make sure the underlying array was initialized as expected
     */
    @Test
    void testUnderlyingArray(){
        // initialize the nodes
        connected0 = new HashSet<>();
        connected0.add(new NodeLengthPair(1, 1.0));
        connected0.add(new NodeLengthPair(3, 3.0));
        connected0.add(new NodeLengthPair(5, 9.0));
        connected0.add(new NodeLengthPair(2, 10.0));
        node0 = new Node(0, connected0);

        connected1 = new HashSet<>();
        connected1.add(new NodeLengthPair(0, 1.0));
        connected1.add(new NodeLengthPair(2, 2.0));
        node1 = new Node(1, connected1);

        connected2 = new HashSet<>();
        connected2.add(new NodeLengthPair(1, 2.0));
        connected2.add(new NodeLengthPair(4, 4.0));
        connected2.add(new NodeLengthPair(5, 8.0));
        connected2.add(new NodeLengthPair(0, 10.0));
        node2 = new Node(2, connected2);

        connected3 = new HashSet<>();
        connected3.add(new NodeLengthPair(0, 3.0));
        connected3.add(new NodeLengthPair(4, 5.0));
        connected3.add(new NodeLengthPair(5, 6.0));
        node3 = new Node(3, connected3);

        connected4 = new HashSet<>();
        connected4.add(new NodeLengthPair(2, 4.0));
        connected4.add(new NodeLengthPair(3, 5.0));
        connected4.add(new NodeLengthPair(5, 7.0));
        node4 = new Node(4, connected4);

        connected5 = new HashSet<>();
        connected5.add(new NodeLengthPair(3, 6.0));
        connected5.add(new NodeLengthPair(4, 7.0));
        connected5.add(new NodeLengthPair(2, 8.0));
        connected5.add(new NodeLengthPair(0, 9.0));
        node5 = new Node(5, connected5);

        Node[] expectedArray = {
                node0,
                node1,
                node2,
                node3,
                node4,
                node5
        };
        // check the correct ID's
        ConnectivityListGraph testGraph = new ConnectivityListGraph(TEST_FILE_NAME);
        assertArrayEquals(expectedArray, testGraph.connections);

        for (int i=0; i<expectedArray.length; i++){
            Node first = expectedArray[i];
            Node second = testGraph.connections[i];
            assertTrue(equalHashSets(first.getConnected(), second.getConnected()));
        }
    }

    /**
     * A test to ensure Kruskal's algorithm behaves as expected
     */
    @Test
    void kruskalMST() {
        ConnectivityListGraph testGraph = new ConnectivityListGraph(TEST_FILE_NAME);

        testGraph.performKruskalMST();
        assertTrue(Arrays.equals(testGraph.minimumSpanningTree,
                new Edge[]{
                        new Edge(0,1,1.0),
                        new Edge(1,2,2.0),
                        new Edge(0,3,3.0),
                        new Edge(2,4,4.0),
                        new Edge(3,5,6.0)
                }));
    }

    /**
     * A test to ensure Prim's algorithm behaves as expected
     */
    @Test
    void primMST() {
        ConnectivityListGraph testGraph = new ConnectivityListGraph(TEST_FILE_NAME);

        testGraph.performPrimMST();
        assertTrue(Arrays.equals(testGraph.minimumSpanningTree,
                new Edge[]{
                        new Edge(0,1,1.0),
                        new Edge(1,2,2.0),
                        new Edge(0,3,3.0),
                        new Edge(2,4,4.0),
                        new Edge(3,5,6.0)
                }));
    }

    /**
     * Test if two hash sets are equal, in that they contain the same elements
     * @param first
     * @param second
     * @return boolean
     */
    static boolean equalHashSets(HashSet<NodeLengthPair> first, HashSet<NodeLengthPair> second){
        if (first.size() != second.size())
            return false;
        // equal counts, now make sure all elements are present
        for (NodeLengthPair nodeLengthPair : first){
            if (!(second.contains(nodeLengthPair))){
                return false;
            }
        }
        // make sure all the edge lengths are equal
        for (NodeLengthPair nodeLengthPairFirst : first){
            for (NodeLengthPair nodeLengthPairSecond : second){
                // if the node IDs are the same, edge lengths had better be the same
                if (nodeLengthPairFirst.getConnectedID() == nodeLengthPairSecond.getConnectedID() && !nodeLengthPairFirst.equals(nodeLengthPairSecond)){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Determines if two {@link ArrayList<Edge>} have the same edges in them
     * @param first
     * @param second
     * @return boolean
     */
    static boolean equalArrayLists(ArrayList<Edge> first, ArrayList<Edge> second) {
        if (first.size() != second.size())
            return false;
        // equal counts, now make sure all elements are present

        double[] total1 = new double[1];
        first.stream()
                .forEach(edge -> {
                    total1[0] += edge.getEdgeLength();
                });

        double[] total2 = new double[1];
        first.stream()
                .forEach(edge -> {
                    total2[0] += edge.getEdgeLength();
                });

        return total1[0] == total2[0];
    }

}