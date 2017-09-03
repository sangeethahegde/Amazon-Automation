package amazonAutomation;

import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class Driver {
	static WebDriver driver;
	public static void main(String[] args) throws Exception {
		
		/* 
		 * Java Reflection
		 */

		String testSuitPath="C:/AmazonAutomation/TestSuit.xls";
		String[][] testSuitMethod=AutomationScripts.readXlSheet(testSuitPath,"Sheet1");

		for(int i=1;i<testSuitMethod.length;i++)
		{
			String exicute=testSuitMethod[i][1];
			String testcase=testSuitMethod[i][2];

			if(exicute.equalsIgnoreCase("Y"))
			{
				//Execute on chrome
				try{
					System.setProperty("webdriver.chrome.driver", "C:/ChromeDriver/chromedriver.exe");
					driver = new ChromeDriver();
					
					Method tc=AutomationScripts.class.getMethod(testcase);
					ReUsableMethods.startReport(testcase,"C:/AmazonAutomation/Amazon Report","Chrome");
					tc.invoke(tc);
					ReUsableMethods.bw.close();
					//driver.close();
					ReUsableMethods.updateXlSheet(testSuitPath,"Sheet1",i,"Chrome",AutomationScripts.result,ReUsableMethods.htmlname);
				}
				catch(Exception e){
					System.out.println(e);
					
				}
                //Execute on Firefox
				try{

					System.setProperty("webdriver.gecko.driver","C:/geckodriver/geckodriver-v0.18.0-win64/geckodriver.exe");
					driver = new FirefoxDriver();

					Method tc=AutomationScripts.class.getMethod(testcase);
					ReUsableMethods.startReport(testcase,"C:/AmazonAutomation/Amazon Report","Firefox");
					tc.invoke(tc);
					ReUsableMethods.bw.close();
					driver.close();
					ReUsableMethods.updateXlSheet(testSuitPath,"Sheet1",i,"Firefox",AutomationScripts.result,ReUsableMethods.htmlname);
				}
				catch(Exception e){
					System.out.println(e);
					
				}
				//Execute on IE
				try{
					System.setProperty("webdriver.ie.driver","C:/ChromeDriver/IEDriverServer.exe");
					driver = new InternetExplorerDriver();
					
					Method tc=AutomationScripts.class.getMethod(testcase);
					ReUsableMethods.startReport(testcase,"C:/AmazonAutomation/Amazon Report","IE");
					tc.invoke(tc);
					ReUsableMethods.bw.close();
					//driver.close();
					ReUsableMethods.updateXlSheet(testSuitPath,"Sheet1",i,"IE",AutomationScripts.result,ReUsableMethods.htmlname);
				}
				catch(Exception e){
					System.out.println(e);
					
				}
				
			}

		}

		/*System.setProperty("webdriver.ie.driver","C:/ChromeDriver/IEDriverServer.exe");
		driver = new InternetExplorerDriver();*/
		/*System.setProperty("webdriver.gecko.driver","C:/geckodriver/geckodriver-v0.18.0-win64/geckodriver.exe");
		driver = new FirefoxDriver();
		String testcase="SearchiPhone6";
		Method tc=AutomationScripts.class.getMethod(testcase);
		ReUsableMethods.startReport(testcase,"C:/AmazonAutomation/Amazon Report","Firefox");
		tc.invoke(tc);
		ReUsableMethods.bw.close();
		driver.close();*/
    }

}

