package com.example.sistemaweb.sistemaweb.Seguridad;

import com.example.sistemaweb.sistemaweb.Services.CustomUserDetailsService;
import com.example.sistemaweb.sistemaweb.Seguridad.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    private static final String[] EXCLUDED_PATHS = {
        "/auth/loginApi",
        "/login",
        "/",
        "/css/",
        "/indexF",
        "/js/",
        "/images/",
        "/img/",
        "/lib/",
        "/scss/"
};





    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();

        // Excluir rutas públicas
        for (String excluded : EXCLUDED_PATHS) {
            if (path.startsWith(excluded)) {
                filterChain.doFilter(request, response);
                return;
            }
        }




        // Obtiene el header
        String authHeader = request.getHeader("Authorization");
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        

        String token = authHeader.substring(7);
        String username = jwtService.extraerUsername(token);


        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }
}
