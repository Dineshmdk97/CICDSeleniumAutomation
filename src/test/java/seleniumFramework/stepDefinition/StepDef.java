package seleniumFramework.stepDefinition;

import java.io.IOException;

import org.testng.Assert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import seleniumFramework.TestComponents.BaseTest;
import seleniumFramework.pageObjects.ProductCatalogue;
import seleniumFramework.pageObjects.cartPage;
import seleniumFramework.pageObjects.checkoutPage;
import seleniumFramework.pageObjects.confirmationPage;
import seleniumFramework.pageObjects.landingPage;

public class StepDef extends BaseTest{
	
	public landingPage landing;
	public ProductCatalogue catalogue;
	public cartPage cartpage;
	public confirmationPage confirmation;
	
	@Given("I landed on Ecommerce page")
	// Format of writing method name is to same line with underscore for spaces
	public void I_landed_on_Ecommerece_page() throws IOException
	{
		landing = launchApplication();
	}
	
//	Regex (Regular Expression)
//	https://stackoverflow.com/questions/8327705/what-are-and-in-regular-expressions

//	. means "any character".
//	* means "any number of this".
//	.* therefore means an arbitrary string of arbitrary length.
//	^ indicates the beginning of the string.
//	$ indicates the end of the string.

//	(.+) it matches for any string and compares it. If anything matches it will go inside
	@Given("^Logged in with username (.+) and password (.+)$")
	public void Logged_in_with_username_and_password(String username, String password)
	{
		catalogue = landing.loginApplication(username, password);
	}
	
	@Given("^I add product (.+) to cart$")
	public void I_add_product_to_cart(String productName) throws InterruptedException
	{
		catalogue.addProductToCart(productName);
	}

	@And("^Checkout (.+) and submit the order$")
	public void Checkout_and_submit_the_order(String productName) throws InterruptedException
	{
		cartPage cartpage = catalogue.goToCartPage();
		Boolean check = cartpage.verifyProduct(productName);
		Assert.assertTrue(check);
		checkoutPage checkproducts = cartpage.goToCheckout();
		checkproducts.selectCountry("india");
		confirmation = checkproducts.submitOrder();
	}
	
	//(.+) will only work if it is driven from "Examples". If the value is directly in the step, Lets assume its a string value then put {string} in that place
	@Then("{string} message is displayed on ConfirmationPage")
	public void message_is_displayed_on_ConfirmationPage(String string) throws InterruptedException
	{
		String checkMessage = confirmation.verifyConfirmationMessage();
		Assert.assertTrue(checkMessage.equalsIgnoreCase(string));
		System.out.println(checkMessage);
		driver.close();
	}
	
	@Then("{string} message is displayed on LandingPage")
	public void message_is_displayed_on_LandingPage(String string2) throws InterruptedException
	{
		Assert.assertEquals(string2, landing.getErrorMessage());
		driver.close();
	}
}
