package com.kms.katalon.keyword.draganddrop

import java.awt.Robot
import java.awt.event.InputEvent
import java.awt.event.KeyEvent

import org.openqa.selenium.Capabilities
import org.openqa.selenium.Dimension
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.Point
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.remote.RemoteWebDriver

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords

import groovy.transform.CompileStatic

public class DragAndDropKeywords {
	static int timeoutSecond = 60
	static int timeoutMilisecond = 2000
	
	/**
	 * This function is used for HTML5 Drag and Drop.
	 *
	 * @param sourceObject The source object.
	 * @param destinationObject The destination object.
	 */
	@CompileStatic
	@Keyword
	static void dragAndDropHtml5(TestObject sourceObject, TestObject destinationObject) {
		WebElement sourceElement = WebUiBuiltInKeywords.findWebElement(sourceObject);
		WebElement destinationElement = WebUiBuiltInKeywords.findWebElement(destinationObject);
		WebDriver webDriver = DriverFactory.getWebDriver();
		((JavascriptExecutor) webDriver).executeScript(Utils.getJsDndHelper() + "simulateDragDrop(arguments[0], arguments[1])", sourceElement, destinationElement)
	}

	/**
	 * This function is used for jQuery drag and drop.
	 *
	 * @param sourceObject The source object.
	 * @param destinationObject The destination object.
	 */
	@CompileStatic
	@Keyword
	static void dragAndDropJquery(TestObject sourceObject, TestObject destinationObject) {
		WebUiBuiltInKeywords.dragAndDropToObject(sourceObject, destinationObject)
	}

	/**
	 * This function is used to handle drag and drop on other object kinds, except to Html5 and JQuery.
	 *
	 * @param sourceObject The source object.
	 * @param destinationObject The destination object.
	 */
	@CompileStatic
	@Keyword
	static void dragAndDrop(TestObject sourceObject, TestObject destinationObject) {
		WebDriver driver = DriverFactory.getWebDriver()
		WebElement from = WebUiCommonHelper.findWebElement(sourceObject, timeoutSecond)
		WebElement to = WebUiCommonHelper.findWebElement(destinationObject, timeoutSecond)
		Actions act= new Actions(driver)
		act.dragAndDrop(from, to).build().perform()
	}

	/**
	 * This function is used to handle drag and drop on other object kinds excepting Html5 and JQuery, with specified offsets. This only works on Firefox and Chrome.
	 *
	 * @param sourceObject The source object.
	 * @param destinationObject The destination object.
	 * @param xOffset Horizontal move offset.
	 * @param yOffset Vertical move offset.
	 */
	@Keyword
	static void dragAndDropBy(TestObject sourceObject, TestObject destinationObject, int xOffset, int yOffset)
	throws Exception {
		WebDriver driver = DriverFactory.getWebDriver()
		WebElement from = WebUiCommonHelper.findWebElement(sourceObject, timeoutSecond)
		WebElement to = WebUiCommonHelper.findWebElement(destinationObject, timeoutSecond)
		Capabilities caps = ((RemoteWebDriver) driver).getCapabilities()
		String browserName = caps.getBrowserName().capitalize()
		//Setup robot
		Robot robot = new Robot();
		robot.setAutoDelay(timeoutSecond);
		
		//Fullscreen page so selenium coordinates work
		if (browserName.toLowerCase().equals("firefox"))
			robot.keyPress(KeyEvent.VK_F11)
		else 
			WebUiBuiltInKeywords.maximizeWindow()
		Thread.sleep(timeoutMilisecond);

		//Get size of elements
		Dimension fromSize = from.getSize();
		Dimension toSize = to.getSize();

		//Get centre distance
		int xCentreFrom = fromSize.width / 2;
		int yCentreFrom = fromSize.height / 2;
		int xCentreTo = toSize.width / 2;
		int yCentreTo = toSize.height / 2;

		//Get x and y of WebElement to drag to
		Point toLocation = to.getLocation();
		Point fromLocation = from.getLocation();

		//Make Mouse coordinate centre of element
		toLocation.x += xOffset + xCentreTo;
		toLocation.y += yOffset + yCentreTo;
		fromLocation.x += xCentreFrom;
		fromLocation.y += yCentreFrom;

		//Move mouse to drag from location
		robot.mouseMove(fromLocation.x.intValue(), fromLocation.y.intValue());
		Thread.sleep(timeoutMilisecond);
		
		//Click and drag
		robot.mousePress(InputEvent.BUTTON1_MASK);

		//Drag events require more than one movement to register
		//Just appearing at destination doesn't work so move halfway first
		robot.mouseMove((((toLocation.x - fromLocation.x) / 2) + fromLocation.x).intValue(), (((toLocation.y
- fromLocation.y) / 2) + fromLocation.y).intValue());

		//Move to final position
		robot.mouseMove(toLocation.x.intValue(), toLocation.y.intValue());

		//Drop
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
}
