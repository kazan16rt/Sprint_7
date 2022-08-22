import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ParameterizedLoginTest {
    private Courier courier;
    private CourierClient courierClient;
    private int courierId;
    private final String login;
    private final String password;

    public ParameterizedLoginTest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Before
    public void setUp() {
        courier = CourierData.getDefault();
        courierClient = new CourierClient();
        courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        courierId = loginResponse.extract().path("id");
    }

    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][] {
                {"Potat", "12345"},
                {"Potato", "123"},
        };
    }

    @Test
    public void wrongLoginOrPassTest() {
        courier.setLogin(login);
        courier.setPassword(password);

        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        int loginStatusCode = loginResponse.extract().statusCode();
        assertEquals("Status code is incorrect", SC_NOT_FOUND, loginStatusCode);

        String message = loginResponse.extract().path("message");
        assertEquals("Error message is not expected", CourierErrors.LOGIN_NOT_FOUND, message);
    }
}
