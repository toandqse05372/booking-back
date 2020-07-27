package com.capstone.booking.service;

import com.capstone.booking.entity.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    //normal register
    ResponseEntity<?> register(UserDTO userDTO);

    //send verify email
    ResponseEntity<?> resendEmailVerify(String mail);

    //verify email for fb account
    ResponseEntity<?> verifyEmailFb(String mail, Long uid);

    //verify email
    ResponseEntity<?> verifyEmail(String verificationToken);

    //edit
    ResponseEntity<?> update(UserDTO userDTO);

    ResponseEntity<?> updateClient(UserDTO userDTO);

    //createUserCMS
    ResponseEntity<?> createUserCMS(UserDTO user);

    //search by firstName, mail, lastName, phoneNumber, role & paging
    ResponseEntity<?> findByMultiParam(String fname, String mail, String lastName, String phoneNumber,
                                       Long roleId, Long limit, Long page);

    //delete
    ResponseEntity<?> delete(long id);

    //search by id
    ResponseEntity<?> getUser(Long id);

    //find all
    ResponseEntity<?> findAllRoles();

    //validate password reset token
    ResponseEntity<?> validatePasswordResetToken(String token);

    //change password after reset
    ResponseEntity<?> changePasswordAfterReset(long uid, String newPassword);

    //change password request
    ResponseEntity<?> changePassword(long uid, String oldPassword, String newPassword);

    ResponseEntity<?> findEmailToChangePassword(String mail);

    ResponseEntity<?> getUserClient(Long id);

    ResponseEntity<?> updateAvatar(Long id, MultipartFile file);
}
