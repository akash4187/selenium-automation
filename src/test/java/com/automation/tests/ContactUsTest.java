package com.automation.tests;

import com.automation.pages.ContactUsPage;
import com.automation.utils.Constants;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ContactUsTest extends BaseTest {

    @Test
    public void verifyContactUsFormSubmission() {
        // Step 1 — Navigate to Contact Us page
        ContactUsPage contactUsPage = homePage.clickContactUs();

        // Step 2 — Verify 'Get In Touch' heading is visible
        Assert.assertTrue(contactUsPage.isGetInTouchVisible(),
            "'Get In Touch' heading should be visible on Contact Us page");

        // Step 3 — Fill the contact form
        contactUsPage.fillContactForm(
            Constants.CONTACT_NAME,
            Constants.CONTACT_EMAIL,
            Constants.CONTACT_SUBJECT,
            Constants.CONTACT_MESSAGE
        );

        // Step 4 — Submit the form
        contactUsPage.clickSubmit();

        // Step 5 — Accept the browser alert
        contactUsPage.acceptAlert();

        // Step 6 — Verify success message
        String successMsg = contactUsPage.getSuccessMessage();
        Assert.assertEquals(successMsg, Constants.CONTACT_SUCCESS_MSG,
            "Contact form success message mismatch");
    }

    @Test
    public void verifyContactUsWithFileUpload() {
        // Step 1 — Navigate to Contact Us page
        ContactUsPage contactUsPage = homePage.clickContactUs();

        // Step 2 — Verify 'Get In Touch' heading is visible
        Assert.assertTrue(contactUsPage.isGetInTouchVisible(),
            "'Get In Touch' heading should be visible on Contact Us page");

        // Step 3 — Fill the contact form
        contactUsPage.fillContactForm(
            Constants.CONTACT_NAME,
            Constants.CONTACT_EMAIL,
            "File Upload Test",
            "Testing contact form with file attachment"
        );

        // Step 4 — Upload a file
        String filePath = System.getProperty("user.dir") + "/pom.xml";
        contactUsPage.uploadFile(filePath);

        // Step 5 — Submit the form
        contactUsPage.clickSubmit();

        // Step 6 — Accept the browser alert
        contactUsPage.acceptAlert();

        // Step 7 — Verify success message
        String successMsg = contactUsPage.getSuccessMessage();
        Assert.assertEquals(successMsg, Constants.CONTACT_SUCCESS_MSG,
            "Contact form success message mismatch");
    }
}
