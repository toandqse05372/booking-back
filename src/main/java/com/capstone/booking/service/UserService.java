package com.capstone.booking.service;
import com.capstone.booking.entity.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {

    //normal register
    ResponseEntity<?> register(UserDTO userDTO);

    ResponseEntity<?> resendEmailVerify(String mail);

    ResponseEntity<?> verifyEmailFb(String mail, Long uid);

    ResponseEntity<?> verifyEmail(String verificationToken);

    //edit
    ResponseEntity<?> update(UserDTO userDTO);

    //createUserCMS
    ResponseEntity<?> createUserCMS(UserDTO user);

    //search by firstName, mail, lastName, phoneNumber, role & paging
    ResponseEntity<?> findByMultiParam(String fname, String mail, String lastName, String phoneNumber, Long roleId, Long limit, Long page);

    //delete
    ResponseEntity<?> delete(long id);

    //search by id
    ResponseEntity<?> getUser(Long id);

    ResponseEntity<?> findAllRoles();

    ResponseEntity<?> validatePasswordResetToken(String token);

    ResponseEntity<?> changePasswordAfterReset(long uid, String newPassword);

    ResponseEntity<?> changePassword(long uid, String oldPassword, String newPassword);

    ResponseEntity<?> createPasswordResetToken(String mail);
}
