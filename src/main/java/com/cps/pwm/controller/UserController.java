package com.cps.pwm.controller;

import com.cps.pwm.business.UserBusiness;
import com.cps.pwm.dto.request.UserRequest;
import com.cps.pwm.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserBusiness userBusiness;
    private final UserService userService;

    public UserController(UserBusiness userBusiness, UserService userService) {
        this.userBusiness = userBusiness;
        this.userService = userService;
    }

    // Create User
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequest request) {
        boolean success = userBusiness.createUser(request.getName(), request.getEmail());
        if (success) {
            return ResponseEntity.ok("User created successfully");
        } else {
            return ResponseEntity.internalServerError().body("Failed to create user");
        }
    }

    // Delete User
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
