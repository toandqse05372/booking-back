package com.capstone.booking.repository;
import com.capstone.booking.entity.Place;
import com.capstone.booking.repository.customRepository.PlaceRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//customer query to category table
public interface PlaceRepository extends JpaRepository<Place, Long>, PlaceRepositoryCustom {
    //find place by exact name
    Place findByName(String name);

    List<Place> findAllByStatus(String status);
}
