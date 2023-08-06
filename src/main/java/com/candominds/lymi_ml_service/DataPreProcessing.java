package com.candominds.lymi_ml_service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.Document;
import java.time.ZoneId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.ConvertOperators;
import org.springframework.stereotype.Service;
//import com.candominds.lymi_ml_service.LinearRegressionModel;



//@Service
public class DataPreProcessing {
//  public static void main(String[] args) {
    //SpringApplication.run(DataPreProcessing.class,args);
 //}


  MongoDBConnector connector = new MongoDBConnector();
  public List<Double> getCPUUtilization(LocalDate targetDate) {
    List<Double> cpuUtilization = new ArrayList<>();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    for (Document document : connector.getCollection().find()) {
      String timestampString = document.getString("timestamp");
      LocalDateTime timestamp = LocalDateTime.parse(timestampString, formatter);
      if (timestamp.toLocalDate().isEqual(targetDate)) {
        double utilization = document.getDouble("cpuutilization_server");
        cpuUtilization.add(utilization);
      }
    }
    System.out.println("cpuUtilization::" +cpuUtilization);
    return cpuUtilization;
  }


  public List<Double> getTimestamps(LocalDate targetDate) {
    List<Double> timestamps = new ArrayList<>();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    for (Document document : connector.getCollection().find()) {
      String timestampString = document.getString("timestamp");
      LocalDateTime timestamp = LocalDateTime.parse(timestampString, formatter);
      if (timestamp.toLocalDate().isEqual(targetDate)) {
        // Extract hours and minutes from the LocalTime object
        int hours = timestamp.getHour();
        int minutes = timestamp.getMinute();

        // Calculate the total hours and minutes in decimal format (double)
        double totalHours = hours + (minutes / 60.0);
        timestamps.add(totalHours);
       // System.out.println("Total hours::" + totalHours + "Total minutes");
      }
    }
    System.out.println("timestamps ::" +timestamps);
    return timestamps;
  }

  public List<Double> getCPUtemperature() {
    List<Double> cpuTemperatures = new ArrayList<>();
    MongoCursor<Document> cursor = connector.getCollection().find().iterator();
    ;
    try {
      while (cursor.hasNext()) {
        Document document = cursor.next();
        Double cpuTemperature = document.getDouble("cpu_temperature");
        cpuTemperatures.add(cpuTemperature);
      }
    } finally {
      cursor.close();
    }
    return cpuTemperatures;
  }
  public void close() {
    connector.getMongoClient().close();
  }
}
