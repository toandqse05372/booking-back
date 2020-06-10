
package com.capstone.booking.repository.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.ParkConverter;
import com.capstone.booking.entity.Park;
import com.capstone.booking.entity.dto.ImageDTO;
import com.capstone.booking.entity.dto.ParkDTO;
import com.capstone.booking.repository.ImageRepository;
import com.capstone.booking.repository.customRepository.ParkRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkRepositoryImpl implements ParkRepositoryCustom {
    private Integer totalItem;
    private long totalPage;
    private boolean searched = false;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private ParkConverter parkConverter;

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Output findByMultiParam(String name, String address, Long cityId,
                                   Long parkTypeId, Long limit, Long page) {
        boolean addedWhere = false;
        String queryStr = "select park0_.* from t_park park0_ ";
        String where = "";
        Integer stack = 1;
        int pageInt = Math.toIntExact(page);

        Map<String, Object> params = new HashMap<>();
        if(parkTypeId !=null && parkTypeId != 0){
            queryStr += "INNER join t_park_park_type ppt on park0_.id = ppt.park_id";
            if(stack > 1){
                where +=" and ";
            }
            where +=" ppt.park_type_id = :ptid ";
            addedWhere = true;
            params.put("ptid", parkTypeId);
            stack++;
        }

        if(name != null&& !name.equals("")){
            if(stack > 1){
                where +=" and ";
            }
            where +="park0_.name like :name ";
            addedWhere = true;
            params.put("name", name);
            stack++;
        }
        if(address != null && !address.equals("")){
            if(stack > 1){
                where +=" and ";
            }
            where +="park0_.address like :address ";
            addedWhere = true;
            params.put("address", address);
            stack++;
        }

        if(cityId !=null && cityId != 0){
            if(stack > 1){
                where +=" and ";
            }
            where +="park0_.city_id = :cid ";
            addedWhere = true;
            params.put("cid", cityId);
            stack++;
        }

        if(addedWhere){
            queryStr += " where ";
        }
        //String between ="";
        if(!searched){
            totalItem = queryPark(params, queryStr +where).size();
            totalPage = (totalItem % limit == 0) ? totalItem / limit : (totalItem / limit )+1;
        }
        params.put("from", (page - 1)*limit);
        stack++;
        params.put("limit", limit);
        stack++;
        where += "limit :from, :limit";

        Output output = new Output();
        output.setListResult(convertList(queryPark(params, queryStr +where)));
        output.setPage(pageInt);
        output.setTotalItems(totalItem);
        output.setTotalPage((int) totalPage);
        return output;
    }

    public List<ParkDTO> convertList (List<Park> parkList){
        List<ParkDTO> results = new ArrayList<>();
        for (Park item : parkList) {
            ParkDTO parkDTO = parkConverter.toDTO(item);
            //

            results.add(parkDTO);
        }
        return  results;
    }

    public List<Park> queryPark(Map<String, Object> params ,String sqlStr){
        Query query = entityManager.createNativeQuery(sqlStr, Park.class);
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