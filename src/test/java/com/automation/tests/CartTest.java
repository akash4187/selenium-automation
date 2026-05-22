package com.automation.tests;

import com.automation.pages.CartPage;
import com.automation.pages.ProductDetailPage;
import com.automation.pages.ProductsPage;
import com.automation.utils.Constants;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartTest extends BaseTest {

    @Test
    public void verifyCartPageTitle() {
        ProductsPage productsPage = homePage.clickProducts();
        CartPage cartPage = new CartPage();
        cartPage.navigateToCart();
        Assert.assertEquals(cartPage.getTitle(), Constants.CART_PAGE_TITLE);
    }

    @Test(dependsOnMethods = "verifyCartPageTitle")
    public void verifyAddProductToCart() {
        ProductsPage productsPage = homePage.clickProducts();
        productsPage.addFirstProductToCart();
        CartPage cartPage = productsPage.viewCart();
        Assert.assertTrue(cartPage.getCartItemCount() > 0, "Cart should have at least one item");
    }

    @Test(dependsOnMethods = "verifyAddProductToCart")
    public void verifyRemoveProductFromCart() {
        ProductsPage productsPage = homePage.clickProducts();
        productsPage.addFirstProductToCart();
        CartPage cartPage = productsPage.viewCart();
        int countBefore = cartPage.getCartItemCount();
        cartPage.removeFirstItem();
        int countAfter = cartPage.getCartItemCount();
        Assert.assertTrue(countAfter < countBefore, "Cart item count should decrease after removal");
    }

    @Test
    public void verifyProductQuantityInCart() {
        // Step 3 — Verify home page is visible
        Assert.assertEquals(homePage.getTitle(), Constants.HOME_PAGE_TITLE,
            "Home page should be visible");

        // Step 4 — Click 'View Product' for any product on home page
        ProductDetailPage productDetailPage = homePage.viewFirstProduct();

        // Step 5 — Verify product detail is opened
        Assert.assertTrue(productDetailPage.isProductDetailVisible(),
            "Product detail page should be visible");

        // Step 6 — Increase quantity to 4
        productDetailPage.setQuantity(4);

        // Step 7 — Click 'Add to cart' button
        productDetailPage.clickAddToCart();

        // Step 8 — Click 'View Cart' button
        CartPage cartPage = productDetailPage.clickViewCart();

        // Step 9 — Verify product is displayed in cart with exact quantity
        Assert.assertTrue(cartPage.getCartItemCount() > 0,
            "Cart should have at least one product");
        Assert.assertEquals(cartPage.getProductQuantity(0), "4",
            "Product quantity in cart should be 4");
    }

    @Test
    public void verifySubscriptionInCartPage() {
        // Step 3 — Verify home page is visible
        Assert.assertEquals(homePage.getTitle(), Constants.HOME_PAGE_TITLE,
            "Home page should be visible");

        // Step 4 — Click Cart button
        CartPage cartPage = homePage.clickCart();

        // Step 5 — Scroll down to footer
        cartPage.scrollToFooter();

        // Step 6 — Verify text 'SUBSCRIPTION'
        Assert.assertEquals(cartPage.getSubscriptionHeadingText(), "SUBSCRIPTION",
            "Subscription heading should be visible in footer");

        // Step 7 — Enter email and click arrow button
        cartPage.enterSubscriptionEmail("testsubscribe" + System.currentTimeMillis() + "@test.com");

        // Step 8 — Verify success message
        String successMsg = cartPage.getSubscriptionSuccessMessage();
        Assert.assertEquals(successMsg, Constants.SUBSCRIPTION_SUCCESS_MSG,
            "Subscription success message mismatch");
    }
}
