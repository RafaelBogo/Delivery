package com.delivery.delivery.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.delivery.delivery.model.Usuario;
import com.delivery.delivery.repository.UsuarioRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FiltroToken extends OncePerRequestFilter {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (request.getRequestURI().equals("/api/usuarios/cadastro") || request.getRequestURI().equals("/api/usuarios/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        var authenticationHeader = request.getHeader("Token");

        if (authenticationHeader != null) {
            Optional<Usuario> usuario = usuarioRepository.findByChave(authenticationHeader);

            if (usuario.isPresent()) {
                var authentication = new UsernamePasswordAuthenticationToken(usuario.get(), null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                return;
            }
        } else {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return;
        }

        filterChain.doFilter(request, response);
    }
}
