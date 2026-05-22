package com.automation.base;

import com.automation.utils.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseDriver {

    private static final Logger log = LogManager.getLogger(BaseDriver.class);
    private static WebDriver driver;
    private static Properties properties;

    static {
        properties = new Properties();
        try (InputStream input = BaseDriver.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static void initDriver(String browser) {
        if (browser == null || browser.isEmpty()) {
            browser = properties.getProperty("browser", "chrome");
        }
        log.info("Launching browser: {}", browser);

        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--disable-extensions", "--disable-popup-blocking",
                    "--disable-notifications", "--no-sandbox", "--disable-dev-shm-usage");
                // Run headless in CI environments (set via -Dheadless=true or CI env variable)
                String headless = System.getProperty("headless", System.getenv("CI") != null ? "true" : "false");
                if ("true".equalsIgnoreCase(headless)) {
                    log.info("Running in headless mode");
                    options.addArguments("--headless", "--window-size=1920,1080", "--disable-gpu");
                }
                // Block ads and popups that interfere with test execution
                java.util.Map<String, Object> prefs = new java.util.HashMap<>();
                prefs.put("profile.managed_default_content_settings.popups", 2);
                prefs.put("profile.managed_default_content_settings.ads", 2);
                options.setExperimentalOption("prefs", prefs);
                options.addArguments("--host-resolver-rules=MAP googleads.g.doubleclick.net 127.0.0.1, MAP pagead2.googlesyndication.com 127.0.0.1, MAP tpc.googlesyndication.com 127.0.0.1, MAP google-analytics.com 127.0.0.1");
                driver = new ChromeDriver(options);
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Constants.PAGE_LOAD_WAIT, TimeUnit.SECONDS);
        log.info("Browser launched successfully");
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static String getBaseUrl() {
        return properties.getProperty("base.url");
    }

    public static void quitDriver() {
        if (driver != null) {
            log.info("Closing browser");
            driver.quit();
            driver = null;
        }
    }
}
