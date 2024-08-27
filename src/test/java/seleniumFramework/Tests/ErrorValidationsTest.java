package seleniumFramework.Tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import seleniumFramework.TestComponents.BaseTest;
import seleniumFramework.TestComponents.Retry;
import seleniumFramework.pageObjects.ProductCatalogue;
import seleniumFramework.pageObjects.cartPage;

public class ErrorValidationsTest extends BaseTest{

//The method which is considered flaky and inconsistent, expected to rerun again need to be tagged with 'retryAnalyzer=<createdclass>.class' in @Test
	//@Test(groups = {"ErrorCases"},retryAnalyzer=Retry.class)
	@Test(groups = {"ErrorCases"})
	public void loginErrorValidation() throws IOException, InterruptedException
	{
		// Since email id is wrong, Assert parameters will be matched and get passed.
		// If email id and password is correct. Assert parameters will not displayed and get failed.
		landing.loginApplication("dineshkumar@gmil.com", "Ssccgl@123");
		//Assert.assertEquals("Incorrect email or password.", landing.getErrorMessage());
		
		// Wantedly failing to screenshot in reports using listeners
		Assert.assertEquals("Incorrect email or word.", landing.getErrorMessage());
	}
	@Test
	public void productErrorValidation() throws InterruptedException
	{
		String productName = "ADIDAS ORIGINAL";
		ProductCatalogue catalogue = landing.loginApplication("rahulshetty@gmail.com", "Iamking@000");
		catalogue.addProductToCart(productName);	
		cartPage cartpage = catalogue.goToCartPage();
		Boolean check = cartpage.verifyProduct("ADIDAS DUPLICATE");
		Assert.assertFalse(check);
	}
}
