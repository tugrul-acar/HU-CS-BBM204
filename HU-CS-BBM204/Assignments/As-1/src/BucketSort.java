import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class BucketSort {
    public static void bucketSort(int[] array) {
        int numberOfBuckets = (int)Math.sqrt(array.length);
        ArrayList<Integer>[] buckets = new ArrayList[numberOfBuckets];
        for (int i = 0; i < numberOfBuckets; i++) {
            buckets[i] = new ArrayList<>();
        }

        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }

        for (int i = 0; i < array.length; i++) {
            int bucketIndex = hash(array[i], max, numberOfBuckets);
            buckets[bucketIndex].add(array[i]);
        }

        for (int i = 0; i < numberOfBuckets; i++) {
            Collections.sort(buckets[i]);
        }

        int index = 0;
        for (int i = 0; i < numberOfBuckets; i++) {
            for (int j = 0; j < buckets[i].size(); j++) {
                array[index] = buckets[i].get(j);
                index++;
            }
        }
    }

    public static int hash(int number, int max, int numberOfBuckets) {
        return (int) Math.floor(number / (double) max * (numberOfBuckets - 1));
    }


    public static void BucketSortRandom(int[] sizes,int[] readArray,double[][] yAxis){
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
                bucketSort(test);
                finishTime = System.currentTimeMillis();
                timeElapsed = finishTime - startTime;
                total += timeElapsed;
            }
            elapsedTimes[k] = total/10;
            System.arraycopy(elapsedTimes, 0, yAxis[2], 0, 10);
        }
        System.out.print("bucket random : ");
        System.out.println(elapsedTimes[0]+" "+elapsedTimes[1]+" "+elapsedTimes[2]+" "+elapsedTimes[3]+" "+elapsedTimes[4]+" "+elapsedTimes[5]+" "+elapsedTimes[6]+" "+elapsedTimes[7]+" "+elapsedTimes[8]+" "+elapsedTimes[9]);

    }

    public static void BucketSortSorted(int[] sizes,int[] readArray,double[][] yAxis){
        double[] elapsedTimes = new double[10];
        double startTime=0, finishTime=0, timeElapsed=0;
        int size = 0;
        for (int k = 0; k < 10; k++) {                      ///Measurement PART
            size = sizes[k];
            int[] deepCopyOfArray = new int[size];
            System.arraycopy(readArray, 0, deepCopyOfArray, 0, size);
            int total = 0;
            bucketSort(deepCopyOfArray);
            for (int i = 0; i < 10; i++) { //algoritmayı 10 defa çalıştırıyorum.
                int[] test = deepCopyOfArray.clone();
                startTime = System.currentTimeMillis();
                bucketSort(test);
                finishTime = System.currentTimeMillis();
                timeElapsed = finishTime - startTime;
                total += timeElapsed;

            }
            elapsedTimes[k] = total/10;
            System.arraycopy(elapsedTimes, 0, yAxis[2], 0, 10);
        }
        System.out.print("bucket sorted : ");
        System.out.println(elapsedTimes[0]+" "+elapsedTimes[1]+" "+elapsedTimes[2]+" "+elapsedTimes[3]+" "+elapsedTimes[4]+" "+elapsedTimes[5]+" "+elapsedTimes[6]+" "+elapsedTimes[7]+" "+elapsedTimes[8]+" "+elapsedTimes[9]);
    }

    public static void BucketSortReverseSorted(int[] sizes,int[] readArray,double[][] yAxis){
        double[] elapsedTimes = new double[10];
        double startTime=0, finishTime=0, timeElapsed=0;
        int size = 0;
        for (int k = 0; k < 10; k++) {                      ///Measurement PART
            size = sizes[k];
            int[] deepCopyOfArray = new int[size];
            System.arraycopy(readArray, 0, deepCopyOfArray, 0, size);
            int total = 0;
            bucketSort(deepCopyOfArray);
            Collections.reverse(Arrays.asList(deepCopyOfArray));
            for (int i = 0; i < 10; i++) { //algoritmayı 10 defa çalıştırıyorum.
                int[] test = deepCopyOfArray.clone();
                startTime = System.currentTimeMillis();
                bucketSort(test);
                finishTime = System.currentTimeMillis();
                timeElapsed = finishTime - startTime;
                total += timeElapsed;

            }
            elapsedTimes[k] = total/10;

            System.arraycopy(elapsedTimes, 0, yAxis[2], 0, 10);
        }
        System.out.print("bucket reverse sorted : ");
        System.out.println(elapsedTimes[0]+" "+elapsedTimes[1]+" "+elapsedTimes[2]+" "+elapsedTimes[3]+" "+elapsedTimes[4]+" "+elapsedTimes[5]+" "+elapsedTimes[6]+" "+elapsedTimes[7]+" "+elapsedTimes[8]+" "+elapsedTimes[9]);

    }
}
