package com.example.sistemaweb.sistemaweb.Seguridad;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.sistemaweb.sistemaweb.Entities.Usuario;
import com.example.sistemaweb.sistemaweb.Repositories.UsuarioRepository;
import com.example.sistemaweb.sistemaweb.Services.UsuarioService;
/* 
@Configuration
@EnableWebSecurity
public class SeguridadConfig {

    @Autowired
    private UsuarioService usuarioService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder amb = http.getSharedObject(AuthenticationManagerBuilder.class);
        amb.userDetailsService(usuarioService).passwordEncoder(passwordEncoder());
        return amb.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login","/", "/error","/indexf","/probar-conexion3", "/css/**", "/js/**").permitAll()
                .anyRequest().authenticated()
            )
            // en SeguridadConfig.securityFilterChain(HttpSecurity http):
            .formLogin(form -> form
                //.loginPage("/login.html")
                .loginPage("/login")
              // URL personalizada (no .html)
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
            )

            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login.html")
            );
        return http.build();
    }
}
*/
@Configuration
@EnableWebSecurity
public class SeguridadConfig {

    @Autowired
    private UsuarioService usuarioService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder amb = http.getSharedObject(AuthenticationManagerBuilder.class);
        amb.userDetailsService(usuarioService)
           .passwordEncoder(passwordEncoder());
        return amb.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
          .csrf(csrf -> csrf.disable())
          .authorizeHttpRequests(auth -> auth
            // Sólo estas rutas quedan abiertas:
            .requestMatchers(
                "/login",            // formulario de login
                "/error",            // página de error
                "/css/**",           // recursos estáticos
                "/js/**",
                "/alumno_grupo",
                "/img/**"         // si usas carpeta de imágenes
            ).permitAll()
            // TODO: si hay más carpetas de recursos, añádelas aquí
            .anyRequest().authenticated()   // ¡TODO lo demás exige login!
          )
          .formLogin(form -> form
            .loginPage("/login")
            .loginProcessingUrl("/login")
            .defaultSuccessUrl("/indexF", true)  // tras login va a /indexF
            .failureUrl("/login?error=true")
          )
          .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login?logout")
          );
        return http.build();
    }
}
