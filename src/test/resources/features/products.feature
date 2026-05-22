@products
Feature: Products Page
  As a user of Automation Exercise website
  I want to view all products and product details

  Scenario: Verify All Products and product detail page
    Given I am on the home page
    When I click on Products button
    Then I should be on All Products page
    And the products list should be visible
    When I click on View Product of first product
    Then I should be on product detail page
    And I should see product name, category, price, availability, condition, brand

  Scenario: Verify product search
    Given I am on the home page
    When I click on Products button
    And I search for product "T-Shirt"
    Then I should see search results with products

  Scenario: Add review on product
    Given I am on the home page
    When I click on Products button
    Then I should be on All Products page
    When I click on View Product of first product
    Then I should see Write Your Review section
    When I enter review name "Reviewer" email "review@test.com" and review "Great product!"
    And I click the review submit button
    Then I should see review success message "Thank you for your review."
