package com.capstone.booking.repository.impl;

import com.capstone.booking.entity.TicketType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class TicketTypeRepositoryImplTest {

    @Mock
    private EntityManager mockEntityManager;

    private TicketTypeRepositoryImpl ticketTypeRepositoryImplUnderTest;

    @Mock
    Query query;

    @Before
    public void setUp() {
        initMocks(this);
        ticketTypeRepositoryImplUnderTest = new TicketTypeRepositoryImpl(mockEntityManager);
    }

    @Test
    public void testFindByPlaceId() {
        TicketType ticketType = new TicketType();
        ticketType.setId(1l);
        final List<TicketType> expectedResult = Arrays.asList(ticketType);
        // Setup
        when(mockEntityManager.createNativeQuery("select type.* from t_ticket_type type Left join " +
                "t_game_ticket_type gtt on gtt.ticket_type_id = type.id" +
                " Left join t_game g on gtt.game_id = g.id where type.place_id = :placeId " +
                "AND  g.status like 'ACTIVE' group by type.id", TicketType.class)).thenReturn(query);
        when(query.setParameter("placeId", 1)).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedResult);
        // Run the test
        final List<TicketType> result = ticketTypeRepositoryImplUnderTest.findByPlaceId(0L);

        // Verify the results
        Assertions.assertEquals(result, expectedResult);
    }

    @Test
    public void testFindByPlaceIdAndStatus() {
        // Setup
        TicketType ticketType = new TicketType();
        ticketType.setId(1l);
        final List<TicketType> expectedResult = Arrays.asList(ticketType);
        // Setup
        when(mockEntityManager.createNativeQuery("select type.* from t_ticket_type type " +
                "Left join t_game_ticket_type gtt " +
                "on gtt.ticket_type_id = type.id " +
                "Left join t_game g on gtt.game_id = g.id " +
                "where type.place_id = :placeId AND  g.status like 'ACTIVE' " +
                "AND type.status like :status group by type.id ", TicketType.class)).thenReturn(query);
        when(query.setParameter("placeId", 1)).thenReturn(query);
        when(query.setParameter("status", "status")).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedResult);

        // Run the test
        final List<TicketType> result = ticketTypeRepositoryImplUnderTest.findByPlaceIdAndStatus(0L, "status");

        // Verify the results
        Assert.assertEquals(expectedResult, result);
    }
}
