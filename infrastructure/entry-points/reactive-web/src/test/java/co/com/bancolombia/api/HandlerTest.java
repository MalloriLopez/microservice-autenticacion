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

    private Handler handler;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        handler = new Handler(requestValidator, userUseCase, userDTOMapper);
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
                1L,
                "3001234567",
                5000000.0
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
                1L,                            //Id Rol
                "3101234567",                  // phone
                5000000.0                       // baseSalary
        );

        ServerRequest request = mock(ServerRequest.class);

        when(request.bodyToMono(CreateUserRecord.class)).thenReturn(Mono.just(dto));
        when(requestValidator.validate(dto)).thenReturn(Mono.just(dto));
        when(userDTOMapper.toModel(dto)).thenReturn(userModel);
        when(userUseCase.saveUser(userModel)).thenReturn(Mono.just(userModel));
        when(userDTOMapper.toResponse(userModel)).thenReturn(response);

        Mono<ServerResponse> result = handler.saveUseCase(request);

        StepVerifier.create(result)
                .expectNextMatches(serverResponse -> {
                    MediaType contentType = serverResponse.headers().getContentType();
                    return serverResponse.statusCode().is2xxSuccessful()
                            && contentType != null
                            && contentType.includes(MediaType.APPLICATION_JSON);
                })
                .verifyComplete();

        verify(requestValidator).validate(dto);
        verify(userUseCase).saveUser(userModel);
        verify(userDTOMapper).toResponse(userModel);
    }

}
