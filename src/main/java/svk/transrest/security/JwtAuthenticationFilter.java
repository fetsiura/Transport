package svk.transrest.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import svk.transrest.exception.TransAPIException;
import svk.transrest.payload.response.ResponseError;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Purpose:
 * The JwtAuthenticationFilter class is responsible for intercepting incoming HTTP requests, extracting JWT tokens from the request headers, and validating them.
 * Upon successful validation, it sets up the authentication context for the current request by associating the authenticated user details with the security context.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider tokenProvider;
    private final UserDetailsService detailsService;

    /**
     * This method is invoked for each incoming HTTP request. It extracts the JWT token from the request headers, validates it using the JwtTokenProvider,
     * and loads the user details using the UserDetailsService. If the token is valid and the user details are successfully loaded,
     * it creates an Authentication object (UsernamePasswordAuthenticationToken) and sets it in the security context (SecurityContextHolder).
     * This establishes the authenticated user's identity for the current request.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = getTokenFromRequest(request);
            if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
                String username = tokenProvider.getUsername(token);
                UserDetails userDetails = detailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (TransAPIException ex) {
            response.setStatus(ex.getHttpStatus().value());
            response.setContentType("application/json");
            ObjectMapper objectMapper = new ObjectMapper();
            ResponseError error = new ResponseError(ex.getHttpStatus(), ex.getMessage(), LocalDateTime.now().toString());
            String json = objectMapper.writeValueAsString(error);
            response.getWriter().write(json);
            response.getWriter().flush();
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
