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

    private Integer totalItem;
    private long totalPage;
    private boolean searched = false;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private TicketTypeConverter ticketTypeConverter;


    @Override
    public Output findByTypeName(String typeName, Long limit, Long page) {
        boolean addedWhere = false;
        String queryStr = "select type.* from t_ticket_type type ";
        String where = "";
        Integer stack = 1;
        int pageInt = Math.toIntExact(page);

        Map<String, Object> params = new HashMap<>();

        if (typeName != null && !typeName.equals("")) {
            if (stack > 1) {
                where += " and ";
            }
            where += "type.type_name like :tname ";
            addedWhere = true;
            params.put("tname", typeName);
            stack++;
        }

        if (addedWhere) {
            queryStr += " where ";
        }
        if (!searched) {
            totalItem = queryTicketType(params, queryStr + where).size();
            totalPage = (totalItem % limit == 0) ? totalItem / limit : (totalItem / limit) + 1;
        }
        params.put("from", (page - 1) * limit);
        stack++;
        params.put("limit", limit);
        stack++;
        where += "limit :from, :limit";

        Output output = new Output();
        output.setListResult(convertList(queryTicketType(params, queryStr + where)));
        output.setPage(pageInt);
        output.setTotalItems(totalItem);
        output.setTotalPage((int) totalPage);
        return output;
    }

    public List<TicketTypeDTO> convertList(List<TicketType> ticketTypeList) {
        List<TicketTypeDTO> results = new ArrayList<>();
        for (TicketType item : ticketTypeList) {
            TicketTypeDTO typeDTO = ticketTypeConverter.toDTO(item);
            results.add(typeDTO);
        }
        return results;
    }

    public List<TicketType> queryTicketType(Map<String, Object> params, String sqlStr) {
        Query query = entityManager.createNativeQuery(sqlStr, TicketType.class);
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
