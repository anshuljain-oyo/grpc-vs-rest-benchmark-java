package com.oyo.sampleclient.sample;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import model.MemoryStats;

@RestController
public class JvmPropertyController {

  private final RestTemplate restTemplate;

  public JvmPropertyController(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @RequestMapping(value = "/memory-status", method = RequestMethod.GET)
  public MemoryStats getMemoryStatistics() {
    return new MemoryStats(Runtime.getRuntime());
  }

  @RequestMapping(value = "/rest/memory-status", method = RequestMethod.GET)
  public MemoryStats getRestMemoryStatistics() {
    return restTemplate.getForObject("http://localhost:4000/memory-status/", MemoryStats.class);
  }

  @RequestMapping(value = "/grpc/memory-status", method = RequestMethod.GET)
  public MemoryStats getGrpcMemoryStatistics() {
    return restTemplate.getForObject("http://localhost:2999/memory-status/", MemoryStats.class);
  }

  @RequestMapping(value = "/rest/objectSizes", method = RequestMethod.GET)
  public String getRestObjectSizes() {
    return restTemplate.getForObject("http://localhost:4000/objectSizes/", String.class);
  }

  @RequestMapping(value = "/grpc/objectSizes", method = RequestMethod.GET)
  public String getGrpcObjectSizes() {
    return restTemplate.getForObject("http://localhost:2999/objectSizes/", String.class);
  }

}
