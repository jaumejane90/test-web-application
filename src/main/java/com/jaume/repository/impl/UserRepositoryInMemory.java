package com.jaume.repository.impl;

import java.util.concurrent.ConcurrentMap;

import com.google.common.collect.MapMaker;
import com.jaume.domain.User;
import com.jaume.domain.UserRoles;
import com.jaume.domain.builder.UserBuilder;
import com.jaume.repository.UserRepository;

public class UserRepositoryInMemory implements UserRepository {
    private ConcurrentMap<String, User> userTable;

    public UserRepositoryInMemory() {
        userTable = new MapMaker().makeMap();
        initDB();
    }

    public void insert(User user) {
        if (!userTable.containsKey(user.getUserName())) {
            userTable.put(user.getUserName(), user);
        }
    }

    public void update(User user) {
        User userDB = userTable.get(user.getUserName());
        if (userDB != null) {
            userTable.put(user.getUserName(), user);
        }
    }

    public void delete(String userName) {
        userTable.remove(userName);
    }

    public User findByUserName(String userName) {
        return userTable.get(userName);
    }

    private void initDB() {
        this.insert(UserBuilder.anUser()
                .withUserName("UserRole1")
                .withPassword("123")
                .addRole(UserRoles.PAGE_1.toString())
                .build());

        this.insert(UserBuilder.anUser()
                .withUserName("UserRole2")
                .withPassword("123")
                .addRole(UserRoles.PAGE_2.toString())
                .build());

        this.insert(UserBuilder.anUser()
                .withUserName("UserRole3")
                .withPassword("123")
                .addRole(UserRoles.PAGE_3.toString())
                .build());

        this.insert(UserBuilder.anUser()
                .withUserName("UserRole1And3")
                .withPassword("123")
                .addRole(UserRoles.PAGE_1.toString())
                .addRole(UserRoles.PAGE_3.toString())
                .build());

        this.insert(UserBuilder.anUser()
                .withUserName("UserRoleAdmin")
                .withPassword("123")
                .addRole(UserRoles.ADMIN.toString())
                .build());
    }
}
