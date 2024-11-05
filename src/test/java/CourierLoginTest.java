import courier.CourierClient;
import courier.CourierId;
import courier.CourierLogin;
import courier.CreateCourier;
import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requestspecification.BaseUrl;

import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.hamcrest.Matchers.*;

public class CourierLoginTest extends BaseUrl {
    CourierClient courierClient = new CourierClient();
    private int id;


    @After
    public void tearDown() {
          courierClient.deleteCourier(id);
    }

    @DisplayName("Test for login courier and delete courier")
    @Test
    public void loginCourierTest() {
         Response loginResponse = createCourierAndLogin();
         checkCourierId(loginResponse);
        System.out.println(loginResponse.body().asString());
        id = loginResponse.as(CourierId.class).getId();
    }


    @Step("Create courier and login")
    public Response createCourierAndLogin() {
        CreateCourier createCourier = new CreateCourier()
                .withFirstName(random(15)).withLogin(random(9)).withPassword(random(12));


      courierClient.create(createCourier);

        Response loginResponse = CourierClient.createdLogin(createCourier);
        return loginResponse;
    }

    @Step("Check response status code of courier")
    public void checkCourierId(Response loginResponse) {
        loginResponse.then().statusCode(200).and().body("id", notNullValue());
    }

    @DisplayName("Login courier without password and check status code")
    @Test
    public void validationPasswordTest() {
        Response loginResponse = loginWithoutPassword();
        checkLoginWithoutPassword(loginResponse);
        id = loginResponse.as(CourierId.class).getId();
    }

    @Step("Courier login without password")
    public Response loginWithoutPassword() {
        CreateCourier createCourier = new CreateCourier()
                .withFirstName("Алексей").withLogin("QABeast228").withPassword("Large_kitchen900");

        courierClient.create(createCourier);

        CourierLogin loginWithoutPassword = new CourierLogin()
                .withLogin("QABeast228");

        Response loginResponse = courierClient.login(loginWithoutPassword);

        return loginResponse;
    }

    @Step("Check status code of courier without password")
    public void checkLoginWithoutPassword(Response loginResponse) {
       loginResponse.then().statusCode(400).assertThat().body("message", equalTo("Недостаточно данных для входа"));

}
    @DisplayName("Login courier without login and check status code")
    @Test
    public void validationLoginTest() {
        Response loginResponse = loginWithoutLogin();
        checkLoginWithoutLogin(loginResponse);
        id = loginResponse.as(CourierId.class).getId();
    }

    @Step("Courier login without login")
    public Response loginWithoutLogin() {
        CreateCourier createCourier = new CreateCourier()
                .withFirstName("Алексей").withLogin("QABeast228").withPassword("Large_kitchen900");

        courierClient.create(createCourier);

        CourierLogin loginWithoutPassword = new CourierLogin()
                .withPassword("Large_kitchen900");

        Response loginResponse = courierClient.login(loginWithoutPassword);

        return loginResponse;
    }

    @Step("Check status code of courier without login")
    public void checkLoginWithoutLogin(Response loginResponse) {
        loginResponse.then().statusCode(400).assertThat().body("message", equalTo("Недостаточно данных для входа"));

    }
    @DisplayName("Login courier with incorrect login. Check body and status code")
    @Test
    public void incorrectLogin() {
    Response loginResponse = sendIncorrectLogin();
    checkIncorrectLogin(loginResponse);
        id = loginResponse.as(CourierId.class).getId();

    }
    @Step("Send incorrect login for courier")
    public Response sendIncorrectLogin() {
        CreateCourier createCourier = new CreateCourier()
                .withFirstName("Алексей").withLogin("QABeast228").withPassword("Large_kitchen900");

        courierClient.create(createCourier);

        CourierLogin loginWithoutPassword = new CourierLogin()
                .withPassword("Large_kitchen900").withLogin("QA");

        Response loginResponse = courierClient.login(loginWithoutPassword);
        return  loginResponse;
    }

    @Step("Check status code and body of courier with incorrect login")
    public void checkIncorrectLogin(Response loginResponse) {
        loginResponse.then().statusCode(404).and().assertThat().body("message", equalTo("Учетная запись не найдена"));
    }
    @DisplayName("Login courier with incorrect password. Check body and status code")
    @Test
    public void incorrectPassword() {
        Response loginResponse = sendIncorrectPassword();
        checkIncorrectPassword(loginResponse);
        id = loginResponse.as(CourierId.class).getId();

    }
    @Step("Send incorrect password for courier")
    public Response sendIncorrectPassword() {
        CreateCourier createCourier = new CreateCourier()
                .withFirstName("Алексей").withLogin("QABeast228").withPassword("Large_kitchen900");

        courierClient.create(createCourier);

        CourierLogin loginWithoutPassword = new CourierLogin()
                .withPassword("Large").withLogin("QABeast228");

        Response loginResponse = courierClient.login(loginWithoutPassword);
        return  loginResponse;
    }

    @Step("Check status code and body of courier with incorrect password")
    public void checkIncorrectPassword(Response loginResponse) {
        loginResponse.then().statusCode(404).and().assertThat().body("message", equalTo("Учетная запись не найдена"));
    }
    @DisplayName("Try to login with non-existent user. Check status code and body")
    @Test
    public void nonExistentUserTest() {
     Response loginResponse = nonExistentUserSend();
     checkNonExistentUser(loginResponse);
        id = loginResponse.as(CourierId.class).getId();
    }

    @Step("Login with non-existent user")
    public Response nonExistentUserSend() {
        CourierLogin loginWithoutPassword = new CourierLogin()
                .withPassword("4567132").withLogin("IOLIOLIOL");

        Response loginResponse = courierClient.login(loginWithoutPassword);
        return  loginResponse;
    }
    @Step("Check status code and body of non-existent user")
    public void checkNonExistentUser(Response loginResponse) {
        loginResponse.then().statusCode(404).and().assertThat().body("message", equalTo("Учетная запись не найдена"));
    }
}