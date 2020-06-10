package com.capstone.booking.common.key;

public interface PermissionKey {
    enum AdminPermissionKey{
        ADD_USER, UPDATE_USER, DELETE_USER, READ_USER,

        ADD_PARK, UPDATE_PARK, DELETE_PARK, READ_PARK,
    }

    enum UserPermission{
        ADD_USER
    }
}
