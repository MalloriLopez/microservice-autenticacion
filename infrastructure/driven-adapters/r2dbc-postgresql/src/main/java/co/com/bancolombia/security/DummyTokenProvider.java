package co.com.bancolombia.security;

import co.com.bancolombia.model.auth.UserAuth;
import co.com.bancolombia.model.auth.gateways.TokenProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@Profile({"dev","local"})
public class DummyTokenProvider implements TokenProvider {

    private static final long EXP_SECONDS = 3600L;

    @Override
    public Mono<String> generate(UserAuth user) {
        return Mono.just("FAKE-" + UUID.randomUUID());
    }

    @Override
    public long getExpirationSeconds() {
        return EXP_SECONDS;
    }
}
