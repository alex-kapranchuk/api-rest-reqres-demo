package client;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BookingClient extends HttpClient{
    protected BookingClient(String pathUrl) {
        super("booking");
    }
    public Response getBookingId(Integer id){
        return given(defaultRequestSpecification)
                .get("", id );
    }
}
