package yandex.endpoints.operators;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import yandex.endpoints.httpclient.StoreHTTPClients;
import yandex.objects.Order;

public class StoreAPIOperators extends StoreHTTPClients {

    @Step("Получение данных об ассортименте магазина")
    public Response getStoreInventory() {
        return super.getStoreInventory();
    }
    @Step("Получение данных о заказе по ID")
    public Response getOrderData(long id) {
        return super.getOrderData(id);
    }

    @Step("Создание нового заказа")
    public Response createNewOrder(Order order) {
        return super.createNewOrder(order);
    }

    @Step("Удаление заказа")
    public Response deleteOrder(long id) {
        return super.deleteOrder(id);
    }
}
