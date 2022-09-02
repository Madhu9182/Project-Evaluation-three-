package org.capgemini.jwt.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static java.util.Arrays.stream;

public class JWTFilter extends OncePerRequestFilter {


    private JWTUtility jwtUtility;

    public JWTFilter(JWTUtility jwtUtility) {
        this.jwtUtility = jwtUtility;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String[] allowedURLS = {"/ap/login"};
        return stream(allowedURLS).anyMatch((url)-> new AntPathRequestMatcher(url).matches(request));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getMethod().equalsIgnoreCase("options"))
        {
            response.setStatus(200);
        }else {
            String Authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
            if(Authorization != null || Authorization.startsWith("Bearer"))
            {
                String token = Authorization.substring(7);
                String username = jwtUtility.getUserNameFromToken(token);
                if(jwtUtility.isTokenValid(token))
                {
                    List<GrantedAuthority> authority  = this.jwtUtility.getAuthorityFromToken(token);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authority);
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                    return;
                }
                else{
                    response.setStatus(403);
                }
            }
            else{
                response.setStatus(500);
            }
        }
    }
}
