import java.io.*;
import java.util.*;

public class Kingdom {

    // TODO: You should add appropriate instance variables.
    public static int[][] adjacencyMatrix;
    private int size;
    private ArrayList<ArrayList<Integer>> adjListArray;

    private ArrayList<ArrayList<Integer>> adjListArrayundirected;
    public void initializeKingdom(String filename) {
        // Read the txt file and fill your instance variables
        // TODO: Your code here
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String line = scanner.nextLine();
        String [] b = line.split(" ");
        size = line.split(" ").length;
        adjacencyMatrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            adjacencyMatrix[0][i] = Integer.parseInt(b[i]);
        }
        for (int i = 1; i < size; i++) {
            for (int j = 0; j < size; j++) {
                adjacencyMatrix[i][j] = scanner.nextInt();
            }
        }
        scanner.close();


        int l = adjacencyMatrix[0].length;
        adjListArray = new ArrayList<ArrayList<Integer>>(l);

        for (int i = 0; i < l; i++) {
            adjListArray.add(new ArrayList<Integer>());
        }

        int i, j;
        for (i = 0; i < adjacencyMatrix[0].length; i++) {
            for (j = 0; j < adjacencyMatrix.length; j++) {
                if (adjacencyMatrix[i][j] != 0) {
                    adjListArray.get(i).add(j);
                }
            }
        }


        adjListArrayundirected = new ArrayList<>(l);
        for (int k = 0; k < l; k++) {
            adjListArrayundirected.add(new ArrayList<>());
        }
        for (int u = 0; u < l; u++) {
            for (int v : adjListArray.get(u)) {
                adjListArrayundirected.get(u).add(v);
                adjListArrayundirected.get(v).add(u);
            }
        }

    }


    public List<Colony> getColonies() {
        List<Colony> colonies = new ArrayList<>();
        // TODO: DON'T READ THE .TXT FILE HERE!
        // Identify the colonies using the given input file.
        // TODO: Your code here

        boolean[] isVisited = new boolean[adjListArrayundirected.size()];

        for (int i = 0; i < adjListArrayundirected.size(); i++) {
            if (!isVisited[i]) {
                Colony colony = new Colony();
                findConnectedComponent(i, isVisited, colony);
                Collections.sort(colony.cities);
                colonies.add(colony);
            }
        }


        return colonies;

    }



    public void printColonies(List<Colony> discoveredColonies) {
        // Print the given list of discovered colonies conforming to the given output format.
        // TODO: Your code here
        System.out.println("Finding Hope in the Darkest of Times");
        System.out.println("####################################");
        System.out.println("Discovered colonies are:");
        for (Colony discoveredColony : discoveredColonies) {
            for (int i = 0; i < discoveredColony.cities.size(); i++) {
                discoveredColony.cities.set(i, discoveredColony.cities.get(i) + 1);
            }
        }
        int colonyNum = 1;
        for (Colony colony : discoveredColonies) {
            List<Integer> cities = colony.cities;
            Collections.sort(cities);
            System.out.printf("Colony %d: %s%n", colonyNum, cities.toString());
            colonyNum++;
        }

    }



    // Finds a connected component
    // starting from source using DFS
    private void
    findConnectedComponent(int src, boolean[] isVisited, Colony colony)
    {
        isVisited[src] = true;
        colony.roadNetwork.put(src,adjListArray.get(src));
        colony.cities.add(src);

        for (int v : adjListArrayundirected.get(src))
            if (!isVisited[v])
                findConnectedComponent(v, isVisited, colony);
    }



}
