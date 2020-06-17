package com.capstone.booking.repository.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.PlaceTypeConverter;
import com.capstone.booking.entity.PlaceType;
import com.capstone.booking.entity.dto.PlaceTypeDTO;
import com.capstone.booking.repository.customRepository.PlaceTypeCustom;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaceTypeRepositoryImpl implements PlaceTypeCustom {

    private Integer totalItem;
    private long totalPage;
    private boolean searched = false;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private PlaceTypeConverter placeTypeConverter;

    @Override
    public Output findByMulParam(String typeName, Long limit, Long page) {
        boolean addedWhere = false;
        String queryStr = "select pt.* from t_place_type pt ";
        String where = "";
        Integer stack = 1;
        int pageInt = Math.toIntExact(page);

        Map<String, Object> params = new HashMap<>();

        if (typeName != null && !typeName.equals("")) {
            if (stack > 1) {
                where += " and ";
            }
            where += "pt.type_name like :tname ";
            addedWhere = true;
            params.put("tname", typeName);
            stack++;
        }

        if (addedWhere) {
            queryStr += " where ";
        }
        if (!searched) {
            totalItem = queryPlaceType(params, queryStr + where).size();
            totalPage = (totalItem % limit == 0) ? totalItem / limit : (totalItem / limit) + 1;
        }
        params.put("from", (page - 1) * limit);
        stack++;
        params.put("limit", limit);
        stack++;
        where += " limit :from, :limit";

        Output output = new Output();
        output.setListResult(convertList(queryPlaceType(params, queryStr + where)));
        output.setPage(pageInt);
        output.setTotalItems(totalItem);
        output.setTotalPage((int) totalPage);
        return output;
    }

    public List<PlaceTypeDTO> convertList(List<PlaceType> placeTypes) {
        List<PlaceTypeDTO> results = new ArrayList<>();
        for (PlaceType item : placeTypes) {
            PlaceTypeDTO typeDTO = placeTypeConverter.toDTO(item);
            results.add(typeDTO);
        }
        return results;
    }

    public List<PlaceType> queryPlaceType(Map<String, Object> params, String sqlStr) {
        Query query = entityManager.createNativeQuery(sqlStr, PlaceType.class);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (key.equals("id") || key.equals("from") || key.equals("limit")) {
                query.setParameter(key, value);
            } else
                query.setParameter(key, "%" + value + "%");
        }
        return query.getResultList();
    }
}
