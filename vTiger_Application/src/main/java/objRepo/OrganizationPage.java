
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

public class OrganizationPage {

    WebDriver driver;

    public OrganizationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // =============== ORGANIZATIONS MODULE ===============

    @FindBy(xpath = "//a[normalize-space()='Organizations']")
    private WebElement organizationsLink;


    public void clickOrganizations() {
        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        By dialog = By.cssSelector("div[id^='__vtigerjs_dialogbox_']");

        List<WebElement> dialogs = driver.findElements(dialog);

        for (WebElement element : dialogs) {
            if (element.isDisplayed()) {
                wait.until(ExpectedConditions.invisibilityOf(element));
            }
        }

        WebElement organizationsLink = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[normalize-space()='Organizations']")));

        organizationsLink.click();
    }

    // =============== CREATE ORGANIZATION ===============
    // Verify this locator against the actual Create Organization button.

//    @FindBy(xpath = "//input[@value='Create' or @value='New Organization'] | //a[normalize-space()='Create']")
//    private WebElement createButton;
//
//    public void clickCreate() {
//        createButton.click();
//    }
    @FindBy(xpath = "//img[@alt='Create Organization...']")
    private WebElement createOrganizationButton;

    public void clickCreate() {
        createOrganizationButton.click();
    }
    // =============== ORGANIZATION NAME ===============
    // Verify the name attribute on the Create/Edit form.

    @FindBy(name = "accountname")
    private WebElement organizationName;

    public void enterOrganizationName(String name) {
        organizationName.clear();
        organizationName.sendKeys(name);
    }

    // =============== WEBSITE ===============

    @FindBy(name = "website")
    private WebElement website;

    public void enterWebsite(String url) {
        website.clear();
        website.sendKeys(url);
    }

    // =============== PHONE ===============

    @FindBy(name = "phone")
    private WebElement phone;

    public void enterPhone(String number) {
        phone.clear();
        phone.sendKeys(number);
    }

    // =============== BILLING CITY ===============

    @FindBy(name = "bill_city")
    private WebElement billingCity;

    public void enterBillingCity(String city) {
        billingCity.clear();
        billingCity.sendKeys(city);
    }


@FindBy(name = "bill_country")
private WebElement billingCountry;

public void enterBillingCountry(String country) {
    billingCountry.clear();
    billingCountry.sendKeys(country);
}

//Description
@FindBy(name = "description")
private WebElement description;
public void addDescription(String Description) {
	description.sendKeys(Description);
}
//    // =============== SAVE ===============
//    // Verify the locator against the actual form.
//
//    @FindBy(name = "button")
//    private WebElement saveButton;
//
//    public void clickSave() {
//        saveButton.click();
//    }
//=============== SAVE ===============

@FindBy(xpath = "//input[@class = 'crmbutton small save']")
private WebElement saveButton;

public void clickSave() {
 saveButton.click();
}

    // =============== SEARCH ===============
    // The screenshot does not show the search input HTML.
    // Replace this locator after inspecting the search field.

//    @FindBy(name = "search_text")
//    private WebElement searchText;
//    public void searchOrganization(String name) {
//        searchText.clear();
//        searchText.sendKeys(name);
//    }
//    
//    @FindBy(name = "submit")
//    private WebElement searchButton;
//    public void clickSearch() {
//    	searchButton.click();
//    }
    

    // =============== SEARCH ===============

    @FindBy(name = "search_text")
    private WebElement searchText;

    // Replace this XPath with the actual locator
    // for the search dropdown after inspecting it.
    @FindBy(xpath = "//select[option[normalize-space()='Organization Name']]")
    private WebElement searchFieldDropdown;

    @FindBy(name = "submit")
    private WebElement searchButton;

    public void searchOrganization(String name) {

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
                        By.xpath("//select[option[normalize-space()='Organization Name']]")
                )
        );

        new Select(dropdown).selectByVisibleText("Organization Name");

        WebElement searchButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.name("submit")
                )
        );

        searchButton.click();
    }


    // =============== VERIFY ORGANIZATION ===============


public boolean isOrganizationDisplayed(String name) {

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(10));

    try {
        return wait.until(d -> {
            List<WebElement> records = d.findElements(
                By.xpath(
                    "//*[normalize-space(text())='"
                    + name + "']"
                )
            );

            for (WebElement record : records) {
                if (record.isDisplayed()) {
                    return true;
                }
            }

            return false;
        });

    } catch (org.openqa.selenium.TimeoutException e) {
        return false;
    }
}

    // =============== OPEN ORGANIZATION ===============

    public void openOrganization(String name) {
        driver.findElement(
            By.xpath("//a[normalize-space()='" + name + "']")
        ).click();
    }

    // =============== EDIT ORGANIZATION ===============
    @FindBy(xpath = "//a[text()='edit']")
    private WebElement editBtn;
    public void clickEdit() {
        editBtn.click();
    }
    

    // =============== DELETE ORGANIZATION ===============

    public void clickDelete(String name) {
        driver.findElement(
            By.xpath(
                "//tr[.//a[normalize-space()='" + name + "']]"
                + "//a[normalize-space()='del']"
            )
        ).click();
    }

    public String acceptDeleteAlert() {
        Alert alert = driver.switchTo().alert();
        String message = alert.getText();
        alert.accept();
        return message;
    }

    // =============== CHECKBOX ===============

    public void selectOrganizationCheckbox(String name) {
        driver.findElement(
            By.xpath(
                "//tr[.//a[normalize-space()='" + name + "']]"
                + "//input[@type='checkbox']"
            )
        ).click();
    }

    // =============== MASS EDIT ===============

    @FindBy(xpath = "//input[@value='Mass Edit']")
    private WebElement massEditButton;

    public void clickMassEdit() {
        massEditButton.click();
    }

    // =============== EXPORT ===============

    @FindBy(xpath = "//input[contains(@value,'Export')]")
    private WebElement exportButton;

    public void clickExport() {
        exportButton.click();
    }

    // =============== FILTERS ===============

    @FindBy(xpath = "//select[option[normalize-space()='All']]")
    private WebElement filtersDropdown;

    public void selectFilterByIndex(int index) {
        new Select(filtersDropdown).selectByIndex(index);
    }
}

