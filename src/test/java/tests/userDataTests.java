package tests;

import model.response.UserData;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import spec.Specifications;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class userDataTests {
    private final static String URL = "https://reqres.in/";

    /**
     * 1. Get List from https://reqres.in/
     * 2. Check  id contains in avatar;
     * 3. Emails end with reqres.in;
     */
    @Test
    @DisplayName("Avatars contains id and end with reqres.in ")
    public void checkAvatarContainsIdTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpec(200));
        List<UserData> users = given()
                .when()
                .get("api/users?page=2")
                .then()
                .log().status()
                .extract().body().jsonPath().getList("data", UserData.class);

        users.forEach(x -> Assert.assertTrue("Avatars are not contains id of users", x.getAvatar().contains(String.valueOf(x.getId()))));
        Assert.assertTrue("Emails are not end with @reqres.in", users.stream().allMatch(x -> x.getEmail().endsWith("@reqres.in")));
        // same check with lists
        List<String> avatars = users.stream().map(UserData::getAvatar).collect(Collectors.toList());
        List<Integer> ids = users.stream().map(UserData::getId).collect(Collectors.toList());
        for (int i = 0; i < avatars.size(); i++) {
            Assert.assertTrue(avatars.get(i).contains(String.valueOf(ids.get(i))));
        }
    }

    /**
     * 1. Same check as previous but will be some DELAY
     */
    @Test
    @DisplayName("Avatars contains id and end with reqres.in ")
    public void checkDelayResponseTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpec(200));
        List<UserData> users = given()
                .when()
                .get("/api/users?delay=3")
                .then()
                .log().status()
                .extract().body().jsonPath().getList("data", UserData.class);

        users.forEach(x -> Assert.assertTrue("Avatars are not contains id of users", x.getAvatar().contains(String.valueOf(x.getId()))));
        Assert.assertTrue("Emails are not end with @reqres.in", users.stream().allMatch(x -> x.getEmail().endsWith("@reqres.in")));
    }


    /**
     * Get single user and check avatar url
     */
    @Test
    @DisplayName("User are contains data")
    public void checkUserData() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpec(200));
        UserData response = given()
                .when()
                .get("api/users/3")
                .then()
                .log().status()
                .extract().body().jsonPath().getObject("data", UserData.class);

        assertThat(response.getAvatar().startsWith("https://reqres.in/img/faces"));
        assertThat(response.getAvatar().endsWith("-image.jpg"));
        Assert.assertEquals("equals", "3", response.getId().toString());
    }

    /**
     * Negative test single user is not found
     */
    @Test
    @DisplayName("Single user is not found")
    public void checkNegativeScenario() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpec(404));
        given().when()
                .get("/api/users/23")
                .then()
                .log().status()
                .extract().body();
        //response {}
    }
}
