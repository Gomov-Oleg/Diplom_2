
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.UserCreate;
import model.UserLogin;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.UserSteps;

import static data.TestData.*;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static steps.UserSteps.createNewUser;



public class CreateUserTest  extends BaseApiTest {

    UserCreate userCreate;
    UserLogin userLogin;
    UserSteps userSteps;


    @Before
    public void createTestUser() {
        userCreate = new UserCreate(EMAIL, PASSWORD, NAME);
        userLogin = new UserLogin(EMAIL, PASSWORD);
        userSteps = new UserSteps();
    }

    @Test
    @DisplayName("Успешное создание нового пользователя")
    @Description("Проверяем, что пользователя можно создать, если передать все необходимые поля")
    public void successCreateNewUserTest() {
        // Сохраняем email, который передаём при создании пользователя в переменную для дальнейшего сравнения с фактическим ответом
        String email = userCreate.getEmail();
        // Сохраняем имя, которое передаём при создании пользователя в переменную для дальнейшего сравнения с фактическим ответом
        String name = userCreate.getName();
        // Создаём пользователя
        createNewUser(userCreate)
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true))
                .body("user.email", equalTo(email))
                .body("user.name", equalTo(name));
    }

    @Test
    @DisplayName("Ошибка при создании двух одинаковых пользователей")
    @Description("Проверяем, что появляется ошибка при попытке создать двух одинаковых пользователей")
    public void errorCreatingTwoIdenticalUsersTest() {
        // Сохраняем сообщение об ошибке в переменную для дальнейшего сравнения с фактическим ответом
        String messageError = "User already exists";

        // Создаём пользователя
        createNewUser(userCreate);

        // Передаём данные пользователя, который уже создан
        createNewUser(userCreate)
                .then()
                .log().all()
                .statusCode(HTTP_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo(messageError));
    }

    @Test
    @DisplayName("Ошибка при создании пользователя без email")
    @Description("Проверяем, что появляется ошибка при создании пользователя, если не передать email")
    public void errorCreatingUserWithoutEmailTest() {
        // Сохраняем сообщение об ошибке в переменную для дальнейшего сравнения с фактическим ответом
        String messageError = "Email, password and name are required fields";
        // Создаём объект без email
        UserCreate userCreate = new UserCreate(null, PASSWORD, NAME);

        // Передаём данные пользователя без email
        createNewUser(userCreate)
                .then()
                .log().all()
                .statusCode(HTTP_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo(messageError));
    }

    @Test
    @DisplayName("Ошибка при создании пользователя без пароля")
    @Description("Проверяем, что появляется ошибка при создании пользователя, если не передать пароль")
    public void errorCreatingUserWithoutPasswordTest() {
        // Сохраняем сообщение об ошибке в переменную для дальнейшего сравнения с фактическим ответом
        String messageError = "Email, password and name are required fields";
        // Создаём объект без пароля
        UserCreate userCreate = new UserCreate(EMAIL, null, NAME);

        // Передаём данные пользователя без пароля
        createNewUser(userCreate)
                .then()
                .log().all()
                .statusCode(HTTP_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo(messageError));
    }

    @Test
    @DisplayName("Ошибка при создании пользователя без имени")
    @Description("Проверяем, что появляется ошибка при создании пользователя, если не передать имя")
    public void errorCreatingUserWithoutNameTest() {
        // Сохраняем сообщение об ошибке в переменную для дальнейшего сравнения с фактическим ответом
        String messageError = "Email, password and name are required fields";
        // Создаём объект без имени
        UserCreate userCreate = new UserCreate(EMAIL, PASSWORD, null);

        // Передаём данные пользователя без имени
        createNewUser(userCreate)
                .then()
                .log().all()
                .statusCode(HTTP_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo(messageError));
    }





    // После каждого теста удаляем созданного пользователя
    @After
    public void tearDown() {
        userSteps.cleanUp(userLogin);
    }

}
