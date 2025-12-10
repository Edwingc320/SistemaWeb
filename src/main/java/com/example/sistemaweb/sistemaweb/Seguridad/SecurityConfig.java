package com.example.sistemaweb.sistemaweb.Seguridad;

import com.example.sistemaweb.sistemaweb.Services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    /*    
    @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                // Login y recursos estáticos
                .requestMatchers(
                        "/auth/loginApi",
                        "/login",
                        "/",
                        "/index",
                        "/index.html",
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/img/**",
                        "/lib/**",
                        "/scss/**"
                ).permitAll()

                // HOME accesible solo si tiene token
                .requestMatchers("/indexF").hasAnyRole("ADMIN", "JEFE_DIVISION", "MAESTRO", "TUTOR")

                // ADMIN y JEFE_DIVISION
                .requestMatchers("/indexGrupo", "/anadirPeriodo", "/agregarProfesores", "/Usuarios")
                        .hasAnyRole("ADMIN", "JEFE_DIVISION")

                // ADMIN, MAESTRO, JEFE_DIVISION
                .requestMatchers("/calificacionesGrupo")
                        .hasAnyRole("ADMIN", "MAESTRO", "JEFE_DIVISION")

                // ADMIN, MAESTRO, TUTOR, JEFE_DIVISION
                .requestMatchers("/calificacionesIndividual", "/gestionarAlumnos")
                        .hasAnyRole("ADMIN", "MAESTRO", "TUTOR", "JEFE_DIVISION")

                .anyRequest().authenticated()
                )

                .sessionManagement(sess ->
                sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
        }

        */ 
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF temporalmente
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // Permitir todas las rutas
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public AuthenticationProvider authenticationProvider(CustomUserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
