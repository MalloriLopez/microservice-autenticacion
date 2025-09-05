package co.com.bancolombia.usecase.auth.interfaces;

import co.com.bancolombia.usecase.auth.AuthenticationUseCase;
import co.com.bancolombia.usecase.auth.LoginResult;
import reactor.core.publisher.Mono;

public interface IAuthenticationUseCase {
    Mono<LoginResult> login(String email, String password);
}
