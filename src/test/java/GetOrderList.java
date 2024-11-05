
import org.junit.Before;
import org.junit.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import requestspecification.BaseUrl;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.request;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.hamcrest.Matchers.notNullValue;







public class GetOrderList extends BaseUrl {



   @Test
    public void orderListTest() {
      Response response = given().get("api/v1/orders");
      response.then().statusCode(200).and().assertThat().body("orders", notNullValue());

       System.out.println(response.body().asString());
   }
}
