import java.util.Arrays;
import java.util.Collections;
import java.util.MissingFormatArgumentException;

public class SelectionSort {
    public static void selectionSort(int arr[])
    {
        int n = arr.length;
        for (int i = 0; i < n-1; i++)
        {
            int min_i = i;
            for (int j = i+1; j < n; j++)
                if (arr[j] < arr[min_i])
                    min_i = j;
            int temp = arr[min_i];
            arr[min_i] = arr[i];
            arr[i] = temp;
        }
    }

    public static void SelectionSortRandom(int[] sizes,int[] readArray,double[][] yAxis){
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
                selectionSort(test);
                finishTime = System.currentTimeMillis();
                timeElapsed = finishTime - startTime;
                total += timeElapsed;
            }
            elapsedTimes[k] = total/10;

            System.arraycopy(elapsedTimes, 0, yAxis[0], 0, 10);
        }
        System.out.print("Selection sort random : ");
        System.out.println(elapsedTimes[0]+" "+elapsedTimes[1]+" "+elapsedTimes[2]+" "+elapsedTimes[3]+" "+elapsedTimes[4]+" "+elapsedTimes[5]+" "+elapsedTimes[6]+" "+elapsedTimes[7]+" "+elapsedTimes[8]+" "+elapsedTimes[9]);


    }

    public static void SelectionSortSorted(int[] sizes,int[] readArray,double[][] yAxis){
        double[] elapsedTimes = new double[10];
        double startTime=0, finishTime=0, timeElapsed=0;
        int size = 0;
        for (int k = 0; k < 10; k++) {                      ///Measurement PART
            size = sizes[k];
            int[] deepCopyOfArray = new int[size];
            System.arraycopy(readArray, 0, deepCopyOfArray, 0, size);
            int total = 0;
            QuickSort.quickSort(deepCopyOfArray,0,size-1);
            for (int i = 0; i < 10; i++) { //algoritmayı 10 defa çalıştırıyorum.
                int[] test = deepCopyOfArray.clone();
                startTime = System.currentTimeMillis();
                selectionSort(test);
                finishTime = System.currentTimeMillis();
                timeElapsed = finishTime - startTime;
                total += timeElapsed;
            }
            elapsedTimes[k] = total/10;
            System.arraycopy(elapsedTimes, 0, yAxis[0], 0, 10);
        }
        System.out.print("Selection sort sorted : ");
        System.out.println(elapsedTimes[0]+" "+elapsedTimes[1]+" "+elapsedTimes[2]+" "+elapsedTimes[3]+" "+elapsedTimes[4]+" "+elapsedTimes[5]+" "+elapsedTimes[6]+" "+elapsedTimes[7]+" "+elapsedTimes[8]+" "+elapsedTimes[9]);

    }

    public static void SelectionSortReverseSorted(int[] sizes,int[] readArray,double[][] yAxis){
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
            for (int i = 0; i < 10; i++) { //algoritmayı 10 defa çalıştırıyorum.
                int[] test = deepCopyOfArray.clone();
                startTime = System.currentTimeMillis();
                selectionSort(test);
                finishTime = System.currentTimeMillis();
                timeElapsed = finishTime - startTime;
                total += timeElapsed;
            }
            elapsedTimes[k] = total/10;
            System.arraycopy(elapsedTimes, 0, yAxis[0], 0, 10);
        }
        System.out.print("Selection sort reverse sorted : ");
        System.out.println(elapsedTimes[0]+" "+elapsedTimes[1]+" "+elapsedTimes[2]+" "+elapsedTimes[3]+" "+elapsedTimes[4]+" "+elapsedTimes[5]+" "+elapsedTimes[6]+" "+elapsedTimes[7]+" "+elapsedTimes[8]+" "+elapsedTimes[9]);
    }
}
