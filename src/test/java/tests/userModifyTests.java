package tests;

import model.request.User;
import model.response.UserResponse;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import spec.Specifications;

import java.time.Clock;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class userModifyTests {

    private final static String URL = "https://reqres.in/";

    /**
     * Create user with valid date test
     */
    @Test
    @DisplayName("Create user successful")
    public void createUserSuccessfulTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpec(201));

        User createUser = new User();
        createUser.setName("morpheus");
        createUser.setJob("leader");

        UserResponse userResponse = given()
                .body(createUser)
                .when()
                .post("api/users/")
                .then().log().status()
                .extract().as(UserResponse.class);

        assertThat(userResponse)
                .isNotNull()
                .extracting(UserResponse::getName)
                .isEqualTo(createUser.getName());
    }

    /**
     * Check date of time users update from servers and pc
     */
    @Test
    @DisplayName("Date of user are equals")
    public void checkServerAndPcDateTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpec(200));
        User user = new User("morpheus", "zion resident");
        UserResponse response = given()
                .body(user)
                .when()
                .put("api/users/2")
                .then().log().status()
                .extract().as(UserResponse.class);

        String regex = "(.{8})$"; // regular expression -- useful site
        String regex2 = "(.{5})$";

        // best practices add data formatter
        String currentTime = Clock.systemUTC().instant().toString().replaceAll(regex, "");
        Assertions.assertEquals(response.getUpdatedAt().replaceAll(regex2, ""), currentTime);
    }

    @Test
    @DisplayName("Update user by path and check data")
    public void pathUserTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpec(200));
        User user = new User("morpheus", "zion resident");

        UserResponse response = given()
                .body(user)
                .when()
                .patch("api/users/2")
                .then().log().status()
                .extract().as(UserResponse.class);

        String regex = "(.{8})$"; // regular expression -- useful site
        String regex2 = "(.{3})$";

        // best practices add data formatter
        String currentTime = Clock.systemUTC().instant().toString().replaceAll(regex, "");

        assertThat(response.getUpdatedAt().replaceAll(regex, ""))
                .isNotNull()
//                .extracting(UserResponse::getUpdatedAt)
                .isEqualTo(currentTime.replaceAll(regex2, ""));
    }

    /**
     * 1. Delete user
     */
    @Test
    @DisplayName("Delete User")
    public void deleteUserTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpec(204));
        given().when().delete("/api/users/2")
                .then()
                .log().status()
                .extract().body();
    }
}
