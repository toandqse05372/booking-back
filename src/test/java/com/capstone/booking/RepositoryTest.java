package com.capstone.booking;

import com.capstone.booking.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class RepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PlaceRepository placeRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CityRepository cityRepository;
    @Autowired
    GameRepository gameRepository;
    @Autowired
    TicketTypeRepository ticketTypeRepository;
    @Autowired
    OrderRepository orderRepository;

    @Test
    void nullRepoTest(){
        assertNotNull(userRepository);
        assertNotNull(placeRepository);
        assertNotNull(gameRepository);
        assertNotNull(cityRepository);
        assertNotNull(categoryRepository);
        assertNotNull(ticketTypeRepository);
        assertNotNull(orderRepository);
    }

    @Test
    void invalidIdTest(){
        final Long id = -1l;
        assertThrows(NoSuchElementException.class,() -> userRepository.findById(id).get());
        assertThrows(NoSuchElementException.class,() -> placeRepository.findById(id).get());
        assertThrows(NoSuchElementException.class,() -> cityRepository.findById(id).get());
        assertThrows(NoSuchElementException.class,() -> categoryRepository.findById(id).get());
        assertThrows(NoSuchElementException.class,() -> gameRepository.findById(id).get());
        assertThrows(NoSuchElementException.class,() -> ticketTypeRepository.findById(id).get());
        assertThrows(NoSuchElementException.class,() -> orderRepository.findById(id).get());
    }
}
