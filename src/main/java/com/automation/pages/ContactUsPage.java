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

public class ContactUsPage {

    private static final Logger log = LogManager.getLogger(ContactUsPage.class);
    private WebDriver driver;

    @FindBy(css = "input[data-qa='name']")
    private WebElement nameInput;

    @FindBy(css = "input[data-qa='email']")
    private WebElement emailInput;

    @FindBy(css = "input[data-qa='subject']")
    private WebElement subjectInput;

    @FindBy(css = "textarea[data-qa='message']")
    private WebElement messageInput;

    @FindBy(css = "input[name='upload_file']")
    private WebElement uploadFileInput;

    @FindBy(css = "input[data-qa='submit-button']")
    private WebElement submitButton;

    @FindBy(css = ".status.alert.alert-success")
    private WebElement successMessage;

    public ContactUsPage() {
        this.driver = BaseDriver.getDriver();
        PageFactory.initElements(driver, this);
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public boolean isGetInTouchVisible() {
        try {
            WebElement heading = new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h2[contains(text(),'Get In Touch')]")));
            return heading.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void fillContactForm(String name, String email, String subject, String message) {
        log.info("Filling contact form for: {}", name);
        WaitUtils.waitForVisible(nameInput).clear();
        nameInput.sendKeys(name);
        WaitUtils.waitForVisible(emailInput).clear();
        emailInput.sendKeys(email);
        WaitUtils.waitForVisible(subjectInput).clear();
        subjectInput.sendKeys(subject);
        WaitUtils.waitForVisible(messageInput).clear();
        messageInput.sendKeys(message);
    }

    public void uploadFile(String filePath) {
        log.info("Uploading file: {}", filePath);
        uploadFileInput.sendKeys(filePath);
    }

    public void clickSubmit() {
        log.info("Clicking Submit button");
        WaitUtils.clickWhenReady(submitButton);
    }

    public void acceptAlert() {
        log.info("Accepting browser alert");
        try {
            new WebDriverWait(driver, 5)
                .until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();
        } catch (Exception e) {
            log.warn("No alert present to accept");
        }
    }

    public String getSuccessMessage() {
        WebElement msg = new WebDriverWait(driver, 15)
            .until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".status.alert.alert-success")));
        return msg.getText().trim();
    }
}
