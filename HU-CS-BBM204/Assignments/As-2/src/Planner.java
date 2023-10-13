import java.util.ArrayList;
import java.util.Collections;

public class Planner {

    public final Task[] taskArray;
    public final Integer[] compatibility;
    public final Double[] maxWeight;
    public final ArrayList<Task> planDynamic;
    public final ArrayList<Task> planGreedy;

    public Planner(Task[] taskArray) {

        // Should be instantiated with an Task array
        // All the properties of this class should be initialized here

        this.taskArray = taskArray;
        this.compatibility = new Integer[taskArray.length];
        maxWeight = new Double[taskArray.length];

        this.planDynamic = new ArrayList<>();
        this.planGreedy = new ArrayList<>();
    }

    /**
     * @param index of the {@link Task}
     * @return Returns the index of the last compatible {@link Task},
     * returns -1 if there are no compatible {@link Task}s.
     */
    public int binarySearch(int index) {
        int left = 0;
        int right = index - 1;
        int compatibleIndex = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (taskArray[mid].getFinishTime().compareTo(taskArray[index].getStartTime()) <= 0) {
                compatibleIndex = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return compatibleIndex;
    }


    /**
     * {@link #compatibility} must be filled after calling this method
     */
    public void calculateCompatibility() {
        for (int i = 0; i < taskArray.length; i++) {
            int compatibleIndex = binarySearch(i);
            compatibility[i] = compatibleIndex; // Set the compatibility index for task i
        }
    }


    /**
     * Uses {@link #taskArray} property
     * This function is for generating a plan using the dynamic programming approach.
     * @return Returns a list of planned tasks.
     */
    public ArrayList<Task> planDynamic() {
        calculateCompatibility();
        System.out.println("Calculating max array\n" +
                "---------------------");
        // First, we need to calculate the maximum weight for each task.
        calculateMaxWeight(taskArray.length - 1);
        System.out.println("\nCalculating the dynamic solution\n" +
                "--------------------------------");
        // Now we can construct the plan by backtracking through the max weights.

        solveDynamic(taskArray.length - 1);
        System.out.println("\nDynamic Schedule\n" +
                "----------------");
        for (Task task : planDynamic) {
            System.out.println("At " +task.getStartTime()+", "+ task.getName()+".");
        }
        return planDynamic;
    }

    /**
     * {@link #planDynamic} must be filled after calling this method
     */
    public void solveDynamic(int i) {
        System.out.println("Called solveDynamic("+i+")");
        if(compatibility[i] != -1){
            if (taskArray[i].getWeight()+ maxWeight[compatibility[i]] > maxWeight[i-1] ) {
                planDynamic.add(0,taskArray[i]);
                    solveDynamic(compatibility[i]);
            }else{
                if(i-1 >= 0){
                    solveDynamic(i - 1);
                }

            }

        }else{
            planDynamic.add(0,taskArray[i]);
        }


    }

    /**
     * {@link #maxWeight} must be filled after calling this method
     */
    /* This function calculates maximum weights and prints out whether it has been called before or not  */
    public Double calculateMaxWeight(int i) {
        System.out.println("Called calculateMaxWeight("+i+")");
        if (i == -1)
            return 0.0;
        if (maxWeight[i] != null && i!=0)
            return maxWeight[i];
        double weightWithTask = taskArray[i].getWeight() + calculateMaxWeight(compatibility[i]);
        double weightWithoutTask = calculateMaxWeight(i - 1);
        maxWeight[i] = Math.max(weightWithTask, weightWithoutTask);
        return maxWeight[i];
    }

    /**
     * {@link #planGreedy} must be filled after calling this method
     * Uses {@link #taskArray} property
     *
     * @return Returns a list of scheduled assignments
     */

    /*
     * This function is for generating a plan using the greedy approach.
     * */
    public ArrayList<Task> planGreedy() {
        ArrayList<Task> plannedTasks = new ArrayList<>();
        plannedTasks.add(taskArray[0]);
        for (int i=1;i<taskArray.length;i++) {
            String stime = taskArray[i].getStartTime();
            if (Integer.parseInt(stime.split(":")[0]) == Integer.parseInt(plannedTasks.get(plannedTasks.size() - 1).getFinishTime().split(":")[0]) && Integer.parseInt(stime.split(":")[1]) == Integer.parseInt(plannedTasks.get(plannedTasks.size() - 1).getFinishTime().split(":")[1])) {
                plannedTasks.add(taskArray[i]);
            } else if(Integer.parseInt(stime.split(":")[0]) == Integer.parseInt(plannedTasks.get(plannedTasks.size()-1).getFinishTime().split(":")[0]) && Integer.parseInt(stime.split(":")[1]) > Integer.parseInt(plannedTasks.get(plannedTasks.size()-1).getFinishTime().split(":")[1])){
                plannedTasks.add(taskArray[i]);
            } else if (Integer.parseInt(stime.split(":")[0]) > Integer.parseInt(plannedTasks.get(plannedTasks.size()-1).getFinishTime().split(":")[0])) {
                plannedTasks.add(taskArray[i]);
            }
        }

        System.out.println("\nGreedy Schedule\n" + "---------------");
        for (Task task : plannedTasks) {
            System.out.println("At " + task.getStartTime() + ", " + task.getName() + ".");
        }

        planGreedy.addAll(plannedTasks);
        return plannedTasks;
    }
}
