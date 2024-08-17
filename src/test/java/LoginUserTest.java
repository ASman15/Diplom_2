import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.is;

public class LoginUserTest {
    String email = RandomStringUtils.randomAlphabetic(5) + "@" + RandomStringUtils.randomAlphabetic(5) + ".ru";
    String password = RandomStringUtils.randomAlphanumeric(8);
    String name = RandomStringUtils.randomAlphabetic(5);
    private final UserSteps createNewUser = new UserSteps();

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
        createNewUser.createNewUser(email, password, name).statusCode(200).and().body("success", is(true));
    }


    @Test
    public void loginRegisteredUserIsSuccess() {
        createNewUser.loginUser(email, password).statusCode(200).and().body("success", is(true));
    }
    @Test
    public void loginWithIncorrectEmailIsNotSuccess() {
        email = "";
        createNewUser.loginUser(email, password).statusCode(401).and().body("success", is(false));
    }
    @Test
    public void loginWithIncorrectpasswordIsNotSuccess() {
        password = "";
        createNewUser.loginUser(email, password).statusCode(401).and().body("success", is(false));
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
