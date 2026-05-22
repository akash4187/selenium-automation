package com.automation.tests;

import com.automation.pages.ProductDetailPage;
import com.automation.pages.ProductsPage;
import com.automation.utils.Constants;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class ProductsTest extends BaseTest {

    @Test
    public void verifyProductsPageTitle() {
        ProductsPage productsPage = homePage.clickProducts();
        Assert.assertEquals(productsPage.getTitle(), "Automation Exercise - All Products");
    }

    @Test(dependsOnMethods = "verifyProductsPageTitle")
    public void verifyProductsAreDisplayed() {
        ProductsPage productsPage = homePage.clickProducts();
        Assert.assertTrue(productsPage.getProductCount() > 0, "No products found on the page");
    }

    @Test(dependsOnMethods = "verifyProductsAreDisplayed")
    public void verifyAllProductsAndProductDetailPage() {
        // Step 3 — Verify home page is visible
        Assert.assertEquals(homePage.getTitle(), Constants.HOME_PAGE_TITLE,
            "Home page should be visible");

        // Step 4 — Click on Products button
        ProductsPage productsPage = homePage.clickProducts();

        // Step 5 — Verify navigated to ALL PRODUCTS page
        Assert.assertEquals(productsPage.getTitle(), Constants.PRODUCTS_PAGE_TITLE,
            "Should be on All Products page");

        // Step 6 — Verify products list is visible
        Assert.assertTrue(productsPage.getProductCount() > 0,
            "Products list should be visible");

        // Step 7 — Click on 'View Product' of first product
        ProductDetailPage productDetailPage = productsPage.viewFirstProduct();

        // Step 8 — Verify user is landed on product detail page
        Assert.assertTrue(productDetailPage.isProductDetailVisible(),
            "Product detail page should be visible");

        // Step 9 — Verify product details are visible: name, category, price, availability, condition, brand
        Assert.assertFalse(productDetailPage.getProductName().isEmpty(),
            "Product name should be visible");
        Assert.assertFalse(productDetailPage.getProductCategory().isEmpty(),
            "Product category should be visible");
        Assert.assertFalse(productDetailPage.getProductPrice().isEmpty(),
            "Product price should be visible");
        Assert.assertTrue(productDetailPage.getProductAvailability().contains("Availability"),
            "Product availability should be visible");
        Assert.assertTrue(productDetailPage.getProductCondition().contains("Condition"),
            "Product condition should be visible");
        Assert.assertTrue(productDetailPage.getProductBrand().contains("Brand"),
            "Product brand should be visible");
    }

    @Test(dependsOnMethods = "verifyProductsAreDisplayed")
    public void verifyProductSearch() {
        ProductsPage productsPage = homePage.clickProducts();
        productsPage.searchProduct("T-Shirt");
        Assert.assertTrue(productsPage.getProductCount() > 0, "No products found for search term");
    }

    @Test(dependsOnMethods = "verifyProductsAreDisplayed")
    public void verifySearchShowsRelevantProducts() {
        ProductsPage productsPage = homePage.clickProducts();
        productsPage.searchProduct("Top");
        List<String> names = productsPage.getProductNames();
        Assert.assertTrue(names.size() > 0, "No products found for 'Top'");
        boolean hasRelevant = names.stream()
            .anyMatch(name -> name.toLowerCase().contains("top"));
        Assert.assertTrue(hasRelevant,
            "At least one product should contain search term 'Top' in its name");
    }

    @Test(dependsOnMethods = "verifyProductsAreDisplayed")
    public void verifySearchWithNoResults() {
        ProductsPage productsPage = homePage.clickProducts();
        productsPage.searchProduct("xyznonexistent12345");
        Assert.assertEquals(productsPage.getProductCount(), 0,
            "Products should not be found for invalid search term");
    }

    @Test(dependsOnMethods = "verifyProductsAreDisplayed")
    public void verifyFilterByCategory() {
        ProductsPage productsPage = homePage.clickProducts();
        productsPage.selectCategory("Women", "Dress");
        Assert.assertTrue(productsPage.getProductCount() > 0,
            "No products found for Women > Dress category");
    }

    @Test(dependsOnMethods = "verifyProductsAreDisplayed")
    public void verifyFilterByBrand() {
        ProductsPage productsPage = homePage.clickProducts();
        productsPage.selectBrand("Polo");
        Assert.assertTrue(productsPage.getProductCount() > 0,
            "No products found for brand Polo");
    }
}
