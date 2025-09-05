package co.com.bancolombia.usecase.auth;

import co.com.bancolombia.model.auth.gateways.PasswordHashService;
import co.com.bancolombia.model.auth.gateways.TokenProvider;
import co.com.bancolombia.model.auth.gateways.UserAuthRepository;
import co.com.bancolombia.model.exceptions.InvalidCredentialsException;
import co.com.bancolombia.usecase.auth.interfaces.IAuthenticationUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class AuthenticationUseCase implements IAuthenticationUseCase {

    private final UserAuthRepository userAuthRepository;
    private final PasswordHashService passwordHashService;
    private final TokenProvider tokenProvider;

    @Override
    public Mono<LoginResult> login(String email, String rawPassword) {
        return userAuthRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new InvalidCredentialsException()))
                .flatMap(user ->
                        passwordHashService.matches(rawPassword, user.getPasswordHash())
                                .filter(Boolean::booleanValue)
                                .switchIfEmpty(Mono.error(new InvalidCredentialsException()))
                                .flatMap(ignored ->
                                        tokenProvider.generate(user)
                                                .map(token -> new LoginResult(
                                                        token,
                                                        tokenProvider.getExpirationSeconds(),
                                                        user
                                                ))
                                )
                );
    }
}