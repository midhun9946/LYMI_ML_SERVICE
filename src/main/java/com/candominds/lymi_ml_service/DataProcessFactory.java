package com.candominds.lymi_ml_service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DataProcessFactory {
  public List<ProcessedData> processData(int predictiveTimeStampSample, LocalDate targetDate) {
    // Perform data processing and return the result as a list of ProcessedData objects
    List<ProcessedData> processedDataList = new ArrayList<>();

    ProcessedData processedData=new ProcessedData();
    DataPreProcessing dataPreProcessing = new DataPreProcessing();
    LinearRegressionModel linearRegressionModel = new LinearRegressionModel();

    //int predictiveTimeStampSample = 10;
    // ... (data processing logic)
   // dataPreProcessing.close();
    List<Double> cpuUtilization = dataPreProcessing.getCPUUtilization(targetDate);
    List<Double> timestamps = dataPreProcessing.getTimestamps(targetDate);

    if (cpuUtilization.size()==0|| timestamps.size()==0){
      System.out.println("Matched DataSet is Missing  for given data");
    }
    else {

      //   System.out.println("processedDataList::" + pro);
      System.out.println("Predicted Start Time::" + processedData.getPredicted_time_start());
      return linearRegressionModel.buildRegressionModel(timestamps, cpuUtilization, predictiveTimeStampSample);
    }
    return null;
  }
}
