package co.com.bancolombia.r2dbc.reactiverepositoryuser;

import co.com.bancolombia.model.auth.UserAuth;
import co.com.bancolombia.model.auth.gateways.UserAuthRepository;
import co.com.bancolombia.model.exceptions.DataIntegrityException;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.r2dbc.entities.RolEntity;
import co.com.bancolombia.r2dbc.entities.UserEntity;
import co.com.bancolombia.r2dbc.reactiverepositoryrol.RolReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserAuthReactiveRepositoryAdapter implements UserAuthRepository {

    private final UserReactiveRepository userRepo;
    private final RolReactiveRepository rolRepo;

    @Override
    public Mono<UserAuth> findByEmail(String email) {
        return userRepo.findByEmail(email)
                .flatMap(u ->
                        rolRepo.findById(u.getIdRol())
                                .switchIfEmpty(Mono.error(new DataIntegrityException(
                                        "Usuario " + u.getDocumentId() + " tiene id_rol=" + u.getIdRol() + " inexistente")))
                                .map(r -> toDomain(u, r))
                );
    }

    private UserAuth toDomain(User u, RolEntity r) {
        return UserAuth.builder()
                .id(u.getUserId())
                .email(u.getEmail())
                .passwordHash(u.getPasswordHash())
                .role(r.getNombre())
                .permissions(List.of())
                .build();
    }
}

