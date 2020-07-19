package com.capstone.booking.repository;

import com.capstone.booking.entity.ImagePlace;
import com.capstone.booking.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//query to image place table
public interface ImagePlaceRepository extends JpaRepository<ImagePlace, Long> {

    ImagePlace findByImageName(String imageName);

}
