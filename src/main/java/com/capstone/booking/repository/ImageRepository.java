package com.capstone.booking.repository;

import com.capstone.booking.entity.Image;
import com.capstone.booking.entity.Park;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByPark(Park park);

    Image findByImageLink(String imageLink);

}
