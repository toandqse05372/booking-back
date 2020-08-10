package com.capstone.booking.repository;
import com.capstone.booking.entity.City;
import com.capstone.booking.entity.Place;
import com.capstone.booking.repository.customRepository.PlaceRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//customer query to category table
public interface PlaceRepository extends JpaRepository<Place, Long>, PlaceRepositoryCustom {
    //find place by exact name
    Place findByName(String name);

    List<Place> findAllByStatus(String status);

    //getTop8
    @Query(value="SELECT * FROM t_place p ORDER BY p.city_id ASC LIMIT 8", nativeQuery = true)
    List<Place> getTop8();
}
