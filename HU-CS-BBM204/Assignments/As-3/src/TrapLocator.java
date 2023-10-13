import java.util.*;

public class TrapLocator {
    public List<Colony> colonies;

    public TrapLocator(List<Colony> colonies) {
        this.colonies = colonies;
    }
    private List<Integer> cycle;
    public List<List<Integer>> revealTraps() {

        // Trap positions for each colony, should contain an empty array if the colony is safe.
        // I.e.:
        // 0 -> [2, 15, 16, 31]
        // 1 -> [4, 13, 22]
        // 3 -> []
        // ...
        List<List<Integer>> traps = new ArrayList<>();

        // Identify the time traps and save them into the traps variable and then return it.
        // TODO: Your code here
        for (Colony colony : colonies) {
            List<Integer> cycle = new ArrayList<>();

            cycle = isCyclic(colony);
            traps.add(cycle);
        }
        return traps;
    }
    private List<Integer> isCyclic(Colony colony)
    {
        // Mark all the vertices as not visited and
        // not part of recursion stack
        boolean[] visited = new boolean[Kingdom.adjacencyMatrix.length];
        boolean[] recStack = new boolean[Kingdom.adjacencyMatrix.length];
        List<Integer> a = new ArrayList<>();
        // Call the recursive helper function to
        // detect cycle in different DFS trees
        for (int i = 0; i < Kingdom.adjacencyMatrix.length; i++)
            if (isCyclicUtil(i, visited, recStack,colony)){
                for (int j = 0; j < recStack.length; j++) {
                    if(recStack[j]){
                        a.add(j+1);
                    }
                }
                return a;
            }
        return a;
    }

    private boolean isCyclicUtil(int i, boolean[] visited,
                                 boolean[] recStack,Colony colony)
    {

        // Mark the current node as visited and
        // part of recursion stack
        if (recStack[i])
            return true;

        if (visited[i])
            return false;

        visited[i] = true;

        recStack[i] = true;
        List<Integer> children = colony.roadNetwork.get(i);
        if(children != null){
            for (Integer c: children)
                if (isCyclicUtil(c, visited, recStack,colony))
                    return true;
        }



        recStack[i] = false;

        return false;
    }







    public void printTraps(List<List<Integer>> traps) {
        // For each colony, if you have encountered a time trap, then print the cities that create the trap.
        // If you have not encountered a time trap in this colony, then print "Safe".
        // Print the your findings conforming to the given output format.
        // TODO: Your code here

        System.out.println("Danger exploration conclusions:");

        int colonyNum = 1;
        for (List<Integer> trap : traps) {
            if(trap != null){
                Collections.sort(trap);
                if (trap.isEmpty()) {
                    System.out.printf("Colony %d: Safe%n", colonyNum);
                } else {
                    System.out.printf("Colony %d: Dangerous. Cities on the dangerous path: %s%n", colonyNum, trap.toString());
                }
            }



            colonyNum++;
        }
    }

}
