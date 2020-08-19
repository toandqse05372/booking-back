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
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderRepositoryImpl implements OrderRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    OrderConverter orderConverter;

    private Integer totalItem = 0;
    private long totalPage = 1;

    public OrderRepositoryImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public Output findByStatus(String status, String code, Long placeId, Long page, Long limit) {
        boolean addedWhere = false;
        String count = "select count(o.id)";
        String getAll = "select o.*";
        String queryStr = " from t_order o ";
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

        if (placeId != null && placeId != 0) {
            if (stack > 1) {
                where += " and ";
            }
            where += "o.place_id = :placeId ";
            addedWhere = true;
            params.put("placeId", placeId);
            stack++;
        }

        if (addedWhere) {
            queryStr += " where ";
        }

        totalItem = countOrder(params, count + queryStr + where);
        totalPage = (totalItem % limit == 0) ? totalItem / limit : (totalItem / limit) + 1;

        params.put("from", (page - 1) * limit);
        stack++;
        params.put("limit", limit);
        stack++;
        where += "limit :from, :limit";

        Output output = new Output();
        output.setPage(Math.toIntExact(page));
        output.setTotalItems(totalItem);
        output.setTotalPage((int) totalPage);
        output.setListResult(convertList(queryOrder(params, getAll + queryStr + where)));
        return output;
    }


    public List<OrderDTO> convertList(List<Order> orderList) {
        List<OrderDTO> results = new ArrayList<>();
        for (Order item : orderList) {
            OrderDTO orderDTO = orderConverter.toDTOLite(item);
            results.add(orderDTO);
        }
        return results;
    }

    public List<Order> queryOrder(Map<String, Object> params, String sqlStr) {
        Query query = entityManager.createNativeQuery(sqlStr, Order.class);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (key.equals("placeId") || key.equals("status") | key.equals("from") || key.equals("limit")) {
                query.setParameter(key,  value );
            } else
                query.setParameter(key,  "%"+ value + "%");
        }
        return query.getResultList();
    }

    public int countOrder(Map<String, Object> params, String sqlStr) {
        Query query = entityManager.createNativeQuery(sqlStr);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (key.equals("placeId") || key.equals("status")) {
                query.setParameter(key,  value );
            } else
                query.setParameter(key,  "%"+ value + "%");
        }
        BigInteger counter = (BigInteger) query.getSingleResult();
        return counter.intValue() ;
    }
}