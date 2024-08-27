package cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

//Output of cucumber will come in encoded format, by using 'monochrome=true' it will be in readable format
//plugin is used to pull the report in selected format, by using 'plugin={format:destinationFolder}'

//Cucumber will not be able to read your TestNG assertions or TestNG libraries
//To make out cucumber understand TestNG features 'extends AbstractTestNGCucumberTests' is used in current class
//By default cucumber have ability to run in Junit code which doesn't required 'extends AbstractTestNGCucumberTests'

@CucumberOptions(features="src/test/java/cucumber", glue="seleniumFramework.stepDefinition", tags="@Regression", monochrome=true, plugin={"html:target/cucumber.html"})
public class TestNGTestRunner extends AbstractTestNGCucumberTests
{
	
}
