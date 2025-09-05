package co.com.bancolombia.model.auth;

import lombok.Builder;
import lombok.Value;
import java.util.List;

@Value @Builder
public class UserAuth {
    String id;
    String email;
    String passwordHash;
    String role;
    List<String> permissions;
}

