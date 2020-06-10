package com.capstone.booking.service.impl;
import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.UserConverter;
import com.capstone.booking.common.key.RoleKey;
import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.UserDTO;
import com.capstone.booking.repository.RoleRepository;
import com.capstone.booking.repository.UserRepository;
import com.capstone.booking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

//nghiệp vụ user
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserConverter userConverter;


    //tạo customer mới
    @Override
    public ResponseEntity<?> register(UserDTO userDTO) {
        User user = userConverter.toUser(userDTO);
        if (userRepository.findByMail(user.getMail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("EMAIL_EXISTED");
        }
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleRepository.findByRoleKey(RoleKey.USER.toString()));
        user.setRoles(roleSet);
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    //sửa user
    @Override
    public ResponseEntity<?> update(UserDTO userDTO) {
        User user = new User();
        User oldUser = userRepository.findById(userDTO.getId()).get();
        user = userConverter.toUser(userDTO, oldUser);
        if (!userRepository.findByMail(user.getMail()).getId().equals(oldUser.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("EMAIL_EXISTED");
        }
        Set<Role> roleSet = new HashSet<>();
        for(String role: userDTO.getRoleKey()){
            roleSet.add(roleRepository.findByRoleKey(role));
        }
        user.setRoles(roleSet);
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<?> createUserCMS(UserDTO userDTO) {
        User user = userConverter.toUser(userDTO);
        if (userRepository.findByMail(user.getMail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("EMAIL_EXISTED");
        }
        Set<Role> roleSet = new HashSet<>();
        for(String roleKey: userDTO.getRoleKey()){
            roleSet.add(roleRepository.findByRoleKey(roleKey));
        }
        user.setRoles(roleSet);
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    //search by first_name and mail, lastName, phoneNumber, roleId & paging
    @Override
    public ResponseEntity<?> findByMultiParam(String firstName, String mail, String lastName, String phoneNumber, Long roleId, Long limit, Long page) {

        Output results = userRepository.findByMultiParam(firstName, mail, lastName, phoneNumber, roleId, limit, page);
        return ResponseEntity.ok(results);
    }

    //delete user
    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    //search by id
    @Override
    public  ResponseEntity<?> getUser(Long id) {
        Optional<User> users = userRepository.findById(id);
        User user = users.get();
        return ResponseEntity.ok(userConverter.toDTO(user));
    }


    @Override
    public ResponseEntity<?> findAllRoles() {
        return ResponseEntity.ok(roleRepository.findAll());
    }
}
