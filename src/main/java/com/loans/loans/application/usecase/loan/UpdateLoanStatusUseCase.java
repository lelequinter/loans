package com.loans.loans.application.usecase.loan;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.loans.loans.application.dto.request.UpdateLoanStatusRequest;
import com.loans.loans.application.dto.response.LoanResponse;
import com.loans.loans.domain.model.Loan;
import com.loans.loans.domain.model.LoanStatus;
import com.loans.loans.domain.model.User;
import com.loans.loans.domain.repository.LoanRepository;
import com.loans.loans.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UpdateLoanStatusUseCase {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;

    public LoanResponse execute(UpdateLoanStatusRequest request) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        User admin = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        if (!admin.getRole().equals("ROLE_ADMIN")) {
            throw new IllegalStateException("No tiene permisos para aprobar/rechazar préstamos");
        }

        Loan loan = loanRepository.findById(request.loanId())
                .orElseThrow(() -> new IllegalArgumentException("Préstamo no encontrado"));

        if (loan.getStatus() != LoanStatus.PENDING) {
            throw new IllegalStateException("Solo se pueden gestionar préstamos en estado PENDING");
        }

        if (request.status() != LoanStatus.APPROVED &&
            request.status() != LoanStatus.REJECTED) {
            throw new IllegalArgumentException("Estado inválido");
        }

        loan.setStatus(request.status());

        Loan updatedLoan = loanRepository.save(loan);

        return new LoanResponse(
                updatedLoan.getId(),
                updatedLoan.getAmount(),
                updatedLoan.getTermInMonths(),
                updatedLoan.getStatus(),
                updatedLoan.getCreatedAt(),
                updatedLoan.getUpdatedAt(),
                updatedLoan.getUser().getEmail()
        );
    }
}
