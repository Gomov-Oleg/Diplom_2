package data;

import com.github.javafaker.Faker;

public class TestData {
    public static final String BASE_URI = "https://stellarburgers.education-services.ru/";

    // Генерируем уникальные данные для тестового пользователя
    static Faker user = new Faker();
    public static final String EMAIL = user.name().lastName().toLowerCase() + user.regexify("[0-9]{4}") + "@yandex.ru";
    public static final String PASSWORD = user.regexify("[0-9]{4}");
    public static final String NAME = user.name().firstName();

    // Ингредиенты для бургера
    public static final String BUN = "61c0c5a71d1f82001bdaaa6c"; // "Краторная булка N-200i"
    public static final String CUTLET = "61c0c5a71d1f82001bdaaa70"; // "Говяжий метеорит (отбивная)"

    // Методы API ("ручки")
    public static final String CREATE_USER_PATH = "/api/auth/register";
    public static final String LOGIN_USER_PATH = "/api/auth/login";
    public static final String DELETE_USER_PATH = "/api/auth/user";
    public static final String CREATE_ORDER_PATH = "/api/orders";
}
