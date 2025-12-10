package com.example.sistemaweb.sistemaweb.Seguridad;

import com.example.sistemaweb.sistemaweb.Entities.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.example.sistemaweb.sistemaweb.Entities.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // -------------------------
    // GENERAR TOKEN
    // -------------------------
    public String generarToken(Usuario usuario) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", usuario.getId());
        claims.put("rol", "ROLE_" + usuario.getRol());
        claims.put("activo", usuario.isActivo());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(usuario.getUsername()) // Spring usa username
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24h
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // -------------------------
    // EXTRAER USERNAME (SUBJECT)
    // -------------------------
    public String extraerUsername(String token) {
        return obtenerClaims(token).getSubject();
    }
    

    // -------------------------
    // EXTRAER CLAIMS
    // -------------------------
    public Claims obtenerClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getKey())
                .parseClaimsJws(token)
                .getBody();
    }

    // -------------------------
    // VALIDAR TOKEN
    // -------------------------
    public boolean esTokenValido(String token, Usuario usuario) {
        String username = extraerUsername(token);
        return username.equals(usuario.getUsername()) && !estaExpirado(token);
    }

    private boolean estaExpirado(String token) {
        return obtenerClaims(token).getExpiration().before(new Date());
    }
}
