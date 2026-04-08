package model;

import lombok.Data;

// Класс с информацией об успешном создании пользователя
@Data
public class UserSuccessLoginResponse {

    private boolean success;
    private String accessToken;
    private String refreshToken;
    private UserResponse userResponse;
}