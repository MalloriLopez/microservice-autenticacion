package co.com.bancolombia.model.auth.gateways;
import reactor.core.publisher.Mono;

public interface PasswordHashService {
    Mono<String> encode(String raw);
    Mono<Boolean> matches(String raw, String hashed);
}
