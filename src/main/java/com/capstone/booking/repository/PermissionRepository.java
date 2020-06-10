package com.capstone.booking.repository;

import com.capstone.booking.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Permission findByPermissionKey(String permission);
}
