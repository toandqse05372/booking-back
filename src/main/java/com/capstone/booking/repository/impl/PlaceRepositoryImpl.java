
package com.capstone.booking.repository.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.PlaceConverter;
import com.capstone.booking.entity.Place;
import com.capstone.booking.entity.dto.PlaceDTO;
import com.capstone.booking.repository.ImagePlaceRepository;
import com.capstone.booking.repository.customRepository.PlaceRepositoryCustom;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.*;

public class PlaceRepositoryImpl implements PlaceRepositoryCustom {

    private Integer totalItem = 0;
    private long totalPage = 1;
    private boolean searched = false;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    PlaceConverter placeConverter;

    public PlaceRepositoryImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public Output findByMultiParam(String name, String address, Long cityId, Long categoryId, Long limit, Long page) {
        boolean addedWhere = false;
        String count = "select count(place0_.id)";
        String getAll = "select place0_.*";
        String queryStr = " from t_place place0_ ";
        String where = "";
        Integer stack = 1;
        int pageInt = Math.toIntExact(page);

        Map<String, Object> params = new HashMap<>();
        if (categoryId != null && categoryId != 0) {
            queryStr += "INNER join t_place_category ppt on place0_.id = ppt.place_id";
            if (stack > 1) {
                where += " and ";
            }
            where += " ppt.category_id = :ptid ";
            addedWhere = true;
            params.put("ptid", categoryId);
            stack++;
        }

        if (name != null && !name.equals("")) {
            if (stack > 1) {
                where += " and ";
            }
            where += "place0_.name like :name ";
            addedWhere = true;
            params.put("name", name);
            stack++;
        }
        if (address != null && !address.equals("")) {
            if (stack > 1) {
                where += " and ";
            }
            where += "place0_.address like :address ";
            addedWhere = true;
            params.put("address", address);
            stack++;
        }

        if (cityId != null && cityId != 0) {
            if (stack > 1) {
                where += " and ";
            }
            where += "place0_.city_id = :cid ";
            addedWhere = true;
            params.put("cid", cityId);
            stack++;
        }

        if (addedWhere) {
            queryStr += " where ";
        }
        if (!searched) {
            totalItem = countPlace(params, count + queryStr + where , "CMS");
            totalPage = (totalItem % limit == 0) ? totalItem / limit : (totalItem / limit) + 1;
        }
        params.put("from", (page - 1) * limit);
        stack++;
        params.put("limit", limit);
        stack++;
        where += "limit :from, :limit";

        Output output = new Output();
        output.setListResult(convertList(queryPlace(params, getAll + queryStr + where)));
        output.setPage(pageInt);
        output.setTotalItems(totalItem);
        output.setTotalPage((int) totalPage);
        return output;
    }

    @Override
    public Output findByMultiParamForClient(String name, Long minValue, Long maxValue, List<Long> cityId,
                                            List<Long> categoryId, Long limit, Long page) {
        boolean addedWhere = false;
        String count = "select count(place0_.id)";
        String getAll = "select place0_.*";
        String queryStr = " from t_place place0_ ";
        String where = "";
        Integer stack = 1;
        int pageInt = Math.toIntExact(page);

        stack++;
        where += " place0_.status like 'ACTIVE' ";
        Map<String, Object> params = new HashMap<>();
        if (categoryId != null && categoryId.size() > 0) {
            queryStr += "INNER join t_place_category ppt on place0_.id = ppt.place_id";
            if (stack > 1) {
                where += " and ";
            }
            where += "ppt.category_id in ( ";
            for (int i = 0; i < categoryId.size(); i++) {
                where += categoryId.get(i);
                if(i < categoryId.size() - 1){
                    where += ",";
                }
            }
            where += ") ";
            addedWhere = true;
            stack++;
        }

        if (name != null && !name.equals("")) {
            if (stack > 1) {
                where += " and ";
            }
            where += "place0_.name like :name ";
            addedWhere = true;
            params.put("name", name);
            stack++;
        }

        if (minValue != null && minValue >= 0 && maxValue > minValue) {
            if (stack > 1) {
                where += " and ";
            }
            where += "place0_.basic_price BETWEEN  :minValue and :maxValue ";
            addedWhere = true;
            params.put("minValue", minValue);
            params.put("maxValue", maxValue);
            stack++;
        }


        if (cityId != null && cityId.size() > 0) {
            if (stack > 1) {
                where += " and ";
            }
            where += "place0_.city_id in ( ";
            for (int i = 0; i < cityId.size(); i++) {
                where += cityId.get(i);
                if(i < cityId.size() - 1){
                    where += ",";
                }
            }
            where += ") ";

            addedWhere = true;
            stack++;
        }

        where += "and place0_.id \n" +
                "in ( select tt.place_id from t_ticket_type tt \n" +
                "inner join t_visitor_type vt on tt.id = vt.ticket_type_id )\n" +
                "group by place0_.id ";

        if (addedWhere) {
            queryStr += " where ";
        }

        //String between ="";
        if (!searched) {
            totalItem = countPlace(params, count + queryStr + where, "client");
            totalPage = (totalItem % limit == 0) ? totalItem / limit : (totalItem / limit) + 1;
        }
        params.put("from", (page - 1) * limit);
        stack++;
        params.put("limit", limit);
        stack++;
        where += "limit :from, :limit";

        Output output = new Output();
        output.setListResult(convertList(queryPlace(params, getAll + queryStr + where)));
        output.setPage(pageInt);
        output.setTotalItems(totalItem);
        output.setTotalPage((int) totalPage);
        return output;
    }

    public List<PlaceDTO> convertList(List<Place> placeList) {
        List<PlaceDTO> results = new ArrayList<>();
        for (Place item : placeList) {
            PlaceDTO placeDTO = placeConverter.toDTO(item);
            results.add(placeDTO);
        }
        return results;
    }

    public List<Place> queryPlace(Map<String, Object> params, String sqlStr) {
        Query query = entityManager.createNativeQuery(sqlStr, Place.class);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (key.equals("from") || key.equals("limit") || key.contains("cid") || key.contains("ptid")
                    || key.equals("minValue") || key.equals("maxValue")) {
                query.setParameter(key, value);
            } else
                query.setParameter(key, "%" + value + "%");
        }
        return query.getResultList();
    }

    public int countPlace(Map<String, Object> params, String sqlStr, String page) {
        if(!page.equals("CMS")){
            sqlStr = "select count(*) from ("+sqlStr+") placecount";
        }
        Query query = entityManager.createNativeQuery(sqlStr);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (key.contains("cid") || key.contains("ptid") || key.equals("minValue") || key.equals("maxValue")) {
                query.setParameter(key, value);
            } else
                query.setParameter(key, "%" + value + "%");
        }
        BigInteger counter = (BigInteger) query.getSingleResult();
        return counter.intValue() ;
    }


}