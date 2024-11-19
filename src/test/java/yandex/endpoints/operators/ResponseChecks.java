package yandex.endpoints.operators;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import static org.hamcrest.CoreMatchers.equalTo;

import yandex.objects.Order;
import yandex.objects.OrderResponse;
import yandex.objects.PetResponse;

public class ResponseChecks {

    @Step("Проверка кода и статуса ответа")
    public void checkStatusCode(Response response, int code) {
        Allure.addAttachment("Код и статус: ", response.getStatusLine());
        response.then().statusCode(code);
    }

    @Step("Проверка сообщения об ошибке")
    public void checkMessageText(Response response, String expectedMessage) {
        MatcherAssert.assertThat(
                "Неверное сообщение об ошибке",
                response.getBody().jsonPath().getString("message"),
                equalTo(expectedMessage)
        );
    }

    @Step("Проверка имени питомца в теле ответа")
    public void checkPetName(Response response, PetResponse expectedPetResponse) {
        PetResponse actualPetResponse = response.as(PetResponse.class);
        MatcherAssert.assertThat(
                "Неверное имя питомца",
                expectedPetResponse.getName(),
                equalTo(actualPetResponse.getName())
        );
    }

    @Step("Проверка категории питомца в теле ответа")
    public void checkPetCategory(Response response, PetResponse expectedPetResponse) {
        PetResponse actualPetResponse = response.as(PetResponse.class);
        MatcherAssert.assertThat(
                "Неверная категория питомца",
                expectedPetResponse.getCategory().getName(),
                equalTo(actualPetResponse.getCategory().getName())
        );
    }

    @Step("Проверка статуса питомца в теле ответа")
    public void checkPetStatus(Response response, PetResponse expectedPetResponse) {
        PetResponse actualPetResponse = response.as(PetResponse.class);
        MatcherAssert.assertThat(
                "Неверный статус питомца",
                expectedPetResponse.getStatus(),
                equalTo(actualPetResponse.getStatus())
        );
    }

    @Step("Проверка фото питомца в теле ответа")
    public void checkPetPhotoUrls(Response response, PetResponse expectedPetResponse) {
        PetResponse actualPetResponse = response.as(PetResponse.class);
        MatcherAssert.assertThat(
                "Неверные photoUrls питомца",
                expectedPetResponse.getPhotoUrls(),
                equalTo(actualPetResponse.getPhotoUrls())
        );
    }

    @Step("Проверка тегов питомца в теле ответа")
    public void checkPetTags(Response response, PetResponse expectedPetResponse) {
        PetResponse actualPetResponse = response.as(PetResponse.class);
        MatcherAssert.assertThat(
                "Неверные теги питомца",
                expectedPetResponse.getTags(),
                equalTo(actualPetResponse.getTags())
        );
    }
    @Step("Проверка ID питомца в теле ответа")
    public void checkOrderPetId(Response response, OrderResponse expectedOrder) {
        Order actualOrder = response.as(Order.class);
        MatcherAssert.assertThat(
                "Неверный ID питомца в заказе",
                actualOrder.getPetId(),
                equalTo(expectedOrder.getPetId())
        );
    }

    @Step("Проверка количества питомцев в заказе")
    public void checkOrderQuantity(Response response, OrderResponse expectedOrder) {
        Order actualOrder = response.as(Order.class);
        MatcherAssert.assertThat(
                "Неверное количество питомцев в заказе",
                actualOrder.getQuantity(),
                equalTo(expectedOrder.getQuantity())
        );
    }

    @Step("Проверка даты отправки заказа")
    public void checkOrderShipDate(Response response, OrderResponse expectedOrder) {
        Order actualOrder = response.as(Order.class);
        MatcherAssert.assertThat(
                "Неверная дата отправки заказа",
                actualOrder.getShipDate(),
                equalTo(expectedOrder.getShipDate())
        );
    }

    @Step("Проверка статуса заказа")
    public void checkOrderStatus(Response response, OrderResponse expectedOrder) {
        Order actualOrder = response.as(Order.class);
        MatcherAssert.assertThat(
                "Неверный статус заказа",
                actualOrder.getStatus(),
                equalTo(expectedOrder.getStatus())
        );
    }

    @Step("Проверка завершенности заказа")
    public void checkOrderComplete(Response response, OrderResponse expectedOrder) {
        Order actualOrder = response.as(Order.class);
        MatcherAssert.assertThat(
                "Неверное значение поля complete",
                actualOrder.isComplete(),
                equalTo(expectedOrder.isComplete())
        );
    }
}
