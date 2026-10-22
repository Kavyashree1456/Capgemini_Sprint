
package objRepo;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ContactPage {

    WebDriver driver;

    public ContactPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // =============== CONTACTS MODULE ===============

    @FindBy(xpath = "//a[normalize-space()='Contacts']")
    private WebElement contactsLink;


public void clickContacts() {

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(15));

    WebElement contactsLink = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[normalize-space()='Contacts']")
            )
    );

    contactsLink.click();
}


    // =============== CREATE CONTACT ===============

    @FindBy(xpath = "//img[@alt='Create Contact...']")
    private WebElement createContactButton;

    public void clickCreate() {
        createContactButton.click();
    }

    @FindBy(name = "firstname")
    private WebElement firstName;

    public void enterFirstName(String name) {
        firstName.clear();
        firstName.sendKeys(name);
    }

    @FindBy(name = "lastname")
    private WebElement lastName;

    public void enterLastName(String name) {
        lastName.clear();
        lastName.sendKeys(name);
    }

    @FindBy(name = "title")
    private WebElement title;

    public void enterTitle(String value) {
        title.clear();
        title.sendKeys(value);
    }

    @FindBy(name = "email")
    private WebElement email;

    public void enterEmail(String value) {
        email.clear();
        email.sendKeys(value);
    }

    @FindBy(name = "phone")
    private WebElement phone;

    public void enterPhone(String value) {
        phone.clear();
        phone.sendKeys(value);
    }

    @FindBy(name = "mobile")
    private WebElement mobile;

    public void enterMobile(String value) {
        mobile.clear();
        mobile.sendKeys(value);
    }

    @FindBy(name = "department")
    private WebElement department;

    public void enterDepartment(String value) {
        department.clear();
        department.sendKeys(value);
    }

    @FindBy(name = "mailingcity")
    private WebElement mailingCity;

    public void enterMailingCity(String value) {
        mailingCity.clear();
        mailingCity.sendKeys(value);
    }

    @FindBy(name = "mailingcountry")
    private WebElement mailingCountry;

    public void enterMailingCountry(String value) {
        mailingCountry.clear();
        mailingCountry.sendKeys(value);
    }

    @FindBy(name = "description")
    private WebElement description;

    public void enterDescription(String value) {
        description.clear();
        description.sendKeys(value);
    }

    // Verify the Save button locator using Inspect.
    @FindBy(name = "button")
    private WebElement saveButton;

    public void clickSave() {
        saveButton.click();
    }

    // =============== SEARCH CONTACT ===============


    // =============== SEARCH CONTACT ===============

    @FindBy(name = "search_text")
    private WebElement searchText;

    @FindBy(xpath = "//select[option[normalize-space()='Last Name']]")
    private WebElement searchFieldDropdown;

    @FindBy(name = "submit")
    private WebElement searchButton;
//
////    public void searchContact(String name) {
////        searchText.clear();
////        searchText.sendKeys(name);
////
////        new Select(searchFieldDropdown)
////                .selectByVisibleText("Last Name");
////
////        searchButton.click();
////    }
////
////
////
////    // =============== VERIFY CONTACT ===============
////
////
////
////
////public boolean isContactDisplayed(String firstName, String lastName) {
////
////    String rowXpath =
////        "//table//tr[.//td[contains(normalize-space(.),'" + firstName + "')]"
////      + " and .//td[contains(normalize-space(.),'" + lastName + "')]]";
////
////    try {
////        List<WebElement> rows = driver.findElements(By.xpath(rowXpath));
////
////        for (int i = 0; i < rows.size(); i++) {
////            // Re-fetch the row because the page may have refreshed.
////            List<WebElement> currentRows =
////                driver.findElements(By.xpath(rowXpath));
////
////            if (i < currentRows.size()
////                    && currentRows.get(i).isDisplayed()) {
////                return true;
////            }
////        }
////    } catch (org.openqa.selenium.StaleElementReferenceException e) {
////        // The page changed while checking; report not found for this check.
////    }
////
////    return false;
////}
//
//
public void searchContact(String name) {

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(10));

    WebElement searchBox = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                    By.name("search_text")
            )
    );

    searchBox.clear();
    searchBox.sendKeys(name);

    WebElement dropdown = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//select[option[normalize-space()='Last Name']]")
            )
    );

    new Select(dropdown).selectByVisibleText("Last Name");

    WebElement     searchButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.name("submit")
            )
    );

    searchButton.click();
}

public boolean isContactDisplayed(String firstName, String lastName) {

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(5));

    String rowXpath =
            "//tr[.//*[contains(normalize-space(.),'" + firstName + "')]"
          + " and .//*[contains(normalize-space(.),'" + lastName + "')]]";

    try {
        return wait.until(d -> {
            List<WebElement> rows =
                    d.findElements(By.xpath(rowXpath));

            for (WebElement row : rows) {
                if (row.isDisplayed()) {
                    return true;
                }
            }
            return false;
        });
    } catch (org.openqa.selenium.TimeoutException e) {
        return false;
    }
}


    // =============== EDIT CONTACT ===============

	@FindBy(xpath = "//a[text()='edit']")
	private WebElement editBtn;
	public void clickEdit() {
    editBtn.click();
	}


    // =============== DELETE CONTACT ===============

    @FindBy(xpath = "//a[text()='del']")
    private List<WebElement> deleteLinks;




public void clickDelete(String firstName, String lastName) {

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(10));

    String rowXpath =
            "//tr[.//td[contains(normalize-space(.),'" + firstName + "')]"
          + " and .//td[contains(normalize-space(.),'" + lastName + "')]]";

    WebElement row = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                    By.xpath(rowXpath)
            )
    );

    WebElement deleteLink = row.findElement(
            By.xpath(".//a[normalize-space()='del']")
    );

    wait.until(ExpectedConditions.elementToBeClickable(deleteLink))
        .click();

    Alert alert = wait.until(
            ExpectedConditions.alertIsPresent()
    );

    String message = alert.getText();
    System.out.println("Delete confirmation: " + message);
    alert.accept();

    wait.until(ExpectedConditions.invisibilityOfElementLocated(
            By.xpath(rowXpath)
    ));
}



    // Separate helper method — keep it outside clickDelete()
    private WebElement getContactRow(String firstName, String lastName) {

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        String rowXpath =
                "//tr[.//td[contains(normalize-space(.),'" + firstName + "')]"
              + " and .//td[contains(normalize-space(.),'" + lastName + "')]]";

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(rowXpath)));
    }


    // =============== DELETE ALERT ===============

    public String acceptDeleteAlert() {
        Alert alert = driver.switchTo().alert();
        String message = alert.getText();
        alert.accept();
        return message;
    }

    // =============== SELECT CONTACT ===============

    @FindBy(xpath = "//tr//input[@type='checkbox']")
    private List<WebElement> contactCheckboxes;

    public void selectContactCheckbox(String firstName, String lastName) {
        String fullName = (firstName + " " + lastName).trim();

        for (WebElement checkbox : contactCheckboxes) {
            WebElement row = checkbox.findElement(
                    By.xpath("./ancestor::tr")
            );

            if (row.getText().contains(fullName)) {
                checkbox.click();
                return;
            }
        }

        throw new RuntimeException(
                "Checkbox not found for contact: " + fullName
        );
    }


    // =============== ORGANIZATION ASSOCIATION ===============

    @FindBy(name = "account_name")
    private WebElement organizationName;

    @FindBy(xpath = "//img[contains(@src,'search') and contains(@onclick,'account')]")
    private WebElement organizationLookupButton;

    public void clickOrganizationLookup() {
        organizationLookupButton.click();
    }

    public void enterOrganizationName(String name) {
        organizationName.clear();
        organizationName.sendKeys(name);
    }

}
//

