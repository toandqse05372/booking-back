package com.capstone.booking.api;

import com.capstone.booking.entity.dto.UserDTO;
import com.capstone.booking.service.TokenService;
import com.capstone.booking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

//user's api
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    //normal register
    @PostMapping("/user/register")
    public ResponseEntity<?> register(@RequestBody UserDTO user) {
        return userService.register(user);
    }

    //re sent email
    @PostMapping("/user/resent-email")
    public ResponseEntity<?> resendEmail(@RequestBody String mail) {
        return userService.resendEmailVerify(mail);
    }

    //active email
    @GetMapping("/user/active")
    public ResponseEntity<?> active(@RequestParam("token")String verificationToken) {
        return userService.verifyEmail(verificationToken);
    }

    //cms create new user
    @PostMapping("/user/createUserCMS")
    @PreAuthorize("hasAnyAuthority('USER_EDIT')")
    public ResponseEntity<?> createUserCMS(@RequestBody UserDTO user) {
        return userService.createUserCMS(user);
    }

    //update User
    @PutMapping(value = "/user/{id}")
    @PreAuthorize("hasAnyAuthority('USER_EDIT')")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO model, @PathVariable("id") long id) {
        model.setId(id);
        return userService.update(model);
    }

    //update User
    @PutMapping(value = "/userClient/{id}")
    public ResponseEntity<?> updateUserFromClient(@RequestBody UserDTO model, @PathVariable("id") long id) {
        model.setId(id);
        return userService.updateClient(model);
    }

    //search by firstName, mail, lastName, phoneNumber, role & paging
    @GetMapping("/user/searchMul")
    @PreAuthorize("hasAnyAuthority('USER_EDIT')")
    public ResponseEntity<?> findByMultiParam(@RequestParam(value = "firstName", required = false) String firstName,
                                              @RequestParam(value = "mail", required = false) String mail,
                                              @RequestParam(value = "lastName", required = false) String lastName,
                                              @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
                                              @RequestParam(value = "limit", required = false) Long limit,
                                              @RequestParam(value = "page", required = false) Long page,
                                              @RequestParam(value = "role", required = false) Long roleId) {
        return userService.findByMultiParam(firstName, mail, lastName, phoneNumber, roleId, limit, page);
    }

    //delete User api
    @DeleteMapping("/user/{id}")
    @PreAuthorize("hasAnyAuthority('USER_EDIT')")
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        return userService.delete(id);
    }

    //search by Id api
    @GetMapping("/user/{id}")
    @PreAuthorize("hasAnyAuthority('USER_EDIT')")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    //get all role api
    @GetMapping("user/roles")
    public ResponseEntity<?> findAllRoles(){
        return userService.findAllRoles();
    }

    //reset password api
    @PostMapping("user/resetPassword")
    public ResponseEntity<?> resetPasswordRequest(@RequestBody Long uid, String oldPassword, String newPassword){
        return userService.changePassword(uid, oldPassword, newPassword);
    }

    //verify email for fb account api
    @GetMapping("user/verifyEmailFb")
    public ResponseEntity<?> verifyEmailFb(String mail, Long uid){
        return userService.verifyEmailFb(mail, uid);
    }

    //get user by Id api for client
    @GetMapping("/userClient/{id}")
    public ResponseEntity<?> getUserClient(@PathVariable Long id) {
        return userService.getUserClient(id);
    }

    @PostMapping("/user/avatar/{id}")
    public ResponseEntity<?> updateAvatar(@PathVariable Long id, @RequestPart(value = "file") MultipartFile file){
        return userService.updateAvatar(id, file);
    }


}
