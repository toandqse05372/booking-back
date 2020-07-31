package com.capstone.booking.repository.impl;

import com.capstone.booking.entity.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class VisitorTypeRepositoryImplTest {

    @Mock
    private EntityManager mockEntityManager;

    private VisitorTypeRepositoryImpl visitorTypeRepositoryImplUnderTest;

    @Mock
    TypedQuery typedQuery;

    @Before
    public void setUp() {
        initMocks(this);
        visitorTypeRepositoryImplUnderTest = new VisitorTypeRepositoryImpl(mockEntityManager);
    }

    @Test
    public void testFindByPlaceIdAndBasic() {
        // Setup
        final VisitorType expectedResult = new VisitorType();
        expectedResult.setTypeName("typeName");
        expectedResult.setTypeKey("typeKey");
        expectedResult.setPrice(0);
        expectedResult.setBasicType(false);
        expectedResult.setStatus("status");
        final TicketType ticketType = new TicketType();
        ticketType.setTypeName("typeName");
        ticketType.setPlaceId(0L);
        ticketType.setStatus("status");
        ticketType.setVisitorType(new HashSet<>(Arrays.asList(new VisitorType())));
        final Game game = new Game();
        game.setGameName("gameName");
        game.setGameDescription("gameDescription");
        game.setStatus("status");
        game.setTicketTypes(new HashSet<>(Arrays.asList(new TicketType())));
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
        place.setId(0l);
        game.setPlace(place);
        ticketType.setGame(new HashSet<>(Arrays.asList(game)));
        expectedResult.setTicketType(ticketType);

        when(mockEntityManager.createQuery("SELECT vtINNER FROM  VisitorType vtINNER JOIN TicketType tt ON vtINNER.ticketType = tt.id WHERE vtINNER.isBasicType = :isBasic and tt.placeId = :placeId", VisitorType.class)).thenReturn(typedQuery);
        when(typedQuery.setParameter("isBasic", true)).thenReturn(typedQuery);
        when(typedQuery.setParameter("placeId", place.getId())).thenReturn(typedQuery);
        when((typedQuery.getResultList())).thenReturn(Arrays.asList(expectedResult));

        // Run the test
        final VisitorType result = visitorTypeRepositoryImplUnderTest.findByPlaceIdAndBasic(0L, true);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testFindByPlaceId() {
        // Setup
        final VisitorType visitorType = new VisitorType();
        visitorType.setTypeName("typeName");
        visitorType.setTypeKey("typeKey");
        visitorType.setPrice(0);
        visitorType.setBasicType(false);
        visitorType.setStatus("status");
        final TicketType ticketType = new TicketType();
        ticketType.setTypeName("typeName");
        ticketType.setPlaceId(0L);
        ticketType.setStatus("status");
        ticketType.setVisitorType(new HashSet<>(Arrays.asList(new VisitorType())));
        final Game game = new Game();
        game.setGameName("gameName");
        game.setGameDescription("gameDescription");
        game.setStatus("status");
        game.setTicketTypes(new HashSet<>(Arrays.asList(new TicketType())));
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
        place.setId(0l);
        game.setPlace(place);
        ticketType.setGame(new HashSet<>(Arrays.asList(game)));
        visitorType.setTicketType(ticketType);
        final List<VisitorType> expectedResult = Arrays.asList(visitorType);
        when(mockEntityManager.createQuery("SELECT vtINNER FROM  VisitorType vtINNER JOIN TicketType tt ON vtINNER.ticketType = tt.id WHERE tt.placeId = :placeId", VisitorType.class)).thenReturn(typedQuery);
        when(typedQuery.setParameter("placeId", place.getId())).thenReturn(typedQuery);
        when((typedQuery.getResultList())).thenReturn(expectedResult);
        // Run the test
        final List<VisitorType> result = visitorTypeRepositoryImplUnderTest.findByPlaceId(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
