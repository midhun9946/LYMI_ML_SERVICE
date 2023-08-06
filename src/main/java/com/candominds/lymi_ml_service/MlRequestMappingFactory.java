package com.candominds.lymi_ml_service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.mongodb.core.MongoTemplate;


@RestController
@RequestMapping("/predictioninfo")
public class MlRequestMappingFactory {
  // private final MongoTemplate mongoTemplate;

  // private final MongRepositoryController _mongRepositoryController;
  private final DataProcessFactory dataProcessFactory;

  @Autowired
  //  public MlRequestMappingFactory(MongRepositoryController mongRepositoryController){
  //this._mongRepositoryController = mongRepositoryController;
  public MlRequestMappingFactory(DataProcessFactory dataProcessFactory) {
    this.dataProcessFactory = dataProcessFactory;
  }
  //this.mongoTemplate=mongoTemplate;


  /* param2:: predictiveSample, param1 :: number of days back*/

  @PostMapping("/cpuutilization")
  public List<ProcessedData> getProcessedData(@RequestParam Integer predictiveTimeStampSample,@RequestParam LocalDate targetDate) {
    System.out.println(dataProcessFactory.processData(predictiveTimeStampSample,targetDate));
    return dataProcessFactory.processData(predictiveTimeStampSample,targetDate);
  }
}

//  @PostMapping("/cpuutilization/")
//  public ResponseEntity<String> buildRequestBody(@RequestBody MlParamUtils predictioninfo){
//    DataPreProcessing dataPreProcessing=new DataPreProcessing();
//    _mongRepositoryController.save(predictioninfo);
//    return ResponseEntity.status(HttpStatus.CREATED).body("Data inserted successfully");
//  }
//}
