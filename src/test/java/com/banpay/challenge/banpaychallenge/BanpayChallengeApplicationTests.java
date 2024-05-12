package com.banpay.challenge.banpaychallenge;

import com.banpay.challenge.banpaychallenge.models.User;
import com.banpay.challenge.banpaychallenge.security.services.UserService;
import org.hibernate.validator.internal.constraintvalidators.bv.AssertTrueValidator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The BanpayChallengeApplicationTests class is the test class for the BanpayChallengeApplication.
 * It is responsible for testing the functionality of the application by running various test cases.
 * The class uses the SpringBootTest annotation to specify the web environment as RANDOM_PORT.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BanpayChallengeApplicationTests {

    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @Autowired
    private UserService userService;
    /**
     * This method configures the properties of the PostgreSQL container connection for testing purposes.
     */
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    /**
     * This method starts the PostgreSQL container before all tests.
     */
    @BeforeAll
    public static void beforeAll() {
        List<String> portBindings = new ArrayList<>();
        portBindings.add("5432:5432"); // hostPort:containerPort
        postgres.setPortBindings(portBindings);
        postgres.start();
    }

    /**
     * This method stops the PostgreSQL container after all tests.
     */
    @AfterAll
    public static void afterAll() {
        postgres.stop();
    }

    @Test
    void contextLoads() {
        System.out.println("BANPAY CHALLENGE APPLICATION TESTS: contextLoads");
        postgres.getPortBindings().forEach((p) -> {
            System.setProperty("getPortBindings", p);
        });
        Optional<User> admin = userService.getUserByUsername("orutra971");

        assertTrue(admin.isPresent());
    }

}
