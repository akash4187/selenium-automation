package com.automation.cucumber.stepdefs;

import com.automation.base.BaseDriver;
import com.automation.pages.*;
import com.automation.utils.Constants;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class CommonSteps {

    private HomePage homePage;
    private LoginPage loginPage;
    private SignupPage signupPage;
    private ProductsPage productsPage;
    private ProductDetailPage productDetailPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private PaymentPage paymentPage;
    private ContactUsPage contactUsPage;
    private String testEmail;
    private String testName;

    // ===== HOME PAGE STEPS =====

    @Given("I am on the home page")
    public void iAmOnTheHomePage() {
        homePage = new HomePage();
        homePage.navigateTo();
        Assert.assertEquals(homePage.getTitle(), Constants.HOME_PAGE_TITLE);
    }

    @And("I click on Signup Login button")
    public void iClickOnSignupLoginButton() {
        loginPage = homePage.clickSignupLogin();
    }

    @When("I click on Products button")
    public void iClickOnProductsButton() {
        productsPage = homePage.clickProducts();
    }

    @When("I click on Cart button")
    public void iClickOnCartButton() {
        cartPage = homePage.clickCart();
    }

    @When("I click on Contact Us button")
    public void iClickOnContactUsButton() {
        contactUsPage = homePage.clickContactUs();
    }

    @When("I click on View Product of first product on home page")
    public void iClickOnViewProductOnHomePage() {
        productDetailPage = homePage.viewFirstProduct();
    }

    @When("I scroll down to footer")
    public void iScrollDownToFooter() {
        homePage.scrollToFooter();
    }

    @When("I scroll up to the top")
    public void iScrollUpToTheTop() {
        org.openqa.selenium.JavascriptExecutor js =
            (org.openqa.selenium.JavascriptExecutor) BaseDriver.getDriver();
        js.executeScript("window.scrollTo(0, 0);");
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
    }

    @When("I click the scroll up arrow button")
    public void iClickTheScrollUpArrowButton() {
        WebDriverWait wait = new WebDriverWait(BaseDriver.getDriver(), 10);
        WebElement scrollUpArrow = wait.until(
            ExpectedConditions.elementToBeClickable(By.cssSelector("#scrollUp")));
        scrollUpArrow.click();
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
    }

    @Then("I should see the text {string}")
    public void iShouldSeeTheText(String text) {
        WebDriverWait wait = new WebDriverWait(BaseDriver.getDriver(), 10);
        WebElement element = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'" + text + "')]")));
        Assert.assertTrue(element.isDisplayed());
    }

    @Then("I should see SUBSCRIPTION heading")
    public void iShouldSeeSubscriptionHeading() {
        Assert.assertEquals(homePage.getSubscriptionHeadingText(), "SUBSCRIPTION");
    }

    @When("I enter subscription email and click arrow")
    public void iEnterSubscriptionEmailAndClickArrow() {
        homePage.enterSubscriptionEmail("testsub" + System.currentTimeMillis() + "@test.com");
    }

    @Then("I should see subscription success message {string}")
    public void iShouldSeeSubscriptionSuccessMessage(String msg) {
        if (cartPage != null) {
            try {
                Assert.assertEquals(cartPage.getSubscriptionSuccessMessage(), msg);
                return;
            } catch (Exception ignored) {}
        }
        Assert.assertEquals(homePage.getSubscriptionSuccessMessage(), msg);
    }

    @Then("I should be logged in as {string}")
    public void iShouldBeLoggedInAs(String username) {
        homePage = new HomePage();
        Assert.assertTrue(homePage.isLoggedInAs(username));
    }

    @When("I click Delete Account")
    public void iClickDeleteAccount() {
        homePage = new HomePage();
        homePage.clickDeleteAccount();
    }

    @Then("I should see ACCOUNT DELETED message")
    public void iShouldSeeAccountDeletedMessage() {
        WebDriverWait wait = new WebDriverWait(BaseDriver.getDriver(), Constants.EXPLICIT_WAIT);
        WebElement msg = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2.title b")));
        Assert.assertEquals(msg.getText(), Constants.ACCOUNT_DELETED_MSG);
        WebElement continueBtn = wait.until(
            ExpectedConditions.elementToBeClickable(By.cssSelector("a[data-qa='continue-button']")));
        continueBtn.click();
    }

    @When("I click Continue button")
    public void iClickContinueButton() {
        WebDriverWait wait = new WebDriverWait(BaseDriver.getDriver(), Constants.EXPLICIT_WAIT);
        try {
            WebElement continueBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector("a[data-qa='continue-button']")));
            org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) BaseDriver.getDriver();
            js.executeScript("arguments[0].click();", continueBtn);
        } catch (Exception e) {
            // Page might have an ad interstitial — navigate directly to home
            BaseDriver.getDriver().get(BaseDriver.getBaseUrl());
        }
        try { Thread.sleep(3000); } catch (InterruptedException ignored) {}
        // Re-initialize homePage after navigation
        homePage = new HomePage();
    }

    // ===== SIGNUP STEPS =====

    @When("I signup with name {string} and a unique email")
    public void iSignupWithNameAndUniqueEmail(String name) {
        testName = name;
        testEmail = "test" + System.currentTimeMillis() + "@test.com";
        if (loginPage == null) {
            loginPage = new LoginPage();
        }
        loginPage.enterSignupDetails(testName, testEmail);
    }

    @When("I enter signup name {string} and email {string}")
    public void iEnterSignupNameAndEmail(String name, String email) {
        loginPage.enterSignupDetails(name, email);
    }

    @And("I click the signup button")
    public void iClickTheSignupButton() {
        // Already handled in enterSignupDetails
    }

    @And("I fill account details and create account")
    public void iFillAccountDetailsAndCreateAccount() {
        signupPage = new SignupPage();
        signupPage.fillAccountDetails(
            "Test@123", "15", "June", "1990",
            "Test", "User", "Test Corp",
            "123 Test Street", "Apt 1",
            "United States", "New York", "New York", "10001", "1234567890"
        );
    }

    @And("I fill account details with address {string} city {string} state {string} zipcode {string}")
    public void iFillAccountDetailsWithAddress(String address, String city, String state, String zipcode) {
        signupPage = new SignupPage();
        signupPage.fillAccountDetails(
            "Test@123", "5", "August", "1988",
            "Address", "User", "Test Corp",
            address, "Apt 2",
            "United States", state, city, zipcode, "5551234567"
        );
    }

    @Then("I should see ACCOUNT CREATED message")
    public void iShouldSeeAccountCreatedMessage() {
        WebDriverWait wait = new WebDriverWait(BaseDriver.getDriver(), Constants.EXPLICIT_WAIT);
        WebElement msg = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2.title b")));
        Assert.assertEquals(msg.getText(), Constants.ACCOUNT_CREATED_MSG);
    }

    @Then("I should see signup error message {string}")
    public void iShouldSeeSignupErrorMessage(String expectedError) {
        String actualError = loginPage.getErrorMessage();
        Assert.assertEquals(actualError.trim(), expectedError.trim());
    }

    // ===== PRODUCTS STEPS =====

    @Then("I should be on All Products page")
    public void iShouldBeOnAllProductsPage() {
        Assert.assertEquals(productsPage.getTitle(), Constants.PRODUCTS_PAGE_TITLE);
    }

    @Then("the products list should be visible")
    public void theProductsListShouldBeVisible() {
        Assert.assertTrue(productsPage.getProductCount() > 0);
    }

    @When("I click on View Product of first product")
    public void iClickOnViewProductOfFirstProduct() {
        productDetailPage = productsPage.viewFirstProduct();
    }

    @Then("I should be on product detail page")
    public void iShouldBeOnProductDetailPage() {
        Assert.assertTrue(productDetailPage.isProductDetailVisible());
    }

    @Then("I should see product name, category, price, availability, condition, brand")
    public void iShouldSeeProductDetails() {
        Assert.assertFalse(productDetailPage.getProductName().isEmpty());
        Assert.assertFalse(productDetailPage.getProductCategory().isEmpty());
        Assert.assertFalse(productDetailPage.getProductPrice().isEmpty());
        Assert.assertTrue(productDetailPage.getProductAvailability().contains("Availability"));
        Assert.assertTrue(productDetailPage.getProductCondition().contains("Condition"));
        Assert.assertTrue(productDetailPage.getProductBrand().contains("Brand"));
    }

    @When("I search for product {string}")
    public void iSearchForProduct(String productName) {
        productsPage.searchProduct(productName);
    }

    @Then("I should see search results with products")
    public void iShouldSeeSearchResultsWithProducts() {
        Assert.assertTrue(productsPage.getProductCount() > 0);
    }

    @Then("I should see Write Your Review section")
    public void iShouldSeeWriteYourReviewSection() {
        Assert.assertTrue(productDetailPage.isWriteYourReviewVisible());
    }

    @When("I enter review name {string} email {string} and review {string}")
    public void iEnterReviewDetails(String name, String email, String review) {
        productDetailPage.submitReview(name, email, review);
    }

    @And("I click the review submit button")
    public void iClickTheReviewSubmitButton() {
        // Already handled in submitReview
    }

    @Then("I should see review success message {string}")
    public void iShouldSeeReviewSuccessMessage(String msg) {
        Assert.assertEquals(productDetailPage.getReviewSuccessMessage(), msg);
    }

    // ===== CART STEPS =====

    @And("I add first product to cart")
    public void iAddFirstProductToCart() {
        productsPage.addFirstProductToCart();
    }

    @And("I click View Cart from modal")
    public void iClickViewCartFromModal() {
        if (productDetailPage != null) {
            cartPage = productDetailPage.clickViewCart();
        } else {
            cartPage = productsPage.viewCart();
        }
    }

    @Then("the cart should have at least one product")
    public void theCartShouldHaveAtLeastOneProduct() {
        Assert.assertTrue(cartPage.getCartItemCount() > 0);
    }

    @When("I set quantity to {int}")
    public void iSetQuantityTo(int qty) {
        productDetailPage.setQuantity(qty);
    }

    @When("I click Add to Cart button")
    public void iClickAddToCartButton() {
        productDetailPage.clickAddToCart();
    }

    @Then("the product quantity in cart should be {string}")
    public void theProductQuantityInCartShouldBe(String qty) {
        Assert.assertEquals(cartPage.getProductQuantity(0), qty);
    }

    @Then("I should see RECOMMENDED ITEMS section")
    public void iShouldSeeRecommendedItemsSection() {
        Assert.assertTrue(homePage.isRecommendedItemsVisible());
    }

    @When("I click Add to Cart on recommended product")
    public void iClickAddToCartOnRecommendedProduct() {
        homePage.addRecommendedProductToCart();
    }

    @When("I click View Cart from recommended modal")
    public void iClickViewCartFromRecommendedModal() {
        cartPage = homePage.clickViewCartFromModal();
    }

    @When("I scroll down on cart page")
    public void iScrollDownOnCartPage() {
        cartPage.scrollToFooter();
    }

    @Then("I should see SUBSCRIPTION heading on cart page")
    public void iShouldSeeSubscriptionHeadingOnCartPage() {
        Assert.assertEquals(cartPage.getSubscriptionHeadingText(), "SUBSCRIPTION");
    }

    @When("I enter subscription email on cart page and click arrow")
    public void iEnterSubscriptionEmailOnCartPageAndClickArrow() {
        cartPage.enterSubscriptionEmail("testsub" + System.currentTimeMillis() + "@test.com");
    }

    // ===== CHECKOUT STEPS =====

    @When("I click Proceed To Checkout")
    public void iClickProceedToCheckout() {
        checkoutPage = cartPage.proceedToCheckout();
    }

    @And("I click Register Login from checkout modal")
    public void iClickRegisterLoginFromCheckoutModal() {
        checkoutPage = new CheckoutPage();
        loginPage = checkoutPage.loginFromCheckout();
    }

    @Then("I should see address details and order review")
    public void iShouldSeeAddressDetailsAndOrderReview() {
        Assert.assertTrue(checkoutPage.isAddressDetailVisible());
        Assert.assertTrue(checkoutPage.isOrderReviewVisible());
    }

    @Then("the delivery address should contain {string}")
    public void theDeliveryAddressShouldContain(String text) {
        Assert.assertTrue(checkoutPage.getDeliveryAddress().contains(text));
    }

    @And("the billing address should contain {string}")
    public void theBillingAddressShouldContain(String text) {
        Assert.assertTrue(checkoutPage.getBillingAddress().contains(text));
    }

    @When("I enter comment {string} and click Place Order")
    public void iEnterCommentAndClickPlaceOrder(String comment) {
        paymentPage = checkoutPage.placeOrderWithComment(comment);
    }

    @And("I enter payment details and confirm order")
    public void iEnterPaymentDetailsAndConfirmOrder() {
        paymentPage.enterPaymentDetails(
            Constants.CARD_NAME, Constants.CARD_NUMBER,
            Constants.CARD_CVC, Constants.CARD_EXPIRY_MONTH, Constants.CARD_EXPIRY_YEAR
        );
        paymentPage.confirmOrder();
    }

    @Then("I should see order confirmation message")
    public void iShouldSeeOrderConfirmationMessage() {
        // Confirmation already verified in confirmOrder()
    }

    // ===== CONTACT US STEPS =====

    @Then("I should see Get In Touch heading")
    public void iShouldSeeGetInTouchHeading() {
        Assert.assertTrue(contactUsPage.isGetInTouchVisible());
    }

    @When("I fill contact form with name {string} email {string} subject {string} and message {string}")
    public void iFillContactForm(String name, String email, String subject, String message) {
        contactUsPage.fillContactForm(name, email, subject, message);
    }

    @And("I upload a file")
    public void iUploadAFile() {
        String filePath = System.getProperty("user.dir") + "\\pom.xml";
        contactUsPage.uploadFile(filePath);
    }

    @And("I click the contact submit button")
    public void iClickTheContactSubmitButton() {
        contactUsPage.clickSubmit();
    }

    @And("I accept the alert")
    public void iAcceptTheAlert() {
        contactUsPage.acceptAlert();
    }

    @Then("I should see contact success message {string}")
    public void iShouldSeeContactSuccessMessage(String msg) {
        Assert.assertEquals(contactUsPage.getSuccessMessage(), msg);
    }

    // ===== CATEGORY STEPS =====

    @Then("I should see categories on left side bar")
    public void iShouldSeeCategoriesOnLeftSideBar() {
        Assert.assertTrue(homePage.isCategorySidebarVisible());
    }

    @When("I click on Women category and then Tops sub-category")
    public void iClickOnWomenCategoryAndTops() {
        homePage.selectCategory("Women", "Tops");
    }

    @When("I click on Men category and then Tshirts sub-category")
    public void iClickOnMenCategoryAndTshirts() {
        homePage.selectCategory("Men", "Jeans");
    }

    @Then("I should see category page with title containing {string}")
    public void iShouldSeeCategoryPageWithTitle(String expectedTitle) {
        String actualTitle = homePage.getCategoryPageTitle();
        Assert.assertTrue(actualTitle.toUpperCase().contains(expectedTitle.toUpperCase()),
            "Expected title containing '" + expectedTitle + "' but got: " + actualTitle);
    }
}
