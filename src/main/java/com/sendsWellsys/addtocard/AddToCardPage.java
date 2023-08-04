package com.sendsWellsys.addtocard;

import org.openqa.selenium.support.PageFactory;

import com.SenWellsysassignment.utility.Utility;

public class AddToCardPage extends AddToCardOR {
	private Utility webUtil;

	public AddToCardPage(Utility webUtil) {
		this.webUtil = webUtil;
		PageFactory.initElements(webUtil.getDriver(), this);
	}
	public void userCanAddItemToAddCart() {
webUtil.click(productName, "product");
webUtil.click(addToCard, "Add To Cart");
webUtil.click(shoppingCartLink, "Shopping Cart Link");
webUtil.validataproductIsDispalyed(product);
		
	}

	public void verifyUserCanAddDifferentIteminAddToCart() {

	}
}
