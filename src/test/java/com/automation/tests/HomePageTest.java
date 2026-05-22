package com.automation.tests;

import com.automation.utils.Constants;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {

    @Test
    public void verifyHomePageTitle() {
        Assert.assertEquals(homePage.getTitle(), Constants.HOME_PAGE_TITLE);
    }

    @Test
    public void verifyNavigationToLoginPage() {
        homePage.clickSignupLogin();
        // Navigated successfully if no exception is thrown
    }

    @Test
    public void verifySubscriptionInHomePage() {
        // Step 3 — Verify home page is visible
        Assert.assertEquals(homePage.getTitle(), Constants.HOME_PAGE_TITLE,
            "Home page should be visible");

        // Step 4 — Scroll down to footer
        homePage.scrollToFooter();

        // Step 5 — Verify text 'SUBSCRIPTION'
        Assert.assertEquals(homePage.getSubscriptionHeadingText(), "SUBSCRIPTION",
            "Subscription heading should be visible in footer");

        // Step 6 — Enter email and click arrow button
        homePage.enterSubscriptionEmail("testsubscribe" + System.currentTimeMillis() + "@test.com");

        // Step 7 — Verify success message
        String successMsg = homePage.getSubscriptionSuccessMessage();
        Assert.assertEquals(successMsg, Constants.SUBSCRIPTION_SUCCESS_MSG,
            "Subscription success message mismatch");
    }
}
