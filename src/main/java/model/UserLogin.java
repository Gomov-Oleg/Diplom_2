package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Класс уже созданного пользователя с полями: email и пароль
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLogin {

    private String email;
    private String password;
}