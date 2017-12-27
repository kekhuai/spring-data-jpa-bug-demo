package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.domain.User;

public interface UserService {
    
    User save(User user);
    
    Page<User> getUsers(Pageable pageable);

}
