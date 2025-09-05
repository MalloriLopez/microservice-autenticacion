package co.com.bancolombia.model.auth.gateways;

import co.com.bancolombia.model.auth.UserAuth;
import co.com.bancolombia.model.user.gateways.UserRepository;
import reactor.core.publisher.Mono;

public interface UserAuthRepository {

    Mono<UserAuth> findByEmail(String email);
}
