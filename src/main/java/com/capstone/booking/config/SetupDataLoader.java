package com.capstone.booking.config;

import com.capstone.booking.common.key.PermissionKey;
import com.capstone.booking.common.key.RoleKey;
import com.capstone.booking.entity.Permission;
import com.capstone.booking.entity.Role;
import com.capstone.booking.entity.User;
import com.capstone.booking.repository.PermissionRepository;
import com.capstone.booking.repository.RoleRepository;
import com.capstone.booking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {
 
    public static boolean alreadySetup = false;
 
    @Autowired
    private UserRepository userRepository;
  
    @Autowired
    private RoleRepository roleRepository;
  
    @Autowired
    private PermissionRepository permissionRepository;
  
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
  
        if (alreadySetup)
            return;
        Set<Permission> adminPermission =
                createPermissionIfNotFound(Arrays.asList(PermissionKey.AdminPermissionKey.values()));
        createRoleIfNotFound(RoleKey.ADMIN.toString(), adminPermission);
        Set<Permission> userPermission =
                createPermissionIfNotFound(Arrays.asList(PermissionKey.UserPermission.values()));
        createRoleIfNotFound(RoleKey.USER.toString(), userPermission);
        addPermissionForRoleIfNew(RoleKey.ADMIN.toString());

        Role adminRole = roleRepository.findByRoleKey(RoleKey.ADMIN.toString());
        List<User> user = userRepository.findByRoles(adminRole);
        if (user.size() == 0) {
            User admin = new User();
            admin.setFirstName("Test");
            admin.setLastName("Test");
            admin.setPassword(new BCryptPasswordEncoder().encode("test"));
            admin.setMail("test@test.com");
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(adminRole);
            admin.setRoles(roleSet);
            userRepository.save(admin);
        }
        alreadySetup = true;
    }
 
    @Transactional
    public Set<Permission> createPermissionIfNotFound(List<Enum> keyList) {
        Set<Permission> permissionsList = new HashSet<>();
        for(Enum key: keyList){
            Permission permission = permissionRepository.findByPermissionKey(key.toString());
            if (permission == null) {
                permission = new Permission();
                permission.setPermissionKey(key.toString());
                permissionRepository.save(permission);
                permissionsList.add(permission);
            }
        }
        return permissionsList;
    }
 
    @Transactional
    public Role createRoleIfNotFound(String key, Set<Permission> privileges) {
        Role role = roleRepository.findByRoleKey(key);
        if (role == null) {
            role = new Role();
            role.setRoleKey(key);
            role.setPermissions(privileges);
            roleRepository.save(role);
        }
        return role;
    }

    @Transactional
    public void addPermissionForRoleIfNew(String key) {
        Role role = roleRepository.findByRoleKey(key);
        Set<Permission> newPermissionTemp = new HashSet<>(permissionRepository.findAll());
        newPermissionTemp.removeAll(role.getPermissions());
        if (newPermissionTemp.size() > 0) {
           role.setPermissions(new HashSet<>(permissionRepository.findAll()));
            roleRepository.save(role);
        }
    }
}