@signup
Feature: Signup Functionality
  As a new user of Automation Exercise website
  I want to register and verify error for existing email

  @negative
  Scenario: Register User with existing email
    Given I am on the home page
    And I click on Signup Login button
    When I enter signup name "Existing User" and email "akash@123.com"
    And I click the signup button
    Then I should see signup error message "Email Address already exist!"
