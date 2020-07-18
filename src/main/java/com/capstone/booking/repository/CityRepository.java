package com.capstone.booking.repository;

import com.capstone.booking.entity.City;
import com.capstone.booking.repository.customRepository.CityRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CityRepository extends JpaRepository<City, Long>, CityRepositoryCustom {

    City findByName(String name);

    @Query(value="SELECT * FROM t_city c ORDER BY c.id ASC LIMIT 3", nativeQuery = true)
    List<City> getTop3();

}
