package utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.specification.RequestSpecification;
import model.request.UserReg;

import static io.restassured.RestAssured.given;

public class RestWrapper() {
    private final static String URL = "https://reqres.in/";
    private static  RequestSpecification REQUEST_SPECIFICATION;
    private Cookies cookies;

    private RestWrapper(Cookies cookies){
        this.cookies = cookies;


    REQUEST_SPECIFICATION = new RequestSpecBuilder()
            .addCookie(cookies)
            .setBaseUri("/users")
            .setContentType(ContentType.JSON)
            .build();
    }


    public static RestWrapper loginAs(String login, String password){
        Cookies cookies = given()
                .contentType(ContentType.JSON)
                .baseUri(URL)
                .basePath("/login")
                .body(new UserReg(login,password))
                .post()
                .getDetailedCookies();
        return new RestWrapper(cookies); // method return  request with cookie it could be token
    }
}
