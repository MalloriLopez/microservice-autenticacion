package co.com.bancolombia.security;

import co.com.bancolombia.model.auth.gateways.PasswordHashService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
@Profile({"dev","local"}) // se activa sólo en estos perfiles
public class NoOpHashService implements PasswordHashService {
    @Override public Mono<String> encode(String raw) { return Mono.just(raw); }
    @Override public Mono<Boolean> matches(String raw, String stored) { return Mono.just(Objects.equals(raw, stored)); }
}




