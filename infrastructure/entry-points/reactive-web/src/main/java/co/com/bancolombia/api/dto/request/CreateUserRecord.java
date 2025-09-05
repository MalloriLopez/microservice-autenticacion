package co.com.bancolombia.api.dto.request;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserRecord(
    @NotBlank(message = "El documento de identidad no puede estar vacío")
    String documentId,

    @NotBlank(message = "El nombre no puede estar vacío")
    String name,

    @NotBlank(message = "El apellido no puede estar vacío")
    String lastname,

    LocalDate birthDate,

    String address,

    @NotBlank(message = "El correo electrónico no puede estar vacío")
    @Email(message = "El correo electrónico no tiene un formato válido")
    String email,

    String phone,

    Long idRol,

    @NotNull(message = "El salario base no puede ser nulo")
    @Min(value = 0, message = "El salario base no puede ser un valor negativo")
    @Max(value = 15000000, message = "El salario base no puede ser mayor a $15,000,000")
    BigInteger baseSalary,

    String passwordHash
) {
    
}