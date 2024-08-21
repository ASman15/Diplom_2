import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.is;

public class CreateNewUserTest {
    String email = RandomStringUtils.randomAlphabetic(1) + "@" + RandomStringUtils.randomAlphabetic(1) + ".ru";
    String password = RandomStringUtils.randomAlphanumeric(6);
    String name = RandomStringUtils.randomAlphabetic(1);
    private final UserSteps createNewUser = new UserSteps();

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
    }

    @Test
    public void createUserWithAllFieldsIsSuccess() {
        createNewUser.createNewUser(email, password, name)
                .statusCode(200).and().body("success", is(true));
    }
    @Test
    public void createAlrearyRegesteredUserIsNotSuccess() {
        createNewUser.createNewUser(email, password, name).statusCode(200);
        createNewUser.createNewUser(email, password, name).statusCode(403).and().body("success", is(false));
    }
    @Test
    public void createUserWithoutEmailIsNotSuccess() {
        String email = "";
        createNewUser.createNewUser(email, password, name)
                .statusCode(403).and().body("success", is(false));
    }
    @Test
    public void createUserWithoutPasswordIsNotSuccess() {
        String password = "";
        createNewUser.createNewUser(email, password, name)
                .statusCode(403).and().body("success", is(false));
    }
    @Test
    public void createUserWithoutNameIsNotSuccess() {
        String name = "";
        createNewUser.createNewUser(email, password, name)
                .statusCode(403).and().body("success", is(false));
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
