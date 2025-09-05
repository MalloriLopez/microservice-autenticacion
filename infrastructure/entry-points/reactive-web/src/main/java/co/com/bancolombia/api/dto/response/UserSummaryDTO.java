package co.com.bancolombia.api.dto.response;

import java.util.List;

public record UserSummaryDTO (
        String id,
        String email,
        String role,
        List<String> permissions
) {}
