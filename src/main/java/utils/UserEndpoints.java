package utils;

import static io.restassured.RestAssured.given;
import base.BaseTest;
import base.Routes;
import io.restassured.response.Response;
import pojo.User;

public class UserEndpoints extends BaseTest {

    // This method handles CREATING a user
    public static Response createUser(User payload) {
        return given()
                .spec(requestSpec)
                .body(payload)
            .when()
                .post(Routes.post_endpoint);
    }

    // This method handles GETTING a user by ID
    public static Response getUser(String id) {
        return given()
                .spec(requestSpec)
                .pathParam("id", id) // This fills in the {id} in our Route
            .when()
                .get(Routes.get_endpoint);
    }
}
