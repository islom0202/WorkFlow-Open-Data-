package org.example.projectdevtool.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.projectdevtool.entity.Users;
import org.example.projectdevtool.repo.UsersRepo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class JwtUtil {
    private final UserDetailsService userDetailsService;
    private final UsersRepo usersRepo;

    public JwtUtil(UserDetailsService userDetailsService, UsersRepo usersRepo) {
        this.userDetailsService = userDetailsService;
        this.usersRepo = usersRepo;
    }


    public String generateToken(UserDetails userDetails) {
        // Fetch userId from your user service or repository
        Users user = usersRepo.findByLogin(userDetails.getUsername())
                .orElseThrow(()->new NoSuchElementException("error in fetching user data"));
        Long userId = user.getId();

        String roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))  // 24 hours
                .claim("role", roles)
                .claim("userId", userId)
                .signWith(getKey())
                .compact();
    }


    public boolean isValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Key getKey() {
        byte[] bytes = Decoders.BASE64.decode("1234567891234567891234567891234567891234567891234567891234567890");
        return Keys.hmacShaKeyFor(bytes);
    }
//    public Authentication getUser(String token) {
//
//        Claims claims = Jwts.parserBuilder().
//                setSigningKey(getKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//
//        String roles = claims.get("role", String.class);
//        List<SimpleGrantedAuthority> authorities = roles != null
//                ? Arrays.stream(roles.split(","))
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList())
//                : List.of();
//
//        UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());
//
//        return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
//    }
    public Authentication getUser(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        String roles = claims.get("role", String.class);
        List<SimpleGrantedAuthority> authorities = roles != null
                ? Arrays.stream(roles.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList())
                : List.of();

        UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());

        return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
    }

}
