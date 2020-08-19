package com.capstone.booking.repository.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.CategoryConverter;
import com.capstone.booking.entity.Category;
import com.capstone.booking.entity.dto.CategoryDTO;
import com.capstone.booking.repository.customRepository.CategoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CategoryRepositoryImpl implements CategoryCustom {

    private Integer totalItem = 0;
    private long totalPage = 1;
    private boolean searched = false;

    @PersistenceContext
    EntityManager entityManager;

    public CategoryRepositoryImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Autowired
    CategoryConverter categoryConverter;

    @Override
    public Output findByMulParam(String typeName, Long limit, Long page) {
        boolean addedWhere = false;
        String count = "select count(pt.id)";
        String getAll = "select pt.*";
        String queryStr = " from t_category pt ";
        String where = "";
        Integer stack = 1;
        int pageInt = Math.toIntExact(page);

        Map<String, Object> params = new HashMap<>();

        if (typeName != null && !typeName.equals("")) {
            if (stack > 1) {
                where += " and ";
            }
            where += "pt.type_name like :cname ";
            addedWhere = true;
            params.put("cname", typeName);
            stack++;
        }

        if (addedWhere) {
            queryStr += " where ";
        }

        if (!searched) {
            totalItem = countCategory(params, count + queryStr + where);
            totalPage = (totalItem % limit == 0) ? totalItem / limit : (totalItem / limit) + 1;
        }

        params.put("from", (page - 1) * limit);
        stack++;
        params.put("limit", limit);
        stack++;
        where += " limit :from, :limit";

        Output output = new Output();
        output.setListResult(convertList(queryCategory(params, getAll + queryStr + where)));
        output.setPage(pageInt);
        output.setTotalItems(totalItem);
        output.setTotalPage((int) totalPage);
        return output;
    }

    public List<CategoryDTO> convertList(List<Category> categories) {
        List<CategoryDTO> results = new ArrayList<>();
        for (Category item : categories) {
            CategoryDTO typeDTO = categoryConverter.toDTO(item);
            results.add(typeDTO);
        }
        return results;
    }

    public List<Category> queryCategory(Map<String, Object> params, String sqlStr) {
        Query query = entityManager.createNativeQuery(sqlStr, Category.class);
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

    public int countCategory(Map<String, Object> params, String sqlStr) {
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
