package com.capstone.booking.repository.impl;

import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.UserConverter;
import com.capstone.booking.entity.User;
import com.capstone.booking.entity.dto.UserDTO;
import com.capstone.booking.repository.customRepository.UserRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepositoryImpl implements UserRepositoryCustom {

    private Integer totalItem = 0;
    private long totalPage = 1;
    private boolean searched = false;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    UserConverter userConverter;

    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Output findByMultiParam(String fname, String mail, String lastName,
                                   String phoneNumber, Long roleId, Long limit, Long page) {
        boolean addedWhere = false;
        String count = "select count(user0_.id)";
        String getAll = "select user0_.*";
        String queryStr = " from t_user user0_ ";
        String where = "";
        Integer stack = 1;
        int pageInt = Math.toIntExact(page);

        Map<String, Object> params = new HashMap<>();
        if (roleId != null && roleId != 0) {
            queryStr += "INNER join t_user_role user_role0_ on user0_.id = user_role0_.user_id";
            if (stack > 1) {
                where += " and ";
            }
            where += " user_role0_.role_id = :id ";
            addedWhere = true;
            params.put("id", roleId);
            stack++;
        }

        if (fname != null && !fname.equals("")) {
            if (stack > 1) {
                where += " and ";
            }
            where += "user0_.first_name like :fname ";
            addedWhere = true;
            params.put("fname", fname);
            stack++;
        }
        if (mail != null && !mail.equals("")) {
            if (stack > 1) {
                where += " and ";
            }
            where += "user0_.mail like :mail ";
            addedWhere = true;
            params.put("mail", mail);
            stack++;
        }
        if (lastName != null && !lastName.equals("")) {
            if (stack > 1) {
                where += " and ";
            }
            where += "user0_.last_name like :lname ";
            addedWhere = true;
            params.put("lname", lastName);
            stack++;
        }
        if (phoneNumber != null && !phoneNumber.equals("")) {
            if (stack > 1) {
                where += " and ";
            }
            where += "user0_.phone_number like :pnum ";
            addedWhere = true;
            params.put("pnum", phoneNumber);
            stack++;
        }
        if (addedWhere) {
            queryStr += " where ";
        }
        String between = "";
        if (!searched) {
            totalItem = countUser(params, count + queryStr + where);
            totalPage = (totalItem % limit == 0) ? totalItem / limit : (totalItem / limit) + 1;
        }
        params.put("from", (page - 1) * limit);
        stack++;
        params.put("limit", limit);
        stack++;
        where += "limit :from, :limit";

        Output output = new Output();
        output.setListResult(convertList(queryUser(params, getAll + queryStr + where)));
        output.setPage(pageInt);
        output.setTotalItems(totalItem);
        output.setTotalPage((int) totalPage);
        return output;
    }

    public List<UserDTO> convertList(List<User> userList) {
        List<UserDTO> results = new ArrayList<>();
        for (User item : userList) {
            UserDTO userDTO = userConverter.toDTO(item);
            results.add(userDTO);
        }

        return results;
    }

    public List<User> queryUser(Map<String, Object> params, String sqlStr) {
        Query query = entityManager.createNativeQuery(sqlStr, User.class);
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

    public int countUser(Map<String, Object> params, String sqlStr) {
        Query query = entityManager.createNativeQuery(sqlStr);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (key.equals("id")) {
                query.setParameter(key, value);
            } else
                query.setParameter(key, "%" + value + "%");
        }
        BigInteger counter = (BigInteger) query.getSingleResult();
        return counter.intValue();
    }

}
