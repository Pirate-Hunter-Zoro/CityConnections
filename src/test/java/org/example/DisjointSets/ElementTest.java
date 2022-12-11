package org.example.DisjointSets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElementTest {

    /**
     * {@link Element}s to use for tests
     */
    private static Element element0;
    private static Element element1;
    private static Element element2;
    private static Element element3;
    private static Element element4;
    private static Element element5;
    private static Element element6;

    /**
     * Pre-test setup
     */
    @BeforeEach
    void setUp() {
        element0 = new Element(1);
        element1 = new Element(0);
        element2 = new Element(1);
        element3 = new Element(2);
        element4 = new Element(3);
        element5 = new Element(4);
        element6 = new Element(5);
        element2.setParent(element1);
        element3.setParent(element2);
        element4.setParent(element3);
        element5.setParent(element4);
        element6.setParent(element5);
    }

    /**
     * A simple test to ensure that {@link Element}s are equal when and only when expected
     */
    @Test
    void testEquals() {
        assertEquals(element0, element2);
        assertNotEquals(element0, element1);
        assertNotEquals(element1, element2);
    }

    /**
     * A simple test to ensure that {@link Element}s have equal hash codes when and only when expected
     */
    @Test
    void testHashCode() {
        assertEquals(element0.hashCode(), element2.hashCode());
        assertNotEquals(element1.hashCode(), element2.hashCode());
        assertNotEquals(element1.hashCode(), element0.hashCode());
    }

    /**
     * A simple test to ensure that the findSet() method returns the expected {@link Element} and compresses the path
     */
    @Test
    void testFindSet() {
        // find the representative
        assertEquals(element1, element6.findSet());

        // make sure the parents are correct for all nodes now
        assertTrue(element1 == element6.getParent());
        assertTrue(element1 == element5.getParent());
        assertTrue(element1 == element4.getParent());
        assertTrue(element1 == element3.getParent());
        assertTrue(element1 == element2.getParent());
    }
}