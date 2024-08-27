package seleniumFramework.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import seleniumFramework.AbstractComponents.AbstractComponent;

public class cartPage extends AbstractComponent{
	
	WebDriver driver;
	public cartPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css = ".cartSection h3")
	List<WebElement> productMatch;
	
	@FindBy(css = ".totalRow button")
	WebElement checkout;
	
	public Boolean verifyProduct(String productName)
	{
		Boolean match = productMatch.stream().anyMatch(pr->pr.getText().equalsIgnoreCase(productName));
		return match;
	}
	public checkoutPage goToCheckout()
	{
		checkout.click();
		checkoutPage checkproducts = new checkoutPage(driver);
		return checkproducts;
	}
	
}
