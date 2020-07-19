package com.capstone.booking.repository;

import com.capstone.booking.entity.Role;
import com.capstone.booking.entity.User;
import com.capstone.booking.repository.customRepository.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//customer query to user table
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    //find user by exact mail
    User findByMail(String mail);

    //find user by role
    List<User> findByRoles(Role role);


}
