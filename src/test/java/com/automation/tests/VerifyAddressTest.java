package com.automation.tests;

import com.automation.base.BaseDriver;
import com.automation.pages.CartPage;
import com.automation.pages.CheckoutPage;
import com.automation.pages.LoginPage;
import com.automation.pages.ProductsPage;
import com.automation.pages.SignupPage;
import com.automation.utils.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VerifyAddressTest extends BaseTest {

    @Test
    public void verifyAddressDetailsInCheckoutPage() {
        String testEmail = "testaddr" + System.currentTimeMillis() + "@test.com";
        String testName = "AddressTest User";
        String firstName = "AddressTest";
        String lastName = "User";
        String company = "Address Corp";
        String address1 = "100 Main Street";
        String address2 = "Apt 5A";
        String state = "Texas";
        String city = "Houston";
        String zipcode = "77001";
        String phone = "7135551234";

        // Step 3 — Verify home page is visible
        Assert.assertEquals(homePage.getTitle(), Constants.HOME_PAGE_TITLE,
            "Home page should be visible");

        // Step 4 — Click 'Signup / Login' button
        LoginPage loginPage = homePage.clickSignupLogin();

        // Step 5 — Fill all details in Signup and create account
        loginPage.enterSignupDetails(testName, testEmail);
        SignupPage signupPage = new SignupPage();
        signupPage.fillAccountDetails(
            "Test@123",
            "5", "August", "1988",
            firstName, lastName, company,
            address1, address2,
            "United States",
            state, city, zipcode,
            phone
        );

        // Step 6 — Verify 'ACCOUNT CREATED!' and click 'Continue'
        WebDriverWait wait = new WebDriverWait(BaseDriver.getDriver(), Constants.EXPLICIT_WAIT);
        WebElement accountCreatedMsg = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2.title b")));
        Assert.assertEquals(accountCreatedMsg.getText(), Constants.ACCOUNT_CREATED_MSG,
            "Account created message mismatch");

        WebElement continueBtn = wait.until(
            ExpectedConditions.elementToBeClickable(By.cssSelector("a[data-qa='continue-button']")));
        continueBtn.click();

        // Step 7 — Verify 'Logged in as username' at top
        Assert.assertTrue(homePage.isLoggedInAs(testName),
            "Should be logged in as: " + testName);

        // Step 8 — Add products to cart
        ProductsPage productsPage = homePage.clickProducts();
        productsPage.addFirstProductToCart();

        // Step 9 — Click 'Cart' button
        CartPage cartPage = productsPage.viewCart();

        // Step 10 — Verify that cart page is displayed
        Assert.assertTrue(cartPage.getCartItemCount() > 0,
            "Cart should have at least one product");

        // Step 11 — Click Proceed To Checkout
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();

        // Step 12 — Verify delivery address matches registration details
        String deliveryAddress = checkoutPage.getDeliveryAddress();
        Assert.assertTrue(deliveryAddress.contains(firstName),
            "Delivery address should contain first name");
        Assert.assertTrue(deliveryAddress.contains(lastName),
            "Delivery address should contain last name");
        Assert.assertTrue(deliveryAddress.contains(address1),
            "Delivery address should contain address line 1");
        Assert.assertTrue(deliveryAddress.contains(city),
            "Delivery address should contain city");
        Assert.assertTrue(deliveryAddress.contains(state),
            "Delivery address should contain state");
        Assert.assertTrue(deliveryAddress.contains(zipcode),
            "Delivery address should contain zipcode");
        Assert.assertTrue(deliveryAddress.contains(phone),
            "Delivery address should contain phone");

        // Step 13 — Verify billing address matches registration details
        String billingAddress = checkoutPage.getBillingAddress();
        Assert.assertTrue(billingAddress.contains(firstName),
            "Billing address should contain first name");
        Assert.assertTrue(billingAddress.contains(lastName),
            "Billing address should contain last name");
        Assert.assertTrue(billingAddress.contains(address1),
            "Billing address should contain address line 1");
        Assert.assertTrue(billingAddress.contains(city),
            "Billing address should contain city");
        Assert.assertTrue(billingAddress.contains(state),
            "Billing address should contain state");
        Assert.assertTrue(billingAddress.contains(zipcode),
            "Billing address should contain zipcode");
        Assert.assertTrue(billingAddress.contains(phone),
            "Billing address should contain phone");

        // Step 14 — Click 'Delete Account' button
        homePage.clickDeleteAccount();

        // Step 15 — Verify 'ACCOUNT DELETED!' and click 'Continue'
        WebElement accountDeletedMsg = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2.title b")));
        Assert.assertEquals(accountDeletedMsg.getText(), Constants.ACCOUNT_DELETED_MSG,
            "Account deleted message mismatch");

        WebElement continueAfterDelete = wait.until(
            ExpectedConditions.elementToBeClickable(By.cssSelector("a[data-qa='continue-button']")));
        continueAfterDelete.click();
    }
}
