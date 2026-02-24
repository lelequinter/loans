package com.loans.loans.infrastructure.controller.LoanController;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loans.loans.application.dto.request.CreateLoanRequest;
import com.loans.loans.application.dto.request.UpdateLoanStatusRequest;
import com.loans.loans.application.dto.response.LoanResponse;
import com.loans.loans.application.usecase.loan.CreateLoanUsecase;
import com.loans.loans.application.usecase.loan.UpdateLoanStatusUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/loan")
@RequiredArgsConstructor
public class LoanController {

  private final CreateLoanUsecase createLoanUsecase;
  private final UpdateLoanStatusUseCase updateLoanStatusUseCase;

  // @GetMapping
  // public ResponseEntity<UserResponse> getLoan() {
  // System.out.println("Autenticado correctamente, accediendo a endpoint
  // protegido");
  // return ResponseEntity.status(HttpStatus.CREATED).body(new
  // UserResponse("Prueba", "Prueba de creación de préstamo"));
  // }

  @PostMapping
  public ResponseEntity<LoanResponse> create(@RequestBody CreateLoanRequest request) {

    LoanResponse response = createLoanUsecase.execute(request);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PatchMapping("/{id}/status")
  public ResponseEntity<LoanResponse> updateStatus(
      @PathVariable UUID id,
      @RequestBody UpdateLoanStatusRequest request) {

    UpdateLoanStatusRequest updatedRequest = new UpdateLoanStatusRequest(id, request.status());

    LoanResponse response = updateLoanStatusUseCase.execute(updatedRequest);

    return ResponseEntity.ok(response);
  }
}
