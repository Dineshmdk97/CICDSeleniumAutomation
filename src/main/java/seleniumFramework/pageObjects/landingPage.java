package seleniumFramework.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import seleniumFramework.AbstractComponents.AbstractComponent;

public class landingPage extends AbstractComponent{
	WebDriver driver;
	public landingPage(WebDriver driver)
	{
		//If super keyword is used from specific child class to parent class. 
		//All other child classes must have super keyword in its constructor or else it will lead to error
		super(driver);
		// Constructor will always be 'initialized' first in a class
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	//WebElement email = driver.findElement(By.id("userEmail"));
	//WebElement password = driver.findElement(By.id("userPassword"));
	//WebElement login = driver.findElement(By.id("login"));
	
	//(or)
	
	//PageFactory
	
	//PageFactory is simplified way of setting Web elements
	//initElements will trigger construction and initialization of all elements
	//Clicking on @FindBy will take you to selenium internal java files. where you can check how locator's name should be given.
	//Ex:- In pageFactory, you need to write css instead of cssSelector
	//Double quotes need to be given for locator's value
	
	@FindBy(id="userEmail")
	WebElement email;
	
	@FindBy(id="userPassword")
	WebElement password;
	
	@FindBy(id="login")
	WebElement login;
	
	@FindBy(css="[class*='flyInOut']")
	WebElement errorMessage;
	
	//Note:-
	//Child class have access to parent class' methods as well

	public void goTo()
	{
		driver.get("https://rahulshettyacademy.com/client");
	}
	
	public String getErrorMessage()
	{
		waitAppearance(errorMessage);
		return errorMessage.getText();
	}
	
	public ProductCatalogue loginApplication(String emailid, String passwordid)
	{
		email.sendKeys(emailid);
		password.sendKeys(passwordid);
		login.click();
		ProductCatalogue catalogue = new ProductCatalogue(driver);
		return catalogue;
	}
	
}
