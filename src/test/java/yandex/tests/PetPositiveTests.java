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
import yandex.objects.Pet;
import yandex.objects.PetResponse;

import java.util.ArrayList;
import java.util.List;

import static org.apache.http.HttpStatus.*;


@Link(url = "https://petstore.swagger.io/")
@Tag("Pet-tests")
@Epic("Тестирование API PetStore")
@Feature("Позитивные сценарии API - Pet")
@DisplayName("Набор тестов № 1: Позитивные сценарии создания / изменения / удаления питомца / получение данных")
public class PetPositiveTests {
    private Pet pet;
    private final PetAPIOperators petAPI = new PetAPIOperators();
    private final ResponseChecks checkResponse = new ResponseChecks();
    private final ArrayList<Long> petIds = new ArrayList<>();

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
    @DisplayName("Создание питомца с валидными данными")
    @Description("Тест API для создания питомца с валидными данными. Ожидаемый результат - питомец создан успешно.")
    public void createNewPetSuccessfully() {
        Response response = petAPI.createNewPet(pet);

        PetResponse petResponse = response.as(PetResponse.class);

        checkResponse.checkStatusCode(response, SC_OK);
        checkResponse.checkPetName(response, petResponse);
        checkResponse.checkPetCategory(response, petResponse);
        checkResponse.checkPetStatus(response, petResponse);
        checkResponse.checkPetPhotoUrls(response, petResponse);
        checkResponse.checkPetTags(response, petResponse);
        System.out.println("Response Body: " + response.getBody().asString());
    }

    @Test
    @DisplayName("Изменение данных о питомце")
    @Description("Тест API для изменения данных питомца. Ожидаемый результат - данные изменены успешно.")
    public void updatePetDataSuccessfully() {
        pet.setName("UpdatedPirozhok");
        pet.setStatus("sold");
        pet.getCategory().setName("UpdatedDogs");
        pet.setPhotoUrls(List.of("http://dogs-photo.ru/updated_dog.jpg"));
        pet.getTags().get(0).setName("Active");
        Response response = petAPI.updatePetData(pet);

        PetResponse petResponse = response.as(PetResponse.class);

        checkResponse.checkStatusCode(response, SC_OK);
        checkResponse.checkPetName(response, petResponse);
        checkResponse.checkPetCategory(response, petResponse);
        checkResponse.checkPetStatus(response, petResponse);
        checkResponse.checkPetPhotoUrls(response, petResponse);
        checkResponse.checkPetTags(response, petResponse);

        System.out.println("Updated Response Body: " + response.getBody().asString());
    }

    @Test
    @DisplayName("Получение данных о питомце")
    @Description("Тест API для получения данных о созданном питомце. Ожидаемый результат - данные корректно получены.")
    public void getPetDataSuccessfully() {
        Response response = petAPI.getPetData(pet.getId());

        PetResponse petResponse = response.as(PetResponse.class);

        checkResponse.checkStatusCode(response, SC_OK);
        checkResponse.checkPetName(response, petResponse);
        checkResponse.checkPetCategory(response, petResponse);
        checkResponse.checkPetStatus(response, petResponse);
        checkResponse.checkPetPhotoUrls(response, petResponse);
        checkResponse.checkPetTags(response, petResponse);

        System.out.println("Response Body: " + response.getBody().asString());
    }
}
