package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.UserCreate;
import model.UserLogin;
import model.UserSuccessLoginResponse;

import static data.TestData.*;
import static io.restassured.RestAssured.given;

// Шаги для пользователя
public class UserSteps {

    // Шаг, который создаёт нового пользователя
    @Step ("Создаём нового пользователя")
    public static Response createNewUser(UserCreate userCreate) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(userCreate)
                .when()
                .post(CREATE_USER_PATH)
                .then()
                .extract().response();
    }

    // Шаг, который авторизует пользователя
    @Step ("Авторизуемся в системе")
    public static Response loginUser(UserLogin userLogin) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(userLogin)
                .when()
                .post(LOGIN_USER_PATH)
                .then()
                .extract().response();
    }

    // Шаг, который удаляет пользователя
    @Step ("Удаляем пользователя")
    public static void deleteUser(String accessToken) {
        given()
                .log().all()
                .header("Authorization", accessToken)
                .when()
                .delete(DELETE_USER_PATH);
    }

    // Шаг, который авторизует пользователя и сохраняет accessToken для дальнейшей работы
    @Step ("Авторизуемся и сохраняем accessToken")
    public static String getUserAccessToken(UserLogin userLogin) {
        Response response = loginUser(userLogin);
        UserSuccessLoginResponse userSuccessLoginResponse = response.as(UserSuccessLoginResponse.class);
        return userSuccessLoginResponse.getAccessToken();
    }

    // Шаг, который авторизует пользователя, сохраняет accessToken и удаляет пользователя
    @Step ("Авторизуемся, сохраняем accessToken и удаляем пользователя")
    public void cleanUp(UserLogin userLogin) {
        String accessToken = getUserAccessToken(userLogin);
        if (accessToken != null) {
            deleteUser(accessToken);
        }
    }
}
