package com.capstone.booking.repository.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.CityConverter;
import com.capstone.booking.entity.Category;
import com.capstone.booking.entity.City;
import com.capstone.booking.entity.dto.CityDTO;
import com.capstone.booking.repository.customRepository.CityRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityRepositoryImpl implements CityRepositoryCustom {

    private Integer totalItem = 0;
    private long totalPage = 1;
    private boolean searched = false;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    CityConverter cityConverter;

    public CityRepositoryImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public Output findByName(String name, Long limit, Long page) {
        boolean addedWhere = false;
        String count = "select count(c.id)";
        String getAll = "select c.*";
        String queryStr = " from t_city c ";
        String where = "";
        Integer stack = 1;
        int pageInt = Math.toIntExact(page);

        Map<String, Object> params = new HashMap<>();
        if (name != null && !name.equals("")) {
            if (stack > 1) {
                where += " and ";
            }
            where += "c.name like :cname ";
            addedWhere = true;
            params.put("cname", name);
            stack++;
        }

        if (addedWhere) {
            queryStr += " where ";
        }
        if (!searched) {
            totalItem = countCity(params, count + queryStr + where);
            totalPage = (totalItem % limit == 0) ? totalItem / limit : (totalItem / limit) + 1;
        }

        params.put("from", (page - 1) * limit);
        stack++;
        params.put("limit", limit);
        stack++;
        where += " limit :from, :limit";

        Output output = new Output();
        output.setListResult(convertList(queryCity(params, getAll + queryStr + where)));
        output.setPage(pageInt);
        output.setTotalItems(totalItem);
        output.setTotalPage((int) totalPage);
        return output;
    }

    public List<CityDTO> convertList(List<City> cityList) {
        List<CityDTO> results = new ArrayList<>();
        for (City item : cityList) {
            CityDTO cityDTO = cityConverter.toDTO(item);
            results.add(cityDTO);
        }
        return results;
    }

    public List<City> queryCity(Map<String, Object> params, String sqlStr) {
        Query query = entityManager.createNativeQuery(sqlStr, City.class);
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

    public int countCity(Map<String, Object> params, String sqlStr) {
        Query query = entityManager.createNativeQuery(sqlStr);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            query.setParameter(key, "%" + value + "%");
        }
        BigInteger counter = (BigInteger) query.getSingleResult();
        return counter.intValue() ;
    }
}