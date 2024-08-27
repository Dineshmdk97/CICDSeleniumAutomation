package seleniumFramework.TestComponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import java.util.concurrent.atomic.AtomicInteger;
import seleniumFramework.resources.ExtentReporterNG;

public class Listeners extends BaseTest implements ITestListener{
//When you create listeners always mention in pom.xml file
	
	
	ExtentReports extent = ExtentReporterNG.getReportObject();
	ExtentTest test;
	
//	Previously when ran testcases parallely, someother test may override other testcases which results in capturing wronng screenshot in report. To Avoid this
//	ThreadLocal class is used make thread safe which means even if you cases concurrently each object creation have its own thread i.e., unique thread id. 
//	It won't interrupt other overriding variable
//	Note: If you remove parallel = "tests" and run the xml file. It will work fine capturing screenshot for respective failed testcase
	
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();  // Thread safe
	
	@Override
	public void onTestStart(ITestResult result) {
		// Before executing a testcase it will first reach this block
		
		//results will hold information about testcases which is going to get executed
		//from which we are getting method name by using results.getMethod().getMethodName();
		
		test = extent.createTest(result.getMethod().getMethodName()); 
		extentTest.set(test); // unique thread id
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// After testcase is passed it will reach this block
		test.log(Status.PASS, "Test is passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// After testcase is failed it will reach this block
		
		//getThrowable() print he error message in the report
		//test.fail(result.getThrowable());
		
		//Instead of 'test.' you have to use 'extentTest.get().' to get actual failed test which we set on onTestStart i.e., 'extentTest.set()'
		extentTest.get().fail(result.getThrowable());
		
		// To give life to driver, we will get it from result
		try {
			//Fields are associated with class level not method level. From the class whatever field using driver, it will get it
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		String filePath = null;
		try {
			filePath = getScreenshot(result.getMethod().getMethodName(), driver);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//second argument denotes, with what name you want screenshot file
		//test.addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
		
		//Instead of 'test.' you have to use 'extentTest.get().' to get actual failed test which we set on onTestStart i.e., 'extentTest.set()'
		extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ITestContext context) {
		// Entire execution of all testcases completed it will reach this block
		extent.flush();
		
	}

}
