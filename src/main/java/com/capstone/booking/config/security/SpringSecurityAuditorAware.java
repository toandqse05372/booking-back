package com.capstone.booking.config.security;

import com.capstone.booking.config.SetupDataLoader;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.Optional;

//find out who's having token
@Component
public class SpringSecurityAuditorAware implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        //khi program khởi tạo mặc định user là 1-admin
        if (!SetupDataLoader.alreadySetup) {
            return Optional.of(0l);
        } else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return Optional.of(0l);
            }
            if (authentication.getPrincipal() == "anonymousUser") {
                return Optional.of(0l);
            }
            return Optional.of(((UserPrincipal) authentication.getPrincipal()).getUserId());
        }

    }

}
