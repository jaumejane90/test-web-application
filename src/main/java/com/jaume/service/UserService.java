package com.jaume.service;


import com.jaume.domain.User;
import com.jaume.domain.UserRoles;

public interface UserService {

    void insert(User user);

    void update(User user);

    void deleteByUserName(String userName);

    User findByUserName(String userName);

    boolean authenticate(String userName, String pwd);

    boolean validateRole(String userName, UserRoles userRole);

    boolean validateOperation(String userName, String requestMethod);
}
