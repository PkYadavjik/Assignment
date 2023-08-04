package com.SenWellsysassignment.utility;

import java.io.IOException;
import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.SenWellsysassignment.login.LoginPage;

public class BaseClass {
	protected Utility webUtil;

	@BeforeSuite
	public void initilization() {
		webUtil = new Utility();
		webUtil.initHtmlReports();

	}


	@BeforeMethod
	public void LaunchBrowserAndlogin(Method method) {
		webUtil.setExtentTestLogger(method.getName()); // Ise listenrer me dalana hai sochege
		String browserName = webUtil.getProObj().getProperty("browser");
		webUtil.launchBrowser(browserName);
		String url = webUtil.getProObj().getProperty("url");
		webUtil.openURL(url);
		webUtil.maximizeWindow();
		webUtil.implicityWait(20);
		}

	@AfterMethod
	public void logOut(ITestResult result) {
	webUtil.resultStaus(result);
	LoginPage lp = new LoginPage(webUtil);
    lp.logout();
	webUtil.flushReport(); 
    webUtil.close();
	}


	@AfterSuite
	public void flush() {
		webUtil.flushReport();
	}

}
