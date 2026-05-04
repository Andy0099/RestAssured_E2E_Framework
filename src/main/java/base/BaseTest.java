package base;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import utils.ConfigReader;

public class BaseTest {
	// We make these public so our test classes can see them
    public static RequestSpecification requestSpec;
    public static ResponseSpecification responseSpec;
    
	@BeforeClass
    public void setup() //Why: Avoid repeating base URL in every test // Centralized configuration 
    //Real project usage: Base URL, Auth tokens, Common headers
	{
		// Instead of a hardcoded string, we call our reader
		String url = utils.ConfigReader.getProperty("base_url");
	    System.out.println("The URL being used is: " + url); // Add this line
	 // 1. Create a template for every Request
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(url)
                .setContentType(ContentType.JSON)
                .addHeader("Accept", "application/json")
                .build();
     // 2. Create a template for every Response
        responseSpec = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .build();
	    
    }

}
