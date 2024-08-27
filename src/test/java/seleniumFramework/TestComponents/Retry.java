package seleniumFramework.TestComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer{
//	IRetryAnalyzer interface exposes a method which is implemented by the class created.
//	This method asks whether to re-run a test if it is failed
	@Override
	public boolean retry(ITestResult result) {
		int count = 0;
		int maxTry = 1;
		if(count<maxTry)
		{
			count++;
			//As long as it returning true it will re-run again and again
			return true;
		}
		return false;
	}

}
