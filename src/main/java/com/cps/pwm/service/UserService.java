package com.cps.pwm.service;

import com.cps.pwm.model.User;
import com.cps.pwm.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Create
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Read - all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Read - by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Read - by email
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Update
    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(updatedUser.getName());
                    user.setEmail(updatedUser.getEmail());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    // Delete
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

