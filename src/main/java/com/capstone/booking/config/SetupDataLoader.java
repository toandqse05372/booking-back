package com.capstone.booking.config;

import com.capstone.booking.common.key.PermissionKey;
import com.capstone.booking.common.key.RoleKey;
import com.capstone.booking.entity.*;
import com.capstone.booking.repository.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.*;

//generate data when first run
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

    @Autowired
    private CategoryRepository categoryRepository;
  
    @SneakyThrows
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup)
            return;
        initPermission();
        initCategory();
        alreadySetup = true;
    }

    //add first category
    public void initCategory(){
        if(categoryRepository.findByTypeName("Khu vui chơi") == null){
            Category category = new Category("Khu vui chơi", "PARK", "Cho trẻ tha hồ chạy nhảy, leo trẻo khắp mọi nơi");
            categoryRepository.save(category);
        }
    }

    //add admin account, roles and permission
    public void initPermission(){
        List<Enum> adminKey = Arrays.asList(PermissionKey.AdminPermissionKey.values());
        List<Enum> userKey = Arrays.asList(PermissionKey.UserPermission.values());
        List<Enum> staffKey = Arrays.asList(PermissionKey.StaffPermissionKey.values());
        Set<Permission> adminPermission =
                createPermissionIfNotFound(adminKey);
        createRoleIfNotFound(RoleKey.ADMIN.toString(), adminPermission);
        addPermissionForRoleIfNew(RoleKey.ADMIN.toString(), adminKey);
        Set<Permission> userPermission =
                createPermissionIfNotFound(userKey);
        createRoleIfNotFound(RoleKey.USER.toString(), userPermission);
        addPermissionForRoleIfNew(RoleKey.USER.toString(), userKey);
        Set<Permission> staffPermission =
                createPermissionIfNotFound(staffKey);
        createRoleIfNotFound(RoleKey.STAFF.toString(), staffPermission);
        addPermissionForRoleIfNew(RoleKey.STAFF.toString(), staffKey);

        Role adminRole = roleRepository.findByRoleKey(RoleKey.ADMIN.toString());
        List<User> user = userRepository.findByRoles(adminRole);
        if (user.size() == 0) {
            User admin = new User();
            admin.setFirstName("Test");
            admin.setLastName("Test");
            admin.setPassword(new BCryptPasswordEncoder().encode("test"));
            admin.setMail("test@test.com");
            admin.setStatus("ACTIVATED");
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(adminRole);
            admin.setRoles(roleSet);
            userRepository.save(admin);
        }
    }

    //create permission if db hasn't it
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

    //create role if db hasn't it
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

    //add permission for role
    @Transactional
    public void addPermissionForRoleIfNew(String key,List<Enum> keyList) {
        Role role = roleRepository.findByRoleKey(key);
        Set<Permission> oldPermission = new HashSet<>(role.getPermissions());
        boolean hasNew= false;
        for(Enum permission : keyList){
            if(!oldPermission.stream().filter(c -> c.getPermissionKey().equals(permission.toString())).findAny().isPresent()){
                Permission addPermission = permissionRepository.findByPermissionKey(permission.toString());
                oldPermission.add(addPermission);
                hasNew = true;
            }
        }
        if (hasNew) {
           role.setPermissions(oldPermission);
           roleRepository.save(role);
        }
    }
}