package com.capstone.booking.repository.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.OrderConverter;
import com.capstone.booking.entity.Order;
import com.capstone.booking.entity.dto.OrderDTO;
import com.capstone.booking.repository.customRepository.OrderRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderRepositoryImpl implements OrderRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    OrderConverter orderConverter;

    public OrderRepositoryImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public Output findByStatus(String status, String code) {
        boolean addedWhere = false;
        String queryStr = "select o.* from t_order o ";
        String where = "";
        Integer stack = 1;

        Map<String, Object> params = new HashMap<>();

        if (status != null && !status.equals("")) {
            if (stack > 1) {
                where += " and ";
            }
            where += "o.status like :status ";
            addedWhere = true;
            params.put("status", status);
            stack++;
        }

        if (code != null && !code.equals("")) {
            if (stack > 1) {
                where += " and ";
            }
            where += "o.order_code like :code ";
            addedWhere = true;
            params.put("code", code);
            stack++;
        }

        if (addedWhere) {
            queryStr += " where ";
        }

        Output output = new Output();
        output.setListResult(convertList(queryOrder(params, queryStr + where)));
        return output;
    }


    public List<OrderDTO> convertList(List<Order> orderList) {
        List<OrderDTO> results = new ArrayList<>();
        for (Order item : orderList) {
            OrderDTO orderDTO = orderConverter.toDTO(item);
            results.add(orderDTO);
        }
        return results;
    }

    public List<Order> queryOrder(Map<String, Object> params, String sqlStr) {
        Query query = entityManager.createNativeQuery(sqlStr, Order.class);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (key.equals("code")) {
                query.setParameter(key, "%"+ value + "%");
            } else
                query.setParameter(key,  value + "%");
        }
        return query.getResultList();
    }
}