import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserSteps {
    @Step("Создание пользователя")
    public ValidatableResponse createNewUser(String email, String password, String name) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body("{\"email\": \"" + email + "\", \"password\": \"" + password + "\", \"name\": \"" + name + "\"}")
                .when()
                .post("/api/auth/register").then();
    }

    @Step("Авторизация пользователя")
    public ValidatableResponse loginUser(String email, String password) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body("{\"email\": \"" + email + "\", \"password\": \"" + password + "\"}")
                .when()
                .post("/api/auth/login").then();
    }
    @Step("Изменение email пользователя с авторизацией")
    public ValidatableResponse changeUserEmailWithAuth(String accessToken, String email) {
        return given()
                .contentType(ContentType.JSON)
                .auth().oauth2(accessToken)
                .and()
                .body("{\"email\": \"" + email + "\"}")
                .when()
                .patch("/api/auth/user").then();
    }
    @Step("Изменение name пользователя с авторизацией")
    public ValidatableResponse changeUserNameWithAuth(String accessToken, String name) {
        return given()
                .contentType(ContentType.JSON)
                .auth().oauth2(accessToken)
                .and()
                .body("{\"name\": \"" + name + "\"}")
                .when()
                .patch("/api/auth/user").then();
    }
    @Step("Изменение name пользователя без авторизациии")
    public ValidatableResponse changeUserNameWithoutAuth(String name) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body("{\"name\": \"" + name + "\"}")
                .when()
                .patch("/api/auth/user").then();
    }
    @Step("Обновление токена")
    public ValidatableResponse refreshTokenAccessToken(String refreshToken) {
        return given()
                .contentType(ContentType.JSON)
                .pathParam("refreshToken", refreshToken)
                .and()
                .body("{\"token\": \"" + refreshToken + "\"}")
                .when()
                .post("/api/auth/token").then();
    }
    @Step("Выход из системы")
    public ValidatableResponse logoutUser(String refreshToken) {
        return given()
                .contentType(ContentType.JSON)
                .pathParam("refreshToken", refreshToken)
                .and()
                .body("{\"token\": \"" + refreshToken + "\"}")
                .when()
                .post("/api/auth/logout").then();
    }
    @Step("Удаление пользователя")
    public ValidatableResponse deleteUser(String accessToken) {
        return given()
                .contentType(ContentType.JSON)
                .auth().oauth2(accessToken)
                .when()
                .delete("/api/auth/user").then();
    }
}
