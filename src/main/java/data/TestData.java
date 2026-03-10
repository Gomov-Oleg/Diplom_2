package data;

import com.github.javafaker.Faker;

public class TestData {
    public static final String BASE_URI = "https://stellarburgers.education-services.ru/";

    // Генерируем уникальные данные для тестового курьера
    static Faker user = new Faker();
    public static final String EMAIL = user.name().lastName().toLowerCase() + user.regexify("[0-9]{4}") + "@yandex.ru";
    public static final String PASSWORD = user.regexify("[0-9]{4}");
    public static final String NAME = user.name().firstName();

    // Методы API ("ручки")
    public static final String CREATE_USER_PATH = "/api/auth/register";
    public static final String LOGIN_USER_PATH = "/api/auth/login";
    public static final String DELETE_USER_PATH = "/api/auth/user";
}
