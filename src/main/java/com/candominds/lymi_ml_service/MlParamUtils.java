package com.candominds.lymi_ml_service;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//@Document(collection = "serverinfo")
@Document(collection = "predictioninfo")
public class MlParamUtils {

  public String getRequest_id() {
    return request_id;
  }

  public void setRequest_id(String request_id) {
    this.request_id = request_id;
  }

  public String getSession_id() {
    return session_id;
  }

  public void setSession_id(String session_id) {
    this.session_id = session_id;
  }

  public String getHostname() {
    return hostname;
  }

  public void setHostname(String hostname) {
    this.hostname = hostname;
  }

  public String getMl_operation() {
    return ml_operation;
  }

  public void setMl_operation(String ml_operation) {
    this.ml_operation = ml_operation;
  }



  @Id
  private String request_id;
  private String session_id;
  private String hostname;
  private String ml_operation;

  public Integer getPredictiveTimeStampSample() {
    return predictiveTimeStampSample;
  }

  public void setPredictiveTimeStampSample(Integer predictiveTimeStampSample) {
    this.predictiveTimeStampSample = predictiveTimeStampSample;
  }

  public LocalDate getTargetDate() {
    return targetDate;
  }

  public void setTargetDate(LocalDate targetDate) {
    this.targetDate = targetDate;
  }

  private Integer predictiveTimeStampSample;
  private LocalDate targetDate;
  public  MlParamUtils(){
  }

}
