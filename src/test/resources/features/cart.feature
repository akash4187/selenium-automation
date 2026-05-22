@cart
Feature: Cart Functionality
  As a user of Automation Exercise website
  I want to manage products in my cart

  Scenario: Verify product quantity in cart
    Given I am on the home page
    When I click on View Product of first product on home page
    Then I should be on product detail page
    When I set quantity to 4
    And I click Add to Cart button
    And I click View Cart from modal
    Then the product quantity in cart should be "4"

  Scenario: Verify subscription in cart page
    Given I am on the home page
    When I click on Cart button
    And I scroll down on cart page
    Then I should see SUBSCRIPTION heading on cart page
    When I enter subscription email on cart page and click arrow
    Then I should see subscription success message "You have been successfully subscribed!"

  Scenario: Add to cart from Recommended items
    Given I am on the home page
    When I scroll down to footer
    Then I should see RECOMMENDED ITEMS section
    When I click Add to Cart on recommended product
    And I click View Cart from recommended modal
    Then the cart should have at least one product
