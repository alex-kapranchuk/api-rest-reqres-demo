package stepsBL;

import client.UserClient;
import io.restassured.response.Response;
import model.request.UserReg;
import model.response.UserRegResponseSuccess;
import org.junit.jupiter.api.Assertions;

public class UserBL {
    UserClient userClient;



    public UserBL(){
        this.userClient = new UserClient();
    }

    public void createNewUser(UserReg registerUser){
        Response response = userClient.createUser(registerUser);
        // Assertions from response token and id

    }
    public void successResponseUser (UserRegResponseSuccess userRegResponseSuccess){
        // Assertions from response token and id
        Assertions.assertNotNull(userRegResponseSuccess.getId());
        Assertions.assertNotNull(userRegResponseSuccess.getToken());
        Assertions.assertEquals(4, userRegResponseSuccess.getId());
        Assertions.assertEquals("pwL5tke4Pnpja7X4", userRegResponseSuccess.getToken());

    }
}
