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

import java.io.File;

public class DownloadInvoiceTest extends BaseTest {

    @Test
    public void verifyDownloadInvoiceAfterPurchaseOrder() {
        String testEmail = "testinvoice" + System.currentTimeMillis() + "@test.com";
        String testName = "Invoice User";

        // Step 3 — Verify home page is visible
        Assert.assertEquals(homePage.getTitle(), Constants.HOME_PAGE_TITLE,
            "Home page should be visible");

        // Step 4 — Add products to cart
        ProductsPage productsPage = homePage.clickProducts();
        productsPage.addFirstProductToCart();

        // Step 5 — Click 'Cart' button
        CartPage cartPage = productsPage.viewCart();

        // Step 6 — Verify that cart page is displayed
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
            "12", "January", "1995",
            "Invoice", "User", "Invoice Corp",
            "321 Invoice Lane", "Unit 7",
            "United States",
            "Illinois", "Chicago", "60601",
            "3125559876"
        );

        // Step 10 — Verify 'ACCOUNT CREATED!' and click 'Continue'
        WebDriverWait wait = new WebDriverWait(BaseDriver.getDriver(), Constants.EXPLICIT_WAIT);
        WebElement accountCreatedMsg = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2.title b")));
        Assert.assertEquals(accountCreatedMsg.getText(), Constants.ACCOUNT_CREATED_MSG,
            "Account created message mismatch");

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
            "Address details should be visible");
        Assert.assertTrue(checkoutPage.isOrderReviewVisible(),
            "Order review should show items");

        // Step 15 — Enter description in comment and click 'Place Order'
        PaymentPage paymentPage = checkoutPage.placeOrderWithComment(
            "Download invoice test order");

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

        // Step 19 — Click 'Download Invoice' and verify invoice is downloaded
        paymentPage.clickDownloadInvoice();

        // Verify invoice file was downloaded
        String downloadDir = System.getProperty("user.home") + "\\Downloads";
        File invoiceFile = findLatestInvoiceFile(downloadDir);
        Assert.assertNotNull(invoiceFile,
            "Invoice file should be downloaded successfully");

        // Step 20 — Click 'Continue' button
        paymentPage.clickContinue();

        // Step 21 — Click 'Delete Account' button
        homePage.clickDeleteAccount();

        // Step 22 — Verify 'ACCOUNT DELETED!' and click 'Continue'
        WebElement accountDeletedMsg = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2.title b")));
        Assert.assertEquals(accountDeletedMsg.getText(), Constants.ACCOUNT_DELETED_MSG,
            "Account deleted message mismatch");

        WebElement continueAfterDelete = wait.until(
            ExpectedConditions.elementToBeClickable(By.cssSelector("a[data-qa='continue-button']")));
        continueAfterDelete.click();
    }

    private File findLatestInvoiceFile(String downloadDir) {
        File dir = new File(downloadDir);
        File[] files = dir.listFiles((d, name) -> name.toLowerCase().contains("invoice"));
        if (files == null || files.length == 0) {
            return null;
        }
        // Return the most recently modified invoice file
        File latest = files[0];
        for (File file : files) {
            if (file.lastModified() > latest.lastModified()) {
                latest = file;
            }
        }
        // Only consider files modified in the last 30 seconds
        if (System.currentTimeMillis() - latest.lastModified() < 30000) {
            return latest;
        }
        return null;
    }
}
