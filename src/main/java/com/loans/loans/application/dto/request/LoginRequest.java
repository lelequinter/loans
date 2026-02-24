package com.loans.loans.application.dto.request;

public record LoginRequest(
    String email,
    String password
) {}
