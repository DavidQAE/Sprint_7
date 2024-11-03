import Courier.CourierClient;
import Courier.CourierId;
import Courier.CourierLogin;
import Courier.CreateCourier;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.hamcrest.Matchers.*;

public class CourierLoginTest {
    CourierClient courierClient = new CourierClient();
    private int id;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";

    }

    @After
    public void tearDown() {
          courierClient.deleteCourier(id);
    }


    @Test
    public void loginCourierTest() {
         Response loginResponse = createCourierAndLogin();
         checkCourierId(loginResponse);
        System.out.println(loginResponse.body().asString());
        id = loginResponse.as(CourierId.class).getId();
    }


    @Step
    public Response createCourierAndLogin() {
        CreateCourier createCourier = new CreateCourier()
                .withFirstName(random(15)).withLogin(random(9)).withPassword(random(12));


      courierClient.create(createCourier);

        Response loginResponse = CourierClient.createdLogin(createCourier);
        return loginResponse;
    }

    @Step
    public void checkCourierId(Response loginResponse) {
        loginResponse.then().statusCode(200).and().body("id", notNullValue());
    }

    @Test
    public void ValidationPasswordTest() {
        Response loginResponse = LoginWithoutPassword();
        checkLoginWithoutPassword(loginResponse);
        id = loginResponse.as(CourierId.class).getId();
    }

    @Step
    public Response LoginWithoutPassword() {
        CreateCourier createCourier = new CreateCourier()
                .withFirstName("Алексей").withLogin("QABeast228").withPassword("Large_kitchen900");

        courierClient.create(createCourier);

        CourierLogin loginWithoutPassword = new CourierLogin()
                .withLogin("QABeast228");

        Response loginResponse = courierClient.login(loginWithoutPassword);

        return loginResponse;
    }

    @Step
    public void checkLoginWithoutPassword(Response loginResponse) {
       loginResponse.then().statusCode(400).assertThat().body("message", equalTo("Недостаточно данных для входа"));

}
    @Test
    public void ValidationLoginTest() {
        Response loginResponse = LoginWithoutLogin();
        checkLoginWithoutLogin(loginResponse);
        id = loginResponse.as(CourierId.class).getId();
    }

    @Step
    public Response LoginWithoutLogin() {
        CreateCourier createCourier = new CreateCourier()
                .withFirstName("Алексей").withLogin("QABeast228").withPassword("Large_kitchen900");

        courierClient.create(createCourier);

        CourierLogin loginWithoutPassword = new CourierLogin()
                .withPassword("Large_kitchen900");

        Response loginResponse = courierClient.login(loginWithoutPassword);

        return loginResponse;
    }

    @Step
    public void checkLoginWithoutLogin(Response loginResponse) {
        loginResponse.then().statusCode(400).assertThat().body("message", equalTo("Недостаточно данных для входа"));

    }

    @Test
    public void incorrectLogin() {
    Response loginResponse = sendIncorrectLogin();
    checkIncorrectLogin(loginResponse);
        id = loginResponse.as(CourierId.class).getId();

    }
    @Step
    public Response sendIncorrectLogin() {
        CreateCourier createCourier = new CreateCourier()
                .withFirstName("Алексей").withLogin("QABeast228").withPassword("Large_kitchen900");

        courierClient.create(createCourier);

        CourierLogin loginWithoutPassword = new CourierLogin()
                .withPassword("Large_kitchen900").withLogin("QA");

        Response loginResponse = courierClient.login(loginWithoutPassword);
        return  loginResponse;
    }

    @Step
    public void checkIncorrectLogin(Response loginResponse) {
        loginResponse.then().statusCode(404).and().assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void incorrectPassword() {
        Response loginResponse = sendIncorrectPassword();
        checkIncorrectPassword(loginResponse);
        id = loginResponse.as(CourierId.class).getId();

    }
    @Step
    public Response sendIncorrectPassword() {
        CreateCourier createCourier = new CreateCourier()
                .withFirstName("Алексей").withLogin("QABeast228").withPassword("Large_kitchen900");

        courierClient.create(createCourier);

        CourierLogin loginWithoutPassword = new CourierLogin()
                .withPassword("Large").withLogin("QABeast228");

        Response loginResponse = courierClient.login(loginWithoutPassword);
        return  loginResponse;
    }

    @Step
    public void checkIncorrectPassword(Response loginResponse) {
        loginResponse.then().statusCode(404).and().assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void nonExistentUserTest() {
     Response loginResponse = nonExistentUserSend();
     checkNonExistentUser(loginResponse);
        id = loginResponse.as(CourierId.class).getId();
    }

    @Step
    public Response nonExistentUserSend() {
        CourierLogin loginWithoutPassword = new CourierLogin()
                .withPassword("4567132").withLogin("IOLIOLIOL");

        Response loginResponse = courierClient.login(loginWithoutPassword);
        return  loginResponse;
    }
    @Step
    public void checkNonExistentUser(Response loginResponse) {
        loginResponse.then().statusCode(404).and().assertThat().body("message", equalTo("Учетная запись не найдена"));
    }
}