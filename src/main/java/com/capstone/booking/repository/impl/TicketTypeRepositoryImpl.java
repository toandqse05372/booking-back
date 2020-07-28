package com.capstone.booking.repository.impl;

import com.capstone.booking.entity.TicketType;
import com.capstone.booking.repository.customRepository.TicketTypeCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class TicketTypeRepositoryImpl implements TicketTypeCustom {

    @PersistenceContext
    EntityManager entityManager;

    public TicketTypeRepositoryImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public List<TicketType> findByPlaceId(Long placeId) {
        String queryStr = "select type.* from t_ticket_type type " +
                "Left join t_game_ticket_type gtt on gtt.ticket_type_id = type.id " +
                "Left join t_game g on gtt.game_id = g.id where type.place_id = :placeId " +
                "AND  g.status like 'ACTIVE' group by type.id";
        Query query = entityManager.createNativeQuery(queryStr, TicketType.class);
        query.setParameter("placeId", placeId);
        return query.getResultList();
    }

    @Override
    public List<TicketType> findByPlaceIdAndStatus(Long placeId, String status) {
        String queryStr = "select type.* from t_ticket_type type " +
                "Left join t_game_ticket_type gtt on gtt.ticket_type_id = type.id " +
                "Left join t_game g on gtt.game_id = g.id where type.place_id = :placeId " +
                "AND  g.status like 'ACTIVE' AND type.status like :status group by type.id ";
        Query query = entityManager.createNativeQuery(queryStr, TicketType.class);
        query.setParameter("placeId", placeId);
        query.setParameter("status", status);
        return query.getResultList();
    }

}
