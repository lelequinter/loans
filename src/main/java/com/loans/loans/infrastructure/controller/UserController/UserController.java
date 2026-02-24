package com.loans.loans.infrastructure.controller.UserController;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loans.loans.application.dto.request.CreateUserRequest;
import com.loans.loans.application.dto.response.UserResponse;
import com.loans.loans.application.usecase.CreateUserUsecase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
  private final CreateUserUsecase createUserUseCase;

  @PostMapping
  public ResponseEntity<UserResponse> create(@RequestBody CreateUserRequest requestUser) {
    
    UserResponse userResponse = createUserUseCase.execute(requestUser);

    return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
  }
}

