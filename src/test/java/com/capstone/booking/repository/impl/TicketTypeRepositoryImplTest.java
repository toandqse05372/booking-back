package com.capstone.booking.repository.impl;

import com.capstone.booking.entity.TicketType;
import com.capstone.booking.entity.VisitorType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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

    void mockQuery(String name, List<VisitorType> results) {
        Query mockedQuery = mock(Query.class);
        when(mockedQuery.getResultList()).thenReturn(results);
        when(mockedQuery.setParameter(Matchers.anyString(), Matchers.anyObject())).thenReturn(mockedQuery);
        when(this.ticketTypeRepositoryImplUnderTest.entityManager.createNamedQuery(name)).thenReturn(mockedQuery);
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
