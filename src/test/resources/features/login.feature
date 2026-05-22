@login
Feature: Login Functionality
  As a user of Automation Exercise website
  I want to login with valid and invalid credentials
  So that I can access my account

  Background:
    Given I am on the home page
    And I click on Signup Login button

  @positive
  Scenario: Login with valid credentials
    When I enter valid email "akash@123.com" and password "kpPQur@jPgGXY62"
    And I click the login button
    Then I should be logged in successfully
    And I should see logged in as username at top

  @negative
  Scenario: Login with invalid credentials
    When I enter invalid email "invalid@test.com" and password "wrongpass"
    And I click the login button
    Then I should see error message "Your email or password is incorrect!"

  @negative
  Scenario Outline: Login with multiple invalid data
    When I enter invalid email "<email>" and password "<password>"
    And I click the login button
    Then I should see error message "<errorMessage>"

    Examples:
      | email              | password   | errorMessage                            |
      | wrong@email.com    | Test@123   | Your email or password is incorrect!    |
      | akash@123.com      | wrongpass  | Your email or password is incorrect!    |
