package com.automation.pages;

import com.automation.base.BaseDriver;
import com.automation.utils.WaitUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductDetailPage {

    private static final Logger log = LogManager.getLogger(ProductDetailPage.class);
    private WebDriver driver;

    @FindBy(css = ".product-information h2")
    private WebElement productName;

    @FindBy(css = ".product-information p:nth-of-type(1)")
    private WebElement productCategory;

    @FindBy(css = ".product-information span span")
    private WebElement productPrice;

    @FindBy(css = ".product-information p:nth-of-type(2)")
    private WebElement productAvailability;

    @FindBy(css = ".product-information p:nth-of-type(3)")
    private WebElement productCondition;

    @FindBy(css = ".product-information p:nth-of-type(4)")
    private WebElement productBrand;

    @FindBy(css = "input#quantity")
    private WebElement quantityInput;

    @FindBy(css = "button.btn.cart")
    private WebElement addToCartButton;

    @FindBy(css = "#cartModal a[href='/view_cart']")
    private WebElement viewCartLink;

    // Review section
    @FindBy(css = "input#name")
    private WebElement reviewNameInput;

    @FindBy(css = "input#email")
    private WebElement reviewEmailInput;

    @FindBy(css = "textarea#review")
    private WebElement reviewTextArea;

    @FindBy(css = "button#button-review")
    private WebElement submitReviewButton;

    public ProductDetailPage() {
        this.driver = BaseDriver.getDriver();
        PageFactory.initElements(driver, this);
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public String getProductName() {
        log.info("Getting product name");
        return WaitUtils.waitForVisible(productName).getText().trim();
    }

    public String getProductCategory() {
        return WaitUtils.waitForVisible(productCategory).getText().trim();
    }

    public String getProductPrice() {
        return WaitUtils.waitForVisible(productPrice).getText().trim();
    }

    public String getProductAvailability() {
        return WaitUtils.waitForVisible(productAvailability).getText().trim();
    }

    public String getProductCondition() {
        return WaitUtils.waitForVisible(productCondition).getText().trim();
    }

    public String getProductBrand() {
        return WaitUtils.waitForVisible(productBrand).getText().trim();
    }

    public boolean isProductDetailVisible() {
        try {
            new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOf(productName));
            return productName.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void setQuantity(int quantity) {
        log.info("Setting product quantity to: {}", quantity);
        WaitUtils.waitForVisible(quantityInput).clear();
        quantityInput.sendKeys(String.valueOf(quantity));
    }

    public void clickAddToCart() {
        log.info("Clicking Add to Cart button");
        WaitUtils.clickWhenReady(addToCartButton);
        // Wait for cart modal to appear
        new WebDriverWait(driver, 10)
            .until(ExpectedConditions.visibilityOfElementLocated(
                org.openqa.selenium.By.cssSelector("#cartModal")));
    }

    public CartPage clickViewCart() {
        log.info("Clicking View Cart from modal");
        WaitUtils.clickWhenReady(viewCartLink);
        return new CartPage();
    }

    public boolean isWriteYourReviewVisible() {
        try {
            WebElement heading = new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfElementLocated(
                    org.openqa.selenium.By.xpath("//a[contains(text(),'Write Your Review')]")));
            return heading.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void submitReview(String name, String email, String review) {
        log.info("Submitting review by: {}", name);
        WaitUtils.waitForVisible(reviewNameInput).clear();
        reviewNameInput.sendKeys(name);
        WaitUtils.waitForVisible(reviewEmailInput).clear();
        reviewEmailInput.sendKeys(email);
        WaitUtils.waitForVisible(reviewTextArea).clear();
        reviewTextArea.sendKeys(review);
        WaitUtils.clickWhenReady(submitReviewButton);
    }

    public String getReviewSuccessMessage() {
        WebElement msg = new WebDriverWait(driver, 10)
            .until(ExpectedConditions.visibilityOfElementLocated(
                org.openqa.selenium.By.cssSelector("#review-section .alert-success span, .alert-success")));
        return msg.getText().trim();
    }
}
