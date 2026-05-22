package com.automation.tests;

import com.automation.utils.Constants;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CategoryTest extends BaseTest {

    @Test
    public void verifyViewCategoryProducts() {
        // Step 3 — Verify that categories are visible on left side bar
        Assert.assertTrue(homePage.isCategorySidebarVisible(),
            "Categories should be visible on left side bar");

        // Step 4 & 5 — Click on 'Women' category, then click on 'Tops' sub-category
        homePage.selectCategory("Women", "Tops");

        // Step 6 — Verify category page is displayed and confirm text
        String categoryTitle = homePage.getCategoryPageTitle();
        Assert.assertTrue(categoryTitle.toUpperCase().contains("WOMEN - TOPS PRODUCTS"),
            "Category page should display 'WOMEN - TOPS PRODUCTS' but got: " + categoryTitle);

        // Step 7 — On left side bar, click on any sub-category link of 'Men' category
        homePage.selectCategory("Men", "Tshirts");

        // Step 8 — Verify user is navigated to that category page
        String menCategoryTitle = homePage.getCategoryPageTitle();
        Assert.assertTrue(menCategoryTitle.toUpperCase().contains("MEN - TSHIRTS PRODUCTS"),
            "Should navigate to Men category page but got: " + menCategoryTitle);
    }
}
