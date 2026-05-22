package com.automation.pages;

import com.automation.base.BaseDriver;
import com.automation.utils.WaitUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    private static final Logger log = LogManager.getLogger(LoginPage.class);
    private WebDriver driver;

    @FindBy(css = "input[data-qa='login-email']")
    private WebElement loginEmail;

    @FindBy(css = "input[data-qa='login-password']")
    private WebElement loginPassword;

    @FindBy(css = "button[data-qa='login-button']")
    private WebElement loginButton;

    @FindBy(css = "input[data-qa='signup-name']")
    private WebElement signupName;

    @FindBy(css = "input[data-qa='signup-email']")
    private WebElement signupEmail;

    @FindBy(css = "button[data-qa='signup-button']")
    private WebElement signupButton;

    @FindBy(css = "p.text-danger")
    private WebElement errorMessage;

    public LoginPage() {
        this.driver = BaseDriver.getDriver();
        PageFactory.initElements(driver, this);
        dismissAds();
    }

    private void dismissAds() {
        try {
            Thread.sleep(1000);

            // Check if we're on an ad interstitial page and navigate back
            String currentUrl = driver.getCurrentUrl();
            if (!currentUrl.contains("automationexercise.com")) {
                driver.navigate().back();
                Thread.sleep(2000);
            }

            org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
            // Remove Google ads, consent dialogs, and any overlays
            js.executeScript(
                "document.querySelectorAll(" +
                "'ins.adsbygoogle, iframe[id*=\"aswift\"], iframe[src*=\"googleads\"], " +
                "div[id*=\"google_ads\"], #google_vignette, #credential_picker_container, " +
                "div[id=\"consent-bump\"], .fc-consent-root, #onetrust-consent-sdk, " +
                "iframe[src*=\"consent\"], div[class*=\"consent\"]'" +
                ").forEach(function(el) { el.remove(); });" +
                // Also scroll to the login form to ensure it's in view
                "var loginForm = document.querySelector('input[data-qa=\"login-email\"]');" +
                "if(loginForm) loginForm.scrollIntoView({block: 'center'});"
            );
        } catch (Exception ignored) {}
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public void login(String email, String password) {
        log.info("Logging in with email: {}", email);

        // Dismiss any ads/overlays that may have loaded after page init
        dismissAds();

        // Wait for login form to be present and interactable
        WebElement emailField = new org.openqa.selenium.support.ui.WebDriverWait(driver, 15)
            .until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(By.cssSelector("input[data-qa='login-email']")));

        // Clear and type email
        emailField.clear();
        emailField.sendKeys(email);

        WebElement passwordField = new org.openqa.selenium.support.ui.WebDriverWait(driver, 10)
            .until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(By.cssSelector("input[data-qa='login-password']")));
        passwordField.clear();
        passwordField.sendKeys(password);

        // Click login button using JavaScript — reliable in both headless and UI mode
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        js.executeScript("document.querySelector('button[data-qa=\"login-button\"]').click();");

        // Wait for page to respond (redirect or error message)
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
    }

    public boolean waitForLoginSuccess() {
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(driver, 15)
                .until(org.openqa.selenium.support.ui.ExpectedConditions
                    .not(org.openqa.selenium.support.ui.ExpectedConditions
                        .urlContains("/login")));
            log.info("Login successful, redirected to: {}", driver.getCurrentUrl());
            return true;
        } catch (Exception e) {
            log.warn("Login did not redirect — likely failed");
            return false;
        }
    }

    public void enterSignupDetails(String name, String email) {
        log.info("Entering signup details for: {}", name);
        WaitUtils.waitForVisible(signupName).clear();
        WaitUtils.waitForVisible(signupName).sendKeys(name);
        WaitUtils.waitForVisible(signupEmail).clear();
        WaitUtils.waitForVisible(signupEmail).sendKeys(email);
        // Use JS click for reliable submission in headless mode
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        js.executeScript("document.querySelector('button[data-qa=\"signup-button\"]').click();");
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
    }

    public String getErrorMessage() {
        WebElement error = new org.openqa.selenium.support.ui.WebDriverWait(driver, 20)
            .until(org.openqa.selenium.support.ui.ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("p.text-danger")));
        return error.getText();
    }
}
