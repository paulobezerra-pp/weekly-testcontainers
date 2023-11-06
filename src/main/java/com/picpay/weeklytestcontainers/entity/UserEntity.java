package com.picpay.weeklytestcontainers.entity;

public record UserEntity(
    Long id,
    String name
) {
    public UserEntity(final String name) {
        this(null, name);
    }
}
