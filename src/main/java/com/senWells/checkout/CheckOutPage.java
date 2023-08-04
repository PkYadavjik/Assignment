package com.senWells.checkout;

import org.openqa.selenium.support.PageFactory;

import com.SenWellsysassignment.utility.Utility;

public class CheckOutPage  extends CheckOutOR{
	private Utility webUtil;

	public CheckOutPage(Utility webUtil) {
		this.webUtil = webUtil;
		PageFactory.initElements(webUtil.getDriver(), this);
	}
	public void checkOutProcess() {
webUtil.click(productName, "product");
webUtil.click(addToCard, "Add To Cart");
webUtil.click(shoppingCartLink, "Shopping Cart Link");
webUtil.click(checkOutBtn, "Check Out Btn");
webUtil.sendKey(firstnameField, "Punit ", "First Name ");
webUtil.sendKey(lastNameField, "yadav ", " Last  Name ");		
webUtil.sendKey(postalCode, "221406", "Postal Code");
webUtil.click(continueBtn, "Continue Btn");
webUtil.click(finishBtn, "Finish Btn");
webUtil.validatingscenario(massageOforder, "Thank you for your order!");
	
	}


}
