package com.prod.services;


import com.prod.entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.modelmapper.internal.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;

public class JwtService {
    @Value("{jwt.secret}")
    String mySecretKey="hqwdjnqdkwqdnqwdjnqndwjdnqdu382u9uedj3ij9m21e9wj129ue129ewjs921prm31ne2udg13yd3i1hdu1oj2ond12he3ud1hio12ws";

    public String generateJWT(User user){
        Key key= Keys.hmacShaKeyFor(mySecretKey.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("email",user.getUsername())
                .claim("role",user.getAuthorities())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+6000000))
                .signWith(key)
                .compact();
    }
}
