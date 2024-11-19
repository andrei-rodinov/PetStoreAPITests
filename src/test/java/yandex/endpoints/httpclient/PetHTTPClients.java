package yandex.endpoints.httpclient;

import io.restassured.response.Response;
import yandex.objects.Pet;
import yandex.PetStoreURLs;


public class PetHTTPClients extends BaseHTTPClients {
    public Response createNewPet(Pet pet) {
        String url = PetStoreURLs.SERVER + PetStoreURLs.PET;
        return doPostRequest(url, pet, "application/json");
    }

    public Response deletePet(long petId) {
        String url = PetStoreURLs.SERVER + PetStoreURLs.PET + "/" + petId;
        return doDeleteRequest(url, "application/json");
    }

    public Response updatePetData(Pet pet) {
        String url = PetStoreURLs.SERVER + PetStoreURLs.PET;
        return doPutRequest(url, pet, "application/json");
    }

    public Response getPetData(long petId) {
        String url = PetStoreURLs.SERVER + PetStoreURLs.PET + "/" + petId;
        return doGetRequest(url);
    }
}
