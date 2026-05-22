package com.automation.utils;

public class Constants {

    private Constants() {}

    // Timeouts
    public static final int EXPLICIT_WAIT = 15;
    public static final int PAGE_LOAD_WAIT = 30;

    // Report
    public static final String REPORT_PATH = "test-output/ExtentReport.html";
    public static final String SCREENSHOT_PATH = "test-output/screenshots/";
    public static final String REPORT_TITLE = "Automation Exercise Test Report";
    public static final String REPORT_NAME = "Automation Exercise";

    // Expected Titles
    public static final String HOME_PAGE_TITLE = "Automation Exercise";
    public static final String LOGIN_PAGE_TITLE = "Automation Exercise - Signup / Login";
    public static final String PRODUCTS_PAGE_TITLE = "Automation Exercise - All Products";
    public static final String SIGNUP_PAGE_TITLE = "Automation Exercise - Signup / Login";
    public static final String ACCOUNT_CREATED_TITLE = "Automation Exercise - Account Created";
    public static final String CART_PAGE_TITLE = "Automation Exercise - Checkout";

    // Order Test Login Credentials
    public static final String ORDER_TEST_EMAIL = "akash@123.com";
    public static final String ORDER_TEST_PASSWORD = "kpPQur@jPgGXY62";

    // Order Flow Titles & Messages
    public static final String CHECKOUT_PAGE_TITLE = "Automation Exercise - Checkout";
    public static final String PAYMENT_PAGE_TITLE = "Automation Exercise - Payment";
    public static final String ORDER_PLACED_MSG = "ORDER PLACED!";

    // Payment Details
    public static final String CARD_NAME = "Test User";
    public static final String CARD_NUMBER = "4111111111111111";
    public static final String CARD_CVC = "123";
    public static final String CARD_EXPIRY_MONTH = "12";
    public static final String CARD_EXPIRY_YEAR = "2027";

    // Error Messages
    public static final String INVALID_LOGIN_ERROR = "Your email or password is incorrect!";
    public static final String SIGNUP_EXISTING_EMAIL_ERROR = "Email Address already exist!";
    public static final String ACCOUNT_CREATED_MSG = "ACCOUNT CREATED!";

    // Contact Us
    public static final String CONTACT_NAME = "Test User";
    public static final String CONTACT_EMAIL = "testcontact@test.com";
    public static final String CONTACT_SUBJECT = "Test Subject";
    public static final String CONTACT_MESSAGE = "This is a test message for automation.";
    public static final String CONTACT_SUCCESS_MSG = "Success! Your details have been submitted successfully.";

    // Subscription
    public static final String SUBSCRIPTION_SUCCESS_MSG = "You have been successfully subscribed!";

    // Account Deleted
    public static final String ACCOUNT_DELETED_MSG = "ACCOUNT DELETED!";
    public static final String ORDER_SUCCESS_MSG = "Congratulations! Your order has been confirmed!";
}
