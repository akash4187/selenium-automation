package com.automation.pages;

import com.automation.base.BaseDriver;
import com.automation.utils.WaitUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CheckoutPage {

    private static final Logger log = LogManager.getLogger(CheckoutPage.class);
    private WebDriver driver;

    // Modal shown when guest clicks Proceed to Checkout
    @FindBy(css = ".modal-body a[href='/login']")
    private WebElement loginFromModalButton;

    @FindBy(css = "#checkoutModal")
    private WebElement checkoutModal;

    // Order summary — products listed in checkout
    @FindBy(css = "#cart_info tbody tr")
    private List<WebElement> orderItems;

    // Address details
    @FindBy(css = "#address_delivery .address_firstname")
    private WebElement deliveryAddressName;

    @FindBy(css = "#address_delivery li")
    private List<WebElement> deliveryAddressLines;

    @FindBy(css = "#address_invoice .address_firstname")
    private WebElement billingAddressName;

    @FindBy(css = "#address_invoice li")
    private List<WebElement> billingAddressLines;

    // Comment box before placing order
    @FindBy(css = "textarea[name='message']")
    private WebElement commentBox;

    // Place Order button
    @FindBy(css = "a[href='/payment']")
    private WebElement placeOrderButton;

    public CheckoutPage() {
        this.driver = BaseDriver.getDriver();
        PageFactory.initElements(driver, this);
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public LoginPage loginFromCheckout() {
        log.info("Clicking login from checkout modal");
        // Wait for the checkout modal to be visible
        // Bootstrap 3 uses 'in' class, Bootstrap 4/5 uses 'show' class
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(driver, 10)
                .until(org.openqa.selenium.support.ui.ExpectedConditions
                    .visibilityOfElementLocated(
                        org.openqa.selenium.By.cssSelector("#checkoutModal.modal.in, #checkoutModal.modal.show, #checkoutModal[style*='display: block']")));
        } catch (Exception e) {
            // Fallback: wait for any visible modal with the login link
            new org.openqa.selenium.support.ui.WebDriverWait(driver, 10)
                .until(org.openqa.selenium.support.ui.ExpectedConditions
                    .visibilityOfElementLocated(
                        org.openqa.selenium.By.cssSelector(".modal.in, .modal.show, .modal[style*='display: block']")));
        }

        // Small delay to let modal animation complete
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}

        // Wait for the Register/Login link inside the modal to be clickable
        WebElement modalLoginLink = new org.openqa.selenium.support.ui.WebDriverWait(driver, 10)
            .until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(
                    org.openqa.selenium.By.cssSelector("#checkoutModal a[href='/login'], .modal-body a[href='/login'], .modal-content a[href='/login']")));

        // Use JavaScript click to bypass any overlay issues from ads
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", modalLoginLink);

        // Wait for navigation to login page
        new org.openqa.selenium.support.ui.WebDriverWait(driver, 15)
            .until(org.openqa.selenium.support.ui.ExpectedConditions
                .urlContains("/login"));
        log.info("Navigated to login page from checkout modal");

        return new LoginPage();
    }

    public int getOrderItemCount() {
        return orderItems.size();
    }

    public boolean isAddressDetailVisible() {
        try {
            WaitUtils.waitForVisible(deliveryAddressName);
            return deliveryAddressName.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isOrderReviewVisible() {
        return orderItems.size() > 0;
    }

    public String getDeliveryAddress() {
        StringBuilder address = new StringBuilder();
        for (WebElement line : deliveryAddressLines) {
            String text = line.getText().trim();
            if (!text.isEmpty()) {
                address.append(text).append("\n");
            }
        }
        return address.toString().trim();
    }

    public String getBillingAddress() {
        StringBuilder address = new StringBuilder();
        for (WebElement line : billingAddressLines) {
            String text = line.getText().trim();
            if (!text.isEmpty()) {
                address.append(text).append("\n");
            }
        }
        return address.toString().trim();
    }

    public PaymentPage placeOrderWithComment(String comment) {
        log.info("Placing order with comment: {}", comment);
        WaitUtils.waitForVisible(commentBox).clear();
        commentBox.sendKeys(comment);
        WaitUtils.clickWhenReady(placeOrderButton);
        return new PaymentPage();
    }

    public PaymentPage placeOrder() {
        log.info("Placing order");
        WaitUtils.waitForVisible(commentBox).clear();
        WaitUtils.waitForVisible(commentBox).sendKeys("Test order comment");
        WaitUtils.clickWhenReady(placeOrderButton);
        return new PaymentPage();
    }
}
