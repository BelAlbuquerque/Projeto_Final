package br.com.meli.desafio_final.configJwt;

import br.com.meli.desafio_final.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {
    @Value("${jwt.expiration}")
    private String timeToExpired;

    @Value("${jwt.secret}")
    private String secret;


    public String generateToken(Authentication authentication) {
        User userLogged = (User) authentication.getPrincipal();
        Date today = new Date();
        Long exp = Long.valueOf(timeToExpired);
        Date expiration = new Date(today.getTime()+exp);

        return Jwts.builder()
                .setSubject(userLogged.getEmail())
                .setIssuedAt(today)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }


    public boolean isValidToken(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        }catch(Exception e) {
            return false;
        }
    }

    public String getUserEmail(String token) {
        Jws<Claims> jwsClaims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
        Claims body = jwsClaims.getBody();
        return body.getSubject();
    }

}
