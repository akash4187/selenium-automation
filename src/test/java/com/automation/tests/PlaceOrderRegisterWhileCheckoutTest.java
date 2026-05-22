package com.automation.tests;

import com.automation.base.BaseDriver;
import com.automation.pages.CartPage;
import com.automation.pages.CheckoutPage;
import com.automation.pages.LoginPage;
import com.automation.pages.PaymentPage;
import com.automation.pages.ProductsPage;
import com.automation.pages.SignupPage;
import com.automation.utils.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PlaceOrderRegisterWhileCheckoutTest extends BaseTest {

    @Test
    public void verifyPlaceOrderRegisterWhileCheckout() {
        String testEmail = "testorder" + System.currentTimeMillis() + "@test.com";
        String testName = "TestOrder User";

        // Step 3 — Verify home page is visible
        Assert.assertEquals(homePage.getTitle(), Constants.HOME_PAGE_TITLE,
            "Home page should be visible");

        // Step 4 — Add products to cart
        ProductsPage productsPage = homePage.clickProducts();
        productsPage.addFirstProductToCart();

        // Step 5 — Click 'Cart' button
        CartPage cartPage = productsPage.viewCart();

        // Step 6 — Verify cart page is displayed
        Assert.assertTrue(cartPage.getCartItemCount() > 0,
            "Cart should have at least one product");

        // Step 7 — Click Proceed To Checkout
        cartPage.proceedToCheckout();

        // Step 8 — Click 'Register / Login' button on modal
        CheckoutPage tempCheckout = new CheckoutPage();
        LoginPage loginPage = tempCheckout.loginFromCheckout();

        // Step 9 — Fill all details in Signup and create account
        loginPage.enterSignupDetails(testName, testEmail);
        SignupPage signupPage = new SignupPage();
        signupPage.fillAccountDetails(
            "Test@123",
            "15", "June", "1990",
            "TestOrder", "User", "Test Corp",
            "456 Test Avenue", "Suite 10",
            "United States",
            "New York", "New York", "10001",
            "9876543210"
        );

        // Step 10 — Verify 'ACCOUNT CREATED!' and click 'Continue'
        WebDriverWait wait = new WebDriverWait(BaseDriver.getDriver(), Constants.EXPLICIT_WAIT);
        WebElement accountCreatedMsg = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2.title b")));
        Assert.assertEquals(accountCreatedMsg.getText(), Constants.ACCOUNT_CREATED_MSG,
            "Account created message mismatch");

        // Click Continue button
        WebElement continueBtn = wait.until(
            ExpectedConditions.elementToBeClickable(By.cssSelector("a[data-qa='continue-button']")));
        continueBtn.click();

        // Step 11 — Verify 'Logged in as username' at top
        Assert.assertTrue(homePage.isLoggedInAs(testName),
            "Should be logged in as: " + testName);

        // Step 12 — Click 'Cart' button
        cartPage = homePage.clickCart();

        // Step 13 — Click 'Proceed To Checkout' button
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();

        // Step 14 — Verify Address Details and Review Your Order
        Assert.assertTrue(checkoutPage.isAddressDetailVisible(),
            "Address details should be visible on checkout page");
        Assert.assertTrue(checkoutPage.isOrderReviewVisible(),
            "Order review should show items");

        // Step 15 — Enter description in comment and click 'Place Order'
        PaymentPage paymentPage = checkoutPage.placeOrderWithComment(
            "Order placed via Register while Checkout test");

        // Step 16 — Enter payment details
        paymentPage.enterPaymentDetails(
            Constants.CARD_NAME,
            Constants.CARD_NUMBER,
            Constants.CARD_CVC,
            Constants.CARD_EXPIRY_MONTH,
            Constants.CARD_EXPIRY_YEAR
        );

        // Step 17 — Click 'Pay and Confirm Order'
        String confirmationMsg = paymentPage.confirmOrder();

        // Step 18 — Verify success message
        Assert.assertFalse(confirmationMsg.isEmpty(),
            "Order confirmation message should be displayed");

        // Step 19 — Click 'Delete Account' button
        homePage.clickDeleteAccount();

        // Step 20 — Verify 'ACCOUNT DELETED!' and click 'Continue'
        WebElement accountDeletedMsg = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2.title b")));
        Assert.assertEquals(accountDeletedMsg.getText(), Constants.ACCOUNT_DELETED_MSG,
            "Account deleted message mismatch");

        WebElement continueAfterDelete = wait.until(
            ExpectedConditions.elementToBeClickable(By.cssSelector("a[data-qa='continue-button']")));
        continueAfterDelete.click();
    }
}
