package com.candominds.lymi_ml_service;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class LinearRegressionModel {
  //static ProcessedData processedData=new ProcessedData();
  static ProcessedData processedData=new ProcessedData();
  DecimalFormat decimalFormat = new DecimalFormat("#.##");
  public List<ProcessedData> buildRegressionModel(List<Double> timestampData,List<Double> cpuUtilizationData,int predictiveTimeStampSample){
    SimpleRegression regression = new SimpleRegression();
  //  ProcessedData processedData=new ProcessedData();
    List<ProcessedData> processedDataList=new ArrayList<>();
    for (int i = 0; i < timestampData.size(); i++) {
      regression.addData(timestampData.get(i), cpuUtilizationData.get(i));
    }
    // Find the span of 10 continuous time samples with the least CPU utilization
    int minStartIndex = 0;
    double minCumulativeUtilization = Double.MAX_VALUE;

    for (int i = 0; i <= cpuUtilizationData.size() - predictiveTimeStampSample; i++) {
      double cumulativeUtilization = 0;
      double averageCumulativeUtilization=0;

      for (int j = i; j < i + predictiveTimeStampSample; j++) {
        double timeStamp=timestampData.get(j);
        double predictedUtilization = regression.predict(timeStamp);
        System.out.println("predictedUtilization ::" + predictedUtilization);
        cumulativeUtilization += predictedUtilization;
        averageCumulativeUtilization=cumulativeUtilization/predictiveTimeStampSample;
        System.out.println("cumulativeUtilization ::" + cumulativeUtilization);
        System.out.println("averageCumulativeUtilization ::" + averageCumulativeUtilization);
      }

      if (averageCumulativeUtilization < minCumulativeUtilization) {
        minCumulativeUtilization = averageCumulativeUtilization;
        minStartIndex = i;
      }
    }

    // Print the span of predictiveTimeStampSample continuous time samples with the least CPU utilization
    int minEndIndex = minStartIndex + (predictiveTimeStampSample-1);

   // setStartPrediveTime(timestampData.get(predicted_time_start));
    //setEndPrediveTime(timestampData.get(minEndIndex));
//
      String formattedStartTimeString= decimalFormat.format(timestampData.get(minStartIndex));
      double formattedStartTimeDouble=Double.parseDouble(formattedStartTimeString);
      processedData.setPredicted_time_start(formattedStartTimeDouble);

    String formattedStartEndString= decimalFormat.format(timestampData.get(minEndIndex));
    double formattedStartEndDouble=Double.parseDouble(formattedStartEndString);
      processedData.setPredicted_time_end(formattedStartEndDouble);

      processedDataList.add(processedData);
      System.out.println("processData in LinearModel ::" + processedData.getPredicted_time_start());
      // processedData.setPredicted_time_end(timestampData.get(minEndIndex));
      System.out.println("Span with the least CPU utilization: " + timestampData.get(minStartIndex) + " to " + timestampData.get(
          minEndIndex));

      //

      // Print the regression equation (y = mx + c)
      double slope = regression.getSlope();
      double intercept = regression.getIntercept();
      System.out.println("Regression Equation: y = " + slope + "x + " + intercept);

      // Plot the CPU utilization against timestamp
      plotData(timestampData, cpuUtilizationData, regression);
  return processedDataList;
  }
//
//  public Double setStartPrediveTime(Double startPrediveTime) {
//    //List<ProcessedData> processdata=new ArrayList<>();
//    processedData.setPredicted_time_start(startPrediveTime);
//    return processedData.getPredicted_time_start();
//  }
//
//  public Double setEndPrediveTime(Double endPrediveTime) {
//    //List<ProcessedData> processdata=new ArrayList<>();
//    processedData.setPredicted_time_end(endPrediveTime);
//    return processedData.getPredicted_time_end();
//  }


  private static void plotData(List<Double> xData, List<Double> yData, SimpleRegression regression) {
    XYSeries series = new XYSeries("CPU Utilization vs. Timestamp");
    for (int i = 0; i < xData.size(); i++) {
      series.add(xData.get(i), yData.get(i));
    }

    XYSeries regressionLine = new XYSeries("Regression Line");
    double minX = xData.get(0);
    double maxX = xData.get(xData.size() - 1);


    //double optimumCpuUtilization=regression.predict(minX);
    //setoptimumCpuUtilization(regression.predict(minX));

    processedData.setPredicted_cpu_utilization(regression.predict(minX));

    System.out.println(regression.predict(minX));
    regressionLine.add(minX, regression.predict(minX));
    regressionLine.add(maxX, regression.predict(maxX));

    XYDataset dataset = new XYSeriesCollection(series);
    ((XYSeriesCollection) dataset).addSeries(regressionLine);

    JFreeChart  plot = ChartFactory.createXYLineChart("CPU Utilization vs. Timestamp", "Timestamp", "CPU Utilization", dataset, PlotOrientation.VERTICAL, true, true, false);

    //ChartPanel chartPanel = new ChartPanel(plot);
  /*  ChartFrame frame = new ChartFrame("Linear Regression", plot);
    frame.pack();
    frame.setVisible(true); */
    try {
      // Save the chart as an image file (e.g., PNG)
      File imageFile = new File("chart.png");
      ChartUtils.saveChartAsPNG(imageFile, plot, 800, 600);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

//  public static double setoptimumCpuUtilization(double predict) {
//    processedData.setPredicted_cpu_utilization(predict);
//    return processedData.getPredicted_cpu_utilization();
//  }
}
