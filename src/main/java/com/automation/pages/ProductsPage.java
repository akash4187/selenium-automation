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

import java.util.List;
import java.util.stream.Collectors;

public class ProductsPage {

    private static final Logger log = LogManager.getLogger(ProductsPage.class);
    private WebDriver driver;

    @FindBy(css = ".productinfo p")
    private List<WebElement> productNames;

    @FindBy(css = "input#search_product")
    private WebElement searchInput;

    @FindBy(css = "button#submit_search")
    private WebElement searchButton;

    @FindBy(css = ".productinfo a.btn")
    private List<WebElement> addToCartButtons;

    @FindBy(css = "button[data-dismiss='modal']")
    private WebElement continueShoppingButton;

    @FindBy(css = "#cartModal a[href='/view_cart']")
    private WebElement viewCartButton;

    // Category sidebar
    @FindBy(css = ".panel-group#accordian .panel-title a")
    private List<WebElement> categoryLinks;

    // Brand sidebar
    @FindBy(css = ".brands-name .nav-stacked li a")
    private List<WebElement> brandLinks;

    // Search results heading
    @FindBy(css = ".title.text-center")
    private WebElement searchResultsTitle;

    // View Product links
    @FindBy(css = "a[href^='/product_details/']")
    private List<WebElement> viewProductLinks;

    public ProductDetailPage viewFirstProduct() {
        log.info("Clicking View Product of first product");
        WaitUtils.clickWhenReady(viewProductLinks.get(0));
        return new ProductDetailPage();
    }

    public ProductsPage() {
        this.driver = BaseDriver.getDriver();
        PageFactory.initElements(driver, this);
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public int getProductCount() {
        return productNames.size();
    }

    public List<String> getProductNames() {
        return productNames.stream()
            .map(WebElement::getText)
            .collect(Collectors.toList());
    }

    public String getSearchResultsTitle() {
        return WaitUtils.waitForVisible(searchResultsTitle).getText();
    }

    public void searchProduct(String productName) {
        log.info("Searching for product: {}", productName);
        WaitUtils.typeWhenReady(searchInput, productName);
        WaitUtils.clickWhenReady(searchButton);
    }

    public void addFirstProductToCart() {
        log.info("Adding first product to cart");
        WaitUtils.clickWhenReady(addToCartButtons.get(0));
        new org.openqa.selenium.support.ui.WebDriverWait(driver, 10)
            .until(org.openqa.selenium.support.ui.ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("#cartModal")));
        log.info("Cart modal appeared");
    }

    public void continueShopping() {
        log.info("Clicking Continue Shopping");
        WaitUtils.clickWhenReady(continueShoppingButton);
    }

    public CartPage viewCart() {
        log.info("Clicking View Cart");
        WaitUtils.clickWhenReady(viewCartButton);
        return new CartPage();
    }

    public void selectCategory(String category, String subcategory) {
        log.info("Selecting category: {} > {}", category, subcategory);
        // Click the category accordion header
        for (WebElement link : categoryLinks) {
            if (link.getText().trim().toUpperCase().contains(category.toUpperCase())) {
                WaitUtils.clickWhenReady(link);
                break;
            }
        }
        // Wait for subcategory panel to expand, then click subcategory
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        WebElement subLink = driver.findElement(
            By.xpath("//div[@id='accordian']//a[contains(text(),'" + subcategory + "')]"));
        WaitUtils.clickWhenReady(subLink);
    }

    public void selectBrand(String brandName) {
        log.info("Selecting brand: {}", brandName);
        for (WebElement link : brandLinks) {
            if (link.getText().trim().toUpperCase().contains(brandName.toUpperCase())) {
                WaitUtils.clickWhenReady(link);
                break;
            }
        }
    }
}
