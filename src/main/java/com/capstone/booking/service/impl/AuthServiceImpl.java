package com.capstone.booking.service.impl;

import com.capstone.booking.common.RestFB;
import com.capstone.booking.common.converter.UserConverter;
import com.capstone.booking.common.key.RoleKey;
import com.capstone.booking.config.security.JwtUtil;
import com.capstone.booking.config.security.UserPrincipal;
import com.capstone.booking.entity.Role;
import com.capstone.booking.entity.Token;
import com.capstone.booking.entity.User;
import com.capstone.booking.entity.dto.FBLoginDTO;
import com.capstone.booking.entity.dto.UserDTO;
import com.capstone.booking.repository.RoleRepository;
import com.capstone.booking.repository.UserRepository;
import com.capstone.booking.service.AuthService;
import com.capstone.booking.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RestFB restFB;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private RoleRepository roleRepository;

    //tìm user theo mail
    @Override
    public ResponseEntity<?> findByEmail(UserDTO userDTO) {
        User user = userRepository.findByMail(userDTO.getMail());
        if (null == user || !new BCryptPasswordEncoder().matches(userDTO.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("WRONG_USERNAME_PASSWORD");
        }
        return ResponseEntity.ok(returnToken(setPermission(user)).getToken());
    }

    //đăng kí/đăng nhập bằng fb
    @Override
    public ResponseEntity<?> loginFb(@RequestBody FBLoginDTO fbForm) throws IOException {
        String accessToken = fbForm.getAccessToken();
        UserDTO userDTO = restFB.getUserInfo(accessToken);
        User user = userRepository.findByMail(userDTO.getMail());
        if(user == null){
            return ResponseEntity.ok(returnToken(setPermission(saveFbUser(userDTO))).getToken());
        }else
            return ResponseEntity.ok(returnToken(setPermission(user)).getToken());
    }

    //đặt permission cho user tương ứng vs role
    public UserPrincipal setPermission(User user){
        UserPrincipal userPrincipal = new UserPrincipal();
        Set<String> authorities = new HashSet<>();
        if (null != user.getRoles()) user.getRoles().forEach(r -> {
            authorities.add(r.getRoleKey());
            r.getPermissions().forEach(p -> authorities.add(p.getPermissionKey()));
        });

        userPrincipal.setUserId(user.getId());
        userPrincipal.setMail(user.getMail());
        userPrincipal.setFirstName(user.getFirstName());
        userPrincipal.setLastName(user.getLastName());
        userPrincipal.setPassword(user.getPassword());
        userPrincipal.setAuthorities(authorities);
        return userPrincipal;
    }

    //trả token để remember tk
    private Token returnToken(UserPrincipal userPrincipal){
        Token token = new Token();
        token.setToken(jwtUtil.generateToken(userPrincipal));
        token.setTokenExpDate(jwtUtil.generateExpirationDate());
        token.setCreatedBy(userPrincipal.getUserId());
        tokenService.createToken(token);
        return token;
    }

    private User saveFbUser(UserDTO userDTO) {
        User user = userConverter.toUser(userDTO);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleRepository.findByRoleKey(RoleKey.USER.toString()));
        user.setRoles(roleSet);
        userRepository.save(user);
        return user;
    }
}