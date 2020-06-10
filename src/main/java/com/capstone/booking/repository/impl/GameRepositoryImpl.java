package com.capstone.booking.repository.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.GameConverter;
import com.capstone.booking.common.converter.ParkConverter;
import com.capstone.booking.entity.Game;
import com.capstone.booking.entity.dto.GameDTO;
import com.capstone.booking.repository.customRepository.GameRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameRepositoryImpl implements GameRepositoryCustom {
    private Integer totalItem;
    private long totalPage;
    private boolean searched = false;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private GameConverter gameConverter;



    @Override
    public Output findByMulParam(String gameName, Long parkId, Long limit, Long page) {
        boolean addedWhere = false;
        String queryStr = "select game0_.* from t_game game0_ ";
        String where = "";
        Integer stack = 1;
        int pageInt = Math.toIntExact(page);

        Map<String, Object> params = new HashMap<>();
        if(parkId !=null && parkId != 0){
            if(stack > 1){
                where +=" and ";
            }
            where +=" game0_.park_id = :id ";
            addedWhere = true;
            params.put("id", parkId);
            stack++;
        }

        if(gameName != null && !gameName.equals("")){
            if(stack > 1){
                where +=" and ";
            }
            where +="game0_.game_name like :gname ";
            addedWhere = true;
            params.put("gname", gameName);
            stack++;
        }

        if(addedWhere){
            queryStr += " where ";
        }
        if(!searched){
            totalItem = queryGame(params, queryStr +where).size();
            totalPage = (totalItem % limit == 0) ? totalItem / limit : (totalItem / limit )+1;
        }
        params.put("from", (page - 1)*limit);
        stack++;
        params.put("limit", limit);
        stack++;
        where += "limit :from, :limit";

        Output output = new Output();
        output.setListResult(convertList(queryGame(params, queryStr +where)));
        output.setPage(pageInt);
        output.setTotalItems(totalItem);
        output.setTotalPage((int) totalPage);
        return output;
    }

    public List<GameDTO> convertList (List<Game> gameList){
        List<GameDTO> results = new ArrayList<>();
        for (Game item : gameList) {
            GameDTO gameDTO = gameConverter.toDTO(item);
            results.add(gameDTO);
        }
        return  results;
    }

    public List<Game> queryGame(Map<String, Object> params ,String sqlStr){
        Query query = entityManager.createNativeQuery(sqlStr, Game.class);
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
