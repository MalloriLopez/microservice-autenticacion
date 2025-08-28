package co.com.bancolombia.api;

import co.com.bancolombia.model.user.User;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.com.bancolombia.api.dto.request.CreateUserRecord;
import co.com.bancolombia.api.mapper.UserDTOMapper;
import co.com.bancolombia.usecase.user.UserUseCase;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class Handler {

private final RequestValidator requestValidator;
private  final UserUseCase userUseCase;
private final UserDTOMapper userDTOMapper;
private final TransactionalOperator transactionalOperator;

    public Mono<ServerResponse> saveUseCase(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(CreateUserRecord.class)
        .flatMap(requestValidator::validateUser)
        .map(userDTOMapper::toModel)
        .flatMap(userRequest -> {
            log.info("Usuario recibido: {}", userRequest.toString());
            if (userRequest.getBirthDate() != null && userRequest.getBirthDate().isAfter(LocalDate.now())) {
                log.warn("Fecha de nacimiento inválida para usuario: {}", userRequest.getEmail());
                throw new IllegalArgumentException("La fecha de nacimiento no puede ser futura");
            }
            return userUseCase.saveUser(userRequest)
                    .doOnSuccess(saved -> log.info("Usuario guardado: {}", saved.getEmail()));
        }).flatMap(savedUser -> ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(userDTOMapper.toResponse(savedUser))
         .as(transactionalOperator::transactional) );
    }
}
