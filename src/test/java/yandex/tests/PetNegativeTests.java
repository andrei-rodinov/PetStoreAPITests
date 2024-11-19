package yandex.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import io.restassured.response.Response;
import org.junit.Test;
import yandex.endpoints.operators.PetAPIOperators;
import yandex.endpoints.operators.ResponseChecks;
import yandex.objects.Pet;

import static org.apache.http.HttpStatus.*;

@Link(url = "https://petstore.swagger.io/")
@Tag("Pet-tests")
@Epic("Тестирование API PetStore")
@Feature("Негативные сценарии API - Pet")
@DisplayName("Набор тестов № 2: Негативные сценарии создания / изменения / удаления питомцев")
public class PetNegativeTests {
    private final PetAPIOperators petAPI = new PetAPIOperators();
    private final ResponseChecks checkResponse = new ResponseChecks();

    @Test
    @DisplayName("Создание питомца без обязательных параметров")
    @Description("Тест API для создания питомца без обязательных данных. Ожидаемый результат - ошибка валидации.")
    public void createPetWithoutRequiredFields() {
        Pet pet = new Pet();
        Response response = petAPI.createNewPet(pet);

        checkResponse.checkStatusCode(response, SC_METHOD_NOT_ALLOWED);
        checkResponse.checkMessageText(response, "Invalid input");
    }

    @Test
    @DisplayName("Удаление питомца с несуществующим ID")
    @Description("Тест API для удаления питомца с несуществующим ID. Ожидаемый результат - ошибка 404.")
    public void deletePetWithNonExistentId() {
        long nonExistentId = 0000000L;
        Response response = petAPI.deletePet(nonExistentId);

        checkResponse.checkStatusCode(response, SC_NOT_FOUND);
    }
    @Test
    @DisplayName("Получение данных о питомце с невалидным ID")
    @Description("Тест API для получения данных о питомце с невалидным ID. Ожидаемый результат - ошибка 404.")
    public void getPetDataWithInvalidId() {
        long invalidId = -1L; // Невалидный ID питомца
        Response response = petAPI.getPetData(invalidId);

        checkResponse.checkStatusCode(response, SC_NOT_FOUND);
        checkResponse.checkMessageText(response, "Pet not found");
    }
}
