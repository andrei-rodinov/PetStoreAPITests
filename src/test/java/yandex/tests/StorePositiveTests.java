package yandex.tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import yandex.endpoints.operators.PetAPIOperators;
import yandex.endpoints.operators.ResponseChecks;
import yandex.endpoints.operators.StoreAPIOperators;
import yandex.objects.Order;
import yandex.objects.OrderResponse;
import yandex.objects.Pet;

import java.util.ArrayList;
import java.util.List;

import static org.apache.http.HttpStatus.*;

@Link(url = "https://petstore.swagger.io/")
@Tag("Store-tests")
@Epic("Тестирование API PetStore")
@Feature("Тестирование ручек API - Store")
@DisplayName("Набор тестов № 2: получение ассортимента / создание заказа / удаление заказа")
public class StorePositiveTests {
    private Pet pet;
    private Order order;
    private final PetAPIOperators petAPI = new PetAPIOperators();
    private final ArrayList<Long> petIds = new ArrayList<>();
    private final ArrayList<Long> orderIds = new ArrayList<>();
    private final StoreAPIOperators storeAPI = new StoreAPIOperators();
    private final ResponseChecks checkResponse = new ResponseChecks();

    @Before
    @Step("Подготовка тестовых данных для питомца")
    public void prepareTestData() {
        pet = new Pet();
        pet.setName("Pirozhok");
        pet.setStatus("available");

        Pet.Category category = new Pet.Category();
        category.setId(1);
        category.setName("Dogs");
        pet.setCategory(category);

        pet.setPhotoUrls(List.of("http://dogs-photo.ru/dog.jpg"));

        Pet.Tag tag = new Pet.Tag();
        tag.setId(1);
        tag.setName("Friendly");
        pet.setTags(List.of(tag));

        Response response = petAPI.createNewPet(pet);
        if (response.getStatusCode() == SC_OK) {
            pet.setId(Long.parseLong(response.getBody().jsonPath().getString("id")));
            petIds.add(pet.getId());
        }
    }

    @After
    @Step("Удаление данных после теста")
    public void clearAfterTests() {
        if (!petIds.isEmpty()) {
            for (Long petId : petIds) {
                Response response = petAPI.deletePet(petId);
                checkResponse.checkStatusCode(response, SC_OK);
            }
        }
    }

    @Test
    @DisplayName("Получение об ассортименте магазина")
    @Description("Тест API для получения данных об ассортименте магазина. Ожидаемый результат - данные корректно получены.")
    public void getStoreInventorySuccessfully() {
        Response response = storeAPI.getStoreInventory();
        checkResponse.checkStatusCode(response, SC_OK);
        System.out.println("Response Body: " + response.getBody().asString());
    }

    @Test
    @DisplayName("Создание нового заказа на созданного питомца")
    @Description("Тест API для создания нового заказа. Ожидаемый результат - заказ создан.")
    public void createNewOrderSuccessfully() {
        order = new Order();
        order.setPetId(pet.getId());
        order.setQuantity(1);
        order.setShipDate("2024-11-19T09:59:46.286Z");
        order.setStatus("placed");

        Response postResponse = storeAPI.createNewOrder(order);

        OrderResponse orderResponse = postResponse.as(OrderResponse.class);

        checkResponse.checkStatusCode(postResponse, SC_OK);
        checkResponse.checkOrderPetId(postResponse, orderResponse);
        checkResponse.checkOrderQuantity(postResponse, orderResponse);
        checkResponse.checkOrderShipDate(postResponse, orderResponse);
        checkResponse.checkOrderStatus(postResponse, orderResponse);
        checkResponse.checkOrderComplete(postResponse, orderResponse);

        System.out.println("Response Body: " + postResponse.getBody().asString());
    }

    @Test
    @DisplayName("Получение данных о заказе по ID")
    @Description("Тест API для получения данных о заказе по ID. Ожидаемый результат - данные корректно получены.")
    public void getOrderDataSuccessfully() {
        order = new Order();
        order.setPetId(pet.getId());
        order.setQuantity(1);
        order.setShipDate("2024-11-19T09:59:46.286Z");
        order.setStatus("placed");

        Response orderResponse = storeAPI.createNewOrder(order);
        if (orderResponse.getStatusCode() == SC_OK) {
            order.setId(Long.parseLong(orderResponse.getBody().jsonPath().getString("id")));
            orderIds.add(order.getId());
        }

        Response getResponse = storeAPI.getOrderData(order.getId());
        checkResponse.checkStatusCode(getResponse, SC_OK);

        System.out.println("Response Body: " + getResponse.getBody().asString());
    }

    @Test
    @DisplayName("Удаление заказа по ID")
    @Description("Тест API для удаления заказа по ID. Ожидаемый результат - заказ удален.")
    public void deleteOrderSuccessfully() {
        order = new Order();
        order.setPetId(pet.getId());
        order.setQuantity(1);
        order.setShipDate("2024-11-19T09:59:46.286Z");
        order.setStatus("placed");

        Response orderResponse = storeAPI.createNewOrder(order);
        if (orderResponse.getStatusCode() == SC_OK) {
            order.setId(Long.parseLong(orderResponse.getBody().jsonPath().getString("id")));
            orderIds.add(order.getId());
        }

        Response deleteResponse = storeAPI.deleteOrder(order.getId());
        checkResponse.checkStatusCode(deleteResponse, SC_OK);

        Response getResponse = storeAPI.getOrderData(order.getId());
        checkResponse.checkStatusCode(getResponse, SC_NOT_FOUND);
    }
}
