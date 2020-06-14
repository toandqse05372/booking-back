package com.capstone.booking.common.key;

public interface PermissionKey {
    enum AdminPermissionKey{
        ADD_USER, UPDATE_USER, DELETE_USER, READ_USER,

        ADD_PLACE, UPDATE_PLACE, DELETE_PLACE, READ_PLACE,
    }

    enum UserPermission{
        ADD_USER
    }
}
