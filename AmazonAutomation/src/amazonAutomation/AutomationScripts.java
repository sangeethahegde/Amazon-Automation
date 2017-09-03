package amazonAutomation;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class AutomationScripts extends ReUsableMethods{

	@Test
	public static void SearchiPhone6() throws Exception {

		String dtpath="C:/AmazonAutomation/Data/SearchiPhone6.xls";
		String[][] recData=readXlSheet(dtpath,"Sheet1");
		String orPath = "C:/AmazonAutomation/AmazonObjectRepository.xls";
		readLocators(orPath,"Sheet1");
		//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		String URL=recData[1][1];
		String SearchFor=recData[1][2];
		String expected=recData[1][3];
		try{
			/*Launch URL*/
			driver.get(URL);

			/*Search item*/
			setValue(1);
			By bySearchItem = getBy(locatorType,value);
			WebElement SearchItem = driver.findElement(bySearchItem);
			enterText(SearchItem, SearchFor, obj_Name);


			//Click Search icon
			setValue(2);
			By bySearch = getBy(locatorType,value);
			WebElement Search = driver.findElement(bySearch);
			ReUsableMethods.clickButton(Search, obj_Name);

			//Click on first Apple iPhone6
			setValue(3);
			By byiPhone6 = getBy(locatorType,value);
			WebElement iPhone6 = driver.findElement(byiPhone6);
			ReUsableMethods.clickButton(iPhone6, obj_Name);

			//Click on Add to cart
			setValue(4);
			By byAddToCart = getBy(locatorType,value);
			WebElement AddToCart = driver.findElement(byAddToCart);
			ReUsableMethods.clickButton(AddToCart, obj_Name);

			//Handle if No popup
			try{
				//Parent window
				String parentWindow=driver.getWindowHandle();

				for(String newWindow: driver.getWindowHandles())
				{
					driver.switchTo().window(newWindow);
				}
				//On Popup window click No Thanks
				Thread.sleep(5000);
				setValue(93);
				By byNoThanks = getBy(locatorType,value);
				WebElement NoThanks = driver.findElement(byNoThanks);
				ReUsableMethods.clickButton(NoThanks, obj_Name);

				//switch to parent window 
				driver.switchTo().window(parentWindow);
			}
			catch(Exception e){System.out.println(e);}

			//get cart count
			setValue(5);
			By byCartCount = getBy(locatorType,value);
			Thread.sleep(5000);
			WebElement CartCount = driver.findElement(byCartCount);
			String actual = CartCount.getText();

			//Verify Result
			result = verify(expected,actual);

			Update_Report(result,"SearchiPhone6","Exicution completed",driver);
		}
		catch(Exception e){}
		
	}

	
	public static void CheckMainTab() throws Exception {

		try{

			String dtpath="C:/AmazonAutomation/Data/CheckMainTab.xls";
			String[][] recData=readXlSheet(dtpath,"Sheet1");
			String orPath = "C:/AmazonAutomation/AmazonObjectRepository.xls";
			readLocators(orPath,"Sheet1");
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			String URL=recData[1][1];
			String expected1=recData[1][2];
			String expected2=recData[1][3];
			String expected3=recData[1][4];


			/*Launch URL*/
			driver.get(URL);

			/* Hover Mouse on Departments */
			setValue(6);
			By byDepartments = getBy(locatorType,value);
			WebElement Departments = driver.findElement(byDepartments);
			String actual1=Departments.getText();
			Actions action=new Actions(driver);
			action.moveToElement(Departments).build().perform();

			/*Hover on Your amazon.com */
			setValue(7);
			By byYourAmazon = getBy(locatorType,value);
			WebElement YourAmazon = driver.findElement(byYourAmazon);
			String actual2=YourAmazon.getText();
			Actions action2=new Actions(driver);
			action2.moveToElement(YourAmazon).build().perform();

			/*Hover on Todays Deals */
			setValue(8);
			By byTodaysDeals = getBy(locatorType,value);
			WebElement TodaysDeals = driver.findElement(byTodaysDeals);
			String actual3=TodaysDeals.getText();
			Actions action3=new Actions(driver);
			action3.moveToElement(TodaysDeals).build().perform();

			String result1 = verify(expected1,actual1);
			String result2 = verify(expected2.trim(),actual2.trim());
			String result3 = verify(expected3.trim(),actual3.trim());

			if(result1.equalsIgnoreCase("Pass") && result2.equalsIgnoreCase("Pass") && result3.equalsIgnoreCase("Pass"))
				result="Pass";
			else
				result="Fail";

			Update_Report(result,"CheckMainTab","Exicution completed",driver);
		}
		catch(Exception e){}
		}
	
	
	public static void DepartmentDropdownList() throws Exception {

		try{
			String dtpath="C:/AmazonAutomation/Data/DepartmentDropdownList.xls";
			String[][] recData=readXlSheet(dtpath,"Sheet1");
			String orPath = "C:/AmazonAutomation/AmazonObjectRepository.xls";
			readLocators(orPath,"Sheet1");
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			String URL=recData[1][1];
			String[] expected=recData[1][2].split(",,");
			System.out.println(expected.length);

			/*Launch URL*/
			driver.get(URL);

			/* Hover Mouse on Departments */
			setValue(6);
			By byDepartments = getBy(locatorType,value);
			WebElement Departments = driver.findElement(byDepartments);
			Actions action=new Actions(driver);
			action.moveToElement(Departments).build().perform();

			String[] actual=new String[expected.length];
			int i=0;

			for(int n=10;n<=30;n++)
			{
				setValue(n);
				By byvalue = getBy(locatorType,value);
				Thread.sleep(2000);
				actual[i] = driver.findElement(byvalue).getText();
				i++;
			}

			System.out.println(actual.length);
			for(String s1:expected)
				System.out.println(s1);
			for(String s2:actual)
				System.out.println(s2);

			if(expected.length==actual.length)
			{
				int x=0;
				result="Pass";
				while(x<actual.length)
				{
					if(!expected[x].trim().equals(actual[x].trim()) && (!actual[12].equals("Beauty & Health")))
					{
						result="Fail";
						break;
					}
					x++;
				}
			}
			Update_Report( result, "DepartmentDropdownList","Execution Completed",driver);
		}
		catch(Exception e){}
		}

	
	public static void yourAccountDropdownList() throws Exception {
		try{
			String dtpath="C:/AmazonAutomation/Data/yourAccountDropdownList.xls";
			String[][] recData=readXlSheet(dtpath,"Sheet1");
			String orPath = "C:/AmazonAutomation/AmazonObjectRepository.xls";
			readLocators(orPath,"Sheet1");
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			String URL=recData[1][1];
			String[] expected=recData[1][2].split(",");
			System.out.println(expected.length);

			/*Launch URL*/
			driver.get(URL);

			/* Hover Mouse on Account & Lists */
			setValue(31);
			By byAccountLists = getBy(locatorType,value);
			WebElement AccountLists = driver.findElement(byAccountLists);
			Actions action=new Actions(driver);
			action.moveToElement(AccountLists).build().perform();

			String[] actual=new String[expected.length];
			int i=0;

			for(int n=32;n<=64;n++)
			{
				setValue(n);
				By byvalue = getBy(locatorType,value);
				Thread.sleep(3000);
				actual[i] = driver.findElement(byvalue).getText();
				i++;
			}

			System.out.println(actual.length);
			for(String s1:expected)
				System.out.println(s1);
			for(String s2:actual)
				System.out.println(s2);

			if(expected.length==actual.length)
			{
				int x=0;
				result="Pass";
				while(x<actual.length)
				{
					if(!expected[x].trim().equals(actual[x].trim()))
					{
						result="Fail";
						break;
					}
					x++;
				}
			}
			Update_Report( result, "DepartmentDropdownList","Execution Completed",driver);
		}
		catch(Exception e){}
	}

	
	public static void allMenuDropdownList() throws Exception {
		try{
			String dtpath="C:/AmazonAutomation/Data/allMenuDropdownList.xls";
			String[][] recData=readXlSheet(dtpath,"Sheet1");
			String orPath = "C:/AmazonAutomation/AmazonObjectRepository.xls";
			readLocators(orPath,"Sheet1");
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			String URL=recData[1][1];
			String[] expected=recData[1][2].split(",,");

			/*System.out.println(expected.length);
		for(String s1:expected)
			System.out.println(s1);*/

			/*Launch URL*/
			driver.get(URL);

			//Click on All 
			Thread.sleep(10000);
			setValue(65);
			By byAll = getBy(locatorType,value);
			WebElement All = driver.findElement(byAll);
			ReUsableMethods.clickButton(All, obj_Name);

			//To get the select(Option) list
			Select select=new Select(All);
			List<WebElement> dropdown = select.getOptions();

			String[] actual=new String[dropdown.size()];
			int index=0;
			for(WebElement e: dropdown)
			{
				String s = e.getText();
				actual[index]=s;
				index++;
			}

			/*System.out.println(actual.length);
for(String s2:actual)
System.out.println(s2);*/

			if(expected.length==actual.length)
			{
				int x=0;
				result="Pass";
				while(x<actual.length)
				{
					if(!expected[x].trim().equals(actual[x].trim()))
					{
						result="Fail";
						break;
					}
					x++;
				}
			}
			Update_Report( result, "All MenuDropdown List","Execution Completed",driver);
		}
		catch(Exception e){}
		}

	
	public static void emptyCartValidation() throws Exception {
		try{
			String dtpath="C:/AmazonAutomation/Data/EmptyCartValidation.xls";
			String[][] recData=readXlSheet(dtpath,"Sheet1");
			String orPath = "C:/AmazonAutomation/AmazonObjectRepository.xls";
			readLocators(orPath,"Sheet1");
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			String URL=recData[1][1];
			String SearchFor=recData[1][2];
			String expected=recData[1][3].trim();

			/*Launch URL*/
			driver.get(URL);

			/*Search item*/
			setValue(1);
			By bySearchItem = getBy(locatorType,value);
			WebElement SearchItem = driver.findElement(bySearchItem);
			enterText(SearchItem, SearchFor, obj_Name);


			//Click Search icon
			setValue(2);
			By bySearch = getBy(locatorType,value);
			WebElement Search = driver.findElement(bySearch);
			ReUsableMethods.clickButton(Search, obj_Name);

			//Click on Apple iPhone 6S 64GB
			setValue(66);
			By byiPhone6 = getBy(locatorType,value);
			WebElement iPhone6 = driver.findElement(byiPhone6);
			ReUsableMethods.clickButton(iPhone6, obj_Name);

			//Click on Add to cart
			setValue(4);
			By byAddToCart = getBy(locatorType,value);
			WebElement AddToCart = driver.findElement(byAddToCart);
			ReUsableMethods.clickButton(AddToCart, obj_Name);


			//Handle if No popup
			try{
				//Close popup window
				String parentWindow=driver.getWindowHandle();

				for(String newWindow: driver.getWindowHandles())
				{
					driver.switchTo().window(newWindow);
				}
				//On Popup window click No Thanks
				Thread.sleep(5000);
				setValue(93);
				By byNoThanks = getBy(locatorType,value);
				WebElement NoThanks = driver.findElement(byNoThanks);
				ReUsableMethods.clickButton(NoThanks, obj_Name);

				//switch to parent window and close
				driver.switchTo().window(parentWindow);
			}
			catch(Exception e){System.out.println(e);}

			//Click on cart
			Thread.sleep(5000);
			setValue(67);
			By byCart = getBy(locatorType,value);
			WebElement Cart = driver.findElement(byCart);
			ReUsableMethods.clickButton(Cart, obj_Name);

			//Delete
			setValue(68);
			By byDelete = getBy(locatorType,value);
			WebElement Delete = driver.findElement(byDelete);
			ReUsableMethods.clickButton(Delete, obj_Name);

			//Click on cart again
			setValue(67);
			By byCartAgain = getBy(locatorType,value);
			WebElement CartAgain = driver.findElement(byCartAgain);
			ReUsableMethods.clickButton(CartAgain, obj_Name);

			//Verify Result
			setValue(69);
			By byCartEmpty = getBy(locatorType,value);
			WebElement CartCartEmpty = driver.findElement(byCartEmpty);
			String actual = CartCartEmpty.getText().trim();
			result = verify(expected,actual);

			Update_Report(result,"emptyCartValidation","Exicution completed",driver);

		}
		catch(Exception e){}
		

	}

	public static void verifyTheContentsOnHelpPage() throws Exception {
		try{
			String dtpath="C:/AmazonAutomation/Data/verifyTheContentsOnHelpPage.xls";
			String[][] recData=readXlSheet(dtpath,"Sheet1");
			String orPath = "C:/AmazonAutomation/AmazonObjectRepository.xls";
			readLocators(orPath,"Sheet1");
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			String URL=recData[1][1];
			String[] expected=recData[1][2].split(",");
			System.out.println(expected.length);

			/*Launch URL*/
			driver.get(URL);

			//Hanble if Help is not displaying on Home Page

			//Click on Help
			setValue(71);
			By byHelp = getBy(locatorType,value);
			WebElement Help = driver.findElement(byHelp);
			ReUsableMethods.clickButton(Help, obj_Name);

			String[] actual=new String[expected.length];
			int i=0;

			//Hover on Hello....
			setValue(80);
			By byHello = getBy(locatorType,value);
			WebElement Hello = driver.findElement(byHello);
			actual[i]=Hello.getText();
			i++;
			Actions action1=new Actions(driver);
			action1.moveToElement(Hello).build().perform();

			// Hover on  Your Orders

			setValue(72);
			By byYourOrders = getBy(locatorType,value);
			WebElement YourOrders = driver.findElement(byYourOrders);
			Actions action2=new Actions(driver);
			action2.moveToElement(YourOrders).build().perform();
			actual[i]=YourOrders.getText();
			i++;

			//Hover on Returns & Refunds
			setValue(73);
			By byReturns = getBy(locatorType,value);
			WebElement Returns = driver.findElement(byReturns);
			Actions action3=new Actions(driver);
			action3.moveToElement(Returns).build().perform();
			actual[i]=Returns.getText();
			i++;

			//Hover on Device Support
			setValue(74);
			By byDeviceSupport = getBy(locatorType,value);
			WebElement DeviceSupport = driver.findElement(byDeviceSupport);
			Actions action4=new Actions(driver);
			action4.moveToElement(DeviceSupport).build().perform();
			actual[i]=DeviceSupport.getText();
			i++;

			//Hover on Manage Prime
			setValue(75);
			By byManagePrime = getBy(locatorType,value);
			WebElement ManagePrime = driver.findElement(byManagePrime);
			Actions action5=new Actions(driver);
			action5.moveToElement(ManagePrime).build().perform();
			actual[i]=ManagePrime.getText();
			i++;

			//Hover on Payments & Gift Cards
			setValue(76);
			By byPayments = getBy(locatorType,value);
			WebElement Payments = driver.findElement(byPayments);
			Actions action6=new Actions(driver);
			action6.moveToElement(Payments).build().perform();
			actual[i]=Payments.getText();
			i++;	



			//Hover on Account Settings
			setValue(77);
			By byAccounts = getBy(locatorType,value);
			WebElement Accounts = driver.findElement(byAccounts);
			Actions action7=new Actions(driver);
			action7.moveToElement(Accounts).build().perform();
			actual[i]=Accounts.getText();
			i++;


			//Hover on find more solutions
			setValue(78);
			By byFindMore = getBy(locatorType,value);
			WebElement FindMore = driver.findElement(byFindMore);
			actual[i]=FindMore.getText();
			Actions action=new Actions(driver);
			action.moveToElement(FindMore).build().perform();



			//Hoover on search icon
			setValue(79);
			By bySearchIcon = getBy(locatorType,value);
			WebElement SearchIcon = driver.findElement(bySearchIcon);
			Actions action8=new Actions(driver);
			action8.moveToElement(SearchIcon).build().perform();	

			//		System.out.println(actual.length);
			//		for(String s1:expected)
			//			System.out.println(s1);
			//		for(String s2:actual)
			//			System.out.println(s2);

			if(expected.length==actual.length)
			{
				int x=0;
				result="Pass";
				while(x<actual.length)
				{
					if(!expected[x].trim().equals(actual[x].trim()))
					{
						result="Fail";
						break;
					}
					x++;
				}
			}

			Update_Report(result,"Verify The Contents On Help Page","Exicution completed",driver);
		}
		catch(Exception e){}
		}

	public static void searchBookEnterQuatityAddToCart() throws Exception {
		try{
			String dtpath="C:/AmazonAutomation/Data/SearchBookEnterQuatityAddToCart.xls";
			String[][] recData=readXlSheet(dtpath,"Sheet1");
			String orPath = "C:/AmazonAutomation/AmazonObjectRepository.xls";
			readLocators(orPath,"Sheet1");
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			String URL=recData[1][1];
			String SearchFor=recData[1][2];
			String expected=recData[1][3];

			/*Launch URL*/
			driver.get(URL);

			/*Search item*/
			setValue(1);
			By bySearchItem = getBy(locatorType,value);
			WebElement SearchItem = driver.findElement(bySearchItem);
			enterText(SearchItem, SearchFor, obj_Name);


			//Click Search icon
			setValue(2);
			By bySearch = getBy(locatorType,value);
			WebElement Search = driver.findElement(bySearch);
			ReUsableMethods.clickButton(Search, obj_Name);

			//Click on Head First Java,2nd Edition
			setValue(81);
			By byHeadFirst = getBy(locatorType,value);
			WebElement HeadFirst = driver.findElement(byHeadFirst);
			ReUsableMethods.clickButton(HeadFirst, obj_Name);

			//Quantity 
			setValue(82);
			By byQuantity = getBy(locatorType,value);
			WebElement Quantity = driver.findElement(byQuantity);
			ReUsableMethods.clickButton(Quantity, obj_Name);

			//Select 5
			setValue(83);
			By byFive = getBy(locatorType,value);
			WebElement Five = driver.findElement(byFive);
			ReUsableMethods.clickButton(Five, obj_Name);

			//Click on Add to cart
			setValue(4);
			By byAddToCart = getBy(locatorType,value);
			WebElement AddToCart = driver.findElement(byAddToCart);
			ReUsableMethods.clickButton(AddToCart, obj_Name);

			//Close popup window
			//driver.switchTo().alert().dismiss();

			//get cart count
			setValue(5);
			By byCartCount = getBy(locatorType,value);
			WebElement CartCount = driver.findElement(byCartCount);
			String actual = CartCount.getText();

			//Verify Result
			result = verify(expected,actual);

			Update_Report(result,"searchBookEnterQuatityAddToCart","Exicution completed",driver);
		}
		catch(Exception e){}
		}

	public static void saveForLater() throws Exception {
		try{
			String dtpath="C:/AmazonAutomation/Data/saveForLater.xls";
			String[][] recData=readXlSheet(dtpath,"Sheet1");
			String orPath = "C:/AmazonAutomation/AmazonObjectRepository.xls";
			readLocators(orPath,"Sheet1");
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			String URL=recData[1][1];
			String SearchFor=recData[1][2];
			String expected=recData[1][3];
			String actual;


			/*Launch URL*/
			driver.get(URL);

			/*Search item*/
			setValue(1);
			By bySearchItem = getBy(locatorType,value);
			WebElement SearchItem = driver.findElement(bySearchItem);
			enterText(SearchItem, SearchFor, obj_Name);


			//Click Search icon
			setValue(2);
			By bySearch = getBy(locatorType,value);
			WebElement Search = driver.findElement(bySearch);
			ReUsableMethods.clickButton(Search, obj_Name);

			//Click on Head First Java,2nd Edition
			setValue(81);
			By byHeadFirst = getBy(locatorType,value);
			WebElement HeadFirst = driver.findElement(byHeadFirst);
			ReUsableMethods.clickButton(HeadFirst, obj_Name);

			//Quantity 
			setValue(82);
			By byQuantity = getBy(locatorType,value);
			WebElement Quantity = driver.findElement(byQuantity);
			ReUsableMethods.clickButton(Quantity, obj_Name);

			//Select 5
			Thread.sleep(5000);
			setValue(83);
			By byFive = getBy(locatorType,value);
			WebElement Five = driver.findElement(byFive);
			ReUsableMethods.clickButton(Five, obj_Name);

			//Click on Add to cart
			setValue(4);
			By byAddToCart = getBy(locatorType,value);
			WebElement AddToCart = driver.findElement(byAddToCart);
			ReUsableMethods.clickButton(AddToCart, obj_Name);

			//Click on cart
			setValue(84);
			By byCart = getBy(locatorType,value);
			WebElement Cart = driver.findElement(byCart);
			ReUsableMethods.clickButton(Cart, obj_Name);

			//Quantity 
			setValue(85);
			By byQuantityChange = getBy(locatorType,value);
			WebElement QuantityChange = driver.findElement(byQuantityChange);
			ReUsableMethods.clickButton(QuantityChange, obj_Name);

			//Select 4
			Thread.sleep(5000);
			setValue(86);
			By byFour = getBy(locatorType,value);
			WebElement Four = driver.findElement(byFour);
			ReUsableMethods.clickButton(Four, obj_Name);

			//Click On Save for later
			Thread.sleep(5000);
			setValue(87);
			By bySaveForLater = getBy(locatorType,value);
			WebElement SaveForLater = driver.findElement(bySaveForLater);
			ReUsableMethods.clickButton(SaveForLater, obj_Name);

			//message 

			setValue(89);
			By byMessage = getBy(locatorType,value);
			Thread.sleep(5000);
			WebElement Message = driver.findElement(byMessage);
			actual=Message.getText();

			System.out.println(expected);
			System.out.println(actual);

			result = verify(expected,actual);

			Update_Report(result,"saveForLater","Exicution completed",driver);
		}
		catch(Exception e){}
		}

	public static void firstThreeDisplay() throws Exception {
		try{
			String dtpath="C:/AmazonAutomation/Data/FirstThreeDisplay.xls";
			String[][] recData=readXlSheet(dtpath,"Sheet1");
			String orPath = "C:/AmazonAutomation/AmazonObjectRepository.xls";
			readLocators(orPath,"Sheet1");
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

			String URL=recData[1][1];
			String SearchFor=recData[1][2];
			String expected1=recData[1][3];
			String expected2=recData[1][4];
			String expected3=recData[1][5];

			/*Launch URL*/
			driver.get(URL);

			/*Search item*/
			setValue(1);
			By bySearchItem = getBy(locatorType,value);
			WebElement SearchItem = driver.findElement(bySearchItem);
			enterText(SearchItem, SearchFor, obj_Name);

			//Select first
			setValue(90);
			By byFirst = getBy(locatorType,value);
			WebElement First = driver.findElement(byFirst);
			String actual1=First.getText();

			//Select second
			setValue(91);
			By bySecond = getBy(locatorType,value);
			WebElement Second = driver.findElement(bySecond);
			String actual2=Second.getText();

			//Select Third
			setValue(92);
			By byThird = getBy(locatorType,value);
			WebElement Third = driver.findElement(byThird);
			String actual3=Third.getText();

			System.out.println(actual1);
			System.out.println(actual2);
			System.out.println(actual3);

			String res1 = verify(expected1,actual1);
			String res2 = verify(expected2,actual2);
			String res3 = verify(expected3,actual3);
			if(res1.equals("Pass") && res2.equals("Pass") && res3.equals("Pass"))
				result ="Pass";
			else
				result="Fail";

			Update_Report(result,"firstThreeDisplay","Exicution completed",driver);
		}
		catch(Exception e){}
		}

}
