package com.oyo.samplespringbootserver.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.oyo.samplespringbootserver.enums.HotelType;
import com.oyo.samplespringbootserver.model.HotelData;
import com.oyo.samplespringbootserver.repository.HotelDataRepo;
import com.oyo.samplespringbootserver.service.HotelDataService;

@Service
public class HotelDataServiceImpl implements HotelDataService {

  private static final int MIN_ID = 1;
  private static final int MAX_ID = 134497;
  private static final Random random = new Random();
  private static final Map<Integer, List<HotelData>> LARGE_OBJECT_CACHE = new HashMap<>();

  private final HotelDataRepo hotelDataRepo;


  public HotelDataServiceImpl(HotelDataRepo hotelDataRepo) {
    this.hotelDataRepo = hotelDataRepo;
  }

  @Override
  public List<HotelData> getRandomHotelDataObjects(int count) {
    if (!LARGE_OBJECT_CACHE.containsKey(count)) {
      List<HotelData> hotelDataList = getHotelDataList(count);
      LARGE_OBJECT_CACHE.put(count, hotelDataList);
    }

    return LARGE_OBJECT_CACHE.get(count);
  }

  private List<HotelData> getHotelDataList(int count) {
    return random.ints(MIN_ID, MAX_ID).distinct().limit(count).boxed()
        .map(id -> hotelDataRepo.findById(id).orElse(getDefaultHotelData(id)))
        .collect(Collectors.toList());
  }

  private HotelData getDefaultHotelData(Integer id) {
    HotelData hotelData = new HotelData();
    hotelData.setId(id);
    hotelData.setHotelId(-1);
    hotelData.setCountryId(-1);
    hotelData.setHotelType(HotelType.hotel);
    hotelData.setWizardEnabled(false);
    hotelData.setOyoId("Default");
    hotelData.setGeoLocation("Default");
    hotelData.setIsCpEnabled(false);
    return hotelData;
  }

  @Override
  public HotelData getRandomHotelDataObject() {
    return getRandomHotelDataObjects(1).get(0);
  }
}
