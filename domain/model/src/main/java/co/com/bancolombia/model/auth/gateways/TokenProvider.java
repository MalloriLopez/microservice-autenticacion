package co.com.bancolombia.model.auth.gateways;

import co.com.bancolombia.model.auth.UserAuth;
import reactor.core.publisher.Mono;

public interface TokenProvider {
    Mono<String> generate(UserAuth user);
    long getExpirationSeconds();
}
