package yandex.endpoints.httpclient;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public abstract class BaseHTTPClients {
    private RequestSpecification baseRequest() {
        return new RequestSpecBuilder()
                .addFilter(new AllureRestAssured())
                .setRelaxedHTTPSValidation()
                .build();
    }

    private RequestSpecification baseRequest(String contentType) {
        return new RequestSpecBuilder()
                .addHeader("Content-type", contentType)
                .addFilter(new AllureRestAssured())
                .setRelaxedHTTPSValidation()
                .build();
    }

    public Response doPostRequest(String url, Object requestBody, String contentType) {
        return given(this.baseRequest(contentType))
                .body(requestBody)
                .when()
                .post(url);
    }

    public Response doGetRequest(String url) {
        return given(this.baseRequest())
                .get(url);
    }
    public Response doGetRequest(String url, Object requestBody) {
        return given(this.baseRequest())
                .body(requestBody)
                .when()
                .get(url);
    }

    public Response doDeleteRequest(String url, String contentType) {
        return given(this.baseRequest(contentType))
                .delete(url);
    }

    public Response doPutRequest(String url, Object requestBody, String contentType) {
        return given(this.baseRequest(contentType))
                .body(requestBody)
                .when()
                .put(url);
    }
}
