package com.picpay.weeklytestcontainers.repository;

import static java.util.Objects.requireNonNull;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.picpay.weeklytestcontainers.entity.UserEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserEntity save(final UserEntity userEntity) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        final String sql = """
                insert into tb_users(id,
                    name)
                values (
                    nextval('users_seq'),
                    :name
                ) returning id
                """;
        final SqlParameterSource parameters = new MapSqlParameterSource("name", userEntity.name());
        if(jdbcTemplate.update(sql, parameters, keyHolder, new String[]{"id"}) > 0) {
            return new UserEntity(requireNonNull(keyHolder.getKey()).longValue(), userEntity.name());
        }
        return userEntity;
    }

}
