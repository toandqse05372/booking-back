package com.capstone.booking.service.impl;
import com.capstone.booking.api.output.Output;
import com.capstone.booking.common.converter.UserConverter;
import com.capstone.booking.common.key.RoleKey;
import com.capstone.booking.common.key.UserStatus;
import com.capstone.booking.common.key.UserType;
import com.capstone.booking.config.aws.AmazonS3ClientService;
import com.capstone.booking.entity.*;
import com.capstone.booking.entity.dto.UserDTO;
import com.capstone.booking.repository.PasswordResetTokenRepository;
import com.capstone.booking.repository.RoleRepository;
import com.capstone.booking.repository.UserRepository;
import com.capstone.booking.repository.VerificationTokenRepository;
import com.capstone.booking.service.UserService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.*;

//user service
@Service
public class UserServiceImpl implements UserService {

    @Value("${spring.mail.username}")
    private String fromMail;

    @Value("${frontend.host}")
    private String hostFrontEnd;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private  AuthServiceImpl authService;

    @Autowired
    private PasswordResetTokenRepository passwordTokenRepository;

    @Value("${aws.bucketLink}")
    private String bucketLink;

    @Autowired
    private AmazonS3ClientService amazonS3ClientService;

    //normal register
    @Override
    public ResponseEntity<?> register(UserDTO userDTO) {
        if (userRepository.findByMail(userDTO.getMail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("EMAIL_EXISTED");
        }
        User user = userConverter.toUser(userDTO);
        user.setUserType(UserType.BASIC.toString());
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleRepository.findByRoleKey(RoleKey.USER.toString()));
        user.setRoles(roleSet);
        user.setStatus(UserStatus.NOT.toString());
        userRepository.save(user);
        sendEmailVerify(user);
        return ResponseEntity.ok(userConverter.toDTOClient(user));
    }

    //send email again
    @Override
    public ResponseEntity<?> resendEmailVerify(String mail){
        User user = userRepository.findByMail(mail);
        if(user == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("WRONG_EMAIL");
        }
        tokenRepository.delete(tokenRepository.findByUid(user.getId()));
        sendEmailVerify(user);
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<?> verifyEmailFb(String mail, Long uid){
        User user = userRepository.findById(uid).get();
        user.setMail(mail);
        userRepository.save(user);
        sendEmailVerify(user);
        return ResponseEntity.ok(user);
    }

    //send verify email
    public void sendEmailVerify(User user){
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUid(user.getId());
        verificationToken.setConfirmationToken(UUID.randomUUID().toString());
        tokenRepository.save(verificationToken);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getMail()); //user email
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom(fromMail);
        mailMessage.setText("To confirm your account, please click here : "
                +hostFrontEnd+"confirmMail?token="+verificationToken.getConfirmationToken());
        emailSenderService.sendEmail(mailMessage);
    }

    //verify email user entered
    @Override
    public ResponseEntity<?> verifyEmail(String verificationToken) {
        VerificationToken token = tokenRepository.findByConfirmationToken(verificationToken);
        if(token != null){
            User user = userRepository.findByMail(userRepository.findById(token.getUid()).get().getMail());
            user.setStatus(UserStatus.ACTIVATED.toString());
            userRepository.save(user);
            tokenRepository.delete(tokenRepository.findByUid(user.getId()));
            return ResponseEntity.ok(authService.returnToken(authService.setPermission(user)));
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("INVALID_TOKEN");
        }
    }

    //edit user CMS
    @Override
    public ResponseEntity<?> update(UserDTO userDTO) {
        User user = new User();
        User oldUser = userRepository.findById(userDTO.getId()).get();
        user = userConverter.toUser(userDTO, oldUser);
        if (!userRepository.findByMail(user.getMail()).getId().equals(oldUser.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("EMAIL_EXISTED");
        }
        if(userDTO.getRoleKey() != null){
            Set<Role> roleSet = new HashSet<>();
            for(String role: userDTO.getRoleKey()){
                roleSet.add(roleRepository.findByRoleKey(role));
            }
            user.setRoles(roleSet);
        }

        userRepository.save(user);
        return ResponseEntity.ok(userConverter.toDTOClient(user));
    }

    @Override
    public ResponseEntity<?> updateClient(UserDTO userDTO) {
        User user = new User();
        User oldUser = userRepository.findById(userDTO.getId()).get();
        user = userConverter.toUserFromClient(userDTO, oldUser);
        userRepository.save(user);
        return ResponseEntity.ok(userConverter.toDTOClient(user));
    }

    //create User by CMS
    @Override
    public ResponseEntity<?> createUserCMS(UserDTO userDTO) {
        if (userRepository.findByMail(userDTO.getMail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("EMAIL_EXISTED");
        }
        User user = userConverter.toUser(userDTO);
        user.setUserType(UserType.BASIC.toString());
        user.setStatus(UserStatus.ACTIVATED.toString());
        Set<Role> roleSet = new HashSet<>();
        for(String roleKey: userDTO.getRoleKey()){
            roleSet.add(roleRepository.findByRoleKey(roleKey));
        }
        user.setRoles(roleSet);
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    //search by firstName, mail, lastName, phoneNumber, roleId & paging
    @Override
    public ResponseEntity<?> findByMultiParam(String firstName, String mail, String lastName, String phoneNumber, Long roleId, Long limit, Long page) {

        Output results = userRepository.findByMultiParam(firstName, mail, lastName, phoneNumber, roleId, limit, page);
        return ResponseEntity.ok(results);
    }

    //delete user
    @Override
    @Transactional
    public ResponseEntity<?> delete(long id) {
        User user = userRepository.findById(id).get();
        if (user == null) {
            return new ResponseEntity("USER_NOT_FOUND", HttpStatus.BAD_REQUEST);
        }
        if(user.getRoles().contains(roleRepository.findByRoleKey("ADMIN"))){
            return new ResponseEntity("IS_ADMIN", HttpStatus.BAD_REQUEST);
        }
        tokenRepository.deleteByUid(id);
        userRepository.deleteById(id);
        return new ResponseEntity("Delete Successful", HttpStatus.OK);
    }

    //search by id
    @Override
    public  ResponseEntity<?> getUser(Long id) {
        Optional<User> users = userRepository.findById(id);
        User user = users.get();
        return ResponseEntity.ok(userConverter.toDTO(user));
    }

    //get all user role
    @Override
    public ResponseEntity<?> findAllRoles() {
        return ResponseEntity.ok(roleRepository.findAll());
    }

    //validate reset token
    @Override
    public ResponseEntity<?> validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = passwordTokenRepository.findByToken(token);
        if (passToken == null) {
            return new ResponseEntity("INVALID_TOKEN", HttpStatus.BAD_REQUEST);
        }

        final Calendar cal = Calendar.getInstance();
        if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return new ResponseEntity("TOKEN_EXPIRED", HttpStatus.BAD_REQUEST);
        }
        User user = userRepository.findById(passwordTokenRepository.findByToken(token).getUid()).get();
        String randomPass = UUID.randomUUID().toString();
        user.setPassword(randomPass);
        userRepository.save(user);
        passwordTokenRepository.delete(passwordTokenRepository.findByToken(token));
        return new ResponseEntity(user.getId(), HttpStatus.OK);
    }

    //change password after reset
    @Override
    public ResponseEntity<?> changePasswordAfterReset(long uid, String newPassword) {
        User user = userRepository.findById(uid).get();
        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        userRepository.save(user);
        return new ResponseEntity(user.getId(), HttpStatus.OK);
    }

    //change password in user info
    @Override
    public ResponseEntity<?> changePassword(long uid, String oldPassword, String newPassword) {
        User user = userRepository.findById(uid).get();
        if (!new BCryptPasswordEncoder().matches(oldPassword, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("WRONG_OLD_PASSWORD");
        }
        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        userRepository.save(user);
        return new ResponseEntity(user.getId(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findEmailToChangePassword(String mail) {
        User user = userRepository.findByMail(mail);
        if(user == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("EMAIL_NOT_EXISTED");
        }
        sendChangePassVerify(user);
        return new ResponseEntity(user.getId(), HttpStatus.OK);
    }

    //send verify change password email
    public void sendChangePassVerify(User user){
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setUid(user.getId());
        passwordResetToken.setToken(UUID.randomUUID().toString());
        passwordResetToken.setExpiryDate(new Date(System.currentTimeMillis() + 17280000));
        passwordTokenRepository.save(passwordResetToken);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getMail()); //user email
        mailMessage.setSubject("Change password");
        mailMessage.setFrom(fromMail);
        mailMessage.setText("To change your password, please click here : "
                +hostFrontEnd+"newPassword?token="+passwordResetToken.getToken());
        emailSenderService.sendEmail(mailMessage);
    }

    @Override
    public ResponseEntity<?> getUserClient(Long id) {
        Optional<User> users = userRepository.findById(id);
        User user = users.get();
        return ResponseEntity.ok(userConverter.toDTOClient(user));
    }

    @Override
    public ResponseEntity<?> updateAvatar(Long id, MultipartFile file) {
        User user = userRepository.findById(id).get();
        if(user == null){
            return new ResponseEntity("USER_NOT_FOUND", HttpStatus.BAD_REQUEST);
        }
        try{
            user.setAvatarLink(uploadFile(file, user));
        }catch (Exception e){
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
        User saved = userRepository.save(user);
        return ResponseEntity.ok(userConverter.toDTOClient(saved));
    }

    //upload file to s3
    public String uploadFile(MultipartFile file, User user){
        String ext = "."+ FilenameUtils.getExtension(file.getOriginalFilename());
        String name = "User_"+user.getId();
//        this.amazonS3ClientService.deleteFileFromS3Bucket(user.getAvatarLink());
            this.amazonS3ClientService.uploadFileToS3Bucket(user.getId(), file, "User_" + user.getId(), ext, true);
        return bucketLink + name + ext;
    }

    //create password reset token
    public ResponseEntity<?> createPasswordResetToken(String mail) {
        User user = userRepository.findByMail(mail);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("EMAIL_NOT_EXIST");
        }
        String tokenStr = UUID.randomUUID().toString();
        PasswordResetToken token = new PasswordResetToken();
        token.setToken(tokenStr);
        token.setUid(user.getId());
        passwordTokenRepository.save(token);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getMail()); //user email
        mailMessage.setSubject("Reset password!");
        mailMessage.setFrom(fromMail);
        mailMessage.setText("To reset your account's password, please click here : "
                +hostFrontEnd+"resetPassword?token="+token.getToken());
        return new ResponseEntity("SENT_RESET_EMAIL", HttpStatus.OK);
    }
}
