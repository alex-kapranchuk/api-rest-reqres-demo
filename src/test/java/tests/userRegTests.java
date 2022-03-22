package tests;

import model.request.UserReg;
import model.response.UserRegResponseSuccess;
import model.response.UserRegResponseUnsuccessful;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import spec.Specifications;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class userRegTests {

    private final static String URL = "https://reqres.in/"; // Important - this site has only hardcode data - date couldn't be updated

    /**
     * 1. Registration check  https://reqres.in/
     * 2. Successful registration
     */
    @Test
    @DisplayName("Successful registration")
    public void successUserRegTest2() {
        Integer UserId = 4;
        String UserPassword = "QpwL5tke4Pnpja7X4";
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpec(200));
        UserReg UserReg = new UserReg("eve.holt@reqres.in", "pistol");
        UserRegResponseSuccess userRegResponseSuccess = given()
                .body(UserReg)
                .when()
                .post("api/register")
                .then()
                .log().status()
                .extract().as(UserRegResponseSuccess.class);
        Assertions.assertNotNull(userRegResponseSuccess.getId());
        Assertions.assertNotNull(userRegResponseSuccess.getToken());
        Assertions.assertEquals(UserId, userRegResponseSuccess.getId());
        Assertions.assertEquals(UserPassword, userRegResponseSuccess.getToken());
    }

    /**
     * 1. Registration check  https://reqres.in/
     * 2. Unsuccessful registration - incorrect password
     */
    @Test
    @DisplayName("Unsuccessful registration")
    public void unSuccessUserRegTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpec(400));
        UserReg peopleSecond = new UserReg("sydney@fife", "");
        UserRegResponseUnsuccessful unSuccessUserReg = given()
                .body(peopleSecond)
                .when()
                .post("api/register")
                .then()
                .log().status()
                .extract().as(UserRegResponseUnsuccessful.class);
        Assertions.assertNotNull(unSuccessUserReg.getError());
        Assertions.assertEquals("Missing password", unSuccessUserReg.getError());
    }

    /**
     * Check user get token after successful login
     */
    @Test
    @DisplayName("Successful login")
    public void postSuccessfulLoginTes() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpec(200));
        UserReg login = new UserReg("eve.holt@reqres.in", "cityslicka");
        // login don't have id field
        UserRegResponseSuccess loginResponse = given()
                .body(login)
                .when()
                .post("api/login")
                .then()
                .log().status()
                .extract().as(UserRegResponseSuccess.class);

        assertThat(loginResponse)
                .isNotNull()
                .extracting(UserRegResponseSuccess::getToken)
                .isEqualTo("QpwL5tke4Pnpja7X4");
    }

    @Test
    @DisplayName("Successful login")
    public void postUnSuccessfulLoginTes() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpec(400));
        UserReg login = new UserReg("peter@klaven", "");
        // login don't have id field
        UserRegResponseUnsuccessful loginResponse = given()
                .body(login)
                .when()
                .post("api/login")
                .then()
                .log().status()
                .extract().as(UserRegResponseUnsuccessful.class);

        assertThat(loginResponse)
                .isNotNull()
                .extracting(UserRegResponseUnsuccessful::getError)
                .isEqualTo("Missing password");
    }
}

