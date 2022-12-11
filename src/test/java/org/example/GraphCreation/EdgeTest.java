package org.example.GraphCreation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EdgeTest {

    /** {@link Edge}s which will be initialized and used for testing */
    private static Edge edge1;
    private static Edge edge2;
    private static Edge edge3;
    private static Edge edge4;
    private static Edge edge5;

    /**
     * Setup of the static {@link Edge}s
     */
    @BeforeEach
    void setup() {
        edge1 = new Edge(0, 1, 1.0);
        edge2 = new Edge(1, 0, 1.0);
        edge3 = new Edge(1, 0, 3.0);
        edge4 = new Edge(3, 4, 2.0);
        edge5 = new Edge(1, 3, 1.0);
    }

    /**
     * Make sure that {@link Edge}s are equal when and only when we expect them to be
     */
    @Test
    void testEquals() {
        assertEquals(edge1, edge2);
        assertNotEquals(edge2, edge5);
        assertNotEquals(edge3, edge2);
        assertNotEquals(edge4, edge5);
    }

    /**
     * Make sure that {@link Edge}s produce the same hash code regardless of edge length (not that this will every come up in one graph) or direction
     */
    @Test
    void testHashCode() {
        assertEquals(edge1.hashCode(), edge2.hashCode());

        assertNotEquals(edge2.hashCode(), edge3.hashCode());
        assertNotEquals(edge2.hashCode(), edge4.hashCode());
        assertNotEquals(edge4.hashCode(), edge5.hashCode());
    }

    /**
     * A simple test to ensure that {@link Edge}s are printed out as expected
     */
    @Test
    void testToString() {
        String expected = "0 1 1.000000\n";
        assertEquals(expected, edge1.toString());
    }
}