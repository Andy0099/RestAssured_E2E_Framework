package pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) // This is the Mastery Fix!
public class User {
    public String name;
    public String job;
    public String id; // Optional: Add this if you want to capture the ID
}
