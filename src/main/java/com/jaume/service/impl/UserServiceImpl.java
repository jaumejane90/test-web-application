package com.jaume.service.impl;


import com.jaume.constants.HttpServer;
import com.jaume.domain.User;
import com.jaume.domain.UserRoles;
import com.jaume.repository.UserRepository;
import com.jaume.service.UserService;

public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void insert(User user) {
        userRepository.insert(user);
    }

    public void update(User user) {
        userRepository.update(user);
    }

    public void deleteByUserName(String userName) {
        userRepository.delete(userName);
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public boolean authenticate(String userName, String pwd) {
        User user = userRepository.findByUserName(userName);
        if (user != null && user.getPassword().equals(pwd)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validateRole(String userName, UserRoles userRole) {
        User user = userRepository.findByUserName(userName);
        return user.getRoles().contains(UserRoles.ADMIN.toString()) || user.getRoles().contains(userRole.toString());
    }

    public boolean validateOperation(String userName, String requestMethod) {
        User user = userRepository.findByUserName(userName);
        return user.getRoles().contains(UserRoles.ADMIN.toString()) || requestMethod.equals(HttpServer.Method.GET);
    }
}
