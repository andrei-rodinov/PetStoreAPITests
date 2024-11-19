package yandex.tests;


import com.github.javafaker.Faker;
import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import yandex.endpoints.operators.ResponseChecks;
import yandex.endpoints.operators.UserAPIOperators;
import yandex.objects.User;

import java.util.ArrayList;

import static org.apache.http.HttpStatus.*;

@Link(url = "https://petstore.swagger.io/")
@Tag("User-tests")
@Epic("Тестирование API PetStore")
@Feature("Позитивные сценарии API - User")
@DisplayName("Набор тестов № 5: Позитивные сценарии создания / логина / изменения / удаления пользователя / получение данных")
public class UserPositiveTests {
    private User user;
    private final UserAPIOperators userAPI = new UserAPIOperators();
    private final ResponseChecks checkResponse = new ResponseChecks();
    private final ArrayList<String> userNames = new ArrayList<>();
    private final Faker faker = new Faker();

    @Before
    @Step("Подготовка тестовых данных для пользователя")
    public void prepareTestData() {
        user = new User();
        user.setUsername(faker.name().username());
        user.setFirstname(faker.name().firstName());
        user.setLastname(faker.name().lastName());
        user.setEmail(faker.internet().safeEmailAddress());
        user.setPassword(faker.internet().password(5, 10));
        user.setPhone(faker.phoneNumber().toString());
    }

    @After
    @Step("Удаление данных после теста")
    public void clearAfterTests() {
        if (!userNames.isEmpty()) {
            for (String userNames : userNames) {
                Response response = userAPI.deleteUser(userNames);
                checkResponse.checkStatusCode(response, SC_OK);
            }
        }
    }

    @Test
    @DisplayName("Создание нового пользователя")
    @Description("Тест API для создания нового пользователя. Ожидаемый результат - пользователь создан.")
    public void createNewUserSuccessfully() {
        Response postResponse = userAPI.createNewUser(user);
        if (postResponse.getStatusCode() == SC_OK) {
            userNames.add(user.getUsername());
        }
    }

    @Test
    @DisplayName("Логин нового пользователя")
    @Description("Тест API для логина нового пользователя. Ожидаемый результат - пользователь залогинен.")
    public void loginUserSuccessfully() {
        Response postResponse = userAPI.createNewUser(user);
        if (postResponse.getStatusCode() == SC_OK) {
            userNames.add(user.getUsername());
        }

        Response getResponse = userAPI.loginUser(user);
        checkResponse.checkStatusCode(getResponse, SC_OK);
    }

    @Test
    @DisplayName("Получение данных пользователя")
    @Description("Тест API для получения данных пользователя. Ожидаемый результат - данные получены.")
    public void getUserDataSuccessfully() {
        Response postResponse = userAPI.createNewUser(user);
        if (postResponse.getStatusCode() == SC_OK) {
            userNames.add(user.getUsername());
        }

        Response getResponse = userAPI.getUserData(user.getUsername());
        checkResponse.checkStatusCode(getResponse, SC_OK);
        System.out.println("Response Body: " + getResponse.getBody().asString());
    }

    @Test
    @DisplayName("Обновление данных пользователя")
    @Description("Тест API для обновления данных пользователя. Ожидаемый результат - данные обновлены.")
    public void updateUserDataSuccessfully() {
        Response postResponse = userAPI.createNewUser(user);

        user.setUsername("upd-" + faker.name().username());
        user.setFirstname("upd-" + faker.name().firstName());
        user.setLastname("upd-" + faker.name().lastName());
        user.setEmail("upd-" + faker.internet().safeEmailAddress());
        user.setPassword("upd-" + faker.internet().password(5, 10));
        user.setPhone("+7-" + faker.numerify("###-###-##-##"));

        Response putResponse = userAPI.updateUserData(user, user.getUsername());
        checkResponse.checkStatusCode(putResponse, SC_OK);
        if (putResponse.getStatusCode() == SC_OK) {
            userNames.add(user.getUsername());
        }

        Response getResponse = userAPI.getUserData(user.getUsername());
        checkResponse.checkStatusCode(getResponse, SC_OK);
        System.out.println("Response Body: " + getResponse.getBody().asString());
    }
}

