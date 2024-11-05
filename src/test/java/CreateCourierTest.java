import courier.CourierClient;
import courier.CourierId;
import courier.CreateCourier;
import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import requestspecification.BaseUrl;

import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.hamcrest.Matchers.equalTo;



public class CreateCourierTest extends BaseUrl {
private int id;
CourierClient courierClient = new CourierClient();



    @DisplayName("Create courier. Check status code and body")
    @Test
    public void createCourierPossibility() {
        Response response = createCourier();
        checkStatusCodeAndBody(response);


    }

    @Step("Create courier and send post request.")
    public Response createCourier() {
        CreateCourier createCourier = new CreateCourier()
                .withFirstName(random(15)).withLogin(random(9)).withPassword(random(12));


        Response response = courierClient.create(createCourier);

        // Логинимся, получаем ID и удаляем курьера
        Response loginResponse = CourierClient.createdLogin(createCourier);
        id = loginResponse.as(CourierId.class).getId();
        courierClient.deleteCourier(id);
        return response;
    }

    @Step("Check statusCode and body of created courier")
    public void checkStatusCodeAndBody(Response response) {
        response.then().statusCode(201).
                and().assertThat().body("ok", equalTo(true));
    }

    @DisplayName("Create conflict(same) courier. Check status code and body")
    @Test
    public void conflictCourier(){
        Response response = conflictCourierPost();
        checkConflictCourier(response);


    }

    @Step("Create identical courier")
    public Response conflictCourierPost() {
        CreateCourier createCourier = new CreateCourier()
                .withFirstName("saske").withLogin("ninja").withPassword("1234");


        Response response = courierClient.create(createCourier);

        // Логинимcя, получаем ID и удаляем курьера
        Response loginResponse = CourierClient.createdLogin(createCourier);
        id = loginResponse.as(CourierId.class).getId();
        courierClient.deleteCourier(id);
               return response;

    }
    @Step("Check statusCode of conflict courier")
    public void checkConflictCourier(Response response) {
        response.then().statusCode(409).
                and().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

    }
    @DisplayName("Create courier without firstName. Check status code and body")
    @Test
    public void courierWithoutFirstName() {
             Response response = createCourierWithoutFirstName();
             checkCourierWithoutFirstName(response);
    }

    @Step("Create courier without FirstName")
    public Response createCourierWithoutFirstName() {
        CreateCourier createCourier = new CreateCourier()
                .withPassword(random(12)).withLogin(random(15));


        Response response = courierClient.create(createCourier);
        // Логинимcя, получаем ID и удаляем курьера
        Response loginResponse = CourierClient.createdLogin(createCourier);
        id = loginResponse.as(CourierId.class).getId();
        courierClient.deleteCourier(id);
        return response;
    }
    @Step("Check statusCode of courier without FirstName")
    public void checkCourierWithoutFirstName(Response response) {
        response.then().statusCode(400).
                and().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
    @DisplayName("Create courier without login. Check status code and body")
    @Test
    public void courierWithoutLogin() {
        Response response = createCourierWithoutLogin();
        checkCourierWithoutLogin(response);
    }
    @Step("Create courier without Login")
    public Response createCourierWithoutLogin() {
        CreateCourier createCourier = new CreateCourier()
                .withFirstName(random(12)).withPassword(random(14));



        Response response = courierClient.create(createCourier);
        // Логинимя, получаем ID и удаляем курьера
        Response loginResponse = CourierClient.createdLogin(createCourier);
        id = loginResponse.as(CourierId.class).getId();
        courierClient.deleteCourier(id);
        return response;
    }

    @Step("Check statusCode of courier without Login")
    public void checkCourierWithoutLogin(Response response) {
        response.then().statusCode(400).
                and().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
    @DisplayName("Create courier without password. Check status code and body")
    @Test
    public void courierWithoutPassword(){
        Response response = createCourierWithoutPassword();
        checkCourierWithoutPassword(response);
    }
    @Step("Create courier without password")
    public Response createCourierWithoutPassword() {
        CreateCourier createCourier = new CreateCourier()
                .withLogin(random(15)).withFirstName(random(12));

        Response response = courierClient.create(createCourier);

        // Логинимcя, получаем ID и удаляем курьера
        Response loginResponse = CourierClient.createdLogin(createCourier);
        id = loginResponse.as(CourierId.class).getId();
        courierClient.deleteCourier(id);
        return response;
    }
    @Step("Check statusCode of courier without password")
    public void checkCourierWithoutPassword(Response response) {
        response.then().statusCode(400).
                and().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }

    @DisplayName("Create courier with the identical login. Check status code and body")
   @Test
   public void loginTest() {
        Response response = sameLoginCourier();
        checkSameLoginCourier(response);

   }

    @Step("Create courier with identical login")
    public Response sameLoginCourier() {
        CreateCourier createCourier = new CreateCourier()
                .withFirstName("DavidLuigi11").withLogin("ninja").withPassword("Fi1231313");


        Response response = courierClient.create(createCourier);

        // Логинимcя, получаем ID и удаляем курьера
        Response loginResponse = CourierClient.createdLogin(createCourier);
        id = loginResponse.as(CourierId.class).getId();
        courierClient.deleteCourier(id);
        return response;

    }
    @Step("Check statusCode of courier with identical login")
    public void checkSameLoginCourier(Response response) {
        response.then().statusCode(409).
                and().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

    }


}