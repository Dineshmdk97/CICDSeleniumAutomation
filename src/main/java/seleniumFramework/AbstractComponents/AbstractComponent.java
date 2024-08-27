package seleniumFramework.AbstractComponents;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import seleniumFramework.pageObjects.cartPage;
import seleniumFramework.pageObjects.orderPage;

public class AbstractComponent {

	WebDriver driver;
	public AbstractComponent(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
		// Below line of code need to be used only if we are using @FindBy
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="button[routerlink*='cart']")	
	WebElement cartButton;
	
	@FindBy(css="button[routerlink*='myorders']")	
	WebElement orderButton;
	
	public void waitElement(By findBy)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		// It waits for the element through 'locator' not 'web element'
		//Locator - By.cssSelector(".mb-3")
		//Web element - driver.findElement(By.cssSelector(".mb-3"))
		wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	}
	
	// Since WebElement is used, Parameter need to be passed as 'WebElement' instead of 'By'
	public void waitDisappearance(WebElement elem)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.invisibilityOf(elem));
	}
	
	public void waitAppearance(WebElement eleme)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(eleme));
	}
	
	public cartPage goToCartPage()
	{
		cartButton.click();
		cartPage cartpage = new cartPage(driver);
		return cartpage;
	}
		
	public orderPage goToOrderPage()
	{
		orderButton.click();
		orderPage orderpage = new orderPage(driver);
		return orderpage;
	}
}
