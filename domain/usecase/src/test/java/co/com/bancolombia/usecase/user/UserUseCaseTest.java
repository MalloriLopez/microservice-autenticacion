package co.com.bancolombia.usecase.user;


import co.com.bancolombia.model.exceptions.DuplicateEmailException;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.any;

public class UserUseCaseTest {

   @Mock
   private UserRepository userRepository;
   private UserUseCase userUseCase;

   @BeforeEach
   void setup() {
      MockitoAnnotations.openMocks(this);
      userUseCase = new UserUseCase(userRepository);
   }

   @Test
   void saveUser_EmailNoExiste_GuardaOk() {

      User user = mock(User.class);
      when(user.getEmail()).thenReturn("maria@example.com");
      when(userRepository.existsByEmail("maria@example.com")).thenReturn(Mono.just(false));
      when(userRepository.save(user)).thenReturn(Mono.just(user));

      Mono<User> response = userUseCase.saveUser(user);

      StepVerifier.create(response)
              .expectNext(user)
              .verifyComplete();
   }

   @Test
   void saveUser_EmailExiste_LanzaDuplicateEmailException() {

      User user = mock(User.class);
      when(user.getEmail()).thenReturn("maria@example.com");
      when(userRepository.existsByEmail("maria@example.com")).thenReturn(Mono.just(true));

      Mono<User> response = userUseCase.saveUser(user);

      StepVerifier.create(response)
              .expectError(DuplicateEmailException.class)
              .verify();

      verify(userRepository, never()).save(any());
   }

   @Test
   void saveUser_ErrorEnExistsByEmail_PropagaError() {

      User user = mock(User.class);
      when(user.getEmail()).thenReturn("maria@example.com");
      when(userRepository.existsByEmail("maria@example.com"))
              .thenReturn(Mono.error(new RuntimeException("Falla en BD")));

      Mono<User> response = userUseCase.saveUser(user);

      StepVerifier.create(response)
              .expectErrorMatches(e -> e instanceof RuntimeException && e.getMessage().contains("Falla en BD"))
              .verify();

      verify(userRepository, never()).save(any());
   }
}

