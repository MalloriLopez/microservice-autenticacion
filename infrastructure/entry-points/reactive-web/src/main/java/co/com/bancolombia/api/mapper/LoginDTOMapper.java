package co.com.bancolombia.api.mapper;

import co.com.bancolombia.api.dto.response.LoginRecordResponseDTO;
import co.com.bancolombia.api.dto.response.UserSummaryDTO;
import co.com.bancolombia.model.auth.UserAuth;
import co.com.bancolombia.usecase.auth.LoginResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoginDTOMapper {

    @Mapping(target = "access_token", source = "accessToken")
    @Mapping(target = "token_type",  constant = "Bearer")
    @Mapping(target = "expires_in",  source = "expiresIn")
    @Mapping(target = "user",        source = "user")
    LoginRecordResponseDTO toResponse(LoginResult result);

    UserSummaryDTO toResponse(UserAuth user);
}
