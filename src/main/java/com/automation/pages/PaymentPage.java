package com.automation.pages;

import com.automation.base.BaseDriver;
import com.automation.utils.WaitUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PaymentPage {

    private static final Logger log = LogManager.getLogger(PaymentPage.class);
    private WebDriver driver;

    @FindBy(css = "input[data-qa='name-on-card']")
    private WebElement cardName;

    @FindBy(css = "input[data-qa='card-number']")
    private WebElement cardNumber;

    @FindBy(css = "input[data-qa='cvc']")
    private WebElement cardCvc;

    @FindBy(css = "input[data-qa='expiry-month']")
    private WebElement expiryMonth;

    @FindBy(css = "input[data-qa='expiry-year']")
    private WebElement expiryYear;

    @FindBy(css = "button[data-qa='pay-button']")
    private WebElement payButton;

    @FindBy(css = "a.btn.btn-default.check_out")
    private WebElement downloadInvoiceButton;

    @FindBy(css = "a[data-qa='continue-button']")
    private WebElement continueButton;

    public PaymentPage() {
        this.driver = BaseDriver.getDriver();
        PageFactory.initElements(driver, this);
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public void enterPaymentDetails(String name, String number, String cvc,
                                    String month, String year) {
        log.info("Entering payment details for card: {}", number);
        WaitUtils.waitForVisible(cardName).sendKeys(name);
        WaitUtils.waitForVisible(cardNumber).sendKeys(number);
        WaitUtils.waitForVisible(cardCvc).sendKeys(cvc);
        WaitUtils.waitForVisible(expiryMonth).sendKeys(month);
        WaitUtils.waitForVisible(expiryYear).sendKeys(year);
    }

    public String confirmOrder() {
        log.info("Confirming order");
        WaitUtils.clickWhenReady(payButton);

        // Wait for payment to process and confirmation page to load
        new org.openqa.selenium.support.ui.WebDriverWait(driver, 20)
            .until(org.openqa.selenium.support.ui.ExpectedConditions
                .urlContains("payment_done"));

        // Try multiple selectors for the order confirmation message
        String[] selectors = {
            "h2.title.text-center b",
            "p.title.text-center",
            ".col-sm-9 h2 b",
            ".col-sm-9 p",
            "h2.title b",
            "#success_message b",
            "[data-qa='order-placed'] b",
            "h2 b",
            ".title b"
        };

        for (String selector : selectors) {
            try {
                org.openqa.selenium.WebElement element = new org.openqa.selenium.support.ui.WebDriverWait(driver, 5)
                    .until(org.openqa.selenium.support.ui.ExpectedConditions
                        .visibilityOfElementLocated(org.openqa.selenium.By.cssSelector(selector)));
                String text = element.getText().trim();
                if (!text.isEmpty()) {
                    log.info("Order confirmation found with selector '{}': {}", selector, text);
                    return text;
                }
            } catch (Exception ignored) {}
        }

        // Fallback — look for any element containing "ORDER PLACED" text
        try {
            org.openqa.selenium.WebElement element = driver.findElement(
                org.openqa.selenium.By.xpath("//*[contains(text(),'ORDER PLACED') or contains(text(),'Congratulations')]"));
            return element.getText().trim();
        } catch (Exception e) {
            log.error("Could not find order confirmation message");
            return "";
        }
    }

    public void clickDownloadInvoice() {
        log.info("Clicking Download Invoice button");
        org.openqa.selenium.WebElement invoiceBtn = new org.openqa.selenium.support.ui.WebDriverWait(driver, 10)
            .until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(org.openqa.selenium.By.cssSelector("a.btn.btn-default.check_out, a[href*='download_invoice']")));
        invoiceBtn.click();
        // Wait for download to start
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
    }

    public void clickContinue() {
        log.info("Clicking Continue button");
        WaitUtils.clickWhenReady(continueButton);
    }
}
