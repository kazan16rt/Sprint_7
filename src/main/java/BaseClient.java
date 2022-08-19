import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class BaseClient {
    private static final String URL = "http://qa-scooter.praktikum-services.ru";

    public RequestSpecification getSpec() {
        return new RequestSpecBuilder()
                .addHeader("Content-Type", "application/json")
                .setBaseUri(URL)
                .build();
    }
}
