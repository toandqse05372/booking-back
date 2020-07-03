package com.capstone.booking.repository;
import com.capstone.booking.entity.Place;
import com.capstone.booking.repository.customRepository.PlaceRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long>, PlaceRepositoryCustom {
    Place findByName(String name);

    List<Place> findByCityId(Long cityId);
}
