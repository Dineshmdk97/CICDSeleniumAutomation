package seleniumFramework.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import seleniumFramework.AbstractComponents.AbstractComponent;

public class orderPage extends AbstractComponent{
	
	WebDriver driver;
	public orderPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css = "tr td:nth-child(3)")
	List<WebElement> orderMatch;

	
	public Boolean verifyOrder(String productName)
	{
		Boolean match = orderMatch.stream().anyMatch(pr->pr.getText().equalsIgnoreCase(productName));
		return match;
	}
}
