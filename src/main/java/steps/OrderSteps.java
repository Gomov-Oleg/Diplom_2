package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.OrderCreate;
import model.UserLogin;

import static data.TestData.CREATE_ORDER_PATH;
import static io.restassured.RestAssured.given;
import static steps.UserSteps.getUserAccessToken;

// Шаги для заказа
public class OrderSteps {

    // Шаг, который создаёт заказ авторизованным пользователем
    @Step("Создаём заказ авторизованным пользователем")
    public static Response createNewOrderAuthorizedUser(UserLogin userLogin, OrderCreate orderCreate) {
        String accessToken = getUserAccessToken(userLogin);
        return given()
                .log().all()
                .header("Authorization", accessToken)
                .contentType(ContentType.JSON)
                .body(orderCreate)
                .when()
                .post(CREATE_ORDER_PATH)
                .then()
                .extract().response();
    }

    // Шаг, который создаёт заказ неавторизованным пользователем
    @Step("Создаём заказ неавторизованным пользователем")
    public static Response createNewOrderUnauthorizedUser(OrderCreate orderCreate) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(orderCreate)
                .when()
                .post(CREATE_ORDER_PATH)
                .then()
                .extract().response();
    }
}
