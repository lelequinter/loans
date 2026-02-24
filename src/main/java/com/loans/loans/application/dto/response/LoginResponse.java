package com.loans.loans.application.dto.response;

public record LoginResponse(
  String name,
  String email,
  String role
) {}
