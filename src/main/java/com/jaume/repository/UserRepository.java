package com.jaume.repository;

import com.jaume.domain.User;

public interface UserRepository {

    void insert(User user);

    void update(User user);

    void delete(String userName);

    User findByUserName(String userName);
}
