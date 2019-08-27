package com.zelin.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;

public class ParseJwtToken {

    //解析基本token信息
    @Test
    public void testParseToken() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjM0NTYiLCJpYXQiOjE1NjU3Njk1NzYsInN1YiI6ImFkbWluIn0.xO6HgR6h0l9CKDFtfdoT9JR5XkfjOqnDywI-J0PVMB8";
        //解析得到Claims对象
        Claims claims = Jwts.parser().setSigningKey("alibaba")
                .parseClaimsJws(token).getBody();
        //从Claims对象提取信息
        System.out.println("id=" + claims.getId());
        System.out.println("issuedAt=" + claims.getIssuedAt());
        System.out.println("subject=" + claims.getSubject());
    }

    //解析包含过期时间的token信息
    @Test
    public void testParseTokenWithExpire() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NjY2NjYiLCJzdWIiOiJ1c2VyIiwiaWF0IjoxNTY1NzcwMTI3LCJleHAiOjE1NjU3NzA3Mjd9.ezldMT8XkUmn_yox5nOxgg2FXgjmmrjudg7yF4tY6Os";
        //解析得到Claims对象
        Claims claims = Jwts.parser().setSigningKey("alibaba")
                .parseClaimsJws(token).getBody();
        //从Claims对象提取信息
        System.out.println("id=" + claims.getId());
        System.out.println("issuedAt=" + claims.getIssuedAt());
        System.out.println("subject=" + claims.getSubject());
        System.out.println("expiration=" + claims.getExpiration());
    }

    //解析带有数据的token
    @Test
    public void testParseTokenWithExpireAndData() {
        String token = "eyJhbGciOiJub25lIn0.eyJqdGkiOiI4ODgiLCJzdWIiOiJqYWNreSIsImlhdCI6MTU2NTc3MDE1OCwiZXhwIjoxNTY1NzcwMjE4LCJyb2xlcyI6ImFkbWluIn0.";
        //解析得到Claims对象
        Claims claims = Jwts.parser().setSigningKey("alibaba")
                .parseClaimsJwt(token).getBody();
        //从Claims对象提取信息
        System.out.println("id=" + claims.getId());
        System.out.println("issuedAt=" + claims.getIssuedAt());
        System.out.println("subject=" + claims.getSubject());
        System.out.println("expiration=" + claims.getExpiration());
        System.out.println("roles=" + claims.get("roles"));
    }
}
