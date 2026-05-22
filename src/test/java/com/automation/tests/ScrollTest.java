package com.automation.tests;

import com.automation.base.BaseDriver;
import com.automation.utils.Constants;
import com.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ScrollTest extends BaseTest {

    @Test
    public void verifyScrollUpUsingArrowButton() {
        // Step 3 — Verify home page is visible
        Assert.assertEquals(homePage.getTitle(), Constants.HOME_PAGE_TITLE,
            "Home page should be visible");

        // Step 4 — Scroll down page to bottom
        homePage.scrollToFooter();

        // Step 5 — Verify 'SUBSCRIPTION' is visible
        Assert.assertEquals(homePage.getSubscriptionHeadingText(), "SUBSCRIPTION",
            "Subscription heading should be visible at bottom");

        // Step 6 — Click on arrow at bottom right side to move upward
        WebDriverWait wait = new WebDriverWait(BaseDriver.getDriver(), 10);
        WebElement scrollUpArrow = wait.until(
            ExpectedConditions.elementToBeClickable(By.cssSelector("#scrollUp")));
        scrollUpArrow.click();

        // Wait for scroll animation to complete
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

        // Step 7 — Verify page is scrolled up and text is visible
        WebElement headerText = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Full-Fledged practice website for Automation Engineers')]")));
        Assert.assertTrue(headerText.isDisplayed(),
            "'Full-Fledged practice website for Automation Engineers' text should be visible");
    }

    @Test
    public void verifyScrollUpWithoutArrowButton() {
        // Step 3 — Verify home page is visible
        Assert.assertEquals(homePage.getTitle(), Constants.HOME_PAGE_TITLE,
            "Home page should be visible");

        // Step 4 — Scroll down page to bottom
        homePage.scrollToFooter();

        // Step 5 — Verify 'SUBSCRIPTION' is visible
        Assert.assertEquals(homePage.getSubscriptionHeadingText(), "SUBSCRIPTION",
            "Subscription heading should be visible at bottom");

        // Step 6 — Scroll up using JavaScript (without arrow button)
        org.openqa.selenium.JavascriptExecutor js =
            (org.openqa.selenium.JavascriptExecutor) BaseDriver.getDriver();
        js.executeScript("window.scrollTo(0, 0);");

        // Wait for scroll to complete
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

        // Step 7 — Verify page is scrolled up and text is visible
        WebDriverWait wait = new WebDriverWait(BaseDriver.getDriver(), 10);
        WebElement headerText = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Full-Fledged practice website for Automation Engineers')]")));
        Assert.assertTrue(headerText.isDisplayed(),
            "'Full-Fledged practice website for Automation Engineers' text should be visible");
    }
}
