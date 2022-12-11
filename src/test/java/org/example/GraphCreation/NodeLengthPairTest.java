package org.example.GraphCreation;

import GraphCreation.NodeLengthPair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeLengthPairTest {

    /**
     * To be initialized for testing
     */
    private static NodeLengthPair nodeLengthPair1;
    private static NodeLengthPair nodeLengthPair2;
    private static NodeLengthPair nodeLengthPair3;

    @BeforeEach
    void setup() {
        nodeLengthPair1 = new NodeLengthPair(3, 1.0);
        nodeLengthPair2 = new NodeLengthPair(3, 1.1);
        nodeLengthPair3 = new NodeLengthPair(2, 1.0);
    }

    /**
     * A simple test to ensure that the {@link NodeLengthPair} objects are equal when and only when we expect them to be
     */
    @Test
    void testEquals() {
        assertEquals(new NodeLengthPair(3, 1.0), nodeLengthPair1);
        assertEquals(new NodeLengthPair(3, 1.1), nodeLengthPair2);
        assertEquals(new NodeLengthPair(2, 1.0), nodeLengthPair3);

        assertNotEquals(nodeLengthPair1, nodeLengthPair2);
        assertNotEquals(nodeLengthPair1, nodeLengthPair3);
        assertNotEquals(nodeLengthPair2, nodeLengthPair3);
    }

    /**
     * A test to ensure that hash codes are equal when and only when we expect them to be
     */
    @Test
    void testHashCode() {
        assertEquals((new NodeLengthPair(3, 1.0)).hashCode(), nodeLengthPair1.hashCode());
        assertEquals((new NodeLengthPair(3, 1.1)).hashCode(), nodeLengthPair2.hashCode());
        assertEquals((new NodeLengthPair(2, 1.0)).hashCode(), nodeLengthPair3.hashCode());

        // here, we only care about the ID
        assertEquals(nodeLengthPair2.hashCode(), nodeLengthPair1.hashCode());
        assertNotEquals(nodeLengthPair3.hashCode(), nodeLengthPair1.hashCode());
        assertNotEquals(nodeLengthPair2.hashCode(), nodeLengthPair3.hashCode());
    }

    /**
     * A test to ensure that {@link NodeLengthPair}s are compared as expected in terms of being greater or less
     */
    @Test
    void testCompare() {
        assertTrue(NodeLengthPair.NODE_LENGTH_PAIR_COMPARATOR.compare(nodeLengthPair1, nodeLengthPair2) < 0);
        assertTrue(NodeLengthPair.NODE_LENGTH_PAIR_COMPARATOR.compare(nodeLengthPair2, nodeLengthPair3) > 0);
        assertTrue(NodeLengthPair.NODE_LENGTH_PAIR_COMPARATOR.compare(nodeLengthPair1, nodeLengthPair3) == 0);
    }
}