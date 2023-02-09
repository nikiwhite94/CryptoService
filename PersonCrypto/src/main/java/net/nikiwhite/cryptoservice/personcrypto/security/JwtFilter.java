package net.nikiwhite.cryptoservice.personcrypto.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    //todo keycloak

    private static final String BEARER = "Bearer_";
    private static final String AUTHORIZATION = "Authorization";
    private static final String INCORRECT_TOKEN = "Неверный токен";

    private final JwtUtil jwtUtil;
    private final PersonDetailsService personDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader(AUTHORIZATION);

        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith(BEARER)) {
            String jwtToken = authHeader.substring(7);

            if (jwtToken.isBlank()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, INCORRECT_TOKEN);
            } else {
                try {
                    String email = jwtUtil.validateTokenAndRetrieveClaim(jwtToken);
                    UserDetails userDetails = personDetailsService.loadUserByUsername(email);

                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    userDetails.getPassword(),
                                    userDetails.getAuthorities()
                            );

                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                } catch (JWTVerificationException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, INCORRECT_TOKEN);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
