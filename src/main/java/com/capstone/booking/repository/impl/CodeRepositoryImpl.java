package com.capstone.booking.repository.impl;

import com.capstone.booking.entity.Code;
import com.capstone.booking.entity.VisitorType;
import com.capstone.booking.repository.customRepository.CodeCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

public class CodeRepositoryImpl implements CodeCustom {

    @PersistenceContext
    private EntityManager entityManager;

    public CodeRepositoryImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public List<Code> findByVisitorTypeIdLimitTo(int limit, VisitorType visitorType, Date date) {
        return entityManager.createQuery("SELECT p FROM Code p WHERE p.visitorType like :visitorType " +
                        "And p.redemptionDate = :date ORDER BY p.id",
                Code.class).setParameter("visitorType", visitorType).setParameter("date", date)
                .setMaxResults(limit)
                .getResultList();
    }
}
