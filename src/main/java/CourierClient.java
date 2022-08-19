import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends BaseClient{

    private static final String COURIER_CREATE_PATH = "/api/v1/courier/";
    private static final String COURIER_LOGIN_PATH = "/api/v1/courier/login/";
    private static final String COURIER_DELETE_PATH = "/api/v1/courier/";

    @Step("Send POST request to")
    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getSpec())
                .body(courier)
                .when()
                .post(COURIER_CREATE_PATH)
                .then();
//                .log()
//                .all();
    }
    @Step("Send POST request to {COURIER_LOGIN_PATH}")
    public ValidatableResponse login(CourierCredentials courierCredentials) {
        return given()
                .spec(getSpec())
//                .log()
//                .all()
                .body(courierCredentials)
                .when()
                .post(COURIER_LOGIN_PATH)
                .then();
    }
    @Step("Send DELETE request to {COURIER_DELETE_PATH}")
    public ValidatableResponse delete(int id) {
        return given()
                .spec(getSpec())
                .pathParam("id", id)
                .when()
                .delete(COURIER_DELETE_PATH+"{id}")
                .then();
    }
}
