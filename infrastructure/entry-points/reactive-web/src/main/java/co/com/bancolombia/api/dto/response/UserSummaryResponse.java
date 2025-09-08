package co.com.bancolombia.api.dto.response;

public record UserSummaryResponse(
        String email,
        String name,
        Double baseSalary
) {}

