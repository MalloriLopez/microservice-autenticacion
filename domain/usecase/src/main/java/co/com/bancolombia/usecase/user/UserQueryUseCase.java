package co.com.bancolombia.usecase.user;

import co.com.bancolombia.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserQueryUseCase {

    public record Summary(String email, String name, Double baseSalary) {}

    private final UserRepository userRepository;

    public Mono<Summary> findSummaryByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(u -> new Summary(u.getEmail(), u.getName(), u.getBaseSalary()));
    }
}

