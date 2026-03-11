package model;

// Класс уже созданного пользователя с полями: email и пароль
public class UserLogin {

    private String email;
    private String password;

    // Конструктор с параметрами: email и пароль
    public UserLogin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Конструктор без параметров
    public UserLogin(){}

    // Геттеры и сеттеры
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
