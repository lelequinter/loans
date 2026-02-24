package com.loans.loans.application.usecase.user;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.loans.loans.application.dto.request.CreateUserRequest;
import com.loans.loans.application.dto.response.UserResponse;
import com.loans.loans.domain.model.User;
import com.loans.loans.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateUserUsecase {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserResponse execute(CreateUserRequest request) {

    if (userRepository.findByEmail(request.email()).isPresent()) {
      throw new IllegalArgumentException("Ya existe un usuario con ese correo");
    }

    User user = new User();
    user.setName(request.name());
    user.setEmail(request.email());
    user.setPassword(passwordEncoder.encode(request.password()));
    user.setRole(request.role());

    User savedUser = userRepository.save(user);
    return new UserResponse(savedUser.getName(), savedUser.getEmail());
  }
}

