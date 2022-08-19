import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.*;

public class OrderListTest {
    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    public void getAllOrderListTest() {
        ValidatableResponse response = orderClient.orders();

        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrect", SC_OK, statusCode);

        List<String> orders = new ArrayList<>(response.extract().path("orders"));
        assertNotNull("Order list is empty", orders);
        assertTrue("Order list is empty", !orders.isEmpty());
    }
}
