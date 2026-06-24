package com.backend.ecommerce_store.services;

import com.backend.ecommerce_store.exceptions.UserNotFoundException;
import com.backend.ecommerce_store.models.User;
import com.backend.ecommerce_store.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user){
        return userRepository.save(user);
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();

    }
    public User getUserById(Integer id){
        return userRepository.findById(id)
            .orElseThrow( () ->
                        new UserNotFoundException("User not found"));

    }
    public User updateUser(Integer id, User user){
        User existingUser =
                userRepository.findById(id)
                        .orElseThrow( () -> new UserNotFoundException("user not found"));
        existingUser.setEmail(user.getEmail());
        existingUser.setName(user.getName());
        existingUser.setPassword(user.getPassword());
        return this.userRepository.save(existingUser);
    }
    public void deleteUser(Integer id){
        userRepository.deleteById(id);
    }
}
