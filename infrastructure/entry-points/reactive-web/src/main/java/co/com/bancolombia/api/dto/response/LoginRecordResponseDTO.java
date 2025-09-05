package co.com.bancolombia.api.dto.response;

public record LoginRecordResponseDTO (

    String access_token,
    String token_type,
    long   expires_in,
    UserSummaryDTO user
) {}

