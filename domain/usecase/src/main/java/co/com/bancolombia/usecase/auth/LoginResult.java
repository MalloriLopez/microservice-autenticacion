package co.com.bancolombia.usecase.auth;

import co.com.bancolombia.model.auth.UserAuth;

public record LoginResult(String accessToken, long expiresIn, UserAuth user) {}
