import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;


import java.util.List;

import static io.restassured.RestAssured.given;

public class OrderSteps {
    IngredientsList ingredients = new IngredientsList();

    @Step("Создание заказа без ингредиентов и без авторизации")
    public ValidatableResponse createNewOrder() {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body("{\"ingredients\": \"[\"]\"}")
                .when()
                .post("api/orders").then();
    }
    @Step("Создание заказа без ингредиентов с авторизацией")
    public ValidatableResponse createNewOrderWithAuth(String accessToken) {
        return given()
                .contentType(ContentType.JSON)
                .auth().oauth2(accessToken)
                .and()
                .body("{\"ingredients\": \"[\"]\"}")
                .when()
                .post("api/orders").then();
    }
    @Step("Создание заказа с известными ингредиентами и без авторизации")
    public ValidatableResponse createNewOrderWithHesh(IngredientsList ingredients) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(ingredients)
                .when()
                .post("api/orders").then();
    }
    @Step("Создание заказа с известными ингредиентами и с авторизацией")
    public ValidatableResponse createNewAuthOrderWithHesh(String accessToken, IngredientsList ingredients) {
        return given()
                .contentType(ContentType.JSON)
                .auth().oauth2(accessToken)
                .and()
                .body(ingredients)
                .when()
                .post("api/orders").then();
    }
    @Step("Запрос списка заказов с авторизацией")
    public ValidatableResponse getOrdersListWithAuth(String accessToken) {
        return given()
                .contentType(ContentType.JSON)
                .auth().oauth2(accessToken)
                .when()
                .get("/api/orders").then();
    }
    @Step("Запрос списка заказов без авторизации")
    public ValidatableResponse getOrdersListWithoutAuth() {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/orders").then();
    }
}
