import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;

import static org.hamcrest.Matchers.is;

public class NewOrderTest {
    String email = RandomStringUtils.randomAlphabetic(5) + "@" + RandomStringUtils.randomAlphabetic(5) + ".ru";
    String password = RandomStringUtils.randomAlphanumeric(8);
    String name = RandomStringUtils.randomAlphabetic(5);
    IngredientsList ingredients = new IngredientsList("ingredients", Arrays.asList("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f"));
    private final UserSteps createNewUser = new UserSteps();
    private final OrderSteps createNewOrder = new OrderSteps();
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
        createNewUser.createNewUser(email, password, name).statusCode(200).and().body("success", is(true));

    }
    @Test
    public void createNewOrderWithAuthAndIngredients() {
        createNewUser.loginUser(email, password).statusCode(200).and().body("success", is(true));
        String accessToken = createNewUser.loginUser(email, password).extract().body().path("accessToken");
        accessToken = accessToken.replace("Bearer ", "");
        createNewOrder.createNewAuthOrderWithHesh(accessToken, ingredients).statusCode(200).and().body("success", is(true));
    }
    @Test
    public void createNewOrderWithAuthAndWithoutIngredients() {
        createNewUser.loginUser(email, password).statusCode(200).and().body("success", is(true));
        String accessToken = createNewUser.loginUser(email, password).extract().body().path("accessToken");
        accessToken = accessToken.replace("Bearer ", "");
        createNewOrder.createNewOrderWithAuth(accessToken).statusCode(400);
        createNewOrder.createNewOrder().extract().body().asString().contains("Bad Request");
    }
    @Test
    public void createNewOrderWithoutAuthAndIngredients() {
        createNewOrder.createNewOrder().statusCode(400);
        createNewOrder.createNewOrder().extract().body().asString().contains("Bad Request");
    }
    @Test
    public void createNewOrderWithIngreidintsButWithoutAuth() {
        createNewOrder.createNewOrderWithHesh(ingredients).statusCode(200).and().body("success", is(true));
    }
    @Test
    public void createNewOrderWithIncorrectIngredients() {
        ingredients = new IngredientsList("ingredients", Arrays.asList("61c0c5a71d1f82001bdaaa6", "61c0c5a71d1f82001bdaaa6"));
        createNewOrder.createNewOrderWithHesh(ingredients).statusCode(500);
        createNewOrder.createNewOrder().extract().body().asString().contains("Internal Server Error");
    }
    @After
    public void tearDown() {
        String accessToken = createNewUser.loginUser(email, password).extract().body().path("accessToken");
        if (accessToken != null) {
            accessToken = accessToken.replace("Bearer ", "");
            createNewUser.deleteUser(accessToken).statusCode(202).and().body("success", is(true));
        }
    }
}
