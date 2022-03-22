package client;

import io.restassured.response.Response;
import model.request.UserReg;
import model.response.UserRegResponseSuccess;

import static io.restassured.RestAssured.given;

public class UserClient extends HttpClient {


    public UserClient(){
        super("/api/users");
    }

    public Response createUser(UserReg registerUser){
        return given(defaultRequestSpecification)
                .body(registerUser)
                .post();
    }

    public UserRegResponseSuccess successUserReg(UserRegResponseSuccess userRegResponseSuccess){
    return given(defaultRequestSpecification)
                .body(userRegResponseSuccess)
                .when()
                .post("api/register")
                .then()
                .log().all()
                .extract().as(UserRegResponseSuccess.class);}
}
