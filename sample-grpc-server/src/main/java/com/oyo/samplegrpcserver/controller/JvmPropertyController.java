package com.oyo.samplegrpcserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import model.MemoryStats;

@RestController
public class JvmPropertyController {

  @RequestMapping("/memory-status")
  public MemoryStats getMemoryStatistics() {
    return new MemoryStats(Runtime.getRuntime());
  }

//  @RequestMapping("/objectSizes")
//  public String getObjectSizeChart() {
//    ObjectSizeList objectSizeList = new ObjectSizeList();
//    objectSizeList.addObject();
//    return new MemoryStats(Runtime.getRuntime());
//  }

}
