package com.automation.pages;

import com.automation.base.BaseDriver;
import com.automation.utils.WaitUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class SignupPage {

    private static final Logger log = LogManager.getLogger(SignupPage.class);
    private WebDriver driver;

    @FindBy(css = "input[data-qa='signup-name']")
    private WebElement signupName;

    @FindBy(css = "input[data-qa='signup-email']")
    private WebElement signupEmail;

    @FindBy(css = "button[data-qa='signup-button']")
    private WebElement signupButton;

    @FindBy(css = "input[id='id_gender1']")
    private WebElement titleMr;

    @FindBy(css = "input[data-qa='password']")
    private WebElement password;

    @FindBy(css = "select[data-qa='days']")
    private WebElement dobDay;

    @FindBy(css = "select[data-qa='months']")
    private WebElement dobMonth;

    @FindBy(css = "select[data-qa='years']")
    private WebElement dobYear;

    @FindBy(css = "input[data-qa='first_name']")
    private WebElement firstName;

    @FindBy(css = "input[data-qa='last_name']")
    private WebElement lastName;

    @FindBy(css = "input[data-qa='company']")
    private WebElement company;

    @FindBy(css = "input[data-qa='address']")
    private WebElement address;

    @FindBy(css = "input[data-qa='address2']")
    private WebElement address2;

    @FindBy(css = "select[data-qa='country']")
    private WebElement country;

    @FindBy(css = "input[data-qa='state']")
    private WebElement state;

    @FindBy(css = "input[data-qa='city']")
    private WebElement city;

    @FindBy(css = "input[data-qa='zipcode']")
    private WebElement zipcode;

    @FindBy(css = "input[data-qa='mobile_number']")
    private WebElement mobileNumber;

    @FindBy(css = "button[data-qa='create-account']")
    private WebElement createAccountButton;

    public SignupPage() {
        this.driver = BaseDriver.getDriver();
        PageFactory.initElements(driver, this);
    }

    public void enterSignupDetails(String name, String email) {
        log.info("Entering signup details for: {}", name);
        WaitUtils.waitForVisible(signupName).clear();
        WaitUtils.waitForVisible(signupName).sendKeys(name);
        WaitUtils.waitForVisible(signupEmail).clear();
        WaitUtils.waitForVisible(signupEmail).sendKeys(email);
        WaitUtils.clickWhenReady(signupButton);
    }

    public void fillAccountDetails(String pwd, String day, String month, String year,
                                   String fName, String lName, String comp,
                                   String addr, String addr2,
                                   String ctry, String st, String cty,
                                   String zip, String mobile) {
        log.info("Filling account details for: {} {}", fName, lName);
        WaitUtils.waitForVisible(titleMr);
        WaitUtils.clickWhenReady(titleMr);
        WaitUtils.waitForVisible(password).sendKeys(pwd);
        new Select(WaitUtils.waitForVisible(dobDay)).selectByVisibleText(day);
        new Select(WaitUtils.waitForVisible(dobMonth)).selectByVisibleText(month);
        new Select(WaitUtils.waitForVisible(dobYear)).selectByVisibleText(year);
        WaitUtils.waitForVisible(firstName).sendKeys(fName);
        WaitUtils.waitForVisible(lastName).sendKeys(lName);
        WaitUtils.waitForVisible(company).sendKeys(comp);
        WaitUtils.waitForVisible(address).sendKeys(addr);
        WaitUtils.waitForVisible(address2).sendKeys(addr2);
        new Select(WaitUtils.waitForVisible(country)).selectByVisibleText(ctry);
        WaitUtils.waitForVisible(state).sendKeys(st);
        WaitUtils.waitForVisible(city).sendKeys(cty);
        WaitUtils.waitForVisible(zipcode).sendKeys(zip);
        WaitUtils.waitForVisible(mobileNumber).sendKeys(mobile);
        WaitUtils.clickWhenReady(createAccountButton);
    }

    public String getTitle() {
        return driver.getTitle();
    }
}
