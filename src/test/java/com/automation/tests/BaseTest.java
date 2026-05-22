package com.automation.tests;

import com.automation.base.BaseDriver;
import com.automation.pages.HomePage;
import com.automation.utils.ExtentReportManager;
import com.automation.utils.ScreenshotUtils;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.lang.reflect.Method;

public class BaseTest {

    private static final Logger log = LogManager.getLogger(BaseTest.class);
    protected HomePage homePage;

    @BeforeClass
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {
        log.info("Running tests on browser: {}", browser);
        BaseDriver.initDriver(browser);
        homePage = new HomePage();
        homePage.navigateTo();
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        log.info("Starting test: {}", method.getName());
        ExtentReportManager.setTest(
            ExtentReportManager.getInstance().createTest(method.getName())
        );
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            log.error("Test FAILED: {}", result.getName());
            String screenshotPath = ScreenshotUtils.captureScreenshot(result.getName());
            ExtentReportManager.getTest()
                .fail(result.getThrowable(),
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            log.info("Test PASSED: {}", result.getName());
            ExtentReportManager.getTest().log(Status.PASS, "Test passed");
        } else {
            log.warn("Test SKIPPED: {}", result.getName());
            ExtentReportManager.getTest().log(Status.SKIP, "Test skipped");
        }
        homePage.navigateTo();
        BaseDriver.getDriver().manage().deleteAllCookies();
        BaseDriver.getDriver().navigate().refresh();
    }

    @AfterClass
    public void tearDown() {
        BaseDriver.quitDriver();
    }

    @AfterSuite
    public void afterSuite() {
        ExtentReportManager.flush();
        log.info("Extent Report generated at: test-output/ExtentReport.html");
    }
}
