import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;

public class GetUserOrderListTest {
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
    public void getOrderListWithAuth() {
        createNewUser.loginUser(email, password).statusCode(200).and().body("success", is(true));
        String accessToken = createNewUser.loginUser(email, password).extract().body().path("accessToken");
        accessToken = accessToken.replace("Bearer ", "");
        createNewOrder.createNewAuthOrderWithHesh(accessToken, ingredients).statusCode(200).and().body("success", is(true));
        createNewOrder.getOrdersListWithAuth(accessToken).statusCode(200).and().body("success", is(true));
    }
    @Test
    public void getOrderListWithoutAuth() {
        createNewOrder.getOrdersListWithoutAuth().statusCode(401).and().body("success", is(false));
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