package com.capstone.booking.config.facebook;

import com.capstone.booking.entity.dto.UserDTO;
import com.restfb.types.User;
import org.springframework.stereotype.Component;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;

//hàm nhận thông tin tk fb
@Component
public class RestFB {

  public static String FACEBOOK_APP_SECRET = "c722c6ae7f91a2cd627bb9ae01c3e1f4";

  //lấy thông tin tk fb
  public UserDTO getUserInfo(final String accessToken) {
    FacebookClient facebookClient = new DefaultFacebookClient(accessToken, FACEBOOK_APP_SECRET, Version.LATEST);
    User user = facebookClient.fetchObject("me", User.class);
    UserDTO userDTO = new UserDTO();
    userDTO.setFirstName(user.getFirstName());
    userDTO.setLastName(user.getLastName());
    userDTO.setMail(user.getEmail());
    userDTO.setDob(user.getBirthdayAsDate());
    return userDTO;
  }
}