package yandex.endpoints.httpclient;

import io.restassured.response.Response;
import yandex.PetStoreURLs;
import yandex.objects.Order;

public class StoreHTTPClients extends BaseHTTPClients {
    public Response getStoreInventory () {
        String url = PetStoreURLs.SERVER + PetStoreURLs.STORE_INVENTORY;
        return doGetRequest(url);
    }
    public Response createNewOrder(Order order) {
        String url = PetStoreURLs.SERVER + PetStoreURLs.STORE_ORDER;
        return doPostRequest(url, order, "application/json");
    }
    public Response deleteOrder(long id) {
        String url = PetStoreURLs.SERVER + PetStoreURLs.STORE_ORDER + "/" + id;
        return doDeleteRequest(url, "application/json");
    }
    public Response getOrderData(long id) {
        String url = PetStoreURLs.SERVER + PetStoreURLs.STORE_ORDER + "/" + id;
        return doGetRequest(url);
    }
}
