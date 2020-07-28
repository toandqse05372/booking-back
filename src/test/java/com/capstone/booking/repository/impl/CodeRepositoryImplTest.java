package com.capstone.booking.repository.impl;

import com.capstone.booking.entity.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CodeRepositoryImplTest {

    @Mock
    private EntityManager mockEntityManager;

    private CodeRepositoryImpl codeRepositoryImplUnderTest;

    @Mock
    TypedQuery query;

    @Before
    public void setUp() {
        initMocks(this);
        codeRepositoryImplUnderTest = new CodeRepositoryImpl(mockEntityManager);
    }

    @Test
    public void testFindByVisitorTypeIdLimitTo() {
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
        game.setPlace(place);
        ticketType.setGame(new HashSet<>(Arrays.asList(game)));
        visitorType.setTicketType(ticketType);
        final Code code = new Code();
        code.setCode("code");
        code.setVisitorType(visitorType);
        visitorType.setCode(new HashSet<>(Arrays.asList(code)));


        final List<Code> expectedResult = Arrays.asList(code);
        when(mockEntityManager.createQuery("SELECT p FROM Code p WHERE p.visitorType like :visitorType ORDER BY p.id", Code.class)).thenReturn(query);
        when(query.setParameter("visitorType", visitorType)).thenReturn(query);
        when(query.setMaxResults(1)).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedResult);

        // Run the test
        final List<Code> result = codeRepositoryImplUnderTest.findByVisitorTypeIdLimitTo(1, visitorType);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
