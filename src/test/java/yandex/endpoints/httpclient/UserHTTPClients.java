package yandex.endpoints.httpclient;

import io.restassured.response.Response;
import yandex.PetStoreURLs;
import yandex.objects.User;

public class UserHTTPClients extends BaseHTTPClients {
    public Response createNewUser(User user) {
        String url = PetStoreURLs.SERVER + PetStoreURLs.USER;
        return doPostRequest(url, user, "application/json");
    }

    public Response deleteUser(String username) {
        String url = PetStoreURLs.SERVER + PetStoreURLs.USER + "/" + username;
        return doDeleteRequest(url, "application/json");
    }

    public Response updateUserData(User user, String username) {
        String url = PetStoreURLs.SERVER + PetStoreURLs.USER + "/" + username;
        return doPutRequest(url, user, "application/json");
    }

    public Response getUserData(String username) {
        String url = PetStoreURLs.SERVER + PetStoreURLs.USER + "/" + username;
        return doGetRequest(url);
    }

    public Response loginUser(User user) {
        String url = PetStoreURLs.SERVER + PetStoreURLs.USER + "/login";
        return doGetRequest(url, user);
    }
}
