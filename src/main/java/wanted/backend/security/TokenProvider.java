package wanted.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import wanted.backend.user.UserEntity;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenProvider { // 유저정보를 받아 JWT 생성
    @Value("${jwt.secret.key}")
    private String salt;
    private Key secretKey;

    // 만료시간 : 1Hour
    private final long exp = 1000L * 60 * 60;

    public String createToken(UserEntity userEntity) {

        Date now = new Date();
        // JWT Token 생성
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, salt)
                // payload에 들어갈 내용
                .setSubject(Integer.toString(userEntity.getId()))
                .setIssuer("wantedInternship")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + exp))
                .compact();
    }
    public String validateAndGetUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(salt)  // 서명
                .parseClaimsJws(token)      // token과 비교
                .getBody();

        return claims.getSubject();         // id 리턴
    }
}
