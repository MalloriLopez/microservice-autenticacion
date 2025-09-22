package co.com.bancolombia.api.user;

import co.com.bancolombia.api.dto.response.UserSummaryResponse;
import co.com.bancolombia.usecase.user.UserQueryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserSummaryHandler {

    private final UserQueryUseCase userQueryUseCase;

    //@PreAuthorize("hasAnyRole('ASESOR','CLIENTE')")
    public Mono<ServerResponse> getByEmail(ServerRequest request) {
        String email = request.pathVariable("email");
        return userQueryUseCase.findSummaryByEmail(email)
                .map(u -> new UserSummaryResponse(u.email(), u.name(), u.baseSalary()))
                .flatMap(dto -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(dto))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}

