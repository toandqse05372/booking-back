package com.capstone.booking.repository;

import com.capstone.booking.entity.Game;
import com.capstone.booking.repository.customRepository.GameRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;



public interface GameRepository extends JpaRepository<Game, Long>, GameRepositoryCustom {
    Game findOneByGameName(String name);

    Game findByGameName(String name);

}
