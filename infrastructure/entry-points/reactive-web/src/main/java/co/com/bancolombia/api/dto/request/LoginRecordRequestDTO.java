package co.com.bancolombia.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRecordRequestDTO(
        @Email
        @NotBlank
        String email,

        @NotBlank
        String password
) {}
