package com.stockmarketapp.usermanagement.service;

import com.stockmarketapp.usermanagement.entities.User;
import com.stockmarketapp.usermanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Cacheable("users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Cacheable("user")
    public User getUserById(String id) {
        Optional<User> optional = userRepository.findById(id);

        if (optional.isPresent()) {
            return optional.get();
        } else {
            System.out.println("User not found with id: " + id);
            return null;
        }

    }

    @Caching(evict = {
            @CacheEvict(value = "user", allEntries = true),
            @CacheEvict(value = "users", allEntries = true)})
    public String save(User user) throws Exception {

        userRepository.save(user);
//        String subject = "Your account has been created";
//        String body = "Welcome to FSE Flight booking Application !";
//        String email = user.getEmail();
//        emailUtil.send(email, subject, body);
        return "SuccessFully added";
    }

    @Caching(evict = {
            @CacheEvict(value = "user", allEntries = true),
            @CacheEvict(value = "users", allEntries = true)})
    public User modifyUser(String id, User user) {
        if (userRepository.existsById(id)) {
            user.setId(id);
            return userRepository.save(user);
        } else {
            System.out.println("User not found with id: " + id);
            return null;
        }
    }

    @Caching(evict = {
            @CacheEvict(value = "user", allEntries = true),
            @CacheEvict(value = "users", allEntries = true)})
    public boolean removeUser(String id) {
        userRepository.deleteById(id);
        return true;
    }

//    public boolean ValidateUser(String id, String password) {
//        return userRepository.validateUser(id, password);
//    }
}
