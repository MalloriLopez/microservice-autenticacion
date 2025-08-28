package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.request.CreateUserRecord;
import co.com.bancolombia.api.dto.response.UserRecordResponse;
import co.com.bancolombia.api.mapper.UserDTOMapper;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.usecase.user.UserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigInteger;
import java.time.LocalDate;

import static org.mockito.Mockito.*;

public class HandlerTest {

    @Mock
    private RequestValidator requestValidator;

    @Mock
    private UserUseCase userUseCase;

    @Mock
    private UserDTOMapper userDTOMapper;

    @Mock
    private TransactionalOperator transactionalOperator;

    private Handler handler;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        handler = new Handler(requestValidator, userUseCase, userDTOMapper, transactionalOperator);
    }

    @Test
    void saveUseCase_UsuarioValido_RetornaOK() {

        CreateUserRecord dto = new CreateUserRecord(
                "123456",
                "Maria",
                "Lopez",
                LocalDate.of(1990, 1, 1),
                "Calle 123",
                "maria@example.com",
                "3001234567",
                BigInteger.valueOf(5000000)
        );

        User userModel = mock(User.class);
        when(userModel.getName()).thenReturn("Maria");
        when(userModel.getBirthDate()).thenReturn(LocalDate.of(1990, 1, 1));
        when(userModel.getEmail()).thenReturn("maria@example.com");

        UserRecordResponse response = new UserRecordResponse(
                "id123",                 // userId
                "123456789",                   // documentId
                "Maria",                       // name
                "Lopez",                       // lastname
                LocalDate.of(1990, 1, 1),      // birthDate
                "Cra 10 #20-30",               // address
                "maria@example.com",          // email
                "3101234567",                  // phone
                BigInteger.valueOf(5_000_000) // baseSalary
        );

        ServerRequest request = mock(ServerRequest.class);

        when(request.bodyToMono(CreateUserRecord.class)).thenReturn(Mono.just(dto));
        when(requestValidator.validateUser(dto)).thenReturn(Mono.just(dto));
        when(userDTOMapper.toModel(dto)).thenReturn(userModel);
        when(userUseCase.saveUser(userModel)).thenReturn(Mono.just(userModel));
        when(userDTOMapper.toResponse(userModel)).thenReturn(response);
        when(transactionalOperator.transactional(any(Mono.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Mono<ServerResponse> result = handler.saveUseCase(request);

        StepVerifier.create(result)
                .expectNextMatches(serverResponse -> {
                    MediaType contentType = serverResponse.headers().getContentType();
                    return serverResponse.statusCode().is2xxSuccessful()
                            && contentType != null
                            && contentType.includes(MediaType.APPLICATION_JSON);
                })
                .verifyComplete();

        verify(requestValidator).validateUser(dto);
        verify(userUseCase).saveUser(userModel);
        verify(userDTOMapper).toResponse(userModel);
    }

    @Test
    void saveUser_FutureBirthDate_LanzaIllegalArgumentException() {

        CreateUserRecord dto = new CreateUserRecord(
                "123456789",
                "Maria",
                "Lopez",
                LocalDate.now().plusDays(1),
                "Cra 10 #20-30",
                "maria@example.com",
                "3101234567",
                BigInteger.valueOf(5_000_000)
        );

        User userModel = mock(User.class);
        ServerRequest request = mock(ServerRequest.class);

        when(request.bodyToMono(CreateUserRecord.class)).thenReturn(Mono.just(dto));
        when(requestValidator.validateUser(dto)).thenReturn(Mono.just(dto));
        when(userDTOMapper.toModel(dto)).thenReturn(userModel);
        when(userModel.getBirthDate()).thenReturn(dto.birthDate());
        when(userModel.getEmail()).thenReturn(dto.email());

        Mono<ServerResponse> result = handler.saveUseCase(request);

        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof IllegalArgumentException &&
                                throwable.getMessage().contains("La fecha de nacimiento no puede ser futura"))
                .verify();

        verify(requestValidator).validateUser(dto);
        verify(userDTOMapper).toModel(dto);
        verify(userModel, atLeastOnce()).getBirthDate();
        verify(userModel).getEmail();
        verifyNoInteractions(userUseCase);
    }

}
