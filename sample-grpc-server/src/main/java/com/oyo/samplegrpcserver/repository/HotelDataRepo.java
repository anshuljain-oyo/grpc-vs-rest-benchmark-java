package com.oyo.samplegrpcserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.oyo.samplegrpcserver.model.HotelData;

@Repository
public interface HotelDataRepo extends JpaRepository<HotelData, Integer> {

  HotelData findByHotelId(Integer hotelId);

}

