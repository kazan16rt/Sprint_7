import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    private Order order;
    private OrderClient orderClient;

    private final String[] color;

    public CreateOrderTest(String[] color) {
        this.color = color;
    }

    @Before
    public void setUp() {
        order = OrderData.getDefault();
        orderClient = new OrderClient();
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][] {
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{"GREY", "BLACK"}},
                {new String[]{}},
        };
    }

    @Test
    public void createNewOrderTest() {
        order.setCollor(color);
        ValidatableResponse response = orderClient.create(order);

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrect", SC_CREATED, statusCode);

        int track = response.extract().path("track");
        assertNotNull("Track number is not received", track);
    }

}
