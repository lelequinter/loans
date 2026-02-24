package com.loans.loans.application.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.loans.loans.domain.model.LoanStatus;

public record LoanResponse(
        UUID id,
        BigDecimal amount,
        Integer termInMonths,
        LoanStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String userEmail
) {}
