package com.sendsWellsys.addtocard;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddToCardOR {
@FindBy(xpath = "//a//img[@alt='Sauce Labs Backpack']")
protected WebElement productName;

@FindBy(xpath = "//button[text()='Add to cart']")
protected WebElement addToCard;

@FindBy(xpath = "//a//span[@class='shopping_cart_badge']")
protected WebElement shoppingCartLink;

@FindBy(xpath = "//div[@class='inventory_item_name']")
protected WebElement product;


}
