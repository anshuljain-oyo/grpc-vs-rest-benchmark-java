package com.oyo.samplespringbootserver.controller;

import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.oyo.samplespringbootserver.model.HotelData;
import com.oyo.samplespringbootserver.service.HotelDataService;

@RestController
public class HotelDataController {

  private final HotelDataService hotelDataService;


  public HotelDataController(HotelDataService hotelDataService) {
    this.hotelDataService = hotelDataService;
  }

  /**
   * @param count int
   * @return List of HotelData Objects
   */
  @RequestMapping("/hotelDataObjects")
  public List<HotelData> getHotelDataObjects(@RequestParam("count") int count) {
    return hotelDataService.getRandomHotelDataObjects(count);
  }

  /**
   * @return a HotelData Object
   */
  @RequestMapping("/hotelDataObject")
  public HotelData getHotelDataObject() {
    return hotelDataService.getRandomHotelDataObject();
  }

}
