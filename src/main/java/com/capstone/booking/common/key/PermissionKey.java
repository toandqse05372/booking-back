package com.capstone.booking.common.key;

public interface PermissionKey {
    enum AdminPermissionKey{
        USER_EDIT, PLACE_EDIT, CITY_EDIT, GAME_EDIT, ORDER_EDIT, PAYMENT_METHOD_EDIT, TICKET_TYPE_EDIT,
        VISITOR_TYPE_EDIT, CATEGORY_EDIT, ORDER_READ, REPORT_READ
    }

    enum StaffPermissionKey{
        ORDER_EDIT, REPORT_READ, ORDER_READ, VISITOR_TYPE_EDIT, TICKET_TYPE_EDIT
    }

    enum UserPermission{
        PAYMENT, USER_EDIT_CLIENT, OWN_ORDER_READ
    }
}
