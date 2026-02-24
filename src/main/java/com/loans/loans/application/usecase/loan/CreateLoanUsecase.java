package com.loans.loans.application.usecase.loan;

import org.springframework.stereotype.Component;

import com.loans.loans.application.dto.request.CreateLoanRequest;
import com.loans.loans.application.dto.response.LoanResponse;
import com.loans.loans.domain.model.Loan;
import com.loans.loans.domain.model.LoanStatus;
import com.loans.loans.domain.model.User;
import com.loans.loans.domain.repository.LoanRepository;
import com.loans.loans.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateLoanUsecase {

  private final LoanRepository loanRepository;
  private final UserRepository userRepository;

  public LoanResponse execute(CreateLoanRequest request) {

    if (request.amount() == null || request.amount().compareTo(java.math.BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("El monto debe ser mayor a 0");
    }

    if (request.termInMonths() == null || request.termInMonths() <= 0) {
      throw new IllegalArgumentException("El plazo debe ser mayor a 0 meses");
    }

    User user = userRepository.findById(request.userId())
        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

    Loan loan = new Loan();
    loan.setAmount(request.amount());
    loan.setTermInMonths(request.termInMonths());
    loan.setStatus(LoanStatus.PENDING);
    loan.setUser(user);

    Loan savedLoan = loanRepository.save(loan);

    LoanResponse loanResponse = new LoanResponse(
        savedLoan.getId(),
        savedLoan.getAmount(),
        savedLoan.getTermInMonths(),
        savedLoan.getStatus(),
        savedLoan.getCreatedAt(),
        savedLoan.getUpdatedAt(),
        savedLoan.getUser().getEmail());
    return loanResponse;
  }
}
