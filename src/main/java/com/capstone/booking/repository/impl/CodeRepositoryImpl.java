package com.capstone.booking.repository.impl;

import com.capstone.booking.entity.Code;
import com.capstone.booking.entity.VisitorType;
import com.capstone.booking.repository.customRepository.CodeCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class CodeRepositoryImpl implements CodeCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Code> findByVisitorTypeIdLimitTo(int limit, VisitorType visitorType) {
        return entityManager.createQuery("SELECT p FROM Code p WHERE p.visitorType like :visitorType ORDER BY p.id",
                Code.class).setParameter("visitorType", visitorType)
                .setMaxResults(limit)
                .getResultList();
    }

}
