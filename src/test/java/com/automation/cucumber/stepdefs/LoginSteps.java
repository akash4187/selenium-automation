package com.automation.cucumber.stepdefs;

import com.automation.base.BaseDriver;
import com.automation.pages.HomePage;
import com.automation.pages.LoginPage;
import com.automation.utils.Constants;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class LoginSteps {

    private LoginPage loginPage;

    @When("I enter valid email {string} and password {string}")
    public void iEnterValidEmailAndPassword(String email, String password) {
        loginPage = new LoginPage();
        loginPage.login(email, password);
    }

    @When("I enter invalid email {string} and password {string}")
    public void iEnterInvalidEmailAndPassword(String email, String password) {
        loginPage = new LoginPage();
        loginPage.login(email, password);
    }

    @And("I click the login button")
    public void iClickTheLoginButton() {
        // Login button click is already handled in loginPage.login()
    }

    @Then("I should be logged in successfully")
    public void iShouldBeLoggedInSuccessfully() {
        Assert.assertTrue(loginPage.waitForLoginSuccess(),
            "Login should succeed and redirect away from login page");
    }

    @Then("I should see logged in as username at top")
    public void iShouldSeeLoggedInAsUsernameAtTop() {
        WebDriverWait wait = new WebDriverWait(BaseDriver.getDriver(), 10);
        WebElement loggedInText = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li a b")));
        Assert.assertFalse(loggedInText.getText().trim().isEmpty(),
            "Logged in username should be visible");
    }

    @Then("I should see error message {string}")
    public void iShouldSeeErrorMessage(String expectedError) {
        try {
            String actualError = loginPage.getErrorMessage();
            Assert.assertEquals(actualError.trim(), expectedError.trim(),
                "Error message mismatch");
        } catch (org.openqa.selenium.TimeoutException e) {
            // Form likely didn't submit — retry by clicking login button via JS
            org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) BaseDriver.getDriver();
            js.executeScript("document.querySelector('button[data-qa=\"login-button\"]').click();");
            try { Thread.sleep(3000); } catch (InterruptedException ignored) {}
            String actualError = loginPage.getErrorMessage();
            Assert.assertEquals(actualError.trim(), expectedError.trim(),
                "Error message mismatch after retry");
        }
    }
}
