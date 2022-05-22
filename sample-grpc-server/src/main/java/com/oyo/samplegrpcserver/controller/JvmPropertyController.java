package com.oyo.samplegrpcserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import model.MemoryStats;

@RestController
public class JvmPropertyController {

  @RequestMapping("/memory-status")
  public MemoryStats getMemoryStatistics() {
    MemoryStats stats = new MemoryStats();
    stats.setHeapSize((double)Runtime.getRuntime().totalMemory()/(1024*1024));
    stats.setHeapMaxSize((double)Runtime.getRuntime().maxMemory()/ (1024*1024));
    stats.setHeapFreeSize((double)Runtime.getRuntime().freeMemory()/ (1024*1024));
    return stats;
  }

}
