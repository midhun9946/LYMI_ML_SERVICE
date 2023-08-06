package com.candominds.lymi_ml_service;

public class ProcessedData {

  public Double getPredicted_time_start() {
    return predicted_time_start;
  }

  public void setPredicted_time_start(Double predicted_time_start) {
    this.predicted_time_start = predicted_time_start;
  }

  public Double getPredicted_time_end() {
    return predicted_time_end;
  }

  public void setPredicted_time_end(Double predicted_time_end) {
    this.predicted_time_end = predicted_time_end;
  }

  public Double getPredicted_cpu_utilization() {
    return predicted_cpu_utilization;
  }

  public void setPredicted_cpu_utilization(Double predicted_cpu_utilization) {
    this.predicted_cpu_utilization = predicted_cpu_utilization;
  }


 // private String hostname;
  private Double predicted_time_start;
  private Double predicted_time_end;
  private Double predicted_cpu_utilization;
  //private String timestamp;
}
