package com.SenWellsysassignment.login;

import org.openqa.selenium.support.PageFactory;

import com.SenWellsysassignment.utility.Utility;

public class LoginPage extends LoginOR {

	private Utility webUtil;

	public LoginPage(Utility webUtil) {
		this.webUtil = webUtil;
		PageFactory.initElements(webUtil.getDriver(), this);

	}

	public void validLogin() {
		String username = webUtil.getProObj().getProperty("username");
		System.out.println(username);
		webUtil.sendKey(userName, username, "User Name  Field");
		webUtil.sendKey(passwordField, "secret_sauce", "Password Field");
		webUtil.click(LoginBtn, "Login Btn");

	}

	public void invalidLogin() {
		webUtil.sendKey(userName, "invalid username", "User Name  Field");
		webUtil.sendKey(passwordField, "invalid password", "Password Field");
		webUtil.click(LoginBtn, "Login Btn");
		String expErrorMassage="Epic sadface: Username and password do not match any user in this service";
		webUtil.validatingscenario(erroMassage  , expErrorMassage);
		// massage bhi verifykarana hai

	}

	public void logout() {

	}
}
