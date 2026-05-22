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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CartPage {

    private static final Logger log = LogManager.getLogger(CartPage.class);
    private WebDriver driver;

    @FindBy(css = "a[href='/view_cart']")
    private WebElement cartLink;

    @FindBy(css = "#cart_info_table tbody tr")
    private List<WebElement> cartItems;

    @FindBy(css = ".cart_quantity_delete")
    private List<WebElement> removeButtons;

    @FindBy(css = "#susbscribe_email")
    private WebElement subscriptionEmailInput;

    @FindBy(css = "#subscribe")
    private WebElement subscribeButton;

    @FindBy(css = ".footer-widget h2")
    private WebElement subscriptionHeading;

    @FindBy(css = "#success-subscribe .alert-success")
    private WebElement subscriptionSuccessMsg;

    public CartPage() {
        this.driver = BaseDriver.getDriver();
        PageFactory.initElements(driver, this);
    }

    public CartPage navigateToCart() {
        log.info("Navigating to cart");
        driver.get(BaseDriver.getBaseUrl() + "/view_cart");
        return this;
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public int getCartItemCount() {
        return cartItems.size();
    }

    public String getProductQuantity(int index) {
        log.info("Getting quantity of product at index: {}", index);
        org.openqa.selenium.WebElement quantityCell = cartItems.get(index)
            .findElement(org.openqa.selenium.By.cssSelector(".cart_quantity button, .cart_quantity"));
        return quantityCell.getText().trim();
    }

    public void removeFirstItem() {
        log.info("Removing first item from cart");
        WaitUtils.clickWhenReady(removeButtons.get(0));
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
    }

    public CheckoutPage proceedToCheckout() {
        log.info("Clicking Proceed to Checkout");
        // Try multiple known selectors for the checkout button
        String[] selectors = {
            ".btn.btn-default.check_out",
            "a.btn.check_out",
            ".check_out",
            "a[href='/checkout']",
            ".col-sm-6 a.btn"
        };
        WebElement btn = null;
        for (String selector : selectors) {
            try {
                btn = new WebDriverWait(driver, 5)
                    .until(ExpectedConditions.elementToBeClickable(
                        By.cssSelector(selector)));
                log.info("Found checkout button with selector: {}", selector);
                break;
            } catch (Exception ignored) {}
        }
        if (btn == null) {
            // Fallback — find by link text
            btn = new WebDriverWait(driver, 5)
                .until(ExpectedConditions.elementToBeClickable(
                    By.partialLinkText("Proceed To Checkout")));
        }
        btn.click();
        return new CheckoutPage();
    }

    public void scrollToFooter() {
        log.info("Scrolling down to footer");
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
    }

    public String getSubscriptionHeadingText() {
        return WaitUtils.waitForVisible(subscriptionHeading).getText().trim();
    }

    public void enterSubscriptionEmail(String email) {
        log.info("Entering subscription email: {}", email);
        WaitUtils.waitForVisible(subscriptionEmailInput).clear();
        subscriptionEmailInput.sendKeys(email);
        WaitUtils.clickWhenReady(subscribeButton);
    }

    public String getSubscriptionSuccessMessage() {
        return WaitUtils.waitForVisible(subscriptionSuccessMsg).getText().trim();
    }
}
