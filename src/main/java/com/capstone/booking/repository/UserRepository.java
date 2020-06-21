package com.capstone.booking.repository;

import com.capstone.booking.entity.Role;
import com.capstone.booking.entity.User;
import com.capstone.booking.repository.customRepository.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    User findByMail(String mail);
    List<User> findByRoles(Role role);


}
