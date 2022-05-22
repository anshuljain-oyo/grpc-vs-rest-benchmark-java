package model;

import java.util.ArrayList;
import java.util.List;
import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;

public class ObjectSizeList {

  private final List<Object> objects;

  public ObjectSizeList() {
    objects = new ArrayList<>();
  }

  public List<Object> getObjects() {
    return objects;
  }

  public void addObject(Object object) {
    this.objects.add(object);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Object obj : objects) {
      sb.append("Object of class ")
          .append(obj.getClass().getSimpleName())
          .append(" has a size of ")
          .append(ObjectSizeCalculator.getObjectSize(obj))
          .append(" bytes\n");
    }
    return sb.toString();
  }
}
