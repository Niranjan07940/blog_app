package com.niran.demo.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {
    String secretKey="";
    public JWTService() throws NoSuchAlgorithmException {
        KeyGenerator keyGen=KeyGenerator.getInstance("HmacSHA256");
        SecretKey sk =keyGen.generateKey();
        secretKey=Base64.getEncoder().encodeToString(sk.getEncoded());
    }
    public String generateToken(String username) {
        Map<String,Object> data =new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(data)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60*600*30))
                .and()
                .signWith(getKey())
                .compact();
    }
    private SecretKey getKey() {
        byte [] keyBytes= Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String jwtToken) {

        return extractClaims(jwtToken, Claims::getSubject);
    }

    private <T> T extractClaims(String jwtToken, Function<Claims, T> claimResolver) {
        Claims claims=extractAllClaim(jwtToken);
        return claimResolver.apply(claims);

    }

    private Claims extractAllClaim(String jwtToken) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build().parseSignedClaims(jwtToken)
                .getPayload();
    }


    public boolean validateToken(String jwtToken, UserDetails userDetails) {
        String username=extractUserName(jwtToken);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken));
    }

    private boolean isTokenExpired(String jwtToken) {

        return extractExpiration(jwtToken).before(new Date());
    }

    private Date extractExpiration(String jwtToken) {

        return extractClaims(jwtToken,Claims::getExpiration);
    }
}
