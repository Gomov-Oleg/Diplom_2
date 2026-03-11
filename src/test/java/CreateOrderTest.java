import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.OrderCreate;
import model.UserCreate;
import model.UserLogin;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.UserSteps;

import java.util.Arrays;
import java.util.List;

import static data.TestData.*;
import static data.TestData.EMAIL;
import static data.TestData.PASSWORD;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static steps.OrderSteps.createNewOrderAuthorizedUser;
import static steps.OrderSteps.createNewOrderUnauthorizedUser;
import static steps.UserSteps.createNewUser;

// Класс тестирования создания заказа
public class CreateOrderTest extends BaseApiTest{

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
    @DisplayName("Авторизованный пользователь может создать заказ с ингредиентами")
    @Description("Проверяем, что авторизованный пользователь может создать заказ с ингредиентами")
    public void authorizedUserCanCreateOrderTest() {

        // Сохраняем email, который передаём при авторизации в переменную для дальнейшего сравнения с фактическим ответом
        String email = userCreate.getEmail();
        // Сохраняем имя, которое передаём при создании пользователя в переменную для дальнейшего сравнения с фактическим ответом
        String name = userCreate.getName();

        // Создаём список ингредиентов для заказа
        List<String> ingredientId = Arrays.asList(BUN, CUTLET);
        // Создаём объект на основе списка ингредиентов
        OrderCreate orderCreate = new OrderCreate(ingredientId);

        // Передаём данные пользователя для авторизации и список ингредиентов
        createNewOrderAuthorizedUser(userLogin, orderCreate)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true))
                .body("order.owner.email", equalTo(email))
                .body("order.owner.name", equalTo(name))
                .body("order.number", instanceOf(Integer.class));
    }

    @Test
    @DisplayName("Неавторизованный пользователь может создать заказ с ингредиентами")
    @Description("Проверяем, что неавторизованный пользователь может создать заказ с ингредиентами")
    public void unauthorizedUserCanCreateOrderTest() {

        // Создаём список ингредиентов для заказа
        List<String> ingredientId = Arrays.asList(BUN, CUTLET);
        // Создаём объект на основе списка ингредиентов
        OrderCreate orderCreate = new OrderCreate(ingredientId);

        // Передаём список ингредиентов
        createNewOrderUnauthorizedUser(orderCreate)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HTTP_OK)
                .body("success", equalTo(true))
                .body("order.number", instanceOf(Integer.class));
    }

    @Test
    @DisplayName("Ошибка при создании заказа без ингредиентов авторизованным пользователем")
    @Description("Проверяем, что появляется ошибка, если создать заказ без ингредиентов авторизованным пользователем")
    public void errorCreateOrderWithoutIngredientsAuthorizedUserTest() {

        // Сохраняем сообщение об ошибке в переменную для дальнейшего сравнения с фактическим ответом
        String messageError = "Ingredient ids must be provided";

        // Создаём объект без ингредиентов
        OrderCreate orderCreate = new OrderCreate();

        // Передаём данные пользователя для авторизации и не передаём ингредиенты
        createNewOrderAuthorizedUser(userLogin, orderCreate)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo(messageError));
    }

    @Test
    @DisplayName("Ошибка при создании заказа без ингредиентов неавторизованным пользователем")
    @Description("Проверяем, что появляется ошибка, если создать заказ без ингредиентов неавторизованным пользователем")
    public void errorCreateOrderWithoutIngredientsUnauthorizedUserTest() {

        // Сохраняем сообщение об ошибке в переменную для дальнейшего сравнения с фактическим ответом
        String messageError = "Ingredient ids must be provided";

        // Создаём объект без ингредиентов
        OrderCreate orderCreate = new OrderCreate();

        // Создаём заказ без ингредиентов
        createNewOrderUnauthorizedUser(orderCreate)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo(messageError));
    }

    @Test
    @DisplayName("Ошибка при создании заказа с неверным хешем ингредиентов авторизованным пользователем")
    @Description("Проверяем, что появляется ошибка, если создать заказ с неверным хешем ингредиентов авторизованным пользователем")
    public void errorCreateOrderInvalidHashIngredientsAuthorizedUserTest() {

        // Создаём список с неверным хешем ингредиентов для заказа
        List<String> ingredientId = Arrays.asList(150 + BUN, CUTLET);
        // Создаём объект на основе списка ингредиентов
        OrderCreate orderCreate = new OrderCreate(ingredientId);

        // Передаём данные пользователя для авторизации и список с неверным хешем ингредиентов
        createNewOrderAuthorizedUser(userLogin, orderCreate)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HTTP_INTERNAL_ERROR);
    }

    @Test
    @DisplayName("Ошибка при создании заказа с неверным хешем ингредиентов неавторизованным пользователем")
    @Description("Проверяем, что появляется ошибка, если создать заказ с неверным хешем ингредиентов неавторизованным пользователем")
    public void errorCreateOrderInvalidHashIngredientsUnauthorizedUserTest() {

        // Создаём список с неверным хешем ингредиентов для заказа
        List<String> ingredientId = Arrays.asList(150 + BUN, CUTLET);
        // Создаём объект на основе списка ингредиентов
        OrderCreate orderCreate = new OrderCreate(ingredientId);

        // Передаём список с неверным хешем ингредиентов
        createNewOrderUnauthorizedUser(orderCreate)
                .then()
                .log().all()
                .assertThat()
                .statusCode(HTTP_INTERNAL_ERROR);
    }

    // После каждого теста удаляем созданного пользователя
    @After
    public void tearDown() {
        userSteps.cleanUp(userLogin);
    }
}
