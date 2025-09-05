package co.com.bancolombia.security;

import co.com.bancolombia.model.auth.UserAuth;
import co.com.bancolombia.model.auth.gateways.TokenProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Component
@Primary
public class JwtTokenProvider implements TokenProvider {

    private final SecretKey key;
    private final long expSeconds;
    private final String issuer;

    public JwtTokenProvider(
            @Value("${security.jwt.secret}") String base64Secret,
            @Value("${security.jwt.expiration-seconds:3600}") long expSeconds,
            @Value("${security.jwt.issuer:autenticacion}") String issuer
    ) {
        this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(base64Secret));
        this.expSeconds = expSeconds;
        this.issuer = issuer;
    }

    @Override
    public Mono<String> generate(UserAuth user) {
        Instant now = Instant.now();
        String token = Jwts.builder()
                .subject(user.getId())
                .issuer(issuer)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(expSeconds)))
                .claim("email", user.getEmail())
                .claim("role", user.getRole())
                .claim("permissions", user.getPermissions())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        return Mono.just(token);
    }

    @Override
    public long getExpirationSeconds() {
        return expSeconds;
    }
}
