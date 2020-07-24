package com.capstone.booking.common.converter;
import com.capstone.booking.entity.Role;
import com.capstone.booking.entity.User;
import com.capstone.booking.entity.dto.UserDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

//conver user
@Component
public class UserConverter {

    //convert from entity to dto
    public UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setPassword(user.getPassword());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setMail(user.getMail());
        dto.setDob(user.getDob());
        dto.setAvatarLink(user.getAvatarLink());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setStatus(user.getStatus());
        dto.setUserType(user.getUserType());
        Set<Role> roleKeySet = user.getRoles();
        Set<String> roleKeyString = new HashSet<>();
        for(Role role: roleKeySet){
            roleKeyString.add(role.getRoleKey());
        }
        dto.setRoleKey(roleKeyString);
        return dto;
    }

    public UserDTO toDTOClient(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setMail(user.getMail());
        dto.setDob(user.getDob());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setAvatarLink(user.getAvatarLink());
        return dto;
    }

    //convert from dto to entity (for add)
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

    //convert from dto to entity (for update)
    public User toUser(UserDTO dto, User user) {
        if(!dto.getPassword().equals(user.getPassword())) {
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

    //convert from dto to entity (for update)
    public User toUserFromClient(UserDTO dto, User user) {
        if(dto.getFirstName() != null){
            user.setFirstName(dto.getFirstName());
        }
        if(dto.getLastName() != null){
            user.setLastName(dto.getLastName());
        }
        if(dto.getDob() != null){
            user.setDob(dto.getDob());
        }
        if(dto.getPhoneNumber() != null){
            user.setPhoneNumber(dto.getPhoneNumber());
        }
        return user;
    }

}
