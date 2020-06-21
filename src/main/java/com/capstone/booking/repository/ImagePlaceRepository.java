package com.capstone.booking.repository;

import com.capstone.booking.entity.ImagePlace;
import com.capstone.booking.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagePlaceRepository extends JpaRepository<ImagePlace, Long> {
    List<ImagePlace> findByPlace(Place place);

    ImagePlace findByImageName(String imageName);

}
