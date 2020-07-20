package com.capstone.booking.repository.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.TicketTypeConverter;
import com.capstone.booking.entity.TicketType;
import com.capstone.booking.entity.dto.TicketTypeDTO;
import com.capstone.booking.repository.customRepository.TicketTypeCustom;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketTypeRepositoryImpl implements TicketTypeCustom {

    @PersistenceContext
    EntityManager entityManager;
    @Override
    public List<TicketType> findByPlaceId(Long placeId) {
        String queryStr = "select type.* from t_ticket_type type " +
                "Left join t_game_ticket_type gtt on gtt.ticket_type_id = type.id " +
                "Left join t_game g on gtt.game_id = g.id where type.place_id = :placeId " +
                "AND  g.status like 'ACTIVE' group by type.id";
        Query query = entityManager.createNativeQuery(queryStr, TicketType.class);
        query.setParameter("placeId", placeId);
        return query.getResultList();
    }

//    public List<TicketTypeDTO> convertList(List<TicketType> ticketTypeList) {
//        List<TicketTypeDTO> results = new ArrayList<>();
//        for (TicketType item : ticketTypeList) {
//            TicketTypeDTO typeDTO = ticketTypeConverter.toDTO(item);
//            results.add(typeDTO);
//        }
//        return results;
//    }
//
//    public List<TicketType> queryTicketType(Map<String, Object> params, String sqlStr) {
//        Query query = entityManager.createNativeQuery(sqlStr, TicketType.class);
//        for (Map.Entry<String, Object> entry : params.entrySet()) {
//            String key = entry.getKey();
//            Object value = entry.getValue();
//            if (key.equals("from") || key.equals("limit")) {
//                query.setParameter(key, value);
//            } else
//                query.setParameter(key, "%" + value + "%");
//        }
//        return query.getResultList();
//    }
}
