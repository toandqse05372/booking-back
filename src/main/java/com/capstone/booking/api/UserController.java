package com.capstone.booking.api;
import com.capstone.booking.entity.Token;
import com.capstone.booking.entity.dto.TokenDTO;
import com.capstone.booking.entity.dto.UserDTO;
import com.capstone.booking.service.TokenService;
import com.capstone.booking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    //đăng kí tài khoản bt
    @PostMapping("/user/register")
    public ResponseEntity<?> register(@RequestBody UserDTO user) {
        return userService.register(user);
    }

    @GetMapping("/user/active")
    public ResponseEntity<?> active(@RequestParam("token")String verificationToken) {
        return userService.verifyEmail(verificationToken);
    }

    //đăng kí tài khoản bt
    @PostMapping("/user/createUserCMS")
    @PreAuthorize("hasAnyAuthority('ADD_USER')")
    public ResponseEntity<?> createUserCMS(@RequestBody UserDTO user) {
        return userService.createUserCMS(user);
    }

    //sửa User
    @PutMapping(value = "/user/{id}")
    @PreAuthorize("hasAnyAuthority('UPDATE_USER')")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO model, @PathVariable("id") long id) {
        model.setId(id);
        return userService.update(model);
    }

    //search by first_name & mail, lname, phoneNumber, role
    @GetMapping("/user/searchMul")
    @PreAuthorize("hasAnyAuthority('READ_USER')")
    public ResponseEntity<?> findByMultiParam(@RequestParam(value = "firstName", required = false) String firstName,
                                              @RequestParam(value = "mail", required = false) String mail,
                                              @RequestParam(value = "lastName", required = false) String lastName,
                                              @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
                                              @RequestParam(value = "limit", required = false) Long limit,
                                              @RequestParam(value = "page", required = false) Long page,
                                              @RequestParam(value = "role", required = false) Long roleId) {
        return userService.findByMultiParam(firstName, mail, lastName, phoneNumber, roleId, limit, page);
    }

    //delete User
    @DeleteMapping("/user/{id}")
    @PreAuthorize("hasAnyAuthority('DELETE_USER')")
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        userService.delete(id);
        return new ResponseEntity("Delete Successful", HttpStatus.OK);
    }

    //search by Id
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping("user/roles")
    public ResponseEntity<?> findAllRoles(){
        return userService.findAllRoles();
    }
}
