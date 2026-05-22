package com.automation.tests;

import com.automation.pages.ProductDetailPage;
import com.automation.pages.ProductsPage;
import com.automation.utils.Constants;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductReviewTest extends BaseTest {

    @Test
    public void verifyAddReviewOnProduct() {
        // Step 3 — Click on 'Products' button
        ProductsPage productsPage = homePage.clickProducts();

        // Step 4 — Verify user is navigated to ALL PRODUCTS page
        Assert.assertEquals(productsPage.getTitle(), Constants.PRODUCTS_PAGE_TITLE,
            "Should be on All Products page");

        // Step 5 — Click on 'View Product' button
        ProductDetailPage productDetailPage = productsPage.viewFirstProduct();

        // Step 6 — Verify 'Write Your Review' is visible
        Assert.assertTrue(productDetailPage.isWriteYourReviewVisible(),
            "'Write Your Review' should be visible");

        // Step 7 — Enter name, email and review
        // Step 8 — Click 'Submit' button
        productDetailPage.submitReview(
            "Test Reviewer",
            "reviewer@test.com",
            "This is an excellent product! Great quality and fast delivery."
        );

        // Step 9 — Verify success message
        String successMsg = productDetailPage.getReviewSuccessMessage();
        Assert.assertEquals(successMsg, "Thank you for your review.",
            "Review success message mismatch");
    }
}
