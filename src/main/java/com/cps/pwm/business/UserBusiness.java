package com.cps.pwm.business;

import com.cps.pwm.model.User;
import com.cps.pwm.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserBusiness {
    private final UserService userService;

    public UserBusiness(UserService userService) {
        this.userService = userService;
    }

    public boolean createUser(String name, String email) {
        try {
            if (userService.getUserByEmail(email).isPresent()) {
                throw new IllegalArgumentException("Email already exists");
            }

            User user = User.builder()
                    .name(name)
                    .email(email)
                    .build();

            userService.createUser(user);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save user", e);
        }
    }
}
