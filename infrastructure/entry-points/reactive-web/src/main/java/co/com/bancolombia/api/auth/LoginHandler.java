package co.com.bancolombia.api.auth;

import co.com.bancolombia.api.RequestValidator;
import co.com.bancolombia.api.dto.request.LoginRecordRequestDTO;
import co.com.bancolombia.api.dto.request.LoginRecordRequestDTO;
import co.com.bancolombia.api.mapper.LoginDTOMapper;
import co.com.bancolombia.usecase.auth.interfaces.IAuthenticationUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginHandler {

    private final RequestValidator requestValidator;
    private final IAuthenticationUseCase authUseCase;
    private final LoginDTOMapper mapper;

    public Mono<ServerResponse> login(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(LoginRecordRequestDTO.class)
                .flatMap(requestValidator::validate)
                .flatMap(dto -> {
                    log.info("Intento de login para email: {}", dto.email());
                    return authUseCase.login(dto.email(), dto.password())
                            .doOnSuccess(res -> log.info("Login OK para usuario: {}", res.user().getEmail()));
                })
                .map(mapper::toResponse)
                .flatMap(resp -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(resp));
    }
}
