package seleniumFramework.Tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;
import seleniumFramework.pageObjects.landingPage;

public class StandAloneTest {

	public static void main(String[] args) {
		String productName = "ADIDAS ORIGINAL";
//		WebDriverManager.chromedriver().setup();
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\dines\\Downloads\\chromedriver_win32\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		driver.manage().window().maximize();
		
		// Constructor created in landingPage class
		landingPage landing = new landingPage(driver);
		
		driver.get("https://rahulshettyacademy.com/client");
		
		driver.findElement(By.id("userEmail")).sendKeys("dineshkumar@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Ssccgl@123");
		driver.findElement(By.id("login")).click();
		
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".mb-3"))));
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		WebElement prod = products.stream().filter(product->
		//There could be multiple products with name 'ADIDAS ORIGINAL'. findFirst() helps in returning the first product with such name
		product.findElement(By.cssSelector("b")).getText().equals("ADIDAS ORIGINAL")).findFirst().orElse(null);
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		
		//wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("#toast-container"))));
		
		//Without disappearance of this toast message, clicking on 'cart' button element will be intercepted
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		driver.findElement(By.cssSelector("button[routerlink*='cart']")).click();
		
		List<WebElement> prd = driver.findElements(By.cssSelector(".cartSection h3"));
		Boolean x = prd.stream().anyMatch(pr->pr.getText().equalsIgnoreCase(productName));
		Assert.assertTrue(x);
		driver.findElement(By.cssSelector(".totalRow button")).click();
		
		Actions a = new Actions(driver);
		a.sendKeys(driver.findElement(By.cssSelector("input[placeholder='Select Country']")), "india").build().perform();
		
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".ta-results"))));
		
		driver.findElement(By.xpath("//button[contains(@class, 'ta-item')] [2]")).click();
		driver.findElement(By.cssSelector("a[class*='action__submit']")).click();
		
		String finalMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(finalMessage.equalsIgnoreCase("Thankyou for the order."));
		System.out.println(finalMessage);
		System.out.println("Success");
		
	}

}
