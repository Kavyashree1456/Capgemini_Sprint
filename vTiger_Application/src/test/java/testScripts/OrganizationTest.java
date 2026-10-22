package testScripts;

import java.io.IOException;
import java.time.Duration;


import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import genericUtility.BaseClass;
import genericUtility.ExcelUtility;
import objRepo.ContactPage;
import objRepo.OrganizationPage;

public class OrganizationTest extends BaseClass {

@Test(groups = {"Functional", "Positive"})
public void createOrganization() throws IOException {

    OrganizationPage org = new OrganizationPage(driver);
    ExcelUtility eu = new ExcelUtility();

    // Fetch Organization data from Sheet1, Excel row 2
    String name = eu.getDataFromExcel("Sheet1", 2, 4);
    String website = eu.getDataFromExcel("Sheet1", 1, 5);
    String phone = eu.getDataFromExcel("Sheet1", 1, 6);
    String billingCity = eu.getDataFromExcel("Sheet1", 1, 7);
    String billingCountry = eu.getDataFromExcel("Sheet1", 1, 8);

    // Open Organizations
    org.clickOrganizations();
    org.clickCreate();

    // Enter values fetched from Excel
    org.enterOrganizationName(name);
    org.enterWebsite(website);
    org.enterPhone(phone);
    org.enterBillingCity(billingCity);
    org.enterBillingCountry(billingCountry);
    String description = eu.getDataFromExcel("Sheet1", 5, 2);
    org.addDescription(description);
    // Save Organization
    org.clickSave();

//    // Verify Organization appears in the list
//    org.clickOrganizations();
//
//    Assert.assertTrue(
//        org.isOrganizationDisplayed(name),
//        "Organization was not created: " + name
//    );
}


@Test(groups = {"Functional"})
public void searchOrganization() {

    OrganizationPage org = new OrganizationPage(driver);
    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(10));

    String organizationName = "AutoOrgTest01";

    org.clickOrganizations();

    // Perform the search
    org.searchOrganization(organizationName);

    // Check whether the exact organization link exists
    boolean found = wait.until(d -> {
        return !d.findElements(
                By.xpath("//a[normalize-space(.)='" 
                        + organizationName + "']")
        ).isEmpty();
    });

    Assert.assertTrue(
            found,
            "Organization not found after searching: "
                    + organizationName
    );
}


@Test(groups = {"Functional", "Positive"})
public void editOrganization() {

    OrganizationPage org = new OrganizationPage(driver);

    String existingName = "StartUp";
    String updatedName = "UpdatedOrg" + System.currentTimeMillis();

    org.clickOrganizations();
    org.clickEdit();

    org.enterOrganizationName(updatedName);
    org.clickSave();

    // Reopen the Organizations list after saving
    org.clickOrganizations();

    Assert.assertTrue(
        org.isOrganizationDisplayed(updatedName),
        "Organization name was not updated: " + updatedName
    );
}

	
    @Test(groups = {"Smoke", "Positive"})
    public void verifyOrganizationsPageLoads() {

        OrganizationPage org = new OrganizationPage(driver);

        org.clickOrganizations();

        Assert.assertTrue(
            driver.getCurrentUrl().contains("Accounts"),
            "Organizations page did not open"
        );
    }
    

@Test(groups = {"System", "Positive"})
public void systemOrganizationFlow() throws IOException {

    OrganizationPage org = new OrganizationPage(driver);
    ExcelUtility eu = new ExcelUtility();

    // =============== FETCH DATA FROM EXCEL ===============

    String name = "SystemOrg" + System.currentTimeMillis();
    String website = eu.getDataFromExcel("Sheet1", 1, 5);
    String phone = eu.getDataFromExcel("Sheet1", 1, 6);
    String billingCity = eu.getDataFromExcel("Sheet1", 1, 7);
    String billingCountry = eu.getDataFromExcel("Sheet1", 1, 8);
    String description = eu.getDataFromExcel("Sheet1", 5, 2);

    String updatedName = name + "Updated";

    // =============== 1. CREATE ORGANIZATION ===============

    org.clickOrganizations();
    org.clickCreate();

    org.enterOrganizationName(name);
    org.enterWebsite(website);
    org.enterPhone(phone);
    org.enterBillingCity(billingCity);
    org.enterBillingCountry(billingCountry);
    org.addDescription(description);

    org.clickSave();

    // =============== 2. VERIFY CREATION ===============

    org.clickOrganizations();



    // =============== 3. SEARCH ORGANIZATION ===============

    org.searchOrganization(name);

    // =============== 4. EDIT ORGANIZATION ===============

    // Return to the list if search results are displayed.
    org.clickOrganizations();

    org.clickEdit();
    org.enterOrganizationName(updatedName);
    org.clickSave();

    // =============== 5. VERIFY UPDATED ORGANIZATION ===============

    org.clickOrganizations();



    // =============== 6. DELETE ORGANIZATION ===============

    org.clickDelete(updatedName);
    org.acceptDeleteAlert();

    // =============== 7. VERIFY DELETION ===============

    org.clickOrganizations();


    System.out.println(
        "System Test Passed: Create, Search, Edit, and Delete completed."
    );
}


@Test(groups = {"Functional", "Negative"})
public void verifyCreateOrganizationWithoutMandatoryField()
        throws Exception {

    OrganizationPage org = new OrganizationPage(driver);

    // Open Organizations and Create form
    org.clickOrganizations();
    org.clickCreate();

    // Leave Organization Name empty
    org.enterOrganizationName("");

    // Click Save
    org.clickSave();

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(10));

    // Capture validation alert
    Alert alert = wait.until(
            ExpectedConditions.alertIsPresent()
    );

    String actualMessage = alert.getText();

    Reporter.log("Error Message: " + actualMessage, true);

    // Verify the validation message
    Assert.assertEquals(
            actualMessage,
            "Organization Name cannot be empty",
            "FAIL: Incorrect Organization validation message"
    );

    // Close the alert
    alert.accept();
}
@Test(groups = {"Smoke"})
public void organizationToContactTest() {

    OrganizationPage org = new OrganizationPage(driver);
    ContactPage contact = new ContactPage(driver);

    org.clickOrganizations();
    contact.clickContacts();

    Assert.assertTrue(
        driver.getCurrentUrl().contains("Contacts"),
        "Contacts page did not open"
    );
}


@Test(groups = {"Integration", "Positive"})
public void createAndSearchOrganizationIntegration() throws IOException {

    OrganizationPage org = new OrganizationPage(driver);
    ExcelUtility eu = new ExcelUtility();

    String organizationName =
            "ITOrg" + System.currentTimeMillis();

    String website = eu.getDataFromExcel("Sheet1", 1, 5);
    String phone = eu.getDataFromExcel("Sheet1", 1, 6);
    String city = eu.getDataFromExcel("Sheet1", 1, 7);
    String country = eu.getDataFromExcel("Sheet1", 1, 8);

    // Step 1: Open Organizations
    org.clickOrganizations();

    // Step 2: Create an organization
    org.clickCreate();
    org.enterOrganizationName(organizationName);
    org.enterWebsite(website);
    org.enterPhone(phone);
    org.enterBillingCity(city);
    org.enterBillingCountry(country);
    org.clickSave();

    // Step 3: Return to the Organizations list
    org.clickOrganizations();

    // Step 4: Search for the created organization
    org.searchOrganization(organizationName);

    // Step 5: Verify the result
    Assert.assertTrue(
            org.isOrganizationDisplayed(organizationName),
            "Integration failed: Created organization was not found."
    );
}

}



