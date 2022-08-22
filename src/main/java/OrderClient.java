import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends BaseClient{

    private static final String CREATE_ORDER = "/api/v1/orders";

    @Step("Send POST request to {CREATE_ORDER}")
    public ValidatableResponse create(Order order) {
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post(CREATE_ORDER)
                .then();
    }
    @Step("Send GET request to {CREATE_ORDER}")
    public ValidatableResponse orders() {
        return given()
                .spec(getSpec())
                .when()
                .get(CREATE_ORDER)
                .then();
    }
}
