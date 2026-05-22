@subscription
Feature: Subscription
  As a user of Automation Exercise website
  I want to subscribe to the newsletter

  Scenario: Verify subscription in home page
    Given I am on the home page
    When I scroll down to footer
    Then I should see SUBSCRIPTION heading
    When I enter subscription email and click arrow
    Then I should see subscription success message "You have been successfully subscribed!"
