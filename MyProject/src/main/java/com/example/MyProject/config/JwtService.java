package com.example.MyProject.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service //Turn it into a managed Bean
public class JwtService {

    private static final String SECRET_KEY = "4428472B4B6250655367566B5970337336763979244226452948404D63516654"; //Generated at allkeysgenerator.com > Encryption key > 256-bit

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername()) //Subject will be username/email
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) //Token duration 24 hours.
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact(); //Generates and returns the token
    }

    //Validates if token belongs to userDetails
    public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
        final String username = extractUsername(jwtToken); //Username or email
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken));
    }

    private boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    private Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }

    //Takes jwtToken and returns a username/email based on that token
    public String extractUsername(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject); //The subject should be the email/username of user
    }

    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String jwtToken) { //Extract info from token

        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey()) ////SigningKey is a secret that is used to digitally sign the JWT. The signing key is used to create the signature part of the JWT, which is used to verify that the sender of the JWT is who it claims to be and ensure that the message was not changed along the way.
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
