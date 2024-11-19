package yandex.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import io.restassured.response.Response;
import org.junit.Test;
import yandex.endpoints.operators.ResponseChecks;
import yandex.endpoints.operators.StoreAPIOperators;
import yandex.objects.Order;

import static org.apache.http.HttpStatus.*;

@Link(url = "https://petstore.swagger.io/")
@Tag("Store-tests")
@Epic("Тестирование API PetStore")
@Feature("Негативные сценарии API - Store")
@DisplayName("Набор тестов № 4: Негативные сценарии создания / удаления / получения заказа")
public class StoreNegativeTests {
    private final StoreAPIOperators storeAPI = new StoreAPIOperators();
    private final ResponseChecks checkResponse = new ResponseChecks();

    @Test
    @DisplayName("Создание заказа без обязательных данных")
    @Description("Тест API для создания заказа без обязательных данных. Ожидаемый результат - ошибка валидации.")
    public void createOrderWithoutRequiredFields() {
        Order order = new Order();
        Response response = storeAPI.createNewOrder(order);

        checkResponse.checkStatusCode(response, SC_BAD_REQUEST);
        checkResponse.checkMessageText(response, "Invalid input");
    }

    @Test
    @DisplayName("Удаление заказа с несуществующим ID")
    @Description("Тест API для удаления заказа с несуществующим ID. Ожидаемый результат - ошибка 404.")
    public void deleteOrderWithNonExistentId() {
        long nonExistentId = 00000000L;
        Response response = storeAPI.deleteOrder(nonExistentId);


        checkResponse.checkStatusCode(response, SC_NOT_FOUND);
        checkResponse.checkMessageText(response, "Order Not Found");
    }

    @Test
    @DisplayName("Получение данных о заказе с невалидным ID")
    @Description("Тест API для получения данных о заказе с невалидным ID. Ожидаемый результат - ошибка 404.")
    public void getOrderDataWithInvalidId() {
        long invalidId = -1L;
        Response response = storeAPI.getOrderData(invalidId);

        checkResponse.checkStatusCode(response, SC_NOT_FOUND);
        checkResponse.checkMessageText(response, "Order not found");
    }
}
