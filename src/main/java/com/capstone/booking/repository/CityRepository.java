package com.capstone.booking.repository;

import com.capstone.booking.entity.City;
import com.capstone.booking.repository.customRepository.CityRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CityRepository extends JpaRepository<City, Long>, CityRepositoryCustom {

    City findByName(String name);

}
