package com.capstone.booking.config.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UserPrincipal implements UserDetails {
    private Long userId;
    private String username;
    private String password;
    private Collection authorities;
    private String firstName;
    private String lastName;
    private String mail;
    private String phoneNumber;
    private Date dob;
    //   private List<String> permissions;

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
