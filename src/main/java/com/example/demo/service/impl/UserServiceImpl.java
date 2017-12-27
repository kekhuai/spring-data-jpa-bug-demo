package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

}
