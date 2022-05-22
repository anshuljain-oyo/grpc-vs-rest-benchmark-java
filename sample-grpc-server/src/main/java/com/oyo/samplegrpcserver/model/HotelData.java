package com.oyo.samplegrpcserver.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import com.oyo.samplegrpcserver.service.HotelType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@Table(name = "hotel_data")
public class HotelData extends AbstractEntity {

  @Column(name = "hotel_id")
  private Integer hotelId;

  @Column(name = "country_id")
  private Integer countryId;

  @Enumerated(EnumType.STRING)
  @Column(name = "hotel_type")
  private HotelType hotelType;

  @Column(name = "wizard_enabled")
  private Boolean wizardEnabled;

  @Column(name = "oyo_id")
  private String oyoId;

  @Column(name = "geo_location")
  private String geoLocation;

  // whether hotel is CP enabled or not
  @Column(name = "is_cp_enabled")
  private Boolean isCpEnabled;
}
