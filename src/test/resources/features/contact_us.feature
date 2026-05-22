@contactus
Feature: Contact Us Form
  As a user of Automation Exercise website
  I want to submit the contact us form

  Scenario: Verify Contact Us form submission
    Given I am on the home page
    When I click on Contact Us button
    Then I should see Get In Touch heading
    When I fill contact form with name "Test User" email "test@test.com" subject "Test Subject" and message "Test message"
    And I click the contact submit button
    And I accept the alert
    Then I should see contact success message "Success! Your details have been submitted successfully."

  Scenario: Verify Contact Us form with file upload
    Given I am on the home page
    When I click on Contact Us button
    Then I should see Get In Touch heading
    When I fill contact form with name "Test User" email "test@test.com" subject "File Upload" and message "Testing with file"
    And I upload a file
    And I click the contact submit button
    And I accept the alert
    Then I should see contact success message "Success! Your details have been submitted successfully."
