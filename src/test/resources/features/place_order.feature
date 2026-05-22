@order
Feature: Place Order
  As a user of Automation Exercise website
  I want to place orders through different checkout flows

  Scenario: Place Order - Register while Checkout
    Given I am on the home page
    When I click on Products button
    And I add first product to cart
    And I click View Cart from modal
    Then the cart should have at least one product
    When I click Proceed To Checkout
    And I click Register Login from checkout modal
    And I signup with name "OrderWhile User" and a unique email
    And I fill account details and create account
    Then I should see ACCOUNT CREATED message
    When I click Continue button
    Then I should be logged in as "OrderWhile User"
    When I click on Cart button
    And I click Proceed To Checkout
    Then I should see address details and order review
    When I enter comment "Test order" and click Place Order
    And I enter payment details and confirm order
    Then I should see order confirmation message
    When I click Delete Account
    Then I should see ACCOUNT DELETED message

  Scenario: Place Order - Register before Checkout
    Given I am on the home page
    And I click on Signup Login button
    When I signup with name "OrderBefore User" and a unique email
    And I fill account details and create account
    Then I should see ACCOUNT CREATED message
    When I click Continue button
    Then I should be logged in as "OrderBefore User"
    When I click on Products button
    And I add first product to cart
    And I click View Cart from modal
    Then the cart should have at least one product
    When I click Proceed To Checkout
    Then I should see address details and order review
    When I enter comment "Test order" and click Place Order
    And I enter payment details and confirm order
    Then I should see order confirmation message
    When I click Delete Account
    Then I should see ACCOUNT DELETED message
