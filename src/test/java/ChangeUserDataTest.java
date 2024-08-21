import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.is;

public class ChangeUserDataTest {
    String email = RandomStringUtils.randomAlphabetic(1) + "@" + RandomStringUtils.randomAlphabetic(1) + ".ru";
    String password = RandomStringUtils.randomAlphanumeric(6);
    String name = RandomStringUtils.randomAlphabetic(1);
    private final UserSteps createNewUser = new UserSteps();

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
        createNewUser.createNewUser(email, password, name).statusCode(200).and().body("success", is(true));
    }

    @Test
    public void changeUserEmailWithAuthIsSuccess() {
        createNewUser.loginUser(email, password).statusCode(200).and().body("success", is(true));
        String accessToken = createNewUser.loginUser(email, password).extract().body().path("accessToken");
        accessToken = accessToken.replace("Bearer ", "");
        email = email + "1";
        createNewUser.changeUserEmailWithAuth(accessToken, email).statusCode(200).and().body("success", is(true));
    }
    @Test
    public void changeUserNameWithAuthIsSuccess() {
        createNewUser.loginUser(email, password).statusCode(200).and().body("success", is(true));
        String accessToken = createNewUser.loginUser(email, password).extract().body().path("accessToken");
        accessToken = accessToken.replace("Bearer ", "");
        name = name + "1";
        createNewUser.changeUserNameWithAuth(accessToken, name).statusCode(200).and().body("success", is(true));
    }
    @Test
    public void changeUserNameWithoutAuthIsNotSuccess() {
        createNewUser.loginUser(email, password).statusCode(200).and().body("success", is(true));
        name = name + "2";
        createNewUser.changeUserNameWithoutAuth(name).statusCode(401).and().body("success", is(false));
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

