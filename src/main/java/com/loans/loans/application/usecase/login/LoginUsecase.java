package com.loans.loans.application.usecase.login;



import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.loans.loans.application.dto.Result.LoginResult;
import com.loans.loans.application.dto.request.LoginRequest;
import com.loans.loans.domain.model.User;
import com.loans.loans.domain.repository.UserRepository;
import com.loans.loans.infrastructure.security.JwtService.JwtService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginUsecase {
  
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;

  public LoginResult execute(LoginRequest request) {

    User user = userRepository.findByEmail(request.email())
        .orElseThrow(() -> new IllegalArgumentException("Correo o contraseña inválidos"));

    if (!passwordEncoder.matches(request.password(), user.getPassword())) {
      throw new IllegalArgumentException("Correo o contraseña inválidos");
    }

    String token = jwtService.generateToken(user.getEmail(), user.getRole());

    return new LoginResult(user.getName(), user.getEmail(), user.getRole(), token);
  }
}
