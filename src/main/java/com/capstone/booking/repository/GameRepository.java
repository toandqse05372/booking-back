package com.capstone.booking.repository;

import com.capstone.booking.entity.Game;
import com.capstone.booking.entity.Place;
import com.capstone.booking.repository.customRepository.GameRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface GameRepository extends JpaRepository<Game, Long>, GameRepositoryCustom {

    List<Game> findByPlaceId(Long placeId);

    Game findByGameNameAndPlace(String name, Place place);

    @Query("select g from Game g where g.status like 'ACTIVE' and g.place.status like 'ACTIVE'")
    List<Game> findAll();

}
