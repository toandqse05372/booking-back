package com.capstone.booking.repository.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.PaymentMethodsConverter;
import com.capstone.booking.entity.PaymentMethods;
import com.capstone.booking.entity.dto.PaymentMethodsDTO;
import com.capstone.booking.repository.customRepository.PaymentMethodsCustom;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentMethodsRepositoryImpl implements PaymentMethodsCustom {

    private Integer totalItem;
    private long totalPage;
    private boolean searched = false;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private PaymentMethodsConverter methodsConverter;

    @Override
    public Output findByMulParam(String methodName, Long limit, Long page) {
        boolean addedWhere = false;
        String queryStr = "select pm.* from t_payment_methods pm ";
        String where = "";
        Integer stack = 1;
        int pageInt = Math.toIntExact(page);

        Map<String, Object> params = new HashMap<>();

        if (methodName != null && !methodName.equals("")) {
            if (stack > 1) {
                where += " and ";
            }
            where += "pm.method_name like :mname ";
            addedWhere = true;
            params.put("mname", methodName);
            stack++;
        }

        if (addedWhere) {
            queryStr += " where ";
        }
        if (!searched) {
            totalItem = queryPaymentMethods(params, queryStr + where).size();
            totalPage = (totalItem % limit == 0) ? totalItem / limit : (totalItem / limit) + 1;
        }
        params.put("from", (page - 1) * limit);
        stack++;
        params.put("limit", limit);
        stack++;
        where += " limit :from, :limit";

        Output output = new Output();
        output.setListResult(convertList(queryPaymentMethods(params, queryStr + where)));
        output.setPage(pageInt);
        output.setTotalItems(totalItem);
        output.setTotalPage((int) totalPage);
        return output;
    }

    public List<PaymentMethodsDTO> convertList(List<PaymentMethods> paymentMethods) {
        List<PaymentMethodsDTO> results = new ArrayList<>();
        for (PaymentMethods item : paymentMethods) {
            PaymentMethodsDTO methodsDTO = methodsConverter.toDTO(item);
            results.add(methodsDTO);
        }
        return results;
    }

    public List<PaymentMethods> queryPaymentMethods(Map<String, Object> params, String sqlStr) {
        Query query = entityManager.createNativeQuery(sqlStr, PaymentMethods.class);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (key.equals("from") || key.equals("limit")) {
                query.setParameter(key, value);
            } else
                query.setParameter(key, "%" + value + "%");
        }
        return query.getResultList();
    }
}
