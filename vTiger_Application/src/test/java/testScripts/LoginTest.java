
package testScripts;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import genericUtility.BaseClass;
import genericUtility.FileUtility;
import genericUtility.WebDriverUtility;
import objRepo.LoginPom;



public class LoginTest  {
    

	@Test(groups = "Negative")
    public void verifyInvalidPassword() throws Exception {

        WebDriverUtility wu = new WebDriverUtility();
        FileUtility fu = new FileUtility();

        WebDriver driver = wu.launchBrowser("chrome");

        try {
            driver.get(fu.getDataFromPropertyFile("url"));

            LoginPom l = new LoginPom(driver);

            l.login(
                fu.getDataFromPropertyFile("username"),
                "InvalidPassword123"
            );

            wu.takeScreenshot(driver, "InvalidPassword");

            String actualMessage = l.getErrorMessage();

            System.out.println("Actual error message: " + actualMessage);

            Assert.assertTrue(
                actualMessage.contains(
                    "You must specify a valid username and password."
                ),
                "Expected invalid-login error message was not displayed. Actual: "
                    + actualMessage
            );

        } finally {
            driver.quit();
        }
    }

    @Test(groups = "Negative")
    public void verifyInvalidUsername() throws Exception {

        WebDriverUtility wu = new WebDriverUtility();
        FileUtility fu = new FileUtility();

        WebDriver driver = wu.launchBrowser("chrome");

        try {
            driver.get(fu.getDataFromPropertyFile("url"));

            LoginPom l = new LoginPom(driver);

            l.login(
                "invalid_user123",
                fu.getDataFromPropertyFile("password")
            );

            wu.takeScreenshot(driver, "InvalidUsername");

            String actualMessage = l.getErrorMessage();

            System.out.println("Actual error message: " + actualMessage);

            Assert.assertTrue(
                actualMessage.contains(
                    "You must specify a valid username and password."
                ),
                "Expected invalid-login error message was not displayed. Actual: "
                    + actualMessage
            );

        } finally {
            driver.quit();
        }
    }
}

