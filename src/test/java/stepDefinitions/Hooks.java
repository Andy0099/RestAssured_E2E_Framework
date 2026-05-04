package stepDefinitions;

import base.BaseTest;
import io.cucumber.java.Before;

public class Hooks extends BaseTest {

    @Before
    public void setUp() {
        // We call the setup method from BaseTest manually 
        // to ensure requestSpec and responseSpec are NOT null
        if (requestSpec == null) {
            super.setup();
            System.out.println("Cucumber Hook: BaseTest setup initialized.");
        }
    }
}
