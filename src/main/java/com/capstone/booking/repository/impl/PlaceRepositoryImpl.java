
package com.capstone.booking.repository.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.PlaceConverter;
import com.capstone.booking.entity.Place;
import com.capstone.booking.entity.dto.PlaceDTO;
import com.capstone.booking.repository.ImagePlaceRepository;
import com.capstone.booking.repository.customRepository.PlaceRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaceRepositoryImpl implements PlaceRepositoryCustom {
    private Integer totalItem;
    private long totalPage;
    private boolean searched = false;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private PlaceConverter placeConverter;

    @Autowired
    private ImagePlaceRepository imageRepository;

    @Override
    public Output findByMultiParam(String name, String address, Long cityId,
                                   Long categoryId, Long limit, Long page) {
        boolean addedWhere = false;
        String queryStr = "select place0_.* from t_place place0_ ";
        String where = "";
        Integer stack = 1;
        int pageInt = Math.toIntExact(page);

        Map<String, Object> params = new HashMap<>();
        if(categoryId !=null && categoryId != 0){
            queryStr += "INNER join t_place_place_type ppt on place0_.id = ppt.place_id";
            if(stack > 1){
                where +=" and ";
            }
            where +=" ppt.place_type_id = :ptid ";
            addedWhere = true;
            params.put("ptid", categoryId);
            stack++;
        }

        if(name != null&& !name.equals("")){
            if(stack > 1){
                where +=" and ";
            }
            where +="place0_.name like :name ";
            addedWhere = true;
            params.put("name", name);
            stack++;
        }
        if(address != null && !address.equals("")){
            if(stack > 1){
                where +=" and ";
            }
            where +="place0_.address like :address ";
            addedWhere = true;
            params.put("address", address);
            stack++;
        }

        if(cityId !=null && cityId != 0){
            if(stack > 1){
                where +=" and ";
            }
            where +="place0_.city_id = :cid ";
            addedWhere = true;
            params.put("cid", cityId);
            stack++;
        }

        if(addedWhere){
            queryStr += " where ";
        }
        //String between ="";
        if(!searched){
            totalItem = queryPlace(params, queryStr +where).size();
            totalPage = (totalItem % limit == 0) ? totalItem / limit : (totalItem / limit )+1;
        }
        params.put("from", (page - 1)*limit);
        stack++;
        params.put("limit", limit);
        stack++;
        where += "limit :from, :limit";

        Output output = new Output();
        output.setListResult(convertList(queryPlace(params, queryStr +where)));
        output.setPage(pageInt);
        output.setTotalItems(totalItem);
        output.setTotalPage((int) totalPage);
        return output;
    }

    public List<PlaceDTO> convertList (List<Place> placeList){
        List<PlaceDTO> results = new ArrayList<>();
        for (Place item : placeList) {
            PlaceDTO placeDTO = placeConverter.toDTO(item);
            //

            results.add(placeDTO);
        }
        return  results;
    }

    public List<Place> queryPlace(Map<String, Object> params , String sqlStr){
        Query query = entityManager.createNativeQuery(sqlStr, Place.class);
        for(Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if(key.equals("id") || key.equals("from")|| key.equals("limit")){
                query.setParameter(key, value);
            }else
                query.setParameter(key, value+"%");
        }
        return query.getResultList();
    }


}