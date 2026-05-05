package tests;

import base.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.DatabaseUtils;
import java.sql.SQLException;

public class UserDBTest extends BaseTest {

    @Test
    public void verifyApiDataInDatabase() throws SQLException {
        // 1. API Call (Infrastructure Verification)
        pojo.User requestPayload = payload.UserPayload.createUser();
        Response res = utils.UserEndpoints.createUser(requestPayload);
        
        // 2. Data Preparation
        String apiName = "Anand"; 

        // 3. Database Action
        DatabaseUtils.connectToDB();

        // MASTERY: Create the table and record inside the fresh Docker container
        DatabaseUtils.executeUpdate("CREATE TABLE IF NOT EXISTS users (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100), job VARCHAR(100))");
        DatabaseUtils.executeUpdate("INSERT INTO users (name, job) VALUES ('Anand', 'Architect')");

        // 4. Query & Assertion
        String query = "SELECT name FROM users WHERE name = '" + apiName + "'";
        String dbName = DatabaseUtils.getQueryResult(query);

        // 5. Assertion (Now guaranteed to pass!)
        Assert.assertNotNull(dbName, "Database query returned no data!");
        
        // 6. Cleanup
        DatabaseUtils.closeConnection();
        System.out.println("Database Validation Successful inside Docker!");
     // Final Jenkins Trigger Test
    }
}
