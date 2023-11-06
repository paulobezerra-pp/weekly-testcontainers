package com.picpay.weeklytestcontainers.repository;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.picpay.weeklytestcontainers.entity.UserEntity;


@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testInsert() {
        final var userEntity = new UserEntity("Geraldo de Rivia");
        final var result = userRepository.save(userEntity);
        assertNotNull(result.id());
    }
}
