package com.example.demo.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping(path = "/api/v1.0/users")
public class UserResource {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);
    
    private final UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<User> save(@RequestBody User user) {
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }
    
    @GetMapping(produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Page<User> getUsers(Pageable pageable) {
        Sort sort = pageable.getSort();
        PageRequest pageRequest = null;
        if (null != sort) {
            List<Sort.Order> orders = new ArrayList<>();
            for (Sort.Order order : sort) {
                orders.add(order);
            }
            Optional<Order> firstOrder = orders.stream().findFirst();
            if (firstOrder.isPresent()) {
                Order order = firstOrder.get();
                Order nullsLast = order.nullsLast();
//                pageable.getSort().and(new Sort(nullsLast));
                pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(nullsLast));
            }
        } else {
            pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize());
        }
        return userService.getUsers(pageRequest);
    }

}
