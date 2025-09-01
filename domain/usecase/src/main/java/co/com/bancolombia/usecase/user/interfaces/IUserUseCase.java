package co.com.bancolombia.usecase.user.interfaces;

import co.com.bancolombia.model.user.User;
import reactor.core.publisher.Mono;

public interface IUserUseCase {

    Mono<User> saveUser(User user);

    Mono<Boolean> existsUserByEmail(String email);

}
