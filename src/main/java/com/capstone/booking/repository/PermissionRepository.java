package com.capstone.booking.repository;

import com.capstone.booking.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

//customer query to permission table
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    //find permission by role ket
    Permission findByPermissionKey(String permission);
}
