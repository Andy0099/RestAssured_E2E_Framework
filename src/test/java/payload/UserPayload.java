package payload;

import com.github.javafaker.Faker;
import pojo.User;

public class UserPayload {
    public static User createUser() {
        Faker faker = new Faker();
        User user = new User();
        
        user.name = faker.name().fullName(); // Generates a random name
        user.job = faker.job().title();      // Generates a random job
        
        return user;
    }
}
