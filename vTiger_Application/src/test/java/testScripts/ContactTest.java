package testScripts;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import genericUtility.BaseClass;
import genericUtility.ExcelUtility;
import objRepo.ContactPage;
//
public class ContactTest extends BaseClass{
	@Test(groups = {"Smoke",  "Positive"})
    public void verifyContactsPageLoads() {

        ContactPage contacts = new ContactPage(driver);

        contacts.clickContacts();

        Assert.assertTrue(
            driver.getCurrentUrl().contains("Contacts"),
            "Contacts page did not open"
        );
    }


@Test(groups = {"Functional", "Positive"})
public void createContact() throws IOException {

    ContactPage contact = new ContactPage(driver);
    ExcelUtility eu = new ExcelUtility();

    // Fetch Contact data from Sheet1
    String firstName = eu.getDataFromExcel("Sheet1", 1, 9);
    String lastName = eu.getDataFromExcel("Sheet1", 1, 10);
    String title = eu.getDataFromExcel("Sheet1", 1, 11);
    String department = eu.getDataFromExcel("Sheet1", 1, 12);
    String email = eu.getDataFromExcel("Sheet1", 1, 13);
    String phone = eu.getDataFromExcel("Sheet1", 1, 14);
    String mobile = eu.getDataFromExcel("Sheet1", 1, 15);

    // Open Contacts
    contact.clickContacts();
    contact.clickCreate();

    // Enter values fetched from Excel
    contact.enterFirstName(firstName);
    contact.enterLastName(lastName);
    contact.enterTitle(title);
    contact.enterDepartment(department);
    contact.enterEmail(email);
    contact.enterPhone(phone);
    contact.enterMobile(mobile);

    // Save Contact
    contact.clickSave();

}





@Test(groups = { "Negative"})
public void verifyCreateContactWithoutMandatoryField() throws IOException {

    ContactPage contact = new ContactPage(driver);

    contact.clickContacts();
    contact.clickCreate();

    // Enter First Name but leave mandatory Last Name empty
    contact.enterFirstName("TestFirst");
    contact.enterLastName("");

    contact.clickSave();

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(10));

    // Wait for the validation alert
    Alert alert = wait.until(
            ExpectedConditions.alertIsPresent()
    );

    // Read the message before accepting the alert
    String actualMessage = alert.getText();

    Reporter.log("Error Message: " + actualMessage, true);

    // Verify the expected validation message
    Assert.assertEquals(
            actualMessage,
            "Last Name cannot be empty",
            "Incorrect validation message"
    );

    // IMPORTANT: Close the alert before screenshot/browser actions
    alert.accept();

    // Take screenshot only after dismissing the alert
    wu.takeScreenshot(driver, "ContactMissingLastName");
}




@Test(groups = {"System", "Positive"})
public void verifyContactDetailsPersistAfterRefresh() throws IOException {

    ContactPage contact = new ContactPage(driver);
    ExcelUtility eu = new ExcelUtility();

    // Fetch data from your existing Excel sheet
    String firstName = eu.getDataFromExcel("Sheet1", 1, 9);
    String lastName = eu.getDataFromExcel("Sheet1", 1, 10);

    // Generate a unique email for every execution
    String email = "contact" + System.currentTimeMillis() + "@example.com";

    // 1. Navigate to Contacts and create a contact
    contact.clickContacts();
    contact.clickCreate();

    contact.enterFirstName(firstName);
    contact.enterLastName(lastName);
    contact.enterEmail(email);

    contact.clickSave();

    // 2. Verify contact after saving
    contact.clickContacts();
    contact.searchContact(lastName);

    Assert.assertTrue(
        contact.isContactDisplayed(firstName, lastName),
        "Contact was not found after saving: " + firstName + " " + lastName
    );

    // 3. Refresh the page
    driver.navigate().refresh();

    // 4. Search again after refresh and verify persistence
    contact.searchContact(lastName);

    Assert.assertTrue(
        contact.isContactDisplayed(firstName, lastName),
        "Contact details did not persist after refresh: "
            + firstName + " " + lastName
    );

    Reporter.log(
        "PASS: Contact details persisted after refresh: " + email,
        true
    );
}


//@Test(groups = {"Functional", "Positive"})
//public void verifyEditContact() throws IOException {
//
//    ContactPage contact = new ContactPage(driver);
//    ExcelUtility eu = new ExcelUtility();
//
//    String firstName = eu.getDataFromExcel("Sheet1", 1, 9)
//            + System.currentTimeMillis();
//    String lastName = eu.getDataFromExcel("Sheet1", 1, 10);
//    String email = "edit" + System.currentTimeMillis() + "@example.com";
//    String updatedLastName = lastName + "Updated";
//
//    // Create test contact
//    contact.clickContacts();
//    contact.clickCreate();
//    contact.enterFirstName(firstName);
//    contact.enterLastName(lastName);
//    contact.enterEmail(email);
//    contact.clickSave();
//
//    // Find the contact
//    contact.clickContacts();
//    contact.searchContact(lastName);
//
////    Assert.assertTrue(
////        contact.isContactDisplayed(firstName, lastName),
////        "Contact not found before editing"
////    );
//
//    // Edit the contact
//    contact.clickEdit();
//    contact.enterLastName(updatedLastName);
//    contact.clickSave();
//
//    // Verify the updated contact
//    contact.clickContacts();
//    contact.searchContact(updatedLastName);
//
////    Assert.assertTrue(
////        contact.isContactDisplayed(firstName, updatedLastName),
////        "Contact update failed"
////    );
//
//    Reporter.log("PASS: Contact edited successfully", true);
//}




@Test(groups = {"Integration", "Positive"})
public void createAndSearchContactIntegration() {

    ContactPage contact = new ContactPage(driver);

    String firstName = "ITFirst" + System.currentTimeMillis();
    String lastName = "ITLast";

    // Step 1: Open Contacts
    contact.clickContacts();

    // Step 2: Create a new contact
    contact.clickCreate();
    contact.enterFirstName(firstName);
    contact.enterLastName(lastName);
    contact.clickSave();

    // Step 3: Open Contacts again
    contact.clickContacts();

    // Step 4: Search using the last name
    contact.searchContact(lastName);

    // Step 5: Verify the saved contact
    Assert.assertTrue(
            contact.isContactDisplayed(firstName, lastName),
            "Integration failed: Created contact was not found."
    );
}


}


