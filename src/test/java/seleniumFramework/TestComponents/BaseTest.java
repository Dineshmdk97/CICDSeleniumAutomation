package seleniumFramework.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import seleniumFramework.pageObjects.landingPage;

public class BaseTest {
	
	public WebDriver driver;
	public landingPage landing;
	public WebDriver initializeDriver() throws IOException
	{
		Properties prop = new Properties();
		// Path of GlobalData should be passed as argument
		// C:\Users\dines\eclipse-workspace\SeleniumFrameworkDesign -> this path dynamically changing for every users
		// It need to be set as -> System.getProperty("user.dir")
		// FileInputStream fis = new FileInputStream("C:\Users\dines\eclipse-workspace\SeleniumFrameworkDesign\src\main\java\seleniumFramework\resources\GlobalData.properties");
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//src//main//java//seleniumFramework//resources//GlobalData.properties");
		prop.load(fis);
		
		//String browserName = prop.getProperty("browser");
		
		//Java Ternary Operator
		
		//For any system level variables to read (For ex:- in Windows Terminal), System.getProperty() need to be used
		String browserName = System.getProperty("browser")!=null ? System.getProperty("browser") : prop.getProperty("browser");
// When we enter mvn -PRegression -Dbrowser=firefox in terminal, then System.getProperty!=null, So System.getProperty("browser") will be considered which in turn opens firefox browser
// When we enter mvn -PRegression in terminal, then System.getProperty=null, So prop.getProperty("browser"); will be considered which in turn opens defualt chrome browser	
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		//if(browserName.equalsIgnoreCase("chrome"))
		if(browserName.contains("chrome"))
		{
			// WebDriverManager.chromedriver().setup();
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\dines\\Downloads\\chromedriver_win32\\chromedriver.exe");
			if(browserName.contains("headless"))
			{
				options.addArguments("--headless=new");
				
			}
			driver = new ChromeDriver(options);
			driver.manage().window().setSize(new Dimension(1440,900)); //full screen for headless mode
			
		}
		else if(browserName.equalsIgnoreCase("firefox"))
		{
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}
		else if(browserName.equalsIgnoreCase("edge"))
		{
			//edge
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		//options.addArguments("--start-maximized");
		//driver.manage().window().maximize();
		
		// All driver initializing work has been completed, now return it.
		return driver;
	}
	
	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException
	{
		//Json to String
		//In what encoding format you have to write the string. In our case it UTF_8
		String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
		//String to HashMap - using Jackson Databind
		
		//Paste Jackson Databind in pom.xml file
		//https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind/2.15.0
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>()
		{
		});
		return data;
		}
	
	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException
	{
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File destination = new File(System.getProperty("user.dir")+"\\reports\\"+testCaseName+".png");
		FileUtils.copyFile(source, destination);
		return System.getProperty("user.dir")+"\\reports\\"+testCaseName+".png";
	}
	
	@BeforeMethod (alwaysRun=true)
	public landingPage launchApplication() throws IOException
	{
		driver = initializeDriver();
		// Even if many testcases  are there based on this E-commerce web application, common thing is to land on login page
		
		// Constructor created in landingPage class
		landing = new landingPage(driver);
		landing.goTo();
		return landing;
	}
	
	@AfterMethod (alwaysRun=true)
	public void tearDown()
	{
		driver.close();
	}
}
