package com.automation.tests;

import com.automation.pages.CartPage;
import com.automation.pages.CheckoutPage;
import com.automation.pages.LoginPage;
import com.automation.pages.PaymentPage;
import com.automation.pages.ProductsPage;
import com.automation.utils.Constants;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class OrderTest extends BaseTest {

    private CartPage cartPage;
    private CheckoutPage checkoutPage;

    // Override AfterMethod — do NOT delete cookies or navigate away
    // between dependent tests as it clears cart session
    @Override
    @AfterMethod
    public void afterMethod(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            com.automation.utils.ExtentReportManager.getTest()
                .fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            com.automation.utils.ExtentReportManager.getTest()
                .log(com.aventstack.extentreports.Status.PASS, "Test passed");
        }
        // Intentionally skip navigateTo + deleteAllCookies + refresh
        // to preserve cart session across dependent tests
    }

    @Test
    public void verifyAddProductToCart() {
        // Step 1 — Navigate to Products page and add a product to cart
        ProductsPage productsPage = homePage.clickProducts();
        productsPage.addFirstProductToCart();

        // Step 2 — View Cart and verify product was added
        cartPage = productsPage.viewCart();
        Assert.assertTrue(cartPage.getCartItemCount() > 0,
            "Cart should have at least one product");
    }

    @Test(dependsOnMethods = "verifyAddProductToCart")
    public void verifyProceedToCheckout() {
        // Step 1 — Click Proceed to Checkout as guest → checkout modal appears
        cartPage.proceedToCheckout();

        // Step 2 — Click Register/Login link on the modal → redirected to login page
        CheckoutPage tempCheckout = new CheckoutPage();
        LoginPage loginPage = tempCheckout.loginFromCheckout();

        // Step 3 — Login with valid credentials → wait for redirect to home page
        loginPage.login(Constants.ORDER_TEST_EMAIL, Constants.ORDER_TEST_PASSWORD);
        Assert.assertTrue(loginPage.waitForLoginSuccess(),
            "Login should succeed and redirect away from login page");

        // Step 4 — Navigate back to cart (items are preserved in session)
        cartPage = new CartPage();
        cartPage.navigateToCart();
        Assert.assertTrue(cartPage.getCartItemCount() > 0,
            "Cart should still have products after login");

        // Step 5 — Proceed to Checkout again (logged in, no modal this time)
        checkoutPage = cartPage.proceedToCheckout();

        // Step 6 — Verify we are on checkout page with order items
        Assert.assertEquals(checkoutPage.getTitle(), Constants.CHECKOUT_PAGE_TITLE,
            "Checkout page title mismatch");
        Assert.assertTrue(checkoutPage.getOrderItemCount() > 0,
            "Order summary should show at least one item");
    }

    @Test(dependsOnMethods = "verifyProceedToCheckout")
    public void verifyPlaceOrder() {
        PaymentPage paymentPage = checkoutPage.placeOrder();

        Assert.assertEquals(paymentPage.getTitle(), Constants.PAYMENT_PAGE_TITLE,
            "Payment page title mismatch");

        paymentPage.enterPaymentDetails(
            Constants.CARD_NAME,
            Constants.CARD_NUMBER,
            Constants.CARD_CVC,
            Constants.CARD_EXPIRY_MONTH,
            Constants.CARD_EXPIRY_YEAR
        );

        String orderConfirmation = paymentPage.confirmOrder();
        Assert.assertEquals(orderConfirmation, Constants.ORDER_PLACED_MSG,
            "Order confirmation message mismatch");
    }
}
