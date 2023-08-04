package com.SenWellsys.homepage;

import org.openqa.selenium.support.PageFactory;

import com.SenWellsysassignment.utility.Utility;

public class HomePage extends HomePageOR {
	private Utility webUtil;

	public HomePage(Utility webUtil) {
		this.webUtil = webUtil;
		PageFactory.initElements(webUtil.getDriver(), this);

	}
	
	public void verifyhomepagePage() {
		String expHomePageTitle="Swag Labs";
		webUtil.validatePageTitle(expHomePageTitle);
		
	}

}
