package com.capstone.booking.repository.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.CategoryConverter;
import com.capstone.booking.entity.Category;
import com.capstone.booking.entity.dto.CategoryDTO;
import com.capstone.booking.repository.customRepository.CategoryCustom;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryRepositoryImpl implements CategoryCustom {

    private Integer totalItem;
    private long totalPage;
    private boolean searched = false;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private CategoryConverter categoryConverter;

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
            totalItem = queryCategory(params, queryStr + where).size();
            totalPage = (totalItem % limit == 0) ? totalItem / limit : (totalItem / limit) + 1;
        }
        params.put("from", (page - 1) * limit);
        stack++;
        params.put("limit", limit);
        stack++;
        where += " limit :from, :limit";

        Output output = new Output();
        output.setListResult(convertList(queryCategory(params, queryStr + where)));
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
            if (key.equals("id") || key.equals("from") || key.equals("limit")) {
                query.setParameter(key, value);
            } else
                query.setParameter(key, value + "%");
        }
        return query.getResultList();
    }
}
