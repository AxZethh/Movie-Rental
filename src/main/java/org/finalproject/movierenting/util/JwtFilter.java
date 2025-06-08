package org.finalproject.movierenting.util;


import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.finalproject.movierenting.entity.Consumer;
import org.finalproject.movierenting.enums.ConsumerPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String auth = request.getHeader("Authorization");

        if(auth != null && auth.startsWith("Bearer ") && auth.length() > 7) {
            String token = auth.substring(7);
            Claims claim = jwtUtil.validateToken(token);
            String email = claim.getSubject();
            ConsumerPermission permission = ConsumerPermission.valueOf(claim.get("role", String.class));

            Authentication authentication = getAuthentication(permission, email);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        filterChain.doFilter(request, response);
    }

    private Authentication getAuthentication(ConsumerPermission permission, String email) {
        List<GrantedAuthority> auth = new ArrayList<>();

        if(permission.equals(ConsumerPermission.CUSTOMER)) {
            auth.add(new SimpleGrantedAuthority("CUSTOMER"));
        }
        else if(permission.equals(ConsumerPermission.ADMIN)) {
            auth.add(new SimpleGrantedAuthority("CUSTOMER"));
            auth.add(new SimpleGrantedAuthority("ADMIN"));
        }
        else if(permission.equals(ConsumerPermission.SUPER_ADMIN)) {
            auth.add(new SimpleGrantedAuthority("CUSTOMER"));
            auth.add(new SimpleGrantedAuthority("ADMIN"));
            auth.add(new SimpleGrantedAuthority("SUPER_ADMIN"));
        }

        return new UsernamePasswordAuthenticationToken(email, "", auth);
    }
}
