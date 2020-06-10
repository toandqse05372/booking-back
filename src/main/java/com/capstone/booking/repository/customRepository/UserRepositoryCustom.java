package com.capstone.booking.repository.customRepository;

import com.capstone.booking.api.output.Output;

public interface UserRepositoryCustom {
    Output findByMultiParam(String firstName, String mail, String lastName, String phoneNumber, Long roleId, Long limit, Long page);
}
