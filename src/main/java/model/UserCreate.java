package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Класс пользователя, которого создаём
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreate {

    private String email;
    private String password;
    private String name;
}