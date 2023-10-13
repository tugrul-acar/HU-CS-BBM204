import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Search {

    public static int linearSearch(int arr[], int x)
    {
        int N = arr.length;
        for (int i = 0; i < N; i++) {
            if (arr[i] == x)
                return i;
        }
        return -1;
    }
    public static int binarySearch(int v[], int find_item)
    {
        int lo = 0, hi = v.length - 1;
        while (hi - lo > 1) {
            int mid = (hi + lo) / 2;
            if (v[mid] < find_item) {
                lo = mid + 1;
            }
            else {
                hi = mid;
            }
        }
        if (v[lo] == find_item) {
            return lo;
        }
        else if (v[hi] == find_item) {
            return hi;
        }
        else {
            return -1;
        }
    }

    public static void LineerSearchRandom(int[] sizes,int[] readArray,double[][] yAxis){
        double[] elapsedTimes = new double[10];
        double startTime=0, finishTime=0, timeElapsed=0;
        int size = 0;
        for (int k = 0; k < 10; k++) {                      ///Measurement PART
            size = sizes[k];
            int[] deepCopyOfArray = new int[size];
            System.arraycopy(readArray, 0, deepCopyOfArray, 0, size);
            int total = 0;
            Collections.reverse(Arrays.asList(deepCopyOfArray));
            for (int i = 0; i < 1000; i++) {
                Random randI = new Random();
                int myRandInt = randI.nextInt(size);
                startTime = System.nanoTime();
                linearSearch(deepCopyOfArray,myRandInt);
                finishTime = System.nanoTime();
                timeElapsed = finishTime - startTime;
                total += timeElapsed;
            }
            elapsedTimes[k] = total/1000;
            System.arraycopy(elapsedTimes, 0, yAxis[0], 0, 10);
        }
        System.out.print("Linear search (random data) : ");
        System.out.println(elapsedTimes[0]+" "+elapsedTimes[1]+" "+elapsedTimes[2]+" "+elapsedTimes[3]+" "+elapsedTimes[4]+" "+elapsedTimes[5]+" "+elapsedTimes[6]+" "+elapsedTimes[7]+" "+elapsedTimes[8]+" "+elapsedTimes[9]);

    }
    public static void LineerSearchSorted(int[] sizes,int[] readArray,double[][] yAxis){
        double[] elapsedTimes = new double[10];
        double startTime=0, finishTime=0, timeElapsed=0;
        int size = 0;
        for (int k = 0; k < 10; k++) {                      ///Measurement PART
            size = sizes[k];
            int[] deepCopyOfArray = new int[size];
            System.arraycopy(readArray, 0, deepCopyOfArray, 0, size);
            int total = 0;
            BucketSort.bucketSort(deepCopyOfArray);
            Collections.reverse(Arrays.asList(deepCopyOfArray));
            for (int i = 0; i < 1000; i++) {
                Random randI = new Random();
                int myRandInt = randI.nextInt(size);
                startTime = System.nanoTime();
                linearSearch(deepCopyOfArray,myRandInt);
                finishTime = System.nanoTime();
                timeElapsed = finishTime - startTime;
                total += timeElapsed;
            }
            elapsedTimes[k] = total/1000;
            System.arraycopy(elapsedTimes, 0, yAxis[1], 0, 10);
        }
        System.out.print("Linear search (sorted data) : ");
        System.out.println(elapsedTimes[0]+" "+elapsedTimes[1]+" "+elapsedTimes[2]+" "+elapsedTimes[3]+" "+elapsedTimes[4]+" "+elapsedTimes[5]+" "+elapsedTimes[6]+" "+elapsedTimes[7]+" "+elapsedTimes[8]+" "+elapsedTimes[9]);

    }

    public static void BinarySearchSorted(int[] sizes,int[] readArray,double[][] yAxis){
        double[] elapsedTimes = new double[10];
        double startTime=0, finishTime=0, timeElapsed=0;
        int size = 0;
        for (int k = 0; k < 10; k++) {                      ///Measurement PART
            size = sizes[k];
            int[] deepCopyOfArray = new int[size];
            System.arraycopy(readArray, 0, deepCopyOfArray, 0, size);
            int total = 0;
            BucketSort.bucketSort(deepCopyOfArray);
            Collections.reverse(Arrays.asList(deepCopyOfArray));
            for (int i = 0; i < 1000; i++) {
                Random randI = new Random();
                int myRandInt = randI.nextInt(size);
                startTime = System.nanoTime();
                binarySearch(deepCopyOfArray,myRandInt);
                finishTime = System.nanoTime();
                timeElapsed = finishTime - startTime;
                total += timeElapsed;
                elapsedTimes[k] = total/1000;
            }

            System.arraycopy(elapsedTimes, 0, yAxis[2], 0, 10);
        }
        System.out.print("Binary Search (sorted data) : ");
        System.out.println(elapsedTimes[0]+" "+elapsedTimes[1]+" "+elapsedTimes[2]+" "+elapsedTimes[3]+" "+elapsedTimes[4]+" "+elapsedTimes[5]+" "+elapsedTimes[6]+" "+elapsedTimes[7]+" "+elapsedTimes[8]+" "+elapsedTimes[9]);

    }
}
