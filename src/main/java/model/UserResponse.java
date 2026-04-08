package model;

import lombok.Data;

// Класс с информацией о созданном пользователе
@Data
public class UserResponse {

    private String email;
    private String name;
}