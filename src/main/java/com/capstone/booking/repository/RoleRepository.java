package com.capstone.booking.repository;

import com.capstone.booking.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

//customer query to role table
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleKey(String roleName);
}
