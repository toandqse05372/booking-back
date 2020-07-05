package com.capstone.booking.service;
import com.capstone.booking.entity.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {

    //tạo customer mới
    ResponseEntity<?> register(UserDTO userDTO);

    ResponseEntity<?> resendEmailVerify(String mail);

    ResponseEntity<?> verifyEmail(String verificationToken);

    //sửa
    ResponseEntity<?> update(UserDTO userDTO);

    //createUserCMS
    ResponseEntity<?> createUserCMS(UserDTO user);

    //search by first_name & mail, lname, phoneNumber, role
    ResponseEntity<?> findByMultiParam(String fname, String mail, String lastName, String phoneNumber, Long roleId, Long limit, Long page);

    //xóa
    ResponseEntity<?> delete(long id);

    //tim theo id
    ResponseEntity<?> getUser(Long id);

    ResponseEntity<?> findAllRoles();

    ResponseEntity<?> validatePasswordResetToken(String token);

    ResponseEntity<?> changePasswordAfterReset(long uid, String newPassword);

    ResponseEntity<?> changePassword(long uid, String oldPassword, String newPassword);

    ResponseEntity<?> createPasswordResetToken(String mail);
}
