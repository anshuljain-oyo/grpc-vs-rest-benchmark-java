package com.oyo.samplespringbootserver.enums;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum HotelType {

  home("OYO Home"),
  hotel("Hotel"),
  belvilla("Belvilla Home"),
  subscription_hotel("Subscription Hotel");

  private String value;

  private static final Map<String, HotelType> HOTEL_TYPE_MAP =
      Collections.unmodifiableMap(initializeMapping());

  private static Map<String, HotelType> initializeMapping() {
    Map<String, HotelType> map = new HashMap<String, HotelType>();
    for (HotelType ht : HotelType.values()) {
      map.put(ht.getValue(), ht);
    }
    return map;
  }

  private HotelType(String value) {
    this.value = value;
  }

  public static HotelType getHotelTypeByValue(String hotelTypeValue) {
    return HOTEL_TYPE_MAP.get(hotelTypeValue);
  }

  public String getValue() {
    return value;
  }

}

