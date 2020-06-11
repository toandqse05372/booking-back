package com.capstone.booking.repository;
import com.capstone.booking.entity.Game;
import com.capstone.booking.entity.Park;
import com.capstone.booking.repository.customRepository.ParkRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkRepository extends JpaRepository<Park, Long>, ParkRepositoryCustom {
    //List<Park> findByGame(Game game);
}
