package com.SenWellsysassignment.login;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginOR {
@FindBy(xpath = "//input[@id='user-name']")
protected WebElement userName;

@FindBy(xpath = "//input[@id='password']")
protected WebElement passwordField;

@FindBy(xpath = "//input[@id='login-button']")
protected WebElement LoginBtn;

@FindBy(xpath = "//*[text()='Epic sadface: Username and password do not match any user in this service']")
protected  WebElement erroMassage;

}
