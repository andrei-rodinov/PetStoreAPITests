package yandex.endpoints.operators;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import yandex.endpoints.httpclient.UserHTTPClients;
import yandex.objects.User;

public class UserAPIOperators extends UserHTTPClients {
    @Step("Создание нового пользователя")
    public Response createNewUser(User user) {
        return super.createNewUser(user);
    }

    @Step("Удаление пользователя")
    public Response deleteUser(String username) {
        return super.deleteUser(username);
    }

    @Step("Изменение данных пользователя")
    public Response updateUserData(User user, String username) {
        return super.updateUserData(user, username);
    }

    @Step("Получение данных о пользователе")
    public Response getUserData(String username) {
        return super.getUserData(username);
    }

    @Step("Логин пользователя")
    public Response loginUser(User user) {
        return super.loginUser(user);
    }
}
