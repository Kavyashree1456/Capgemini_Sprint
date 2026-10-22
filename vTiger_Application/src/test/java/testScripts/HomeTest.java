package testScripts;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import genericUtility.BaseClass;
import genericUtility.ExcelUtility;
import objRepo.HomePage;

public class HomeTest extends BaseClass {
	@Test(groups="Functional")
	public void addModule() throws IOException
	{
		HomePage home = new HomePage(driver);

		home.clickAddWidget();
		home.clickModuleOption();
		
		ExcelUtility eu = new ExcelUtility();
        String windowTitle = eu.getDataFromExcel("Sheet1", 1, 0);
        home.enterWindowTitle(windowTitle);
        
        home.selectShowByIndex(3);
        home.selectModuleByIndex(0);
        home.selectFilterByIndex(0);
        
        home.clickSave();
	}
	


@Test(groups = { "Negative"})
public void verifyAddModuleWidgetWithoutTitle() throws IOException {

    HomePage home = new HomePage(driver);

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(20));

    home.clickAddWidget();
    home.clickModuleOption();

    home.enterWindowTitle("");

    // Confirm the title field is empty before saving
    String title = driver.findElement(By.id("stufftitle_id"))
                         .getAttribute("value");

    Assert.assertTrue(
            title == null || title.trim().isEmpty(),
            "Window Title should be empty for this negative test"
    );

    home.clickSave();

    Alert alert = new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.alertIsPresent());

    String message = alert.getText();

    Assert.assertEquals(
            message,
            "Please enter Window Title."
    );

    alert.accept();
    wu.takeScreenshot(driver, "WindowTitleMissing");
}


//@Test(groups = {"Functional", "Negative"})
//public void verifyAddModuleWidgetWithoutTitle1() throws Exception {
//
//    HomePage home = new HomePage(driver);
//
//    home.clickAddWidget();
//    home.clickModuleOption();
//
//    // Leave Window Title empty
//    home.enterWindowTitle("");
//
//    home.selectShowByIndex(3);
//    home.selectModuleByIndex(0);
//    home.selectFilterByIndex(0);
//
//    home.clickSave();
//
//    WebDriverWait wait =
//            new WebDriverWait(driver, Duration.ofSeconds(10));
//
//    // Wait until the JavaScript alert appears
//    Alert alert = wait.until(
//            ExpectedConditions.alertIsPresent()
//    );
//
//    // Read the alert message
//    String actualMessage = alert.getText();
//
//    Reporter.log("Error Message: " + actualMessage, true);
//
//    // Verify the expected message
//    Assert.assertEquals(
//            actualMessage,
//            "Please enter Window Title.",
//            "FAIL: Incorrect validation message"
//    );
//
//    // Accept the alert before performing any other browser action
//    alert.accept();
//
//    Reporter.log(
//            "PASS: Validation displayed for an empty Window Title",
//            true
//    );
//}


	@Test(groups="Functional")
    public void createNotebookFromExcel() throws IOException {
        HomePage home = new HomePage(driver);
        home.clickAddWidget();
        home.clickNotebookOption();
        ExcelUtility eu = new ExcelUtility();
        String windowTitle = eu.getDataFromExcel("Sheet1", 1, 0);
        home.enterWindowTitle(windowTitle);
        home.clickSave();
    }
	
	@Test(groups = {"Integration"})
	public void loginToHomeIntegrationTest() {

	    // Login is performed by BaseClass
	    HomePage home = new HomePage(driver);

	    Assert.assertTrue(
	        driver.getTitle().contains("vtiger"),
	        "Home page is not displayed after login"
	    );
	}
}
