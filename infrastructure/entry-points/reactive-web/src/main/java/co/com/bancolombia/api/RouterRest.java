package co.com.bancolombia.api;

import co.com.bancolombia.api.auth.LoginHandler;
import co.com.bancolombia.api.dto.request.CreateUserRecord;
import co.com.bancolombia.api.dto.response.UserRecordResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/users",
                    method = RequestMethod.POST,
                    beanClass = Handler.class,
                    beanMethod = "saveUseCase",
                    operation = @Operation(
                            operationId = "createUser",
                            summary = "Crea un usuario",
                            description = "Crea un usuario nuevo y devuelve su representación",
                            requestBody = @RequestBody(
                                    required = true,
                                    content = @Content(schema = @Schema(implementation = CreateUserRecord.class))
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "Usuario creado",
                                            content = @Content(schema = @Schema(implementation = UserRecordResponse.class))
                                    ),
                                    @ApiResponse(responseCode = "400", description = "Petición inválida"),
                                    @ApiResponse(responseCode = "409", description = "Conflicto (duplicado)")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/v1/login",
                    method = RequestMethod.POST,
                    beanClass = co.com.bancolombia.api.auth.LoginHandler.class,
                    beanMethod = "login",
                    operation = @Operation(
                            operationId = "login",
                            summary = "Autenticación",
                            description = "Valida credenciales y devuelve un JWT",
                            requestBody = @RequestBody(
                                    required = true,
                                    content = @Content(schema = @Schema(implementation = co.com.bancolombia.api.dto.request.LoginRecordRequestDTO.class))
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Login OK",
                                            content = @Content(schema = @Schema(implementation = co.com.bancolombia.api.dto.response.LoginRecordResponseDTO.class))),
                                    @ApiResponse(responseCode = "400", description = "Petición inválida"),
                                    @ApiResponse(responseCode = "401", description = "Credenciales inválidas"),
                                    @ApiResponse(responseCode = "503", description = "Servicio externo no disponible")
                            }
                    )
            )
    })

    public RouterFunction<ServerResponse> routerFunction(Handler handler, LoginHandler loginHandler) {
        return route()
                .POST("/api/v1/users" , handler::saveUseCase)
                .GET("/api/v1/users/email/{email}/exists" , handler::existsUserByEmailUseCase)
                .POST("/api/v1/login", loginHandler::login)
                .build();
    }
}