package com.oyo.samplespringbootserver.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.oyo.samplespringbootserver.model.HotelData;

@Repository
public interface HotelDataRepo extends JpaRepository<HotelData, Integer> {

  HotelData findByHotelId(Integer hotelId);

}

