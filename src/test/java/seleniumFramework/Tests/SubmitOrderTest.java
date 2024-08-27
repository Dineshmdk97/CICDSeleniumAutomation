package seleniumFramework.Tests;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import seleniumFramework.TestComponents.BaseTest;
import seleniumFramework.pageObjects.ProductCatalogue;
import seleniumFramework.pageObjects.cartPage;
import seleniumFramework.pageObjects.checkoutPage;
import seleniumFramework.pageObjects.confirmationPage;
import seleniumFramework.pageObjects.landingPage;
import seleniumFramework.pageObjects.orderPage;

public class SubmitOrderTest extends BaseTest{

	String productName = "ADIDAS ORIGINAL";

	@Test(dataProvider="getData", groups ="Purchase")
//  public void submitOrder(String email, String password, String productName) throws IOException, InterruptedException
    public void submitOrder(HashMap<String,String> input) throws IOException, InterruptedException
	{
		
// Below are two ways of creating object, one is creating in main test class which will result in lengthy code
// Another is creating in test component class. At last method of a class (or page) before moving to another page
// Instead of creating one object for each pages, you can create the new page's object in the 'last method' of previous page itself
		
		//ProductCatalogue catalogue = new ProductCatalogue(driver);
		
// 		using 2D object
//		ProductCatalogue catalogue = landing.loginApplication(email, password);
// 		using HashMap
		ProductCatalogue catalogue = landing.loginApplication(input.get("email"), input.get("password"));
		
//		catalogue.addProductToCart(productName);	
		catalogue.addProductToCart(input.get("product"));
		
		//cartPage cartpage = new cartPage(driver);
		cartPage cartpage = catalogue.goToCartPage();
		
//		Boolean check = cartpage.verifyProduct(productName);
		Boolean check = cartpage.verifyProduct(input.get("product"));
		
		Assert.assertTrue(check);
		checkoutPage checkproducts = cartpage.goToCheckout();
		checkproducts.selectCountry("india");
		confirmationPage confirmation = checkproducts.submitOrder();
		String checkMessage = confirmation.verifyConfirmationMessage();
		Assert.assertTrue(checkMessage.equalsIgnoreCase("Thankyou for the order."));
		System.out.println(checkMessage);
	}
	
	@Test(dependsOnMethods= {"submitOrder"})
	public void orderHistoryTest()
	{
		ProductCatalogue catalogue = landing.loginApplication("dineshkumar@gmail.com", "Ssccgl@123");
		orderPage orders = catalogue.goToOrderPage();
		Assert.assertTrue(orders.verifyOrder(productName));
	}
	
/*	//Method 1 - using 2D Object (This method has drawback when we send multiple parameters)
	@DataProvider
	public Object[][] getData()
	{
		return new Object[][] {{"dineshkumar@gmail.com", "Ssccgl@123", "ADIDAS ORIGINAL"}, {"shetty@gmail.com", "Iamking@000", "ADIDAS ORIGINAL"}};
	}	*/
	
/*	//Method 2 - using HashMap
	@DataProvider
	public Object[][] getData()
	{
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("email", "dineshkumar@gmail.com");
		map.put("password", "Ssccgl@123");
		map.put("product", "ADIDAS ORIGINAL");
		HashMap<String,String> map1 = new HashMap<String,String>();
		map1.put("email", "shetty@gmail.com");
		map1.put("password", "Iamking@000");
		map1.put("product", "ADIDAS ORIGINAL");
		return new Object[][] {{map}, {map1}};
	}	*/
	
	//Method 3 - Using JSON to HashMap
	@DataProvider
	public Object[][] getData() throws IOException
	{
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\test\\java\\seleniumFramework\\data\\PurchaseOrder.json");
		return new Object[][] {{data.get(0)}, {data.get(1)}};
	}	
}
