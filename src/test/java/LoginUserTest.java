import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.UserCreate;
import model.UserLogin;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.UserSteps;

import static data.TestData.*;
import static data.TestData.EMAIL;
import static data.TestData.PASSWORD;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static steps.UserSteps.createNewUser;
import static steps.UserSteps.loginUser;

public class LoginUserTest extends BaseApiTest {

    UserCreate userCreate;
    UserLogin userLogin;
    UserSteps userSteps;

    @Before
    public void createTestUser() {
        userCreate = new UserCreate(EMAIL, PASSWORD, NAME);
        userLogin = new UserLogin(EMAIL, PASSWORD);
        userSteps = new UserSteps();

        // Создаём пользователя
        createNewUser(userCreate);
    }

    @Test
    @DisplayName("Пользователь может войти в систему")
    @Description("Проверяем, что пользователь может войти в систему, если передать валидную пару: email-пароль")
    public void userCanLogInTest() {

        // Сохраняем email, который передаём при авторизации в переменную для дальнейшего сравнения с фактическим ответом
        String email = userCreate.getEmail();
        // Сохраняем имя, которое передаём при создании пользователя в переменную для дальнейшего сравнения с фактическим ответом
        String name = userCreate.getName();

        // Передаём валидные email и пароль созданного пользователя
        loginUser(userLogin)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true))
                .body("user.email", equalTo(email))
                .body("user.name", equalTo(name));
    }

    @Test
    @DisplayName("Ошибка, если при авторизации не передать email")
    @Description("Проверяем, что появляется ошибка, если не передать email")
    public void errorLogInUserWithoutEmailTest() {

        // Сохраняем сообщение об ошибке в переменную для дальнейшего сравнения с фактическим ответом
        String messageError = "email or password are incorrect";

        // Создаём объект без email
        UserLogin userLogin = new UserLogin(null, PASSWORD);

        // Передаём данные без email
        loginUser(userLogin)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo(messageError));
    }

    @Test
    @DisplayName("Ошибка, если при авторизации не передать пароль")
    @Description("Проверяем, что появляется ошибка, если не передать пароль")
    public void errorLogInUserWithoutPasswordTest() {

        // Сохраняем сообщение об ошибке в переменную для дальнейшего сравнения с фактическим ответом
        String messageError = "email or password are incorrect";

        // Создаём объект без пароля
        UserLogin userLogin = new UserLogin(EMAIL, null);

        // Передаём данные без пароля
        loginUser(userLogin)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo(messageError));
    }

    @Test
    @DisplayName("Ошибка, если при авторизации передать не тот email, с которым регистрировались")
    @Description("Проверяем, что появляется ошибка, если написать не тот email, с которым регистрировались")
    public void errorLogInUserIncorrectEmailTest() {

        // Сохраняем сообщение об ошибке в переменную для дальнейшего сравнения с фактическим ответом
        String messageError = "email or password are incorrect";

        // Создаём объект, в котором искажаем email, с которым регистрировались
        UserLogin userLogin = new UserLogin(150 + EMAIL, PASSWORD);

        // Передаём данные не с тем email, с которым регистрировались
        loginUser(userLogin)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo(messageError));
    }

    @Test
    @DisplayName("Ошибка, если при авторизации передать не тот пароль, с которым регистрировались")
    @Description("Проверяем, что появляется ошибка, если написать не тот пароль, с которым регистрировались")
    public void errorLogInUserIncorrectPasswordTest() {

        // Сохраняем сообщение об ошибке в переменную для дальнейшего сравнения с фактическим ответом
        String messageError = "email or password are incorrect";

        // Создаём объект, в котором искажаем пароль, с которым регистрировались
        UserLogin userLogin = new UserLogin(EMAIL, PASSWORD + 15);

        // Передаём данные не с тем паролем, с которым регистрировались
        loginUser(userLogin)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo(messageError));
    }

    // После каждого теста удаляем созданного пользователя
    @After
    public void tearDown() {
        userSteps.cleanUp(userLogin);
    }
}
