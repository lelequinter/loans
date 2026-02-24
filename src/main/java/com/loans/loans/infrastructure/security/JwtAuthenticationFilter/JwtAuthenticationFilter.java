package com.loans.loans.infrastructure.security.JwtAuthenticationFilter;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.loans.loans.domain.model.User;
import com.loans.loans.domain.repository.UserRepository;
import com.loans.loans.infrastructure.security.JwtService.JwtService;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserRepository userRepository;

  // Endpoints que NO requieren JWT
  private static final List<String> PUBLIC_URLS = List.of(
      "/auth/login",
      "/users");

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String path = request.getServletPath();

    // Saltar endpoints públicos
    if (PUBLIC_URLS.contains(path)) {
      filterChain.doFilter(request, response);
      return;
    }

    // Extraer token de cookies
    String token = null;
    if (request.getCookies() != null) {
      for (Cookie cookie : request.getCookies()) {
        if ("SESSION_TOKEN".equals(cookie.getName())) {
          token = cookie.getValue();
          break;
        }
      }
    }

    // 🔴 Si no hay token → 401
    if (token == null) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token missing");
      return;
    }

    // Validar token
    if (!jwtService.isTokenValid(token)) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
      return;
    }

    // Extraer usuario
    User user = userRepository.findByEmail(jwtService.extractEmail(token)).orElse(null);

    if (user == null) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not found");
      return;
    }

    // Usuario inactivo → 403
    if (!user.getIs_active()) {
      response.sendError(HttpServletResponse.SC_FORBIDDEN, "User inactive");
      return;
    }

    // Set Authentication en SecurityContext
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
        user.getEmail(),
        null,
        List.of(new SimpleGrantedAuthority(user.getRole())));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    filterChain.doFilter(request, response);
  }
}

