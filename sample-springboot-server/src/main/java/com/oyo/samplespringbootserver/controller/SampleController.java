package com.oyo.samplespringbootserver.controller;

import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.oyo.samplegrpcserver.service.LargeProtoObjectResponse;
import com.oyo.samplespringbootserver.model.LargePojoObject;
import com.oyo.samplespringbootserver.service.SampleService;

@RestController
public class SampleController {

  SampleService sampleService;

  public SampleController(SampleService sampleService) {
    this.sampleService = sampleService;
  }

  @RequestMapping("/randomNumbers")
  public List<Integer> getRandomNumbers(@RequestParam("count") int count) {
    return sampleService.getRandomNumbers(count);
  }

  /**
   * @param count
   * @return a Proto Object
   */
  @RequestMapping("/largeObjects")
  public LargeProtoObjectResponse getLargeObjects(@RequestParam("count") int count) {
    return LargeProtoObjectResponse.newBuilder()
        .addAllLargeProtoObjects(sampleService.getLargeProtoObjects(count))
        .build();
  }

  /**
   * @param count
   * @return a POJO List as JSON
   */
  @RequestMapping("/largeObjects/json")
  public List<LargePojoObject> getLargeObjectsJson(@RequestParam("count") int count) {
    return sampleService.getLargePojoObjects(count);
  }
}
