package com.loans.loans.application.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateLoanRequest(
        BigDecimal amount,
        Integer termInMonths,
        UUID userId
) {}