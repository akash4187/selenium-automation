@scroll
Feature: Scroll Up and Down Functionality
  As a user of Automation Exercise website
  I want to verify scroll functionality

  Scenario: Verify Scroll Up using Arrow button
    Given I am on the home page
    When I scroll down to footer
    Then I should see SUBSCRIPTION heading
    When I click the scroll up arrow button
    Then I should see the text "Full-Fledged practice website for Automation Engineers"

  Scenario: Verify Scroll Up without Arrow button
    Given I am on the home page
    When I scroll down to footer
    Then I should see SUBSCRIPTION heading
    When I scroll up to the top
    Then I should see the text "Full-Fledged practice website for Automation Engineers"
