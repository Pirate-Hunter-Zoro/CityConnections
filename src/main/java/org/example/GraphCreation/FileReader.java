package org.example.GraphCreation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class FileReader {

    /** Materials for scanning a file to create a graph */
    public static final String EDGE_REGEX = "(\\d+)\\s(\\d+)\\s(\\d+)\\s(\\d+.\\d+)[\\n]?";
    public static final Pattern EDGE_PATTERN = Pattern.compile(EDGE_REGEX);

    /** The number of nodes in the most recent graph */
    private static int numNodes = 0;

    /**
     * Given a text file, use RegEx to scan through said file and return a map corresponding the edge lengths between all pairs of directly connected nodes
     * @param textFile
     * @return {@link Graph}
     */
    public static void getNodeCount(File textFile){
        numNodes = 0;

        // open an input stream from the text file
        try (FileInputStream textInput = new FileInputStream(textFile)){
            Scanner fileReader = new Scanner(textInput);
            // keep looking for edges recorded in the file

            // to spare memory, we have to first count the number of  nodes
            int endNodeID;
            while (fileReader.findWithinHorizon(EDGE_PATTERN, 0) != null) {
                // get the edge information, and record it in the map
                MatchResult edge = fileReader.match();
                endNodeID = Integer.parseInt(edge.group(3));

                // now check, via the endNodeID, if the highest node ID has increased (which will in the end tell us how many nodes there are)
                numNodes = Math.max(endNodeID + 1, numNodes);
            }
        } catch (IOException e){
            // should never happen
            e.printStackTrace();
        }
    }

    /**
     * Helper method to create a {@link ConnectivityListGraph} out of the file
     * @param textFile
     * @return {@link Node[]}
     */
    public static Node[] createConnectivityListGraph(File textFile) {
        // create an array of Nodes
        Node[] adjacencies = new Node[numNodes];

        try (FileInputStream textInput = new FileInputStream(textFile)) {
            Scanner fileReader = new Scanner(textInput);
            // now look at each edge
            int startNodeID;
            int endNodeID;
            double edgeLength;
            MatchResult edge;
            while (fileReader.findWithinHorizon(EDGE_PATTERN, 0) != null) {
                // obtain edge properties
                edge = fileReader.match();
                startNodeID = Integer.parseInt(edge.group(2));
                endNodeID = Integer.parseInt(edge.group(3));
                edgeLength = Double.parseDouble(edge.group(4));

                // add from the POV of the first node
                if (adjacencies[startNodeID] == null){
                    adjacencies[startNodeID] = new Node(startNodeID, new HashSet<>());
                }
                adjacencies[startNodeID].getConnected().add(new NodeLengthPair(endNodeID, edgeLength));

                // add from the POV of the second node
                if (adjacencies[endNodeID] == null){
                    adjacencies[endNodeID] = new Node(endNodeID, new HashSet<>());
                }
                adjacencies[endNodeID].getConnected().add(new NodeLengthPair(startNodeID, edgeLength));
            }
        } catch (IOException e){
            // should never happen
            e.printStackTrace();
        }

        return adjacencies;
    }

    /**
     * Helper method to create a {@link MatrixGraph} out of the file
     * @param textFile
     * @return {@link HashMap<Integer,HashMap<Integer,Double>}
     */
    public static HashMap<Integer,HashMap<Integer,Double>> createMatrixGraph(File textFile) {
        // create a "2D array"
        HashMap<Integer, HashMap<Integer, Double>> adjacencies = new HashMap<>();
        try {
            for (int i = 0; i < numNodes; i++) {
                HashMap<Integer, Double> row = new HashMap<>();
                for (int j = 0; j < numNodes; j++) {
                    row.put(j, 0.0);
                }
                adjacencies.put(i, row);
            }

            try (FileInputStream textInput = new FileInputStream(textFile)) {
                Scanner fileReader = new Scanner(textInput);
                // now look at each edge
                int startNodeID;
                int endNodeID;
                double edgeLength;
                MatchResult edge;
                while (fileReader.findWithinHorizon(EDGE_PATTERN, 0) != null) {
                    // obtain edge properties
                    edge = fileReader.match();
                    startNodeID = Integer.parseInt(edge.group(2));
                    endNodeID = Integer.parseInt(edge.group(3));
                    edgeLength = Double.parseDouble(edge.group(4));
                    adjacencies.get(startNodeID).put(endNodeID, edgeLength);
                    adjacencies.get(endNodeID).put(startNodeID, edgeLength);
                }
            } catch (IOException e) {
                // should never happen
                e.printStackTrace();
            }

            // return the 2D array
            return adjacencies;

        } catch (OutOfMemoryError e){
            System.out.println("There is not enough storage to store every possible edge in a 2D array - run the program again and try a different data structure.");
            adjacencies = new HashMap<>();
            return adjacencies;
        }
    }

    /**
     * Simple getter for the total number of nodes in the most recent graph creation
     * @return int
     */
    public static HashMap<Integer,HashMap<Integer,Double>> createDictionaryGraph(File textFile){
        HashMap<Integer,HashMap<Integer,Double>> connections = new HashMap<>();

        try (FileInputStream textInput = new FileInputStream(textFile)) {
            Scanner fileReader = new Scanner(textInput);
            // now look at each edge
            int startNodeID;
            int endNodeID;
            double edgeLength;
            MatchResult edge;
            while (fileReader.findWithinHorizon(EDGE_PATTERN, 0) != null) {
                // obtain edge properties
                edge = fileReader.match();
                startNodeID = Integer.parseInt(edge.group(2));
                endNodeID = Integer.parseInt(edge.group(3));
                edgeLength = Double.parseDouble(edge.group(4));

                // add from POV of the first node
                connections.computeIfAbsent(startNodeID, k -> new HashMap<>());
                connections.get(startNodeID).put(endNodeID, edgeLength);

                // add from POV of the second node
                connections.computeIfAbsent(endNodeID, k -> new HashMap<>());
                connections.get(endNodeID).put(startNodeID, edgeLength);
            }
        } catch (IOException e){
            // should never happen
            e.printStackTrace();
        }

        return connections;
    }

    /**
     * Simple getter for the current value of numNodes
     * @return int
     */
    public static int getNumNodes() {
        return numNodes;
    }
}