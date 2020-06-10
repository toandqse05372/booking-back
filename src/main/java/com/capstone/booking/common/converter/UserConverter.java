package com.capstone.booking.common.converter;
import com.capstone.booking.entity.Role;
import com.capstone.booking.entity.User;
import com.capstone.booking.entity.dto.UserDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;


@Component
public class UserConverter {
    public UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        if (user.getId() != null) {
            dto.setId(user.getId());
        }

        dto.setPassword(user.getPassword());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setMail(user.getMail());
        dto.setDob(user.getDob());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setStatus(user.getStatus());
        Set<Role> roleKeySet = user.getRoles();
        Set<String> roleKeyString = new HashSet<>();
        for(Role role: roleKeySet){
            roleKeyString.add(role.getRoleKey());
        }
        dto.setRoleKey(roleKeyString);
        return dto;
    }

    public User toUser(UserDTO dto) {
        User user = new User();
        if(dto.getPassword() != null){
            user.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
        }
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setMail(dto.getMail());
        user.setDob(dto.getDob());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setStatus(dto.getStatus());
        return user;
    }

    public User toUser(UserDTO dto, User user) {
        if(dto.getPassword() != null) {
            user.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
        }
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setMail(dto.getMail());
        user.setDob(dto.getDob());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setStatus(dto.getStatus());
        return user;
    }

}
