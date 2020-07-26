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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CategoryRepositoryImpl implements CategoryCustom {

    private Integer totalItem = 0;
    private long totalPage = 1;

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
        String queryStr = "select pt.* from t_category pt ";
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

        List<Category> categories = queryCategory(params, queryStr + where);

//        params.put("from", (page - 1) * limit);
//        stack++;
//        params.put("limit", limit);
//        stack++;
//        where += " limit :from, :limit";
        List<Category> limitList = new ArrayList<>();
        if(categories.size() > 0){
            totalItem = categories.size();
            totalPage = (totalItem % limit == 0) ? totalItem / limit : (totalItem / limit) + 1;
            int range = totalItem >= limit ? (int) (page * limit) : totalItem;
            for(int i = (int) ((page - 1) * limit); i < range; i++){
                limitList.add(categories.get(i));
            }
        }

        Output output = new Output();
        output.setListResult(convertList(limitList));
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
                query.setParameter(key, value + "%");
        }
        return query.getResultList();
    }
}
