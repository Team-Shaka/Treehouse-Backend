package org.example.tree.global.security.provider;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.member.service.MemberQueryService;
import org.example.tree.global.exception.*;
import org.example.tree.global.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.GeneralSecurityException;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {
    public static final String ACCESS_TOKEN_HEADER = "Authorization";
    public static final String REFRESH_TOKEN_HEADER = "Refresh";
    private static final String BEARER_PREFIX = "Bearer ";

    @Value("${jwt.access-token-validity-in-seconds}")
    private Long TOKEN_TIME;

    @Value("${jwt.refresh-token-validity-in-seconds}")
    private Long REFRESH_TOKEN_TIME;


    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    // signature 생성 알고리즘 /토큰을 인코딩하거나 유효성 검증을 할 때 사용하는 고유한 암호화 코드
    // signature -> jwt가 변경되지 않았음을 검증하는 역할
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;


    // key 객체 생성
    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
        int h = 7;
    }

    // header 토큰을 가져오기
    public String resolveToken(HttpServletRequest request, String tokenType) {
        String headerName;

        if ("Access".equals(tokenType)) {
            headerName = ACCESS_TOKEN_HEADER; // 올바른 상수 이름이어야 합니다.
        } else if ("Refresh".equals(tokenType)) {
            headerName = REFRESH_TOKEN_HEADER; // 올바른 상수 이름이어야 합니다.
        } else {
            return null; // 또는 적절한 예외를 던질 수 있습니다.
        }

        String token = request.getHeader(headerName);
        if (StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {
            return token.substring(7);
        }
        return null;
    }

    /*
        TODO 아래 코드를 더 잘 짤수 있는지 나중에 수정을 시도할 것
     */
    // 토큰 생성
    public String createAccessToken(Member member, Collection<? extends GrantedAuthority> authorities) {
        Date date = new Date();

        return
                Jwts.builder()
                        .setSubject(member.getId().toString())
                        .claim("authoritiesKey", authorities)
                        .claim("userName", member.getUserName())
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    public String createRefreshToken(){

        Date data = new Date();

        Claims claims = Jwts.claims();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(data)
                .setExpiration(new Date(data.getTime() + REFRESH_TOKEN_TIME))
                .signWith(key, signatureAlgorithm)
                .compact();
    }


    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token, 만료된 JWT token 입니다.");
            throw new JwtAuthenticationException(AuthErrorCode.TOKEN_EXPIRED);

        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        catch (io.jsonwebtoken.security.SignatureException e){
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다");
        }
        return false;
    }

    public void validateRefreshToken(String refreshToken){
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(refreshToken);
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
            throw new AuthException(AuthErrorCode.INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token, 만료된 JWT 리프레시 token 입니다.");
            throw new AuthException(AuthErrorCode.REFRESH_TOKEN_EXPIRED);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
            throw new AuthException(AuthErrorCode.INVALID_TOKEN);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
            throw new AuthException(AuthErrorCode.INVALID_TOKEN);
        }
        catch (io.jsonwebtoken.security.SignatureException e){
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다");
            throw new AuthException(AuthErrorCode.INVALID_TOKEN);
        }
    }

    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        // jwt 토큰을 파싱해서 그 안에 들어있는 클레임을 추출하는 코드
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String getMemberIdFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    public Authentication getAuthentication(String token){
        Claims claims =
                Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("authoritiesKey").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

}


