package org.capgemini.jwt.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;


import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Component
public class JWTUtility {

    private JWTVerifier jwtVerifier(){
        return JWT.require(Algorithm.HMAC512(Base64.getEncoder().encode("ngvkt".getBytes(StandardCharsets.UTF_8)))).build();
    }
    public String generateToken(String username, List<String> authority){
        String[] claims = getClaims(authority);
        return JWT.create().withArrayClaim("Authorities",claims)
                .withIssuer("Mumbai Dabbawala")
                .withExpiresAt(new Date(System.currentTimeMillis() + 20 * 60 * 60 * 1000))
                .withSubject(username)
                .sign(Algorithm.HMAC512(Base64.getEncoder().encode("ngvkt".getBytes(StandardCharsets.UTF_8))));

    }

    private String[] getClaims(List<String> authority) {
        return authority.toArray(new String[0]);
    }


    public boolean isTokenValid(String token){
        return(isTokenExpired(token) && isUserNameValid(token));
    }

    private boolean isUserNameValid(String token) {
        return !jwtVerifier().verify(token).getSubject().isEmpty();
    }

    private boolean isTokenExpired(String token) {
        return jwtVerifier().verify(token).getExpiresAt().after(new Date());
    }

    //To Access Authorities
    public List<GrantedAuthority> getAuthorityFromToken(String token){
        String[] claims = getClaimsFromToken(token);
        return stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }


    private String[] getClaimsFromToken(String token) {
        return jwtVerifier().verify(token).getClaim("Authorities").asArray(String.class);
    }


    public String getUserNameFromToken(String token){
        return jwtVerifier().verify(token).getSubject();
    }
}
