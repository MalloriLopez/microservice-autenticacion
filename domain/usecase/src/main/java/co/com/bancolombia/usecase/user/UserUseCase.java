package co.com.bancolombia.usecase.user;

import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserRepository;
import co.com.bancolombia.usecase.user.interfaces.IUserUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase implements IUserUseCase {

    private final UserRepository userRepository;

    @Override
    public Mono<User> saveUser(User user) {
        return userRepository.save(user);
    }
}
