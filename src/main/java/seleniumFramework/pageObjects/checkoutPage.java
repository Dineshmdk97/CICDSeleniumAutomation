package seleniumFramework.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import seleniumFramework.AbstractComponents.AbstractComponent;

public class checkoutPage extends AbstractComponent {
	WebDriver driver;
	public checkoutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	@FindBy(css="input[placeholder='Select Country']")
	WebElement country;
	
	@FindBy(css=".ta-results")
	WebElement countryResults;
	
	@FindBy(xpath="//button[contains(@class, 'ta-item')] [2]")
	WebElement chooseCountry;
	
	@FindBy(css="a[class*='action__submit']")
	WebElement submit;
	
	public void selectCountry(String countryName)
	{
		Actions a = new Actions(driver);
		a.sendKeys(country, countryName).build().perform();
		waitAppearance(countryResults);
		chooseCountry.click();
	}
	public confirmationPage submitOrder()
	{
		submit.click();
		confirmationPage confirmation = new confirmationPage(driver);
		return confirmation;
	}

}
