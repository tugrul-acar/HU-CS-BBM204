import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class QuickSort {
    public static int partition(int arr[], int low, int high)
    {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }

    public static void quickSort(int arr[], int l, int h)
    {
        int[] stack = new int[h - l + 1];
        int top = -1;
        stack[++top] = l;
        stack[++top] = h;
        while (top >= 0) {
            h = stack[top--];
            l = stack[top--];
            int p = partition(arr, l, h);
            if (p - 1 > l) {
                stack[++top] = l;
                stack[++top] = p - 1;
            }
            if (p + 1 < h) {
                stack[++top] = p + 1;
                stack[++top] = h;
            }
        }
    }

    public static void QuickSortRandom(int[] sizes,int[] readArray,double[][] yAxis){
        double[] elapsedTimes = new double[10];
        double startTime=0, finishTime=0, timeElapsed=0;
        int size = 0;
        for (int k = 0; k < 10; k++) {                      ///Measurement PART
            size = sizes[k];
            int[] deepCopyOfArray = new int[size];
            System.arraycopy(readArray, 0, deepCopyOfArray, 0, size);
            int total = 0;
            for (int i = 0; i < 10; i++) { //algoritmayı 10 defa çalıştırıyorum.
                int[] test = deepCopyOfArray.clone();
                startTime = System.currentTimeMillis();
                quickSort(test,0,size-1);
                finishTime = System.currentTimeMillis();
                timeElapsed = finishTime - startTime;
                total += timeElapsed;
            }
            elapsedTimes[k] = total/10;

            System.arraycopy(elapsedTimes, 0, yAxis[1], 0, 10);
        }
        System.out.print("QuickSort random : ");
        System.out.println(elapsedTimes[0]+" "+elapsedTimes[1]+" "+elapsedTimes[2]+" "+elapsedTimes[3]+" "+elapsedTimes[4]+" "+elapsedTimes[5]+" "+elapsedTimes[6]+" "+elapsedTimes[7]+" "+elapsedTimes[8]+" "+elapsedTimes[9]);

    }

    public static void QuickSortSorted(int[] sizes,int[] readArray,double[][] yAxis){
        double[] elapsedTimes = new double[10];
        double startTime=0, finishTime=0, timeElapsed=0;
        int size = 0;
        for (int k = 0; k < 10; k++) {                      ///Measurement PART
            size = sizes[k];
            int[] deepCopyOfArray = new int[size];
            System.arraycopy(readArray, 0, deepCopyOfArray, 0, size);
            int total = 0;
            quickSort(deepCopyOfArray,0,size-1);
            for (int i = 0; i < 10; i++) { //algoritmayı 10 defa çalıştırıyorum.
                int[] test = deepCopyOfArray.clone();
                startTime = System.currentTimeMillis();
                quickSort(test,0,size-1);
                finishTime = System.currentTimeMillis();
                timeElapsed = finishTime - startTime;
                total += timeElapsed;
            }
            elapsedTimes[k] = total/10;
            System.arraycopy(elapsedTimes, 0, yAxis[1], 0, 10);
        }
        System.out.print("QuickSort sorted : ");
        System.out.println(elapsedTimes[0]+" "+elapsedTimes[1]+" "+elapsedTimes[2]+" "+elapsedTimes[3]+" "+elapsedTimes[4]+" "+elapsedTimes[5]+" "+elapsedTimes[6]+" "+elapsedTimes[7]+" "+elapsedTimes[8]+" "+elapsedTimes[9]);

    }

    public static void QuickSortReverseSorted(int[] sizes,int[] readArray,double[][] yAxis){
        double[] elapsedTimes = new double[10];
        double startTime=0, finishTime=0, timeElapsed=0;
        int size = 0;
        for (int k = 0; k < 10; k++) {                      ///Measurement PART
            size = sizes[k];
            int[] deepCopyOfArray = new int[size];
            System.arraycopy(readArray, 0, deepCopyOfArray, 0, size);
            int total = 0;
            quickSort(deepCopyOfArray,0,size-1);
            Collections.reverse(Arrays.asList(deepCopyOfArray));
            for (int i = 0; i < 10; i++) { //algoritmayı 10 defa çalıştırıyorum.
                int[] test = deepCopyOfArray.clone();
                startTime = System.currentTimeMillis();
                quickSort(test,0,size-1);
                finishTime = System.currentTimeMillis();
                timeElapsed = finishTime - startTime;
                total += timeElapsed;
            }
            elapsedTimes[k] = total/10;

            System.arraycopy(elapsedTimes, 0, yAxis[1], 0, 10);
        }

        System.out.print("QuickSort reverse sorted : ");
        System.out.println(elapsedTimes[0]+" "+elapsedTimes[1]+" "+elapsedTimes[2]+" "+elapsedTimes[3]+" "+elapsedTimes[4]+" "+elapsedTimes[5]+" "+elapsedTimes[6]+" "+elapsedTimes[7]+" "+elapsedTimes[8]+" "+elapsedTimes[9]);
    }






}
