package com.capstone.booking.repository;

import com.capstone.booking.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CityRepository extends JpaRepository<City, Long> {

    City findByName(String name);

}
