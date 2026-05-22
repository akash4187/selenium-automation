package com.automation.tests;

import com.automation.pages.LoginPage;
import com.automation.utils.ExcelUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class LoginTest extends BaseTest {

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        List<Map<String, String>> testData = ExcelUtils.getTestData("logindata.xlsx", "Sheet1");
        Object[][] data = new Object[testData.size()][1];
        for (int i = 0; i < testData.size(); i++) {
            data[i][0] = testData.get(i);
        }
        return data;
    }

    @Test(dataProvider = "loginData")
    public void verifyLoginWithMultipleData(Map<String, String> testData) {
        String email = testData.get("email").trim();
        String password = testData.get("password").trim();
        String expectedResult = testData.get("expectedResult").trim();
        String expectedError = testData.get("errorMessage").trim();

        if (email.isEmpty() && password.isEmpty() && expectedResult.isEmpty()) {
            return;
        }

        LoginPage loginPage = homePage.clickSignupLogin();
        loginPage.login(email, password);

        if (expectedResult.equalsIgnoreCase("failure")) {
            String actualError = loginPage.getErrorMessage();
            Assert.assertEquals(actualError.trim(), expectedError.trim(),
                "Expected: '" + expectedError + "' but got: '" + actualError + "'");
        } else {
            loginPage.waitForLoginSuccess();
            homePage.clickLogout();
        }
    }
}
