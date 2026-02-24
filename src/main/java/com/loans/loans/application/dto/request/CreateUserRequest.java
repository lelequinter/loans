package com.loans.loans.application.dto.request;

public record CreateUserRequest(
        String name,
        String email,
        String password,
        String role
) {}
