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
    MemoryStats stats = new MemoryStats();
    stats.setHeapSize((double)Runtime.getRuntime().totalMemory()/(1024*1024));
    stats.setHeapMaxSize((double)Runtime.getRuntime().maxMemory()/ (1024*1024));
    stats.setHeapFreeSize((double)Runtime.getRuntime().freeMemory()/ (1024*1024));
    return stats;
  }

  @RequestMapping(value = "/rest/memory-status", method = RequestMethod.GET)
  public MemoryStats getRestMemoryStatistics() {
    return restTemplate.getForObject("http://localhost:4000/memory-status/", MemoryStats.class);
  }

  @RequestMapping(value = "/grpc/memory-status", method = RequestMethod.GET)
  public MemoryStats getGrpcMemoryStatistics() {
    return restTemplate.getForObject("http://localhost:2999/memory-status/", MemoryStats.class);
  }

}
