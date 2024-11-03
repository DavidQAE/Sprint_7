package Courier;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierClient {

    public Response create(CreateCourier createCourier) {
       return  given()
                .header("Content-type", "application/json")
                .body(createCourier)
                .post("/api/v1/courier");

    }


    public Response login(CourierLogin courierLogin) {
        return given()
                .header("Content-type", "application/json")
                .body(courierLogin)
                .post("/api/v1/courier/login");
    }

    public static Response createdLogin(CreateCourier createCourier) {
        return given()
                .header("Content-type", "application/json")
                .body(CourierCreds.credsFromCourier(createCourier))
                .post("/api/v1/courier/login");
    }

    public Response deleteCourier(int id) {
        return given()
                .header("Content-type", "application/json")
                .delete("/api/v1/courier/" + id);
    }






}


