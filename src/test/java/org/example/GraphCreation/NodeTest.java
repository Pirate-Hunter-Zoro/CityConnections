package org.example.GraphCreation;

import GraphCreation.Node;
import GraphCreation.NodeLengthPair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

    /**
     * To be initialized for testing
     */
    private static Node node0;
    private static HashSet<NodeLengthPair> connections0;
    private static Node node1;
    private static HashSet<NodeLengthPair> connections1;

    /**
     * Pre-test setup
     */
    @BeforeEach
    void setUp() {
        connections0 = new HashSet<>();
        connections0.add(new NodeLengthPair(1, 3.0));
        connections0.add(new NodeLengthPair(2, 1.0));
        node0 = new Node(0, connections0);

        connections1 = new HashSet<>();
        connections1.add(new NodeLengthPair(0, 3.0));
        connections1.add(new NodeLengthPair(2, 1.5));
        node1 = new Node(1, connections1);
    }

    /**
     * A test to ensure that {@link Node}s are equal exactly when we expect them to be
     */
    @Test
    void testEquals() {
        assertEquals(node0, new Node(0, new HashSet<>()));
        assertEquals(node1, new Node(1, new HashSet<>()));

        assertNotEquals(node0, node1);
        assertNotEquals(node1, new Node(3, node1.getConnected()));
        assertNotEquals(node0, new Node(1, node0.getConnected()));
    }

    /**
     * A test to ensure that {@link Node}s share hash codes exactly when we expect them to
     */
    @Test
    void testHashCode() {
        assertEquals(node0.hashCode(), new Node(0, new HashSet<>()).hashCode());
        assertEquals(node1.hashCode(), new Node(1, new HashSet<>()).hashCode());

        assertNotEquals(node0.hashCode(), node1.hashCode());
        assertNotEquals(node1.hashCode(), new Node(3, node1.getConnected()).hashCode());
        assertNotEquals(node0.hashCode(), new Node(1, node0.getConnected()).hashCode());
    }

    /**
     * A test to ensure that {@link Node}s are compared as expected in terms of being greater or less
     */
    @Test
    void testCompare() {
        assertTrue(Node.NODE_COMPARATOR.compare(node0, node1) < 0);
        assertTrue(Node.NODE_COMPARATOR.compare(node0, new Node(0, node1.getConnected())) == 0);
        assertTrue(Node.NODE_COMPARATOR.compare(node1, new Node(0, node1.getConnected())) > 0);
    }
}