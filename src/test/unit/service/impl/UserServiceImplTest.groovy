package service.impl

import constants.HttpServer
import domain.UserRoles
import domain.builder.UserBuilder
import repository.UserRepository
import spock.lang.Specification

class UserServiceImplTest extends Specification {
    public static final String TEST_USER_NAME = "testUserName"
    private UserServiceImpl userService;

    void setup() {

    }

    def "ValidateRole for user with role -> PAGE_1"() {
        given:
        UserRepository userRepository = Mock(UserRepository.class)
        userRepository.findByUserName(TEST_USER_NAME) >> UserBuilder.anUser()
                .withUserName(TEST_USER_NAME)
                .addRole(UserRoles.PAGE_1.toString())
                .build();
        userService = new UserServiceImpl(userRepository)
        expect:
        result == userService.validateRole(TEST_USER_NAME, role);
        where:
        role             | result
        UserRoles.PAGE_1 | true
        UserRoles.PAGE_2 | false
        UserRoles.PAGE_3 | false
    }

    def "ValidateRole for user with role -> PAGE_1 and PAGE_3"() {
        given:
        UserRepository userRepository = Mock(UserRepository.class)
        userRepository.findByUserName(TEST_USER_NAME) >> UserBuilder.anUser()
                .withUserName(TEST_USER_NAME)
                .addRole(UserRoles.PAGE_1.toString())
                .addRole(UserRoles.PAGE_3.toString())
                .build();
        userService = new UserServiceImpl(userRepository)
        expect:
        result == userService.validateRole(TEST_USER_NAME, role);
        where:
        role             | result
        UserRoles.PAGE_1 | true
        UserRoles.PAGE_2 | false
        UserRoles.PAGE_3 | true
    }

    def "ValidateRole for user with role -> ADMIN"() {
        given:
        UserRepository userRepository = Mock(UserRepository.class)
        userRepository.findByUserName(TEST_USER_NAME) >> UserBuilder.anUser()
                .withUserName(TEST_USER_NAME)
                .addRole(UserRoles.ADMIN.toString())
                .build();
        userService = new UserServiceImpl(userRepository)
        expect:
        result == userService.validateRole(TEST_USER_NAME, role);
        where:
        role             | result
        UserRoles.PAGE_1 | true
        UserRoles.PAGE_2 | true
        UserRoles.PAGE_3 | true
    }

    def "ValidateOperation for user with role -> PAGE_1"() {
        given:
        UserRepository userRepository = Mock(UserRepository.class)
        userRepository.findByUserName(TEST_USER_NAME) >> UserBuilder.anUser()
                .withUserName(TEST_USER_NAME)
                .addRole(UserRoles.PAGE_1.toString())
                .build();
        userService = new UserServiceImpl(userRepository)
        expect:
        result == userService.validateOperation(TEST_USER_NAME, method);
        where:
        method                   | result
        HttpServer.Method.GET    | true
        HttpServer.Method.POST   | false
        HttpServer.Method.PUT    | false
        HttpServer.Method.DELETE | false
    }

    def "ValidateOperation for user with role -> PAGE_2"() {
        given:
        UserRepository userRepository = Mock(UserRepository.class)
        userRepository.findByUserName(TEST_USER_NAME) >> UserBuilder.anUser()
                .withUserName(TEST_USER_NAME)
                .addRole(UserRoles.PAGE_2.toString())
                .build();
        userService = new UserServiceImpl(userRepository)
        expect:
        result == userService.validateOperation(TEST_USER_NAME, method);
        where:
        method                   | result
        HttpServer.Method.GET    | true
        HttpServer.Method.POST   | false
        HttpServer.Method.PUT    | false
        HttpServer.Method.DELETE | false
    }

    def "ValidateOperation for user with role -> PAGE_3"() {
        given:
        UserRepository userRepository = Mock(UserRepository.class)
        userRepository.findByUserName(TEST_USER_NAME) >> UserBuilder.anUser()
                .withUserName(TEST_USER_NAME)
                .addRole(UserRoles.PAGE_3.toString())
                .build();
        userService = new UserServiceImpl(userRepository)
        expect:
        result == userService.validateOperation(TEST_USER_NAME, method);
        where:
        method                   | result
        HttpServer.Method.GET    | true
        HttpServer.Method.POST   | false
        HttpServer.Method.PUT    | false
        HttpServer.Method.DELETE | false
    }

    def "ValidateOperation for user with role -> PAGE_1AndPAGE_3"() {
        given:
        UserRepository userRepository = Mock(UserRepository.class)
        userRepository.findByUserName(TEST_USER_NAME) >> UserBuilder.anUser()
                .withUserName(TEST_USER_NAME)
                .addRole(UserRoles.PAGE_1.toString())
                .addRole(UserRoles.PAGE_3.toString())
                .build();
        userService = new UserServiceImpl(userRepository)
        expect:
        result == userService.validateOperation(TEST_USER_NAME, method);
        where:
        method                   | result
        HttpServer.Method.GET    | true
        HttpServer.Method.POST   | false
        HttpServer.Method.PUT    | false
        HttpServer.Method.DELETE | false
    }

    def "ValidateOperation for user with role -> ADMIN"() {
        given:
        UserRepository userRepository = Mock(UserRepository.class)
        userRepository.findByUserName(TEST_USER_NAME) >> UserBuilder.anUser()
                .withUserName(TEST_USER_NAME)
                .addRole(UserRoles.ADMIN.toString())
                .build();
        userService = new UserServiceImpl(userRepository)
        expect:
        result == userService.validateOperation(TEST_USER_NAME, method);
        where:
        method                   | result
        HttpServer.Method.GET    | true
        HttpServer.Method.POST   | true
        HttpServer.Method.PUT    | true
        HttpServer.Method.DELETE | true
    }
}
