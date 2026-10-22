package objRepo;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class HomePage {
	WebDriver driver;
	WebDriverWait wait;
	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		  wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	@FindBy(xpath = "//img[contains(@onclick,'fnAddWindow')]")
	private WebElement addWidgetButton;

	public void clickAddWidget() {
	    addWidgetButton.click();
	}
	@FindBy(id = "addmodule")
	private WebElement moduleOption;
	
	public void clickModuleOption() {
	    moduleOption.click();
	}

	@FindBy(id = "stufftitle_id")
	private WebElement windowTitleTextField;


public void enterWindowTitle(String title) {

    WebDriverWait wait =
            new WebDriverWait(driver, Duration.ofSeconds(10));

    WebElement field = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                    By.id("stufftitle_id")
            )
    );

    field.clear();

    if (title != null && !title.trim().isEmpty()) {
        field.sendKeys(title);
    }
}

	
	@FindBy(id = "maxentryid")
	private WebElement showDropdown;

	public void selectShowByIndex(int index) {
	    Select select = new Select(showDropdown);
	    select.selectByIndex(index);
	}
	  
	  // Module dropdown inside Add Module popup
	    @FindBy(xpath = "//tr[@id='moduleNameRow']//select")
	    private WebElement moduleDropdown;
	    public void selectModuleByIndex(int index) {
	        new Select(moduleDropdown).selectByIndex(index);
	    }
	    
	 // Filter By dropdown
	    @FindBy(xpath = "//tr[@id='moduleFilterRow']//select")
	    private WebElement filterByDropdown;
	    public void selectFilterByIndex(int index) {
	        new Select(filterByDropdown).selectByIndex(index);
	    }
	    	    
	    @FindBy(id="savebtn")
	    private WebElement save;
	    public void clickSave() {
	    	save.click();
	    }	
	    
	 // Notebook option in Add Widget menu
	    @FindBy(id = "addNotebook")
	    private WebElement notebookOption;
	    public void clickNotebookOption() {
	        notebookOption.click();
	    }

//	 // Window Title textbox
//	    @FindBy(id = "stufftitle_id")
//	    private WebElement windowTitleTextbox;
//
//	    public void enterWindowTitle(String title) {
//	        WebElement textbox = wait.until(
//	            ExpectedConditions.visibilityOf(windowTitleTextbox)
//	        );
//
//	        textbox.clear();
//
//	        if (title != null && !title.trim().isEmpty()) {
//	            textbox.sendKeys(title);
//	        }
//	    }

//	   @FindBy(id = "savebtn")
//	    private WebElement saveButton;
//
//	    public void clickSave() {
//	        wait.until(ExpectedConditions.elementToBeClickable(saveButton))
//	            .click();
//	    }
	    
	    public boolean isWindowTitleDisplayed() {
	        try {
	            return wait.until(
	                ExpectedConditions.visibilityOf(windowTitleTextField)
	            ).isDisplayed();
	        } catch (Exception e) {
	            return false;
	        }
	    }
}
