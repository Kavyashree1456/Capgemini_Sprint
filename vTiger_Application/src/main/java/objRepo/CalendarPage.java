package objRepo;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import org.openqa.selenium.support.ui.Select;

public class CalendarPage {
	WebDriver driver;
	public CalendarPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

}
	@FindBy(xpath="//a[normalize-space()='Calendar']")
	private WebElement calendar;
	public void clickCalendar() {
		calendar.click();;
	}	
	
	@FindBy(xpath = "//td[contains(@class,'calAddButton')]")
	private WebElement addbtn;
	public void clickAdd() {
		addbtn.click();
	}
	
	@FindBy(id = "addmeeting")
	private WebElement addmeeting;
	public void clickMeeting() {
		addmeeting.click();
	}
	
	// Event Name
    @FindBy(name = "subject")
    private WebElement eventName;
    public void enterEvent(String EventName) {
    	eventName.clear();
    	eventName.sendKeys(EventName);
    }

    // Description
    @FindBy(name = "description")
    private WebElement description;
    public void addDescription(String Description) {
    	description.sendKeys(Description);
    }

    // Location
    @FindBy(name = "location")
    private WebElement location;
    public void addLocation(String Location) {
    	location.sendKeys(Location);
    }
    
 // =============== STATUS ===============

    @FindBy(xpath = "//select[option[normalize-space()='Planned'] and option[normalize-space()='Held']]")
    private WebElement statusDropdown;

    public void selectStatusByIndex(int index) {
        new Select(statusDropdown).selectByIndex(index);
    }

    // =============== ASSIGNED TO ===============

    @FindBy(name = "assigned_user_id")
    private WebElement assignedToDropdown;

    public void selectAssignedToByIndex(int index) {
        new Select(assignedToDropdown).selectByIndex(index);
    }

    // =============== PRIORITY ===============

    @FindBy(xpath = "//select[option[normalize-space()='High'] and option[normalize-space()='Medium']]")
    private WebElement priorityDropdown;

    public void selectPriorityByIndex(int index) {
        new Select(priorityDropdown).selectByIndex(index);
    }

    // =============== START TIME ===============

    @FindBy(xpath = "//*[contains(normalize-space(),'Event starts at')]/following::select[1]")
    private WebElement startHourDropdown;

    @FindBy(xpath = "//*[contains(normalize-space(),'Event starts at')]/following::select[2]")
    private WebElement startMinuteDropdown;

    public void selectStartHourByIndex(int index) {
        new Select(startHourDropdown).selectByIndex(index);
    }

    public void selectStartMinuteByIndex(int index) {
        new Select(startMinuteDropdown).selectByIndex(index);
    }

    // =============== END TIME ===============

    @FindBy(xpath = "//*[contains(normalize-space(),'Event ends on') or contains(normalize-space(),'Event ends at')]/following::select[1]")
    private WebElement endHourDropdown;

    @FindBy(xpath = "//*[contains(normalize-space(),'Event ends on') or contains(normalize-space(),'Event ends at')]/following::select[2]")
    private WebElement endMinuteDropdown;

    public void selectEndHourByIndex(int index) {
        new Select(endHourDropdown).selectByIndex(index);
    }

    public void selectEndMinuteByIndex(int index) {
        new Select(endMinuteDropdown).selectByIndex(index);
    }

    // =============== AVAILABLE USERS ===============

    @FindBy(id = "availableusers")
    private WebElement availableUsers;

    // =============== ADD USER BUTTON ===============

    @FindBy(xpath = "//input[@value='Add >>' or @value='Add >']")
    private WebElement addUserButton;

    // =============== SELECTED USERS ===============

    @FindBy(id = "selectedusers")
    private WebElement selectedUsers;

    public String selectUserUsingDownArrowAndAdd() {
        availableUsers.click();

        new Actions(driver)
                .sendKeys(Keys.ARROW_DOWN)
                .perform();

        Select availableSelect = new Select(availableUsers);

        String userName = availableSelect
                .getFirstSelectedOption()
                .getText()
                .trim();

        addUserButton.click();

        return userName;
    }

    public boolean isUserSelected(String userName) {
        Select selectedSelect = new Select(selectedUsers);

        List<WebElement> options = selectedSelect.getOptions();

        for (WebElement option : options) {
            if (option.getText().trim().equals(userName)) {
                return true;
            }
        }

        return false;
    }

    // =============== SAVE ===============

    @FindBy(xpath = "//input[@name='eventsave' or @value='Save' or @title='Save'] | //button[normalize-space()='Save']")
    private List<WebElement> saveButtons;

    public void clickSave() {
        for (int i = saveButtons.size() - 1; i >= 0; i--) {
            WebElement button = saveButtons.get(i);

            if (button.isDisplayed() && button.isEnabled()) {
                button.click();
                return;
            }
        }

        throw new org.openqa.selenium.NoSuchElementException(
            "No visible and enabled Save button was found."
        );
    }

    // =============== ALERT HANDLING ===============

    public String handleDateTimeAlert() {
        Alert alert = driver.switchTo().alert();

        String message = alert.getText();
        alert.accept();

        return message;
    }

    // =============== CLICK MEETING EVENT ===============

    public void clickMeetingEvent(String meetingName) {
        driver.findElement(By.xpath(
                "//div[@id='ListViewContents']//a[normalize-space()='"
                + meetingName + "']"
        )).click();
    }

    // =============== SELECT EVENT CHECKBOX ===============

    public void selectMeetingCheckbox(String meetingName) {
        driver.findElement(By.xpath(
                "//tr[.//a[normalize-space()='" + meetingName + "']]"
                + "//input[@type='checkbox' and @name='selected_id']"
        )).click();
    }

    // =============== CHANGE OWNER ===============

    @FindBy(xpath = "//input[@value='Change Owner' or @title='Change Owner']")
    private WebElement changeOwnerButton;

    public void clickChangeOwner() {
        changeOwnerButton.click();
    }

    // =============== OWNERSHIP ===============

    @FindBy(id = "user_checkbox")
    private WebElement userRadioButton;

    @FindBy(id = "group_checkbox")
    private WebElement groupRadioButton;

    @FindBy(id = "lead_owner")
    private WebElement ownerDropdown;

    @FindBy(id = "lead_group_owner")
    private WebElement groupDropdown;

    public void clickOwnership(int index) {
        userRadioButton.click();
        new Select(ownerDropdown).selectByIndex(index);
    }

    // =============== UPDATE OWNER ===============

    @FindBy(xpath = "//input[@value='Update Owner']")
    private WebElement updateOwnerButton;

    public void clickUpdateOwner() {
        updateOwnerButton.click();
    }

    // =============== ALL EVENTS AND TODOS ===============


    public void clickAllEventsAndTodos() {

    WebElement link = driver.findElement(
        By.xpath("//a[normalize-space()='All Events & Todos']")
    );

    // Scroll the link into the visible area
    ((org.openqa.selenium.JavascriptExecutor) driver)
        .executeScript(
            "arguments[0].scrollIntoView({block:'center'});",
            link
        );

    try {
        // Try a normal Selenium click first
        link.click();

    } catch (org.openqa.selenium.ElementClickInterceptedException e) {
        // Fallback if another element intercepts the click
        ((org.openqa.selenium.JavascriptExecutor) driver)
            .executeScript("arguments[0].click();", link);
    }
}


    // =============== VERIFY EVENT ===============



    public boolean isEventDisplayed(String eventName) {

    System.out.println("Current URL: " + driver.getCurrentUrl());
    System.out.println("Searching for event: " + eventName);

    // Print the visible page text to help identify the correct list
    System.out.println("Page title: " + driver.getTitle());

    List<WebElement> events = driver.findElements(
        By.xpath("//*[normalize-space()='" + eventName + "']")
    );

    System.out.println("Matching elements found: " + events.size());

    for (WebElement event : events) {
        if (event.isDisplayed()) {
            System.out.println("Visible event: " + event.getText());
            return true;
        }
    }

    return false;
}


}