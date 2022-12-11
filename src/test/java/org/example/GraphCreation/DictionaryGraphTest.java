package org.example.GraphCreation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



class DictionaryGraphTest {
    public DictionaryGraph testGraph;

    /** Make sure its underlying array of Nodes is what's expected */
    private static final String TEST_FILE_NAME = "testGraph.txt";
    @BeforeEach
    void setUp() {
         testGraph = new DictionaryGraph(TEST_FILE_NAME);
    }


    @Test
    void getEdges() {



        int count = 0;
        for( Edge p : testGraph.getEdges()){
            count++;
            assertEquals(count , (int)p.getEdgeLength());
        }




    }

    @Test
    void performPrimMST() {



    }

    @Test
    void performKruskalMST() {
        testGraph.performKruskalMST();



    }
}