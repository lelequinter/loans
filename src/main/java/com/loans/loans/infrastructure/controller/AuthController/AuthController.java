package com.loans.loans.infrastructure.controller.AuthController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loans.loans.application.dto.Result.LoginResult;
import com.loans.loans.application.dto.request.LoginRequest;
import com.loans.loans.application.dto.response.LoginResponse;
import com.loans.loans.application.usecase.LoginUsecase;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginUsecase loginUseCase;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpServletResponse response) {
      
      LoginResult loginResult = loginUseCase.execute(request);

      Cookie cookie = new Cookie("SESSION_TOKEN", loginResult.token());
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60); //? cookie valida en el navegador por 1 día
        cookie.setAttribute("SameSite", "Strict");

        response.addCookie(cookie);
      
      return ResponseEntity.status(HttpStatus.OK).body(new LoginResponse(loginResult.name(), loginResult.email(), loginResult.role()));
    }
}
