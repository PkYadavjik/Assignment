package com.senWells.checkout;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckOutOR {
	@FindBy(xpath = "//a//img[@alt='Sauce Labs Backpack']")
	protected WebElement productName;

	@FindBy(xpath = "//button[text()='Add to cart']")
	protected WebElement addToCard;

	@FindBy(xpath = "//a//span[@class='shopping_cart_badge']")
	protected WebElement shoppingCartLink;

	@FindBy(xpath = "//button[@id='checkout']")
	protected WebElement checkOutBtn;
	
	///////////////// check Out Information
	
	@FindBy(xpath = "//input[@id='first-name']")
	protected WebElement firstnameField;
	
	@FindBy(xpath = "//input[@id='last-name']")
	protected WebElement lastNameField;
	
	@FindBy(xpath = "//input[@id='postal-code']")
	protected WebElement postalCode;
	
	@FindBy(xpath = "//input[@id='continue']")
	protected WebElement continueBtn;
	
	@FindBy(xpath = "//button[@id='finish']")
	protected WebElement finishBtn;
/////////////////////////
	
	@FindBy(xpath = "//h2[text()='Thank you for your order!']")
	protected WebElement massageOforder;
	
	
	
	
	
	
}
