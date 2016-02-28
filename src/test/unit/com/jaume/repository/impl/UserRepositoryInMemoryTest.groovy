package com.jaume.repository.impl

import com.jaume.domain.User
import com.jaume.domain.UserRoles
import com.jaume.domain.builder.UserBuilder
import spock.lang.Specification


class UserRepositoryInMemoryTest extends Specification {
    private UserRepositoryInMemory userRepositoryInMemory;

    void setup() {
        userRepositoryInMemory = new UserRepositoryInMemory();
    }

    def "Should contain user after insert"() {
        given:
        User user = UserBuilder.anUser()
                .withUserName("userNameTest")
                .withPassword("1234")
                .addRole(UserRoles.PAGE_1.toString())
                .build()
        when:
        userRepositoryInMemory.insert(user)
        then:
        userRepositoryInMemory.findByUserName("userNameTest") == user
    }

    def "Should change password after update password"() {
        given:
        User user = UserBuilder.anUser()
                .withUserName("userNameTest")
                .withPassword("1234")
                .addRole(UserRoles.PAGE_1.toString())
                .build()
        when:
        userRepositoryInMemory.insert(user)
        user.setPassword("12345")
        userRepositoryInMemory.update(user)
        then:
        userRepositoryInMemory.findByUserName("userNameTest").getPassword() == "12345"
    }

    def "Should return null if delete the user"() {
        given:
        User user = UserBuilder.anUser()
                .withUserName("userNameTest")
                .withPassword("1234")
                .addRole(UserRoles.PAGE_1.toString())
                .build()
        when:
        userRepositoryInMemory.insert(user)
        userRepositoryInMemory.delete(user.getUserName())
        then:
        userRepositoryInMemory.findByUserName("userNameTest") == null
    }
}
