package com.loans.loans.infrastructure.controller.LoanController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loans.loans.application.dto.request.CreateLoanRequest;
import com.loans.loans.application.dto.response.LoanResponse;
import com.loans.loans.application.usecase.loan.CreateLoanUsecase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/loan")
@RequiredArgsConstructor
public class LoanController {

  private final CreateLoanUsecase createLoanUsecase;

  // @GetMapping
  // public ResponseEntity<UserResponse> getLoan() {
  //   System.out.println("Autenticado correctamente, accediendo a endpoint protegido");
  //   return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponse("Prueba", "Prueba de creación de préstamo"));
  // }

  @PostMapping
    public ResponseEntity<LoanResponse> create(@RequestBody CreateLoanRequest request) {

        LoanResponse response = createLoanUsecase.execute(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

