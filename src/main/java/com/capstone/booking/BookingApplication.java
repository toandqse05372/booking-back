package com.capstone.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BookingApplication{

    public static void main(String[] args) {
        SpringApplication.run(BookingApplication.class, args);
    }

//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//    @Override
//    public void run(String... args) throws Exception {
//        // Khi chương trình chạy
//        // Insert vào csdl một user.
//        User user = new User();
//        user.setUsername("loda3");
//        user.setPassword(passwordEncoder.encode("loda"));
//        user.setRole("USER");
//        userRepository.save(user);
//        System.out.println(user);
//    }
}
