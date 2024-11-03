package Order;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderClient {

    public Response create(CreateOrder createOrder) {
        return given()
                .header("Content-type", "application/json")
                .body(createOrder)
                .post("/api/v1/orders");
    }
}
