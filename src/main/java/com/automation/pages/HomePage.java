package com.automation.pages;

import com.automation.base.BaseDriver;
import com.automation.utils.WaitUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    private static final Logger log = LogManager.getLogger(HomePage.class);
    private WebDriver driver;

    @FindBy(css = "a[href='/login']")
    private WebElement signupLoginButton;

    @FindBy(css = "a[href='/products']")
    private WebElement productsButton;

    @FindBy(css = "a[href='/logout']")
    private WebElement logoutButton;

    @FindBy(css = "a[href='/view_cart']")
    private WebElement cartButton;

    @FindBy(css = "a[href='/delete_account']")
    private WebElement deleteAccountButton;

    @FindBy(css = "li a b")
    private WebElement loggedInAsText;

    @FindBy(css = "a[href='/contact_us']")
    private WebElement contactUsButton;

    @FindBy(css = "#susbscribe_email")
    private WebElement subscriptionEmailInput;

    @FindBy(css = ".panel-group#accordian")
    private WebElement categorySidebar;

    @FindBy(css = ".panel-group#accordian .panel-title a")
    private java.util.List<WebElement> categoryLinks;

    @FindBy(css = "#subscribe")
    private WebElement subscribeButton;

    @FindBy(css = ".footer-widget h2")
    private WebElement subscriptionHeading;

    @FindBy(css = "#success-subscribe .alert-success")
    private WebElement subscriptionSuccessMsg;

    public HomePage() {
        this.driver = BaseDriver.getDriver();
        PageFactory.initElements(driver, this);
    }

    public void navigateTo() {
        log.info("Navigating to: {}", BaseDriver.getBaseUrl());
        driver.get(BaseDriver.getBaseUrl());
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public LoginPage clickSignupLogin() {
        log.info("Clicking Signup/Login button");
        WaitUtils.clickWhenReady(signupLoginButton);
        return new LoginPage();
    }

    public ProductsPage clickProducts() {
        log.info("Clicking Products button");
        WaitUtils.clickWhenReady(productsButton);
        return new ProductsPage();
    }

    public CartPage clickCart() {
        log.info("Clicking Cart button");
        WaitUtils.clickWhenReady(cartButton);
        return new CartPage();
    }

    public ProductDetailPage viewFirstProduct() {
        log.info("Clicking View Product of first product on home page");
        org.openqa.selenium.WebElement viewProductLink = new org.openqa.selenium.support.ui.WebDriverWait(driver, 10)
            .until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(org.openqa.selenium.By.cssSelector("a[href^='/product_details/']")));
        WaitUtils.clickWhenReady(viewProductLink);
        return new ProductDetailPage();
    }

    public void clickLogout() {
        log.info("Clicking Logout button");
        WaitUtils.clickWhenReady(logoutButton);
    }

    public boolean isLoggedInAs(String username) {
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(driver, 10)
                .until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf(loggedInAsText));
            String text = loggedInAsText.getText().trim();
            log.info("Logged in as: {}", text);
            return text.equalsIgnoreCase(username);
        } catch (Exception e) {
            return false;
        }
    }

    public void clickDeleteAccount() {
        log.info("Clicking Delete Account button");
        WaitUtils.clickWhenReady(deleteAccountButton);
    }

    public ContactUsPage clickContactUs() {
        log.info("Clicking Contact Us button");
        WaitUtils.clickWhenReady(contactUsButton);
        return new ContactUsPage();
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

    public boolean isCategorySidebarVisible() {
        try {
            return WaitUtils.waitForVisible(categorySidebar).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isRecommendedItemsVisible() {
        try {
            org.openqa.selenium.WebElement heading = new org.openqa.selenium.support.ui.WebDriverWait(driver, 10)
                .until(org.openqa.selenium.support.ui.ExpectedConditions
                    .visibilityOfElementLocated(org.openqa.selenium.By.xpath("//h2[contains(text(),'recommended items')]")));
            return heading.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void addRecommendedProductToCart() {
        log.info("Adding recommended product to cart");
        org.openqa.selenium.WebElement addBtn = new org.openqa.selenium.support.ui.WebDriverWait(driver, 10)
            .until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(org.openqa.selenium.By.cssSelector("#recommended-item-carousel .add-to-cart")));
        WaitUtils.clickWhenReady(addBtn);
        // Wait for cart modal
        new org.openqa.selenium.support.ui.WebDriverWait(driver, 10)
            .until(org.openqa.selenium.support.ui.ExpectedConditions
                .visibilityOfElementLocated(org.openqa.selenium.By.cssSelector("#cartModal")));
        log.info("Cart modal appeared");
    }

    public CartPage clickViewCartFromModal() {
        log.info("Clicking View Cart from modal");
        org.openqa.selenium.WebElement viewCartBtn = new org.openqa.selenium.support.ui.WebDriverWait(driver, 10)
            .until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(org.openqa.selenium.By.cssSelector("#cartModal a[href='/view_cart']")));
        WaitUtils.clickWhenReady(viewCartBtn);
        return new CartPage();
    }

    public void selectCategory(String category, String subcategory) {
        log.info("Selecting category: {} > {}", category, subcategory);
        // Use fresh element lookup each time (page may have reloaded)
        java.util.List<org.openqa.selenium.WebElement> catLinks = driver.findElements(
            org.openqa.selenium.By.cssSelector(".panel-group#accordian .panel-title a"));
        for (org.openqa.selenium.WebElement link : catLinks) {
            if (link.getText().trim().toUpperCase().contains(category.toUpperCase())) {
                WaitUtils.clickWhenReady(link);
                break;
            }
        }
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        org.openqa.selenium.WebElement subLink = new org.openqa.selenium.support.ui.WebDriverWait(driver, 10)
            .until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(
                    org.openqa.selenium.By.xpath("//div[@id='accordian']//a[contains(text(),'" + subcategory + "')]")));
        WaitUtils.clickWhenReady(subLink);
    }

    public String getCategoryPageTitle() {
        org.openqa.selenium.WebElement title = new org.openqa.selenium.support.ui.WebDriverWait(driver, 10)
            .until(org.openqa.selenium.support.ui.ExpectedConditions
                .visibilityOfElementLocated(org.openqa.selenium.By.cssSelector(".title.text-center")));
        return title.getText().trim();
    }
}
