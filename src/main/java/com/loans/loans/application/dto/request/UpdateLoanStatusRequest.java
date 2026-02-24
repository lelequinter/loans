package com.loans.loans.application.dto.request;

import java.util.UUID;

import com.loans.loans.domain.model.LoanStatus;

public record UpdateLoanStatusRequest(
        UUID loanId,
        LoanStatus status
) {}
