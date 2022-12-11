package org.example.DisjointSets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DisjointSetsTest {

    /**
     * Pre-test setup
     */
    @BeforeEach
    void setup() {
        for (int i=1; i<=20; i++){
            DisjointSets.makeSet(i);
        }
    }

    /**
     * A simple test to ensure that resetting works as expected
     */
    @Test
    void reset() {
        DisjointSets.reset();
        assertFalse(DisjointSets.sameSet(1,1));
    }

    /**
     * A simple test to ensure that sets are made properly
     */
    @Test
    void makeSet() {
        for (int i=1; i<=20; i++){
            assertTrue(DisjointSets.sameSet(i, i));
        }
        DisjointSets.reset();
    }

    /**
     * A simple test to ensure taking unions of sets is working properly
     */
    @Test
    void union() {
        assertFalse(DisjointSets.sameSet(1, 3));
        DisjointSets.union(1, 2);
        DisjointSets.union(2, 3);
        assertTrue(DisjointSets.sameSet(1, 3));

        assertFalse(DisjointSets.sameSet(3, 10));

        assertFalse(DisjointSets.sameSet(5, 10));
        DisjointSets.union(3, 4);
        DisjointSets.union(4, 5);
        DisjointSets.union(5, 6);
        DisjointSets.union(6, 7);
        DisjointSets.union(7, 8);
        DisjointSets.union(8, 9);
        DisjointSets.union(9, 10);
        assertTrue(DisjointSets.sameSet(5, 10));

        assertTrue(DisjointSets.sameSet(3, 10));

        assertFalse(DisjointSets.sameSet(3, 20));
        DisjointSets.union(10, 11);
        DisjointSets.union(12, 11);
        DisjointSets.union(14, 13);
        DisjointSets.union(12, 13);
        DisjointSets.union(17, 19);
        DisjointSets.union(18, 14);
        DisjointSets.union(20, 18);
        DisjointSets.union(17, 18);
        assertTrue(DisjointSets.sameSet(10, 20));
        assertTrue(DisjointSets.sameSet(3, 20));
        assertTrue(DisjointSets.sameSet(1, 20));

    }

    /**
     * A simple test to ensure that 'isPresent()' behaves as expected
     */
    @Test
    void isPresent() {
        assertFalse(DisjointSets.isPresent(0));
        DisjointSets.makeSet(0);
        assertTrue(DisjointSets.isPresent(0));
    }

}