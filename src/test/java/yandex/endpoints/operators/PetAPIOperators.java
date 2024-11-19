package yandex.endpoints.operators;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import yandex.endpoints.httpclient.PetHTTPClients;
import yandex.objects.Pet;

public class PetAPIOperators extends PetHTTPClients {
    @Step("Создание нового питомца")
    public Response createNewPet(Pet pet) {
        return super.createNewPet(pet);
    }

    @Step("Удаление питомца")
    public Response deletePet(long petId) {
        return super.deletePet(petId);
    }

    @Step("Изменение данных питомца")
    public Response updatePetData(Pet pet) {
        return super.updatePetData(pet);
    }

    @Step("Получение данных о питомце")
    public Response getPetData(long petId) {
        return super.getPetData(petId);
    }
}

