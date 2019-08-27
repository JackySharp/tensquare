package com.tensquare.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;

@Data
@ConfigurationProperties("jwt.config")
public class JwtUtil {
    private String key;
    private long ttl;
    //1.创建jwt，返回得到的token
    public String createJwt(String id,String subject,String roles){
        long l = System.currentTimeMillis();
        Date dt = new Date(l);
        Date expir = new Date(l + ttl);
        //1.1）得一一个jwtBuilder对象
        JwtBuilder jwtBuilder = Jwts.builder().setId(id)
                .setIssuedAt(dt)
                .signWith(SignatureAlgorithm.HS256, key)
                .setSubject(subject)
                .setExpiration(expir)
                .claim("roles",roles);
        //1.2)返回
        return jwtBuilder.compact();
    }
    //2.解析token
    public Claims parseToken(String token){
        //System.out.println("key = " + key);
        Claims claims = Jwts.parser().setSigningKey(key)
                .parseClaimsJws(token).getBody();
        return claims;
    }
}
