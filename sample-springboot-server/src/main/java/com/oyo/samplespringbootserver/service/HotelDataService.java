package com.oyo.samplespringbootserver.service;

import java.util.List;
import com.oyo.samplespringbootserver.model.HotelData;

public interface HotelDataService {

  List<HotelData> getRandomHotelDataObjects(int count);

  HotelData getRandomHotelDataObject();

}
