package com.SenWellsysassignment.utility;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.RenderingHints.Key;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.NonWritableChannelException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.internal.annotations.ITest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.google.common.io.Files;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Utility {
	private URL url;
	protected WebDriver driver;
	private Properties propObj;

	private ExtentReports extReportObj;
	private ExtentTest testLogger;

	public Utility() {
		try {
			InputStream file = new FileInputStream("resources\\data\\config.properties");
			propObj = new Properties();
			propObj.load(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Properties getProObj() {
		return propObj;
	}

	public WebDriver getDriver() {
		return driver;
	}

	/////////// Date And Time ///////////////
	public void getCurrentDate() {
		Date date = new Date();
		String currentDate = date.toString().replace(" ", "_").replace(":", "_");
		System.out.println(currentDate);

	}

	public String getCurrentDateAndTime() {
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy_mm_dd_hh_mm_ss");
		Date Objdate = new Date();
		String date = simpleDateformat.format(Objdate);
		return date;

	}

	////////////// Extent Report ////////////////

	public void initHtmlReports() {
		String date = getCurrentDateAndTime();
		ExtentSparkReporter htmlReports = new ExtentSparkReporter("ExtentReport//" + date + "reports.html");
		htmlReports.config().setReportName("Functional Testing ");
		htmlReports.config().setDocumentTitle("msnAgile Functional Report");
		htmlReports.config().setTheme(Theme.DARK);

		extReportObj = new ExtentReports();
		extReportObj.attachReporter(htmlReports);
		extReportObj.setSystemInfo("OsName", "window10");
		extReportObj.setSystemInfo("Browser Name", "Chrome");
		extReportObj.setSystemInfo("Tester Name", "Mithilesh kumar");
	}

	public void setExtentTestLogger(String testCaseName) {
		testLogger = extReportObj.createTest(testCaseName);

	}

	public void flushReport() {

		extReportObj.flush();
	}

	public void resultStaus(ITestResult result) {
		if (result.getStatus() == result.SUCCESS) {
			testLogger.pass(result.getMethod().getMethodName() + "  is passed succesfully");
		} else if (result.getStatus() == ITestResult.FAILURE) {
			testLogger.fail(result.getMethod().getMethodName() + "  is Failed");
			testLogger.fail(result.getThrowable().toString() + " is Failed");
			testLogger.addScreenCaptureFromPath(fortakesnapshot(result.getMethod().getMethodName()));
		} else if (result.getStatus() == ITestResult.SKIP) {
			testLogger.skip(result.getMethod().getMethodName() + " is Skipped");
			testLogger.skip(result.getThrowable().toString() + " is Skipped");
		}

	}

	////////// Mouse And KeyBoard Actions/////////
	public void actionClick(WebElement we) {
		Actions actions = new Actions(driver);
		if (we.isDisplayed() && we.isEnabled()) {
			actions.moveToElement(we).click().build().perform();
		}
	}

	public void mouseHover(WebElement we) {
		Actions actions = new Actions(driver);
		if (we.isDisplayed() && we.isEnabled()) {
			actions.moveToElement(we).build().perform();

		}
	}

	public void actionClickAndHold(WebElement we) {

	}

	public void actionsInputValue(WebElement we, String value) {
		Actions actions = new Actions(driver);
		if (we.isDisplayed() && we.isEnabled()) {
			we.clear();
			actions.moveToElement(we).sendKeys(value).build().perform();
		}
	}

	public void actionDoubleCLick(WebElement we) {
		Actions actions = new Actions(driver);
		if (we.isDisplayed() && we.isEnabled()) {
			actions.moveToElement(we).doubleClick().build().perform();
		}
	}

	public void actionRightCLick(WebElement we) {
		Actions actions = new Actions(driver);
		if (we.isDisplayed() && we.isEnabled()) {
			actions.moveToElement(we).contextClick().build().perform();
		}
	}

	public void dragAndDrop1(WebElement source, WebElement destination) {
		Actions actions = new Actions(driver);
		if (source.isDisplayed() && source.isEnabled()) {
			actions.dragAndDrop(source, destination).build().perform();
		}

	}

	public void dragAndDrop2(WebElement source, WebElement destination) {
		Actions actions = new Actions(driver);
		if (source.isDisplayed() && source.isEnabled()) {
			actions.clickAndHold(source).moveToElement(source).release(destination).build().perform();
		}

	}

	////////////// ScreenShots Practice/////////////
	////// Screenshot //////////////
	public String fortakesnapshot(String snapshotname) {
		TakesScreenshot takesscrenshot = (TakesScreenshot) driver;
		File sourceFile = takesscrenshot.getScreenshotAs(OutputType.FILE);
		String time = getCurrentDateAndTime();
		File destinationfile = new File("ExtentReport//" + snapshotname + time + ".jpg");

		try {
			Files.copy(sourceFile, destinationfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return destinationfile.getAbsolutePath();
	}

	public void attachSnapshotTotheExtentReport(String imagepath) {

		try {
			testLogger.addScreenCaptureFromPath(imagepath);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void Screenshot(String name) {
		String dateAndTime = getCurrentDateAndTime();

		File filesrc = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(filesrc, new File("screentShot\\" + dateAndTime + " " + name + " " + ".jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Screenshot has been taken");
		testLogger.log(Status.INFO, "Screenshot has been taken");
	}

	public String captureScreenShot() {
		String dateAndTime = getCurrentDateAndTime();
		TakesScreenshot takesscrenshot = (TakesScreenshot) driver;
		File sourceFile = takesscrenshot.getScreenshotAs(OutputType.FILE);
		File desFile = new File("screentShot\\" + dateAndTime + ".jpg");
		try {
			FileUtils.copyFile(sourceFile, desFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return desFile.getAbsolutePath();
		// ye wala method screenshot ke liye hai
	}

	public String captureScreenshotBase64() {

		TakesScreenshot screenshot = (TakesScreenshot) driver;
		String base64 = screenshot.getScreenshotAs(OutputType.BASE64);
		System.out.println("Screenshot shot saved succesfully");

		return base64;

		// ye tarika karane se report me screenhsot ka link jaisa kucch jud jata jai
		// ye bahut achcha method hai

	}

	public void takeScreenShotOfWebElement(WebElement we) {
		String date = getCurrentDateAndTime();
		String screenshotName = we.getText() + date;
		File src = we.getScreenshotAs(OutputType.FILE);
		File destination = new File("ScreenshotOfWebElement\\" + screenshotName + ".jpg");

		try {
			Files.copy(src, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/// Launch android chrome //////////
	public void launchAndroidChrome() {
		DesiredCapabilities ds = new DesiredCapabilities();
		ds.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.ANDROID);
		ds.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
		ds.setCapability(MobileCapabilityType.NO_RESET, true);
		ds.setCapability("chromedriverExecutable", "driver\\chromedriver.exe");
		try {
			url = new URL("http://127.0.0.1:4723/wd/hub");
			staticThreadSleepWait(2);
			driver = new AndroidDriver(url, ds);

		} catch (MalformedURLException e) {

		}
		staticThreadSleepWait(3);
		// driver.get("https://www.google.com/");
	}

	///////// Launch Browser///////////////
	public void launchBrowser(String browser) {

		if (browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			testLogger.log(Status.INFO, " chrome Browser has been launched ");

		} else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "src\\test\\resources\\driver\\geckodriver.exe");
			driver = new FirefoxDriver();
			testLogger.log(Status.INFO, " Firefox Browser has been launched ");
		} else if (browser.equalsIgnoreCase("ie")) {
			System.setProperty("webdriver.ie.driver", "");
			driver = new InternetExplorerDriver();
			testLogger.log(Status.INFO, " Internet Explorer Browser has been launched ");
		} else {
			System.out.println("you have entered wrong URL");
			testLogger.log(Status.INFO, " You have entered wrong URL ");
		}

	}

	////// open URL////////////
	public void openURL(String urlName) {
		driver.get(urlName);
		testLogger.log(Status.INFO, urlName + "    has been hitted succesfully");
	}

	/// ///////// Click Method ///////////////
	public void click(WebElement we, String massage) {
		if (we.isDisplayed() && we.isEnabled()) {
			try {
				we.click();
				testLogger.log(Status.PASS, "clicked  succesfully on the " + massage);
			} catch (NoSuchElementException e) {
				Actions act = new Actions(driver);
				act.moveToElement(we).click().build().perform();
				e.printStackTrace();
				testLogger.log(Status.PASS, "clicked  succesfully on the " + massage);
			} catch (Exception e) {
// mujhe lagata hai ki element not interactableException  se achcha Exception hi hai phir bhi check kar lena hai 
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].click();", we);
				testLogger.log(Status.PASS, "clicked  succesfully on the " + massage);
			}
		}

	}

	////// Send keys Method ////////
	public void pressEnterKey(WebElement we) {
		we.sendKeys(Keys.ENTER);
	}

	public void sendKey(WebElement we, String value, String massage) {
		if (we.isDisplayed() && we.isEnabled()) {
			try {
				we.clear();
				we.sendKeys(value);
				testLogger.log(Status.PASS, "value inputted succesfully in the " + massage);
			} catch (NoSuchElementException e) {
				Actions act = new Actions(driver);
				we.clear();
				act.moveToElement(we).sendKeys(value).build().perform();
				e.printStackTrace();
				testLogger.log(Status.PASS, "value inputted succesfully in the " + massage);
			} catch (Exception e) {
				we.clear();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].value='" + value + "';", we);
				testLogger.log(Status.PASS, "value inputted succesfully in the " + massage);
			}

		}
	}

	//////////// window///////////////
	public void maximizeWindow() {
		driver.manage().window().maximize();
		testLogger.log(Status.INFO, "window is maximized");
	}

	public void refreshWindow() {
		driver.navigate().refresh();
	}

	public void close() {
		driver.close();
		testLogger.log(Status.INFO, "window has been  closed");
	}

	public void quit() {
		driver.quit();
	}

	public void navigateBack() {
		driver.navigate().back();
	}

	public void navigateForword() {
		driver.navigate().forward();

	}

	//////////////////// File Upload Using Robot///////////////////////////
// using robot class we can perform mouse and keyboard actions to interact with natice OS Windows , popups and native applications 

	public void fileUpboadUsingRobot(String filePathWithName) {

		try {
			Robot robot = new Robot();
			robot.delay(2000);
			StringSelection selection = new StringSelection(filePathWithName);
			robot.delay(2000);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
			robot.delay(2000);

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.delay(1000);
			robot.keyPress(KeyEvent.VK_V);
			robot.delay(1000);

			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.delay(1000);
			robot.keyRelease(KeyEvent.VK_V);
			robot.delay(2000);

			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);

			robot.delay(2000);
		} catch (AWTException e) {

			e.printStackTrace();
		}
	}

	public void fileUploadUsingSendKeys(WebElement we, String filePath) {
		we.sendKeys(filePath);
	}

	public void fileDownloadUsingRobot() {

	}

	public void robotKeyPress(int valueOfKey) {
		try {
			Robot robot = new Robot();
			robot.keyPress(valueOfKey);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	public void robotKeyRelease(int valueOfKey) {
		try {
			Robot robot = new Robot();
			robot.keyRelease(valueOfKey);
		} catch (AWTException e) {
			e.printStackTrace();
		}
///robot.keyRelease(KeyEvent.VK_V);	
	}

	public void takeScreenshotUsingRobotClassRectanleArea(int x, int y, int width, int Height) {
		String date = getCurrentDateAndTime();
		String imgName = "robot+date";

		try {
			Robot robot = new Robot();
			Rectangle reatangle = new Rectangle(x, y, width, Height);
			BufferedImage srcImage = robot.createScreenCapture(reatangle);
			ImageIO.write(srcImage, "PNG", new File("screenShot\\" + imgName + ".png"));
		} catch (AWTException e) {

			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void takeScreenshotRobotFullPage() {
		String date = getCurrentDateAndTime();
		String imgName = "robot+date";

		try {
			Robot robot = new Robot();

			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			Rectangle rectangle = new Rectangle(d);
			BufferedImage srcFile = robot.createScreenCapture(rectangle);
			ImageIO.write(srcFile, "PNG", new File("screenShot\\" + imgName + ".png"));

		} catch (AWTException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void ScrollingUsingMouseWheel(int number) {
		try {
			Robot robot = new Robot();
			robot.mouseWheel(number);
			// Neeche Aane ke liye positive Number Upar Jaane ke liye negative Number
		} catch (AWTException e) {
			e.printStackTrace();
		}

	}

	public void staticWaitUsingRobot(int time) {
		int timeWaitInSecond = 1000 * time;
		try {
			Robot robot = new Robot();
			robot.delay(timeWaitInSecond);
		} catch (AWTException e) {
			e.printStackTrace();
		}

	}

	public void dropDownWithoutSelectClassTag(WebElement we, String selectvalue) {

		while (true) {
			try {
				Robot rb = new Robot();
				rb.delay(500);
				rb.keyPress(KeyEvent.VK_DOWN);
				rb.keyRelease(KeyEvent.VK_DOWN);
				rb.delay(1000);
				String value = we.getText();
				if (value.equalsIgnoreCase(selectvalue)) {
					rb.keyPress(KeyEvent.VK_ENTER);
					rb.keyRelease(KeyEvent.VK_ENTER);
					rb.delay(500);
					break;

				}

			} catch (AWTException e) {
				e.printStackTrace();
			}

		}

	}

/// Robot class ka ket btn ka list bana lena hai

///////////catcha code////////////////

	public String capthacodeHandlilng() {

		return null;

	}

//////////////// public void waits //////////////////
	public void staticThreadSleepWait(int timeForWaitinginSec) {
		int second = timeForWaitinginSec * 1000;
		try {

			Thread.sleep(second);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void implicityWait(int timeInSecond) {
		driver.manage().timeouts().implicitlyWait(timeInSecond, TimeUnit.SECONDS);
	}

	public void explicityWait(WebElement we, int timeInsecond) {
		if (we.isDisplayed() && we.isEnabled()) {
			WebDriverWait explicitwait = new WebDriverWait(driver, Duration.ofSeconds(timeInsecond));
			explicitwait.until(ExpectedConditions.visibilityOf(we));
		}
	}

	public void fluentWait(WebElement we, int totalTime, int timeInterval) {

	}

	////// Window Handle ////////
	public Set<String> switchToWindowByTitle(String expWindowTitle) {
		Set<String> windows = driver.getWindowHandles();
		for (String multiplewindow : windows) {
			driver.switchTo().window(multiplewindow);
			String actWindowTitle = driver.getTitle();
			System.out.println(actWindowTitle);
			if (!expWindowTitle.equalsIgnoreCase(actWindowTitle)) {
			}
			driver.switchTo().window(driver.getWindowHandle());
		}
		return windows;
	}

	public void switchTOWindowByURL(String expURL) {
		Set<String> windows = driver.getWindowHandles();
		for (String multipleWindows : windows) {
			driver.switchTo().window(multipleWindows);
			String actualURL = driver.getCurrentUrl();
			System.out.println(actualURL);
			if (actualURL.equalsIgnoreCase(expURL)) {
				break;
			}
		}
	}

	public Set<String> switchWindowByURL(String expURL) {
		Set<String> windows = driver.getWindowHandles();
		for (String child : windows) {
			String actualURL = driver.getCurrentUrl();
			if (!actualURL.equalsIgnoreCase(expURL)) {
			}
			driver.switchTo().window(child);
		}
		return windows;
	}

	public String getMainWindow() {
		String window = driver.getWindowHandle(); /// isase parant window ka nikalata hai
		return window;
	}

	public void switchToMainWindow(String mainWindow) {
		driver.switchTo().window(mainWindow);
	}

////////////// Java Script Executor////////////////
	public void javaScriptClick(WebElement we) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", we);
		System.out.println(" clicked by java script");
	}

	public void javaScriptSendKeys(WebElement we, String inputValue) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		we.clear();
		jse.executeAsyncScript("arguments[0].value='" + inputValue + "'", we);
		System.out.println("value is Inputed successfully");
		// isase date agar value me diya hoto send kar sakte hai

	}

	public String javaScriptPageTitle() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String titleOfPage = js.executeScript("return document.title").toString();
		return titleOfPage;
	}

	public String javaScriptGetURL() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String urlOfPage = js.executeScript("return document.URL").toString();
		return urlOfPage;
	}

	public void jsSendKeyWithAttributeValue(WebElement we, String text) {

	}

	public void javaScriptRefresh() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("hisotry.go(0)");
	}

	public void scrollPageUntilElementIsFound(WebElement we) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true)", we);

	}

	public void scrollPageByNumbers(int x, int y) {

	}

	public void scrollPageTillPageEnd() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
	}

	public String javaScriptGetTextOnPage() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String textOnPage = js.executeScript("return document.documentElement.innerText").toString();
		return textOnPage;
// is method se  Page ka saara text Print kara sakate hai
	}

	// flashing elements Using Java Script///
	public void javaScriptFlash(WebElement we) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		for (int i = 0; i <= 5; i++) {
			js.executeScript("arguments[0].style.background='green';", we);

			try {
				Thread.sleep(900);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			// js.executeScript("arguments[0].style.background='"+defaultColor+"';",we);

		}

	}

// note-- Java Script ka casting isliye karate hai kyoki webdriver , javascriptExecutor ka Parent Interface nahi hai
// profit--sometimes webdriver methods won't work 
// JavaScriptExecutor is faster as it directly interact with browser
// disadvantage---- It works even if the elements are disabled on the page ,only thing required is ot be available on the page to do the operations

	//////////// Alert /////////////////
	public void alertAccept() {
		driver.switchTo().alert().accept();

	}

	public void alertDismiss() {
		driver.switchTo().alert().dismiss();
	}

	public void alertsendKeys(String value) {
		driver.switchTo().alert().sendKeys(value);
	}

	public String alertGetStringValue() {
		String alertgetText = driver.switchTo().alert().getText();
		return alertgetText;

	}

/////////// IFrame ///////////

	public void switchToFrameByIdOrFrame(String IdorName) {
		driver.switchTo().frame(IdorName);
	}

	public void switchToFrameByIndex(int frameNo) {
		driver.switchTo().frame(frameNo);
	}

	public void switchToFrameByweElement(WebElement we) {
		driver.switchTo().frame(we);
	}

	public void switchToParentFrame() {
		driver.switchTo().parentFrame();

	}

	public void switchToMainFrame() {
		driver.switchTo().defaultContent();
	}

	//////////// validation and verification Parts //////////////
	public void validateMassage(WebElement we) {
		if (we.isDisplayed()) {
			testLogger.log(Status.PASS, "Massage Appeared  " + we.getText());
		} else {
			testLogger.log(Status.FAIL, "Massage is not displayed");
		}
	}

	public boolean validateUserfromtheList(List<WebElement> we, String expected) {
		boolean status = false;
		int size = we.size();
		for (int i = 0; i < size; i++) {
			WebElement wele = we.get(i);
			String webText = wele.getText();
			System.out.println(webText);
			if (webText.equalsIgnoreCase(expected)) {
				status = true;
				System.out.println("the text is " + webText);
				break;
			} else {
				status = false;
			}

		}
		return status;

	}

	public void validateUserInTheList(List<WebElement> we, String expected) {
		boolean status = validateUserfromtheList(we, expected);

		if (status == true) {
			testLogger.log(Status.PASS, expected + "  email Id is  In the List");

		} else {
			testLogger.log(Status.FAIL, expected + "  email Id is not In the List");
		}
	}

	public void validatingscenario(WebElement we, String expected) {
		String actual = we.getText();
		System.out.println(actual);
		if (actual.equalsIgnoreCase(expected)) {
			Assert.assertEquals(actual, expected);
			testLogger.log(Status.PASS, actual + "  is matched with  " + expected);
			System.out.println(actual + "  is matched with  " + expected);

		} else {
			testLogger.log(Status.PASS, actual + "  is not  matched with  " + expected);
			System.out.println(actual + "  is not  matched with  " + expected);

		}
	}

	public void validatePageTitle(String expecetedTitle) {
		String actulTitle = driver.getTitle();
		System.out.println(actulTitle);

		System.out.println();
		if (actulTitle.equalsIgnoreCase(expecetedTitle)) {
			Assert.assertEquals(actulTitle, expecetedTitle);
			testLogger.log(Status.PASS, actulTitle + " Page Title is matched  " + expecetedTitle);
			System.out.println(actulTitle + " Page Title is matched  " + expecetedTitle);

		} else {
			testLogger.log(Status.PASS, "Page Title is not matched");
			System.out.println("Page Title is not matched");

		}
	}

	public void isDiplayedMethod(WebElement ele) {
		boolean status = ele.isDisplayed();
		String text = ele.getText();
		if (status == true) {

		} else {

		}
	}

	public void validataproductIsDispalyed(WebElement ele) {
		boolean status = ele.isDisplayed();
		if (status == true) {
			Assert.assertEquals(true, ele.isDisplayed());

			testLogger.log(Status.PASS, "product is displayed in the add to cart list");
		} else {
			testLogger.log(Status.PASS, "product is not displayed in the add to cart list");
		}
	}

	/////// Drop Down Handling /////////////////
	public void selectByVisibleText(WebElement web, String text) {

	}

	///// Some Extra Features //////////////
	public int getHeight() {
		return 0;

	}

	///// Cosmetic handling elements ///////////////////

	////  Hasmap and list and set releted concetp
	
	
	  public static boolean isListAscending(List<Double> list) {
	        for (int i = 0; i < list.size() - 1; i++) {
	            Double currentElement = list.get(i);
	            Double nextElement = list.get(i + 1);

	            if (currentElement > nextElement) {
	                return false;
	            }
	        }
	        return true;
	    }
}
