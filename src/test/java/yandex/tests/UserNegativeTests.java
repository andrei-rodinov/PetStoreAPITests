package yandex.tests;

import com.github.javafaker.Faker;
import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import yandex.endpoints.operators.ResponseChecks;
import yandex.endpoints.operators.UserAPIOperators;
import yandex.objects.User;

import static org.apache.http.HttpStatus.*;

@Link(url = "https://petstore.swagger.io/")
@Tag("User-tests")
@Epic("Тестирование API PetStore")
@Feature("Негативные сценарии API - User")
@DisplayName("Набор тестов № 6: Негативные сценарии создания / логина / изменения / удаления пользователя / получение данных")
public class UserNegativeTests {
    private User user;
    private final UserAPIOperators userAPI = new UserAPIOperators();
    private final ResponseChecks checkResponse = new ResponseChecks();
    private final Faker faker = new Faker();

    @Before
    @Step("Подготовка тестовых данных для пользователя")
    public void prepareTestData() {
        user = new User();
    }

    @Test
    @DisplayName("Создание пользователя с отсутствующими обязательными данными")
    @Description("Тест API для создания пользователя без обязательных полей. Ожидаемый результат - ошибка 400.")
    public void createUserWithoutRequiredFields() {
        Response postResponse = userAPI.createNewUser(user);
        Allure.addAttachment("Response Body", postResponse.getBody().asPrettyString());
        checkResponse.checkStatusCode(postResponse, SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Логин пользователя с некорректными данными")
    @Description("Тест API для логина пользователя с неверным логином и паролем. Ожидаемый результат - ошибка 401.")
    public void loginUserWithInvalidData() {
        user.setUsername(faker.name().username());
        user.setPassword("invalidPassword");

        Response loginResponse = userAPI.loginUser(user);
        checkResponse.checkStatusCode(loginResponse, SC_UNAUTHORIZED);
    }

    @Test
    @DisplayName("Получение данных несуществующего пользователя")
    @Description("Тест API для получения данных несуществующего пользователя. Ожидаемый результат - ошибка 404.")
    public void getNonExistentUserData() {
        String nonExistentUsername = "nonexistentuser";

        Response getResponse = userAPI.getUserData(nonExistentUsername);
        checkResponse.checkStatusCode(getResponse, SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Обновление данных пользователя с некорректным именем")
    @Description("Тест API для обновления данных пользователя с некорректным именем. Ожидаемый результат - ошибка 404.")
    public void updateUserWithInvalidUsername() {
        String invalidUsername = "invalidUser";

        user.setFirstname(faker.name().firstName());
        user.setLastname(faker.name().lastName());

        Response putResponse = userAPI.updateUserData(user, invalidUsername);
        checkResponse.checkStatusCode(putResponse, SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Удаление несуществующего пользователя")
    @Description("Тест API для удаления несуществующего пользователя. Ожидаемый результат - ошибка 404.")
    public void deleteNonExistentUser() {
        String nonExistentUsername = "nonexistentuser";

        Response deleteResponse = userAPI.deleteUser(nonExistentUsername);
        checkResponse.checkStatusCode(deleteResponse, SC_NOT_FOUND);
    }
}
