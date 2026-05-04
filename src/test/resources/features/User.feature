Feature: User Management API Operations

  As a QA Architect
  I want to verify the Full CRUD flow of the User API
  So that I can ensure data integrity between API and Database

  @Smoke @Regression
  
  Scenario: Verify Create and Read User with Database Validation
    Given the user payload is generated with random data
    When I send a "POST" request to the user endpoint
    Then the API should return a status code 201
    And the response should follow the "schemas/userSchema.json" contract
    And I verify that the created user exists in the local Database
    
  @Negative
  
  Scenario: Verify 404 error for non-existent user
    When I request a user with an invalid ID "999999"
    Then the API should return a status code 404
