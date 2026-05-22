package com.automation.tests;

import com.automation.base.BaseDriver;
import com.automation.pages.LoginPage;
import com.automation.pages.SignupPage;
import com.automation.utils.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SignupTest extends BaseTest {

    @Test
    public void verifySuccessfulSignup() {
        LoginPage loginPage = homePage.clickSignupLogin();
        Assert.assertEquals(loginPage.getTitle(), Constants.SIGNUP_PAGE_TITLE, "Login/Signup page title mismatch");

        loginPage.enterSignupDetails("Test User", "testuser" + System.currentTimeMillis() + "@test.com");
        SignupPage signupPage = new SignupPage();

        signupPage.fillAccountDetails(
            "Test@123",
            "10", "June", "1990",
            "Test", "User", "Test Company",
            "123 Test Street", "Apt 4B",
            "United States",
            "California", "San Francisco", "94105",
            "1234567890"
        );

        WebDriverWait wait = new WebDriverWait(BaseDriver.getDriver(), Constants.EXPLICIT_WAIT);
        WebElement accountCreatedMsg = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2.title b"))
        );
        Assert.assertEquals(accountCreatedMsg.getText(), Constants.ACCOUNT_CREATED_MSG, "Account creation message mismatch");
    }

    @Test
    public void verifySignupWithExistingEmail() {
        LoginPage loginPage = homePage.clickSignupLogin();
        Assert.assertEquals(loginPage.getTitle(), Constants.SIGNUP_PAGE_TITLE, "Login/Signup page title mismatch");

        // Try to register with an email that already exists
        loginPage.enterSignupDetails("Existing User", Constants.ORDER_TEST_EMAIL);

        // Verify error message is displayed
        String errorMsg = loginPage.getErrorMessage();
        Assert.assertEquals(errorMsg, Constants.SIGNUP_EXISTING_EMAIL_ERROR,
            "Expected existing email error message but got: " + errorMsg);
    }
}
