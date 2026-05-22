@category
Feature: View Category Products
  As a user of Automation Exercise website
  I want to browse products by category

  Scenario: Verify View Category Products
    Given I am on the home page
    Then I should see categories on left side bar
    When I click on Women category and then Tops sub-category
    Then I should see category page with title containing "WOMEN - TOPS PRODUCTS"
    When I click on Men category and then Tshirts sub-category
    Then I should see category page with title containing "MEN - JEANS PRODUCTS"
