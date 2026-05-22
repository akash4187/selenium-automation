@address
Feature: Verify Address Details in Checkout
  As a user of Automation Exercise website
  I want to verify my address details are correct at checkout

  Scenario: Verify address details in checkout page
    Given I am on the home page
    And I click on Signup Login button
    When I signup with name "Address User" and a unique email
    And I fill account details with address "100 Main St" city "Houston" state "Texas" zipcode "77001"
    Then I should see ACCOUNT CREATED message
    When I click Continue button
    Then I should be logged in as "Address User"
    When I click on Products button
    And I add first product to cart
    And I click View Cart from modal
    Then the cart should have at least one product
    When I click Proceed To Checkout
    Then the delivery address should contain "100 Main St"
    And the billing address should contain "100 Main St"
    When I click Delete Account
    Then I should see ACCOUNT DELETED message
