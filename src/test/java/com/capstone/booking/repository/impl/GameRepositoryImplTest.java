package com.capstone.booking.repository.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.GameConverter;
import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.GameDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GameRepositoryImplTest {

    @Mock
    private EntityManager mockEntityManager;

    private GameRepositoryImpl gameRepositoryImplUnderTest;

    @Mock
    Query query;

    @Before
    public void setUp() {
        initMocks(this);
        gameRepositoryImplUnderTest = new GameRepositoryImpl(mockEntityManager);
        gameRepositoryImplUnderTest.gameConverter = mock(GameConverter.class);
    }

    @Test
    public void testFindByMulParam() {
        // Setup
        final Output expectedResult = new Output();
        expectedResult.setPage(1);
        expectedResult.setTotalPage(1);
        expectedResult.setListResult(Arrays.asList());
        expectedResult.setTotalItems(1);

        BigInteger counter = BigInteger.valueOf(1);
        when(mockEntityManager.createNativeQuery("select count(game0_.id) from t_game game0_ INNER join t_place p on p.id = game0_.place_id where  p.status like 'ACTIVE' and p.name like :pname and game0_.game_name like :gname ")).thenReturn(query);
        when(query.setParameter("gname","gameName")).thenReturn(query);
        when(query.setParameter("pname","placeName")).thenReturn(query);
        when(query.getSingleResult()).thenReturn(counter);
        when(mockEntityManager.createNativeQuery("select game0_.* from t_game game0_ INNER join t_place p on p.id = game0_.place_id where  p.status like 'ACTIVE' and p.name like :pname and game0_.game_name like :gname  limit :from, :limit", Game.class)).thenReturn(query);
        when(query.setParameter("from",1)).thenReturn(query);
        when(query.setParameter("limit",1)).thenReturn(query);
        when(query.getResultList()).thenReturn(Arrays.asList());

        // Run the test
        final Output result = gameRepositoryImplUnderTest.findByMulParam("gameName", "placeName", 1L, 1L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testFindByPlaceId() {
        // Setup
        final Output expectedResult = new Output();
        expectedResult.setPage(1);
        expectedResult.setTotalPage(1);
        expectedResult.setListResult(Arrays.asList());
        expectedResult.setTotalItems(1);

        BigInteger counter = BigInteger.valueOf(1);
        when(mockEntityManager.createNativeQuery("select count(game0_.id) from t_game game0_  where game0_.place_id = :id ")).thenReturn(query);
        when(query.setParameter("id",1)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(counter);
        when(mockEntityManager.createNativeQuery("select game0_.* from t_game game0_  where game0_.place_id = :id  limit :from, :limit", Game.class)).thenReturn(query);
        when(query.setParameter("from",1)).thenReturn(query);
        when(query.setParameter("limit",1)).thenReturn(query);
        when(query.getResultList()).thenReturn(Arrays.asList());

        // Run the test
        final Output result = gameRepositoryImplUnderTest.findByPlaceId(1L, 1L, 1L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testConvertList() {
        // Setup
        final Game game = new Game();
        game.setGameName("gameName");
        game.setGameDescription("gameDescription");
        game.setStatus("status");

        final Place place = new Place();
        place.setName("name");
        place.setPlaceKey("placeKey");
        place.setAddress("address");
        place.setDetailDescription("detailDescription");
        place.setShortDescription("shortDescription");
        place.setMail("mail");
        place.setPhoneNumber("phoneNumber");
        place.setStatus("status");
        place.setLocation("location");
        place.setCancelPolicy("cancelPolicy");
        game.setPlace(place);
        final List<Game> gameList = Arrays.asList(game);
        final GameDTO gameDTO = new GameDTO();
        gameDTO.setGameName("gameName");
        gameDTO.setGameDescription("gameDescription");
        gameDTO.setTicketTypeName(new HashSet<>(Arrays.asList("value")));
        gameDTO.setPlaceId(0L);
        gameDTO.setPlaceName("placeName");
        gameDTO.setStatus("status");
        final List<GameDTO> expectedResult = Arrays.asList(gameDTO);
        when(gameRepositoryImplUnderTest.gameConverter.toDTO(game)).thenReturn(gameDTO);

        // Run the test
        final List<GameDTO> result = gameRepositoryImplUnderTest.convertList(gameList);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testQueryGame() {
        // Setup
        final Map<String, Object> params = new HashMap<>();
        final Game game = new Game();
        game.setGameName("gameName");
        game.setGameDescription("gameDescription");
        game.setStatus("status");

        final Place place = new Place();
        place.setName("name");
        place.setPlaceKey("placeKey");
        place.setAddress("address");
        place.setDetailDescription("detailDescription");
        place.setShortDescription("shortDescription");
        place.setMail("mail");
        place.setPhoneNumber("phoneNumber");
        place.setStatus("status");
        place.setLocation("location");
        place.setCancelPolicy("cancelPolicy");
        game.setPlace(place);
        final List<Game> expectedResult = Arrays.asList(game);
        when(mockEntityManager.createNativeQuery("select * from t_game", Game.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedResult);

        // Run the test
        final List<Game> result = gameRepositoryImplUnderTest.queryGame(params, "select * from t_game");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testCountGame() {
        // Setup
        final Map<String, Object> params = new HashMap<>();
        when(mockEntityManager.createNativeQuery("select count(pt.id) from t_game pt")).thenReturn(query);
        BigInteger expectedResult = BigInteger.valueOf(0);
        when(query.getSingleResult()).thenReturn(expectedResult);
        // Run the test
        final int result = gameRepositoryImplUnderTest.countGame(params, "select count(pt.id) from t_game pt");

        // Verify the results
        assertThat(result).isEqualTo(0);
    }
}
