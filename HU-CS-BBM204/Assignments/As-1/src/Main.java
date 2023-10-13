import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

class Main {
    public static void main(String args[]) throws IOException {

        // X axis data
        int size = 251281;
        int[] sizes = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 250000};
        int[] readArray = new int[size];
        try {
            BufferedReader br = new BufferedReader(new FileReader(args[0]));
            String line = br.readLine(); // ilk satırı atla.
            int rowNumber=1;

            while (rowNumber<=size) {
                line = br.readLine();
                readArray[rowNumber-1] = Integer.parseInt(line.split(",")[6]);
                rowNumber++;
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        double startTime=0, finishTime=0, timeElapsed=0;
        double[] elapsedTimes = new double[10];
        // X axis data
        int[] inputAxis = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 250000};

        // Create sample data for linear runtime
        double[][] yAxisrandom = new double[3][10];

        yAxisrandom[0] = new double[]{512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 251282};
        yAxisrandom[1] = new double[]{300, 800, 1800, 3000, 7000, 15000, 31000, 64000, 121000, 231000};
        yAxisrandom[2] = new double[]{512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 251282};

        SelectionSort.SelectionSortRandom(sizes,readArray,yAxisrandom);
        QuickSort.QuickSortRandom(sizes,readArray,yAxisrandom);
        BucketSort.BucketSortRandom(sizes,readArray,yAxisrandom);

        double[][] yAxissorted = new double[3][10];
        yAxissorted[0] = new double[]{512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 251282};
        yAxissorted[1] = new double[]{300, 800, 1800, 3000, 7000, 15000, 31000, 64000, 121000, 231000};
        yAxissorted[2] = new double[]{512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 251282};

        SelectionSort.SelectionSortSorted(sizes,readArray,yAxissorted);
        QuickSort.QuickSortSorted(sizes,readArray,yAxissorted);
        BucketSort.BucketSortSorted(sizes,readArray,yAxissorted);


        double[][] yAxisreversesorted = new double[3][10];
        yAxisreversesorted[0] = new double[]{512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 251282};
        yAxisreversesorted[1] = new double[]{300, 800, 1800, 3000, 7000, 15000, 31000, 64000, 121000, 231000};
        yAxisreversesorted[2] = new double[]{512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 251282};

        SelectionSort.SelectionSortReverseSorted(sizes,readArray,yAxisreversesorted);
        BucketSort.BucketSortReverseSorted(sizes,readArray,yAxisreversesorted);
        QuickSort.QuickSortReverseSorted(sizes,readArray,yAxisreversesorted);


        double[][] yAxissearch = new double[3][10];
        yAxissearch[0] = new double[]{512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 251282};
        yAxissearch[1] = new double[]{300, 800, 1800, 3000, 7000, 15000, 31000, 64000, 121000, 231000};
        yAxissearch[2] = new double[]{512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 251282};

        Search.LineerSearchRandom(sizes,readArray,yAxissearch);
        Search.LineerSearchSorted(sizes,readArray,yAxissearch);
        Search.BinarySearchSorted(sizes,readArray,yAxissearch);

        showAndSaveChart("Sorting Tests on Random Data", inputAxis, yAxisrandom);
        showAndSaveChart("Sorting Tests on Sorted Data", inputAxis, yAxissorted);
        showAndSaveChart("Sorting Tests on Reversely Sorted Data", inputAxis, yAxisreversesorted);
        showAndSaveChartsearch("Searching Tests", inputAxis, yAxissearch);

    }
    public static void showAndSaveChart(String title, int[] xAxis, double[][] yAxis) throws IOException {
        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title(title)
                .yAxisTitle("Time in Milliseconds").xAxisTitle("Input Size").build();

        // Convert x axis to double[]
        double[] doubleX = Arrays.stream(xAxis).asDoubleStream().toArray();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        // Add a plot for a sorting algorithm
        chart.addSeries("Selection Sort", doubleX, yAxis[0]);
        chart.addSeries("Quick Sort", doubleX, yAxis[1]);
        chart.addSeries("Bucket Sort", doubleX, yAxis[2]);
        // Save the chart as PNG
        BitmapEncoder.saveBitmap(chart, title + ".png", BitmapEncoder.BitmapFormat.PNG);

        // Show the chart
        new SwingWrapper(chart).displayChart();
    }
    public static void showAndSaveChartsearch(String title, int[] xAxis, double[][] yAxis) throws IOException {
        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title(title)
                .yAxisTitle("Time in Nanoseconds").xAxisTitle("Input Size").build();

        // Convert x axis to double[]
        double[] doubleX = Arrays.stream(xAxis).asDoubleStream().toArray();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        // Add a plot for a sorting algorithm
        chart.addSeries("lineer random", doubleX, yAxis[0]);
        chart.addSeries("lineer sorted", doubleX, yAxis[1]);
        chart.addSeries("binary sorted", doubleX, yAxis[2]);
        // Save the chart as PNG
        BitmapEncoder.saveBitmap(chart, title + ".png", BitmapEncoder.BitmapFormat.PNG);

        // Show the chart
        new SwingWrapper(chart).displayChart();
    }








































}
