package com.oyo.sampleclient.sample;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.oyo.sampleclient.sample.impl.SampleGrpcClientServiceImpl;
import com.oyo.sampleclient.sample.impl.SampleRestClientServiceImpl;

@RestController
public class SampleController {

  SampleRestClientServiceImpl sampleRestClientService;
  SampleGrpcClientServiceImpl sampleGrpcClientService;

  public SampleController(SampleRestClientServiceImpl sampleRestClientService,
      SampleGrpcClientServiceImpl sampleGrpcClientService) {
    this.sampleRestClientService = sampleRestClientService;
    this.sampleGrpcClientService = sampleGrpcClientService;
  }

  @RequestMapping(value = "/rest/randomNumbers")
  public void restRandomNumber(@RequestParam("count") int count) {
    sampleRestClientService.getRandomNumbers(count);
  }

  @RequestMapping(value = "/rest/largeObjects/json")
  public void restLargeObjectsJson(@RequestParam("count") int count) {
    sampleRestClientService.getLargeObjectsJson(count);
  }

  @RequestMapping(value = "/rest/largeObjects")
  public void restLargeObjectsProto(@RequestParam("count") int count) {
    sampleRestClientService.getLargeObjectsProto(count);
  }

  @RequestMapping(value = "/rest/hotelDataObjects")
  public void restHotelDataObjectsProto(@RequestParam("count") int count) {
    sampleRestClientService.getHotelDataObjectsProto(count);
  }

  @RequestMapping(value = "/grpc/randomNumbers")
  public void grpcRandomNumber(@RequestParam("count") int count) {
    sampleGrpcClientService.getRandomNumbers(count);
  }

  @RequestMapping(value = "/grpc/largeObjects")
  public void grpcLargeObjectsProto(@RequestParam("count") int count) {
    sampleGrpcClientService.getLargeObjectsProto(count);
  }

  @RequestMapping(value = "/grpc/hotelDataObjects")
  public void grpcHotelDataObjectsProto(@RequestParam("count") int count) {
    sampleGrpcClientService.getHotelDataObjectsProto(count);
  }
}
