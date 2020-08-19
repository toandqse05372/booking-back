package com.capstone.booking.service.impl;

import com.capstone.booking.common.key.UserStatus;
import com.capstone.booking.config.facebook.RestFB;
import com.capstone.booking.common.converter.UserConverter;
import com.capstone.booking.common.key.CMSRoles;
import com.capstone.booking.common.key.RoleKey;
import com.capstone.booking.common.key.UserType;
import com.capstone.booking.config.security.JwtUtil;
import com.capstone.booking.config.security.UserPrincipal;
import com.capstone.booking.entity.Role;
import com.capstone.booking.entity.Token;
import com.capstone.booking.entity.User;
import com.capstone.booking.entity.dto.FBLoginDTO;
import com.capstone.booking.entity.dto.UserDTO;
import com.capstone.booking.repository.RoleRepository;
import com.capstone.booking.repository.TokenRepository;
import com.capstone.booking.repository.UserRepository;
import com.capstone.booking.service.AuthService;
import com.capstone.booking.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private RestFB restFB;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private RoleRepository roleRepository;

    //find existed user by email
    @Override
    public ResponseEntity<?> findByEmail(UserDTO userDTO, String page) {
        User user = userRepository.findByMail(userDTO.getMail());
        if(null == user){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("NOT_EMAIL_SIGNED_IN");
        }
        if (!new BCryptPasswordEncoder().matches(userDTO.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("WRONG_USERNAME_PASSWORD");
        }
        if(user.getStatus().equals(UserStatus.NOT.toString())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("ACCOUNT_NOT_ACTIVATED");
        }
        Set<Role> userRoles = user.getRoles();
        if (page != null) {
            // check if user logging in from cms site
            if (page.equals("CMS")) {
                boolean cmsAble = false;

                List<CMSRoles> cmsRoles = Arrays.asList(CMSRoles.values());
                for (Role role : userRoles) {
                    for (CMSRoles cmsRole : cmsRoles) {
                        if (cmsRole.toString().equals(role.getRoleKey())) {
                            cmsAble = true;
                            break;
                        }
                    }
                    if(cmsAble){
                        break;
                    }
                }
                if (!cmsAble) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("NO_PERMISSION_HERE");
                }
            }
        }else{
            // check if user logging in from client site
            boolean clientAble = false;
            for (Role role : userRoles) {
                if(role.getRoleKey().equals("USER")){
                    clientAble = true;
                    break;
                }
            }
            if (!clientAble) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("NO_PERMISSION_HERE");
            }
        }
        return ResponseEntity.ok(returnToken(setPermission(user)).getToken());
    }

    //register/login by fb
    @Override
    public ResponseEntity<?> loginFb(FBLoginDTO fbForm) {
        String accessToken = fbForm.getAccessToken();
        UserDTO userDTO = restFB.getUserInfo(accessToken);
        User user = userRepository.findByMail(userDTO.getMail());
        if (user == null) {
            user = userConverter.toUser(userDTO);
            user.setUserType(UserType.FACEBOOK.toString());
            Set<Role> roleSet = new HashSet<>();
            Role role = roleRepository.findByRoleKey(RoleKey.USER.toString());
            roleSet.add(role);
            user.setRoles(roleSet);
            return ResponseEntity.ok(returnToken(setPermission(userRepository.save(user))).getToken());
        } else
            return ResponseEntity.ok(returnToken(setPermission(user)).getToken());
    }

    //logout
    @Override
    public ResponseEntity<?> logout(String tokenStr) {
        Token token = tokenRepository.findByToken(tokenStr.substring(6));
        if (token == null) {
            return new ResponseEntity("BAD_REQUEST", HttpStatus.BAD_REQUEST);
        }
        tokenRepository.delete(token);
        return new ResponseEntity("LOGOUT_SUCCESSFUL", HttpStatus.OK);
    }

    //check if token valid
    @Override
    public ResponseEntity<?> checkToken(String tokenStr) {
        Token token = tokenRepository.findByToken(tokenStr.substring(6));
        if (token == null) {
            return new ResponseEntity("TOKEN_ILLEGAL", HttpStatus.BAD_REQUEST);
        }
        if (!token.getTokenExpDate().after(new Date())) {
            tokenRepository.delete(token);
            return new ResponseEntity("TOKEN_OUT_OF_DATE", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("OK", HttpStatus.OK);
    }

    //set permission and sub info into token
    public UserPrincipal setPermission(User user) {
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
        userPrincipal.setPhoneNumber(user.getPhoneNumber());
        return userPrincipal;
    }

    //return token to remember user
    public Token returnToken(UserPrincipal userPrincipal) {
        Token token = new Token();
        token.setToken(jwtUtil.generateToken(userPrincipal));
        token.setTokenExpDate(jwtUtil.generateExpirationDate());
        token.setCreatedBy(userPrincipal.getUserId());
        token.setUserId(userPrincipal.getUserId());
        tokenService.createToken(token);
        return token;
    }

}
