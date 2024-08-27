package seleniumFramework.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {
	
	//If you put static for method, you can access that method directly with class name without declaring object of current class.
	//But if you dont use static, You need to create object of current class and call the method. 
	public static ExtentReports getReportObject()
	{
		String path = System.getProperty("user.dir")+"//reports//index.html";
		ExtentSparkReporter report = new ExtentSparkReporter(path);
		report.config().setReportName("Web Automation Results");
		report.config().setDocumentTitle("Test Results");
		
		//Extent Report is an open-source library used for generating test reports in automation testing.
		ExtentReports extent = new ExtentReports();
		extent.attachReporter(report);
		extent.setSystemInfo("Tester", "Dinesh Kumar");
		// There may be 100 testcases, to avoid writing 'extent.createTest' for all cases. We use TestNG Listeners
		//extent.createTest(testCase);
		return extent;
	}
}
