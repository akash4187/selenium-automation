package com.automation.tests;

import com.automation.pages.CartPage;
import com.automation.utils.Constants;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RecommendedItemsTest extends BaseTest {

    @Test
    public void verifyAddToCartFromRecommendedItems() {
        // Step 3 — Scroll to bottom of page
        homePage.scrollToFooter();

        // Step 4 — Verify 'RECOMMENDED ITEMS' are visible
        Assert.assertTrue(homePage.isRecommendedItemsVisible(),
            "'RECOMMENDED ITEMS' section should be visible");

        // Step 5 — Click on 'Add To Cart' on Recommended product
        homePage.addRecommendedProductToCart();

        // Step 6 — Click on 'View Cart' button
        CartPage cartPage = homePage.clickViewCartFromModal();

        // Step 7 — Verify that product is displayed in cart page
        Assert.assertTrue(cartPage.getCartItemCount() > 0,
            "Product from recommended items should be displayed in cart");
    }
}
