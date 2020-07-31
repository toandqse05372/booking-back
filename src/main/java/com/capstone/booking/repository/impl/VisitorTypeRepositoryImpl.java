package com.capstone.booking.repository.impl;

import com.capstone.booking.entity.VisitorType;
import com.capstone.booking.repository.customRepository.VisitorTypeRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class VisitorTypeRepositoryImpl implements VisitorTypeRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    public VisitorTypeRepositoryImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public VisitorType findByPlaceIdAndBasic(long placeId, boolean isBasic) {
        return entityManager.createQuery("SELECT vtINNER FROM  VisitorType vt" +
                        "INNER JOIN TicketType tt ON vtINNER.ticketType = tt.id " +
                        "WHERE vtINNER.isBasicType = :isBasic and tt.placeId = :placeId",
                VisitorType.class).setParameter("isBasic", isBasic)
                .setParameter("placeId", placeId)
                .getResultList().get(0);
    }

    @Override
    public List<VisitorType> findByPlaceId(long placeId) {
        return entityManager.createQuery("SELECT vtINNER FROM  VisitorType vt" +
                        "INNER JOIN TicketType tt ON vtINNER.ticketType = tt.id " +
                        "WHERE tt.placeId = :placeId",
                VisitorType.class)
                .setParameter("placeId", placeId)
                .getResultList();
    }
}
