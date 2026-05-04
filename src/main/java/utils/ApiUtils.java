package utils;

import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

public class ApiUtils {

	public static Response postRequest(String endpoint, Object body) //Reusable methods://Why: Avoid duplicate code, Follow DRY principle 
//Real project:All API calls handled here Retry logic, logging, headers added here
	{
	    return given()
	            .header("Content-Type", "application/json")
	            .header("x-api-key", "reqres-free-v1")   // ✅ ADD THIS LINE
	            .body(body)
	            .when()
	            .post(endpoint);
	}

    public static Response getRequest(String endpoint) {
        return given()
                .when()
                .get(endpoint);
    }
}