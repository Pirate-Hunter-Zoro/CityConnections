package org.example.GraphCreation;

import GraphCreation.Edge;
import GraphCreation.MatrixGraph;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class MatrixGraphTest {

    /** Make sure its underlying array of Nodes is what's expected */
    private static final String TEST_FILE_NAME = "testGraph.txt";

    /**
     * Test to make sure the underlying array was initialized as expected
     */
    @Test
    void testUnderlyingArray() {
        // check the correct ID's
        MatrixGraph testGraph = new MatrixGraph(TEST_FILE_NAME);

        HashMap<Integer, double[]> expectedHashMap = new HashMap<>();
        expectedHashMap.put(0, new double[]{0.0, 1.0, 10.0, 3.0, 0.0, 9.0});
        expectedHashMap.put(1, new double[]{1.0, 0.0, 2.0, 0.0, 0.0, 0.0});
        expectedHashMap.put(2, new double[]{10.0, 2.0, 0.0, 0.0, 4.0, 8.0});
        expectedHashMap.put(3, new double[]{3.0, 0.0, 0.0, 0.0, 5.0, 6.0});
        expectedHashMap.put(4, new double[]{0.0, 0.0, 4.0, 5.0, 0.0, 7.0});
        expectedHashMap.put(5, new double[]{9.0, 0.0, 8.0, 6.0, 7.0, 0.0});

//        assertTrue(equalHashMaps(expectedHashMap, testGraph.connections));
    }

    /**
     * A test to ensure Kruskal's algorithm behaves as expected
     */
    @Test
    void kruskalMST() {
        MatrixGraph testGraph = new MatrixGraph(TEST_FILE_NAME);

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
        MatrixGraph testGraph = new MatrixGraph(TEST_FILE_NAME);

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
     * Helper method to compare two {@link HashMap}s
     * @param first
     * @param second
     * @return boolean
     */
    boolean equalHashMaps(HashMap<Integer, double[]> first, HashMap<Integer, double[]> second) {
        if (first.keySet().size() != second.keySet().size()){
            return false;
        }
        for (int key : first.keySet()){
            if (second.get(key) == null || !Arrays.equals(first.get(key), second.get(key))){
                return false;
            }
        }
        return true;
    }

}