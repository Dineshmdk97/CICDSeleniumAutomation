package seleniumFramework.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import seleniumFramework.AbstractComponents.AbstractComponent;

public class ProductCatalogue extends AbstractComponent{
	WebDriver driver;
	public ProductCatalogue(WebDriver driver)
	{
		super(driver);
		// Constructor will always be 'initialized' first in a class
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	// In PageFactory, css should be used instead of cssSelector
	@FindBy(css=".mb-3")	
	List<WebElement> products;
	
	@FindBy(css=".ng-animating")	
	WebElement spinner;
	
	// Below line of code is used to define locator
	By prod = By.cssSelector(".mb-3");
	By addCart = By.cssSelector(".card-body button:last-of-type");
	
	public List<WebElement> getProductsList()
	{
		waitElement(prod);
		// Since products is no.of web elements, instead of 'void', 'List<WebElement> is used
		return products;
	}
	
	public WebElement getProductByName(String productName)
	{
		WebElement prod = products.stream().filter(product->
		product.findElement(By.cssSelector("b")).getText().equals("ADIDAS ORIGINAL")).findFirst().orElse(null);
		return prod;
	}
	
	public void addProductToCart(String productName) throws InterruptedException
	{
		WebElement prod = getProductByName(productName);
		prod.findElement(addCart).click();
//Since we are using explicit wait (invisibility of element) in framework
//sometimes it may wait for other hidden elements at the back end besides the element that we have chosen to disappear 
//In such cases, execution may get delayed by few seconds. To avoid that Thread.sleep() can be used in such cases
		//waitDisappearance(spinner);
		Thread.sleep(1000);
	}
	
}
