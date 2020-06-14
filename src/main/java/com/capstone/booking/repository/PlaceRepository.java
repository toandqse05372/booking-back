package com.capstone.booking.repository;
import com.capstone.booking.entity.Place;
import com.capstone.booking.repository.customRepository.PlaceRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long>, PlaceRepositoryCustom {
    //List<Place> findByGame(Game game);
}
