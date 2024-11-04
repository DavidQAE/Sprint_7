import order.CreateOrder;
import order.OrderClient;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import requestspecification.BaseUrl;

import java.util.Arrays;
import java.util.Collection;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.request;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.hamcrest.Matchers.notNullValue;


@RunWith(Parameterized.class)
public class CreateOrderTest extends BaseUrl {
    OrderClient orderClient = new OrderClient();
    private String[] color;

    public CreateOrderTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> ScooterColor() {
        return Arrays.asList(new Object[][] {
                {new String[]{"BLACK", "GRAY"}},
                {new String[]{"BLACK"}},
                {new String[]{"GRAY"}},
                {new String[]{null}},
        });
    }



    @Test
    public void scooterColorTest() {
        Response response = createOrder();
        checkCreateOrderAndPrintBody(response);
    }

    @Step("Create order while send different colors")
    public Response createOrder() {

        CreateOrder createOrder = new CreateOrder()
                .withAddress(random(20))
                .withFirstName(random(9))
                .withLastName(random(12))
                .withMetroStation("Арбатская")
                .withComment(random(31))
                .withDeliveryDate("04.11.2024")
                .withRentTime(5)
                .withPhone("89678009823")
                .withColor(color);

        Response response = orderClient.create(createOrder);
        return  response;
    }

    @Step("Check the order status code and body")
    public void checkCreateOrderAndPrintBody(Response response) {
        response.then().statusCode(201).and().body("track", notNullValue());
        System.out.println(response.body().asString());
    }
}
