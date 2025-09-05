package co.com.bancolombia.usecase.auth;

import co.com.bancolombia.model.auth.UserAuth;
import co.com.bancolombia.model.auth.gateways.PasswordHashService;
import co.com.bancolombia.model.auth.gateways.TokenProvider;
import co.com.bancolombia.model.auth.gateways.UserAuthRepository;
import co.com.bancolombia.model.exceptions.InvalidCredentialsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

public class AuthenticationUseCaseTest {

    private UserAuthRepository userAuthRepository;
    private PasswordHashService passwordHashService;
    private TokenProvider tokenProvider;
    private AuthenticationUseCase authenticationUseCase;

    @BeforeEach
    void setup() {
        userAuthRepository = mock(UserAuthRepository.class);
        passwordHashService = mock(PasswordHashService.class);
        tokenProvider = mock(TokenProvider.class);
        authenticationUseCase = new AuthenticationUseCase(userAuthRepository, passwordHashService, tokenProvider);
    }

    @Test
    void login_WithValidCredentials_ReturnsTokenAndUser() {

        String email = "test@example.com";
        String rawPassword = "password123";
        String hashedPassword = "hashedPass";
        String token = "jwt-token";

        UserAuth user = UserAuth.builder()
                .email(email)
                .passwordHash(hashedPassword)
                .build();

        when(userAuthRepository.findByEmail(email)).thenReturn(Mono.just(user));
        when(passwordHashService.matches(rawPassword, hashedPassword)).thenReturn(Mono.just(true));
        when(tokenProvider.generate(user)).thenReturn(Mono.just(token));
        when(tokenProvider.getExpirationSeconds()).thenReturn(3600L);


        Mono<LoginResult> result = authenticationUseCase.login(email, rawPassword);


        StepVerifier.create(result)
                .expectNextMatches(res ->
                        res.accessToken().equals(token) &&
                                res.expiresIn() == 3600L &&
                                res.user().equals(user)
                )
                .verifyComplete();

        verify(userAuthRepository).findByEmail(email);
        verify(passwordHashService).matches(rawPassword, hashedPassword);
        verify(tokenProvider).generate(user);
    }


    @Test
    void login_UserNotFound_ThrowsInvalidCredentialsException() {

        String email = "notfound@example.com";
        when(userAuthRepository.findByEmail(email)).thenReturn(Mono.empty());


        Mono<LoginResult> result = authenticationUseCase.login(email, "any");


        StepVerifier.create(result)
                .expectError(InvalidCredentialsException.class)
                .verify();

        verify(userAuthRepository).findByEmail(email);
        verify(passwordHashService, never()).matches(any(), any());
        verify(tokenProvider, never()).generate(any());
    }

    @Test
    void login_InvalidPassword_ThrowsInvalidCredentialsException() {

        String email = "test@example.com";
        String rawPassword = "wrongpass";
        String hashedPassword = "hashedPass";

        UserAuth user = UserAuth.builder()
                .email(email)
                .passwordHash(hashedPassword)
                .build();

        when(userAuthRepository.findByEmail(email)).thenReturn(Mono.just(user));
        when(passwordHashService.matches(rawPassword, hashedPassword)).thenReturn(Mono.just(false));


        Mono<LoginResult> result = authenticationUseCase.login(email, rawPassword);


        StepVerifier.create(result)
                .expectError(InvalidCredentialsException.class)
                .verify();

        verify(userAuthRepository).findByEmail(email);
        verify(passwordHashService).matches(rawPassword, hashedPassword);
        verify(tokenProvider, never()).generate(any());
    }
}

