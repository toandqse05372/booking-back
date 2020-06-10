package com.capstone.booking.common;

import com.capstone.booking.entity.dto.UserDTO;
import com.restfb.types.User;
import org.springframework.stereotype.Component;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;

//hàm nhận thông tin tk fb
@Component
public class RestFB {

  //cấu hình app fb
//  public static String FACEBOOK_APP_ID = "842841396241232";
  public static String FACEBOOK_APP_SECRET = "c722c6ae7f91a2cd627bb9ae01c3e1f4";
//  public static String FACEBOOK_REDIRECT_URL = "https://localhost:8090/login/fb";
//  public static String FACEBOOK_LINK_GET_TOKEN = "https://graph.facebook.com/oauth/access_token?client_id=%s&client_secret=%s&redirect_uri=%s&code=%s";

//  //nhận token fb trả về để rút thông tin người dùng ra
//  public String getToken(final String code) throws ClientProtocolException, IOException {
//    String link = String.format(FACEBOOK_LINK_GET_TOKEN, FACEBOOK_APP_ID, FACEBOOK_APP_SECRET, FACEBOOK_REDIRECT_URL, code);
//    String response = Request.Get(link).execute().returnContent().asString();
//    ObjectMapper mapper = new ObjectMapper();
//    JsonNode node = mapper.readTree(response).get("access_token");
//    return node.textValue();
//  }

  //lấy thông tin tk fb
  public UserDTO getUserInfo(final String accessToken) {
    FacebookClient facebookClient = new DefaultFacebookClient(accessToken, FACEBOOK_APP_SECRET, Version.LATEST);
    User user = facebookClient.fetchObject("me", User.class);
    UserDTO userDTO = new UserDTO();
    //userDTO.setUsername(user.getName());
    return userDTO;
  }
//  public UserPrincipal buildUser(UserDTO user) {
//    boolean enabled = true;
//    boolean accountNonExpired = true;
//    boolean credentialsNonExpired = true;
//    boolean accountNonLocked = true;
//    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//    UserPrincipal userDetail
//            = new User(user.getId(), "", enabled, accountNonExpired, credentialsNonExpired,
//        accountNonLocked, authorities)
//            ;
//    return userDetail;
//  }
}