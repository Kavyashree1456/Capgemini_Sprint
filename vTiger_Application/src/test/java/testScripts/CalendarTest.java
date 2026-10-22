package testScripts;

import java.io.IOException;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import genericUtility.BaseClass;
import genericUtility.ExcelUtility;
import objRepo.CalendarPage;

public class CalendarTest extends BaseClass {
	@Test(groups="Functional")
	public void addmeeting() throws IOException {
		CalendarPage cp = new CalendarPage(driver);
		cp.clickCalendar();
		cp.clickAdd();
		cp.clickMeeting();
		ExcelUtility eu = new ExcelUtility();
		String eventname = eu.getDataFromExcel("Sheet1", 1, 1);
		cp.enterEvent(eventname);
		String description = eu.getDataFromExcel("Sheet1", 1, 2);
		cp.addDescription(description);
		String location = eu.getDataFromExcel("Sheet1", 2, 3);
		cp.addLocation(location);
	    
		cp.selectStatusByIndex(1);
	    cp.selectPriorityByIndex(2);
	    cp.selectAssignedToByIndex(0);

	    // Select Start Time
	    cp.selectStartHourByIndex(2);
	    cp.selectStartMinuteByIndex(1);

	    // Select End Time
	    cp.selectEndHourByIndex(3);
	    cp.selectEndMinuteByIndex(1);

	    // Save the meeting
	    cp.clickSave();

	}
	
	@Test(groups = {"Functional", "Positive"})
    public void updateOwnerName() {

        CalendarPage calendar = new CalendarPage(driver);

        calendar.clickCalendar();
        calendar.clickAllEventsAndTodos();
        calendar.selectMeetingCheckbox("meeting");
        calendar.clickChangeOwner();

        // Select the third owner in the dropdown
        calendar.clickOwnership(2);

        calendar.clickUpdateOwner();

        Reporter.log("Owner updated successfully.", true);
    }
	
	 @Test(groups = {"System", "Positive"})
	    public void verifyCompleteCalendarEventFlow() throws Exception {

	        ExcelUtility eu = new ExcelUtility();
	        CalendarPage calendar = new CalendarPage(driver);

	        // Step 1: Open Calendar
	        calendar.clickCalendar();
	        calendar.clickAdd();
			calendar.clickMeeting();
	       

	        // Step 3: Read event details from Excel
	        String eventName =
	                eu.getDataFromExcel("Sheet1", 1, 1);

	        String eventDescription =
	                eu.getDataFromExcel("Sheet1", 1, 2);

	        String eventLocation =
	                eu.getDataFromExcel("Sheet1", 1, 3);

	        // Step 4: Select Event Type
//	        calendar.selectEventTypeByIndex(0);

	        // Step 5: Enter event details
	        calendar.enterEvent(eventName);
	        calendar.addDescription(eventDescription);
	        calendar.addLocation(eventLocation);

	        // Step 6: Select Planned status
	        calendar.selectStatusByIndex(0);

	        // Step 7: Select Assigned To
	        calendar.selectAssignedToByIndex(3);

	        // Step 8: Select Priority
	        calendar.selectPriorityByIndex(1);

	        // Step 9: Set start and end time.
	        // These indexes depend on the dropdown option order.
	        calendar.selectStartHourByIndex(2);
	        calendar.selectStartMinuteByIndex(1);
	        calendar.selectEndHourByIndex(2);
	        calendar.selectEndMinuteByIndex(1);

	        // Step 10: Select a user dynamically and click Add >>
	        String selectedUser =
	                calendar.selectUserUsingDownArrowAndAdd();
	     // Check and handle the validation alert
	        try {
	            String alertMessage = calendar.handleDateTimeAlert();

	            System.out.println("Alert message: " + alertMessage);

	            // Stop because the meeting was rejected
	            Assert.fail("Meeting was not saved: " + alertMessage);

	        } catch (org.openqa.selenium.NoAlertPresentException e) {
	            // No validation alert: continue
	        }

	        // This runs only if no alert was displayed
	        calendar.clickAllEventsAndTodos();

	        Assert.assertFalse(
	            calendar.isEventDisplayed(eventName),
	            "Meeting was not created: " + eventName
	        );
	    
}
	 
	 
}
