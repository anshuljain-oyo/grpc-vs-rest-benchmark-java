package model;

public class MemoryStats {

  private double heapSize;
  private double heapMaxSize;
  private double heapFreeSize;

  public double getHeapSize() {
    return heapSize;
  }

  public void setHeapSize(double heapSize) {
    this.heapSize = heapSize;
  }

  public double getHeapMaxSize() {
    return heapMaxSize;
  }

  public void setHeapMaxSize(double heapMaxSize) {
    this.heapMaxSize = heapMaxSize;
  }

  public double getHeapFreeSize() {
    return heapFreeSize;
  }

  public void setHeapFreeSize(double heapFreeSize) {
    this.heapFreeSize = heapFreeSize;
  }

  public MemoryStats() {
  }

  public MemoryStats(Runtime runtime) {
    this.heapSize = (double)Runtime.getRuntime().totalMemory()/(1024*1024);
    this.heapMaxSize = (double)Runtime.getRuntime().maxMemory()/ (1024*1024);
    this.heapFreeSize = (double)Runtime.getRuntime().freeMemory()/ (1024*1024);
  }
}
