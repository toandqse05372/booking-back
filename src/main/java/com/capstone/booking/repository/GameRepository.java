package com.capstone.booking.repository;

import com.capstone.booking.entity.Game;
import com.capstone.booking.entity.Place;
import com.capstone.booking.repository.customRepository.GameRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//query to game table
public interface GameRepository extends JpaRepository<Game, Long>, GameRepositoryCustom {

    //find all by plae id
    List<Game> findByPlaceIdAndStatus(Long placeId, String status);

    //find by name and place
    Game findByGameNameAndPlace(String name, Place place);

    //get all game from active place
    @Query("select g from Game g where g.status like 'ACTIVE' and g.place.status like 'ACTIVE'")
    List<Game> findAll();

}
