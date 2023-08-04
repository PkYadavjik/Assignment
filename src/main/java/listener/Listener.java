package listener;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.SenWellsysassignment.utility.BaseClass;
import com.SenWellsysassignment.utility.Utility;
import com.aventstack.extentreports.model.Report;

public class Listener implements ITestListener {

	public void onTestStart(ITestResult result) {
		System.setProperty("org.uncommons.reportng.title", "Testing.MSNAgile.com");
		System.out.println("New Test Started" + result.getName());
		Reporter.log("Test started which method name is :   " + result.getMethod().getMethodName());
	}

	public void onTestSuccess(ITestResult result) {

		System.out.println("Test Successfully Finished" + result.getName());
		Reporter.log("Test Successfully Finished " + result.getMethod().getMethodName() + "  And  Staus is:  "
				+ result.getStatus());

	}

	public void onTestFailure(ITestResult result) {
		String path = CaptureScreenShotWithTestStepNameUsingRobotClass(result.getMethod().getMethodName());

		System.setProperty("org.uncommons.reportng.escape-output", "false");

		Reporter.log("Test Failed Which method name is " + result.getMethod().getMethodName() + "  And status is : "
				+ result.getStatus());
		Reporter.log("<img src=" + path + " alt=\"screenshot Has not taken\" width=\"1400 height=\"700\">");
	}

	public void onTestSkipped(ITestResult result) {

		System.out.println("Test Skipped" + result.getName());

	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

		System.out.println("Test Failed but within success percentage" + result.getName());

	}

	public void onStart(ITestContext context) {

		System.out.println("This is onStart method" + context.getOutputDirectory());

	}

	public void onFinish(ITestContext context) {

		System.out.println("This is onFinish method" + context.getPassedTests());
		System.out.println("This is onFinish method" + context.getFailedTests());
	}

	public static String CaptureScreenShotWithTestStepNameUsingRobotClass(String testStepsName) {
		try {

			Robot robotClassObject = new Robot();
			Rectangle screenSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage tmp = robotClassObject.createScreenCapture(screenSize);
			String path = System.getProperty("user.dir") + "/Screenshot/" + testStepsName + System.currentTimeMillis()
					+ ".png";
			ImageIO.write(tmp, "png", new File(path));
			return path;
		} catch (Exception e) {
			System.out.println("Some exception occured." + e.getMessage());
			return "";
		}
	}
}
