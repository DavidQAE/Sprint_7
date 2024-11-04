package requestspecification;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.BeforeClass;

public class BaseUrl {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

}
