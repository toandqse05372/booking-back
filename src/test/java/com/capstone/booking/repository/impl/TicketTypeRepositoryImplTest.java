package com.capstone.booking.repository.impl;

import com.capstone.booking.entity.TicketType;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TicketTypeRepositoryImplTest {

    private TicketTypeRepositoryImpl ticketTypeRepositoryImplUnderTest;

    @Before
    public void setUp() {
        ticketTypeRepositoryImplUnderTest = new TicketTypeRepositoryImpl();
        ticketTypeRepositoryImplUnderTest.entityManager = mock(EntityManager.class);
    }

    @Test
    public void testFindByPlaceId() {
        // Setup
        when(ticketTypeRepositoryImplUnderTest.entityManager.createNativeQuery("s", TicketType.class)).thenReturn(null);

        // Run the test
        final List<TicketType> result = ticketTypeRepositoryImplUnderTest.findByPlaceId(0L);

        // Verify the results
    }

    @Test
    public void testFindByPlaceIdAndStatus() {
        // Setup
        when(ticketTypeRepositoryImplUnderTest.entityManager.createNativeQuery("s", TicketType.class)).thenReturn(null);

        // Run the test
        final List<TicketType> result = ticketTypeRepositoryImplUnderTest.findByPlaceIdAndStatus(0L, "status");

        // Verify the results
    }
}
