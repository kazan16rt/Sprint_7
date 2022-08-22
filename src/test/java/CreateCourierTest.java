import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class CreateCourierTest {
    private Courier courier;
    private CourierClient courierClient;
    private int courierId;

    @Before
    public void setUp() {
        courier = CourierData.getDefault();
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
    courierClient.delete(courierId);
    }

    @Test
    public void courierCanBeCreateTest() {
        ValidatableResponse response = courierClient.create(courier);

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrect", SC_CREATED, statusCode);

        boolean isCreated = response.extract().path("ok");
        assertTrue("Courier is not created", isCreated);

        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        int loginStatusCode = loginResponse.extract().statusCode();
        assertEquals("Status code is incorrect", SC_OK, loginStatusCode);

        courierId = loginResponse.extract().path("id");
        assertTrue("Login failed", courierId > 0);
    }
    @Test
    public void creationOfTwoCouriersFailedTest() {
        ValidatableResponse response = courierClient.create(courier);

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrect", SC_CREATED, statusCode);

        boolean isCreated = response.extract().path("ok");
        assertTrue("Courier is not created", isCreated);

        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        int loginStatusCode = loginResponse.extract().statusCode();
        assertEquals("Status code is incorrect", SC_OK, loginStatusCode);

        courierId = loginResponse.extract().path("id");
        assertTrue("Login failed", courierId > 0);

        ValidatableResponse responseSecondCourier = courierClient.create(courier);

        int statusCodeTwo = responseSecondCourier.extract().statusCode();
        assertEquals("Status code is incorrect", SC_CONFLICT, statusCodeTwo);

        String message = responseSecondCourier.extract().path("message");
        assertEquals("Creation of the duplicate was successful", CourierErrors.CREATE_DUPLICATE_COURIER, message);
    }

}
