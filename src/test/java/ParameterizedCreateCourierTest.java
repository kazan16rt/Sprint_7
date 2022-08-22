import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ParameterizedCreateCourierTest {
    private Courier courier;
    private CourierClient courierClient;

    private final String login;
    private final String password;

    public ParameterizedCreateCourierTest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Before
    public void setUp() {
        courier = CourierData.getDefault();
        courierClient = new CourierClient();
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][] {
                {null, null},
                {null, "12345"},
                {"Potato", null},
                {"", ""},
        };
    }

    @Test
    public void createCourierWithEmptyRequiredFieldsTest() {
        courier.setLogin(login);
        courier.setPassword(password);
        ValidatableResponse response = courierClient.create(courier);

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrect", SC_BAD_REQUEST, statusCode);

        String message = response.extract().path("message");
        assertEquals("Wrong message", CourierErrors.CREATE_INCORRECT_COURIER, message);
    }
}
