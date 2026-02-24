package com.loans.loans.application.dto.Result;

public record LoginResult(
    String name,
    String email,
    String role,
    String token
) {}
