package com.capstone.booking.repository.customRepository;

import com.capstone.booking.api.output.Output;

//customer query to user table
public interface UserRepositoryCustom {
    //find user with paging
    Output findByMultiParam(String firstName, String mail, String lastName,
                            String phoneNumber, Long roleId, Long limit, Long page);
}
