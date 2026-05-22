package com.automation.utils;

import com.automation.base.BaseDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtils {

    private static WebDriverWait getWait() {
        return new WebDriverWait(BaseDriver.getDriver(), Constants.EXPLICIT_WAIT);
    }

    public static void waitForVisible(WebDriver driver, String cssSelector) {
        new WebDriverWait(driver, Constants.EXPLICIT_WAIT)
            .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssSelector)));
    }

    public static WebElement waitForVisible(WebElement element) {
        return getWait().until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForClickable(WebElement element) {
        return getWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void clickWhenReady(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) BaseDriver.getDriver();
        WebElement el = waitForClickable(element);
        js.executeScript("arguments[0].scrollIntoView(true);", el);
        js.executeScript("arguments[0].click();", el);
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
    }

    public static void typeWhenReady(WebElement element, String text) {
        JavascriptExecutor js = (JavascriptExecutor) BaseDriver.getDriver();
        WebElement el = waitForVisible(element);
        js.executeScript("arguments[0].scrollIntoView(true);", el);
        js.executeScript("arguments[0].click();", el);
        js.executeScript("arguments[0].focus();", el);
        js.executeScript("arguments[0].value='';", el);
        js.executeScript("arguments[0].value=arguments[1];", el, text);
        js.executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", el);
        js.executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", el);
    }
}
