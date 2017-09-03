package amazonAutomation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.openqa.selenium.*;

public class ReUsableMethods extends Driver{

	static BufferedWriter bw = null;
	static BufferedWriter bw1 = null;
	static String htmlname;
	static String objType;
	static String objName;
	static String TestData;
	static String rootPath;
	static int report;


	static Date cur_dt = null;
	static String filenamer;
	static String TestReport;
	int rowcnt;
	static String exeStatus = "True";
	static int iflag = 0;
	static int j = 1;

	static String fireFoxBrowser;
	static String chromeBrowser;

	static String result="Fail";

	static int intRowCount = 0;
	static String dataTablePath;
	static int i;
	static String browserName;
	static String[][] locator;
	static String[][] data;

	static String locatorType;
	static String value;
	static String obj_Name;

	public static void setValue(int row)
	{
		obj_Name=(String) locator[row][0];
		locatorType = (String) locator[row][1];
		value=(String) locator[row][2];
	}

	public static By getBy(String type, String value)
	{

		switch (type)
		{
		case "id":
			return By.id(value);
		case "xpath":
			return By.xpath(value);
		case "className":
			return By.className(value);
		case "name":
			return By.name(value);
		case "linkText":
			return By.linkText(value);
		case "partialLinkText":
			return By.partialLinkText(value);
		case "cssSelector":
			return By.cssSelector(value);
		case "tagName":
			return By.tagName(value);
		default:
			System.out.println("Unknown type");
			return null;

		}
	}

	public static void readLocators(String path,String Sheet) throws IOException
	{
		locator=readXlSheet(path,Sheet); 
	}

	public static void readTestData(String path,String Sheet) throws InterruptedException, IOException
	{
		data = readXlSheet(path,Sheet);
	}

	
	/* Name Of the method: enterText
	 * Brief Description: Enter the text value to the text box
	 * Arguments: obj --> Text box object, textVal --> value to be entered, objName --> name of the object
	 * Created by: Automation team
	 * Creation Date: Aug 23 2017
	 * Last Modified: Aug 23 2017
	 * */
	public static void enterText(WebElement obj, String textVal, String objName) throws IOException{
		if(obj.isDisplayed()){
			obj.sendKeys(textVal);
			Update_Report("Pass", "enterText",textVal + " is entered in " + objName +" field",driver);

		}else{
			Update_Report("Fail", "enterText",objName + " is not displayed",driver);
		}

	}
	/* Name Of the method: clickButton
	 * Brief Description: Click on button
	 * Arguments: obj --> web object,  objName --> name of the object
	 * Created by: Automation team
	 * Creation Date: Aug 23 2017
	 * Last Modified: Aug 23 2017
	 * */

	public static void clickButton(WebElement obj,  String objName) throws IOException{

		if(obj.isDisplayed()){
			obj.click();
			Update_Report("Pass", "clickButton",objName +" is clickedfield",driver);

		}else{
			Update_Report("Fail", "clickButton",objName + " is not displayed",driver);
		}

	}
	//Verification method
	public static String verify(String expected, String actual)
	{
		String result =null;
		if(expected.equals(actual))
		{
			result = "Pass";
			return result;
		}
		else
		{
			result = "Fail";
			return result;
		}
	}

	/*
	 * Name of the Method: startReport
	 * Brief description : Creates HTML report template
	 * Arguments: scriptname:test script name to run,ReportsPath:HTML report path to create,browserName:browser the script is running
	 * Created by: Automation team
	 * Creation date : July 17 2017
	 * last modified:  July 17 2017
	 */
	public static void startReport(String scriptName, String ReportsPath,String browserName) throws IOException{
		j =0;
		String strResultPath = null;
		String testScriptName =scriptName;

		cur_dt = new Date(); 
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String strTimeStamp = dateFormat.format(cur_dt);

		if (ReportsPath == "") { 

			ReportsPath = "C:\\";
		}

		if (ReportsPath.endsWith("\\")) { 
			ReportsPath = ReportsPath + "\\";
		}

		strResultPath = ReportsPath + "Log" + "/" +testScriptName +"/"; 
		File f = new File(strResultPath);
		f.mkdirs();
		htmlname = strResultPath  + testScriptName + "_" + strTimeStamp 
				+ ".html";

		bw = new BufferedWriter(new FileWriter(htmlname));

		bw.write("<HTML><BODY><TABLE BORDER=0 CELLPADDING=3 CELLSPACING=1 WIDTH=100%>");
		bw.write("<TABLE BORDER=0 BGCOLOR=BLACK CELLPADDING=3 CELLSPACING=1 WIDTH=100%>");
		bw.write("<TR><TD BGCOLOR=#66699 WIDTH=27%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Browser Name</B></FONT></TD><TD COLSPAN=6 BGCOLOR=#66699><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>"
				+ browserName + "</B></FONT></TD></TR>");
		bw.write("<HTML><BODY><TABLE BORDER=1 CELLPADDING=3 CELLSPACING=1 WIDTH=100%>");
		bw.write("<TR COLS=7><TD BGCOLOR=#BDBDBD WIDTH=3%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>SL No</B></FONT></TD>"
				+ "<TD BGCOLOR=#BDBDBD WIDTH=10%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Step Name</B></FONT></TD>"
				+ "<TD BGCOLOR=#BDBDBD WIDTH=10%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Execution Time</B></FONT></TD> "
				+ "<TD BGCOLOR=#BDBDBD WIDTH=10%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Status</B></FONT></TD>"
				+ "<TD BGCOLOR=#BDBDBD WIDTH=47%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Detail Report</B></FONT></TD></TR>");


	}
	/*
	 * Name of the Method: Update_Report
	 * Brief description : Updates HTML report with test results
	 * Arguments: Res_type:holds the response of test script,Action:Action performed,result:contains test results
	 * Created by: Automation team
	 * Creation date : July 17 2017
	 * last modified:  July 17 2017
	 */


	public static void Update_Report(String Res_type,String Action, String result,WebDriver dr) throws IOException {
		Date exec_time = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String str_time = dateFormat.format(exec_time);

		if (Res_type.startsWith("Pass")) {

			bw.write("<TR COLS=7><TD BGCOLOR=#EEEEEE WIDTH=3%><FONT FACE=VERDANA SIZE=2>"
					+ (j++)
					+ "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=10%><FONT FACE=VERDANA SIZE=2>"
					+Action
					+ "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=10%><FONT FACE=VERDANA SIZE=2>"
					+ str_time
					+ "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=10%><FONT FACE=VERDANA SIZE=2 COLOR = GREEN>"
					+ "Passed"
					+ "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=30%><FONT FACE=VERDANA SIZE=2 COLOR = GREEN>"
					+ result + "</FONT></TD></TR>");

		} else if (Res_type.startsWith("Fail")) {
			//To generate report in only single file

			String ss1Path= screenshot(dr);
			exeStatus = "Failed";
			report = 1;
			bw.write("<TR COLS=7><TD BGCOLOR=#EEEEEE WIDTH=3%><FONT FACE=VERDANA SIZE=2>"
					+ (j++)
					+ "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=10%><FONT FACE=VERDANA SIZE=2>"
					+Action
					+ "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=10%><FONT FACE=VERDANA SIZE=2>"
					+ str_time
					+ "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=10%><FONT FACE=VERDANA SIZE=2 COLOR = RED>"
					+ "<a href= "
					+ ss1Path

					+ "  style=\"color: #FF0000\"> Failed </a>"

						+ "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=30%><FONT FACE=VERDANA SIZE=2 COLOR = RED>"
						+ result + "</FONT></TD></TR>");


		} 
	}

	public static String screenshot(WebDriver dr) throws IOException{

		Date exec_time = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String str_time = dateFormat.format(exec_time);
		//String fileName = "C:/Users/Sreeram/Desktop/WorkDayScreenShots/ss.png";
		String  ss1Path = "C:/Users/Abhis_lw0caw1/Desktop/HTML/"+ str_time+".png";
		File scrFile = ((TakesScreenshot)dr).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(ss1Path));
		return ss1Path;
	}


	public static String[][] readXlSheet(String dt_path,String sheetName) throws IOException
	{
		//Step1: Get the Xl Path
		File xlFile= new File(dt_path);

		//Step 2:Access the Xl file
		FileInputStream xlDoc=new FileInputStream(xlFile);

		//Step 3:Aceess the workbook
		HSSFWorkbook wb=new HSSFWorkbook(xlDoc);

		//Step 4:Access the worksheet
		HSSFSheet sheet=wb.getSheet(sheetName);

		int iRowCount=sheet.getLastRowNum()+1;
		int iColCount=sheet.getRow(0).getLastCellNum();

		String[][] xlData=new String[iRowCount][iColCount];

		for(int i=0;i<iRowCount;i++)
		{
			for(int j=0;j<iColCount;j++)
				xlData[i][j]=sheet.getRow(i).getCell(j).getStringCellValue();

		}
		return xlData;
	}

	public static void updateXlSheet(String dt_path,String sheetName,int rownum,String browser,String result,String report_path) throws IOException
	{
		//Step1: Get the Xl Path
		File xlFile= new File(dt_path);

		//Step 2:Access the Xl file
		FileInputStream xlDoc=new FileInputStream(xlFile);

		//Step 3:Aceess the workbook
		HSSFWorkbook wb=new HSSFWorkbook(xlDoc);

		//Step 4:Access the worksheet
		HSSFSheet sheet=wb.getSheet(sheetName);

		HSSFRow row;
		HSSFCell cell;

		switch(browser)
		{
		case "Chrome": 	if(result=="Pass")
		{
			row=sheet.getRow(rownum);
			cell=row.getCell(3);
			cell.setCellValue("Y");
			setCell(browser,result,wb,cell,report_path);

		}
		else
		{
			row=sheet.getRow(rownum);
			cell=row.getCell(3);
			cell.setCellValue("N");
			setCell(browser,result,wb,cell,report_path);

		}
		break;
		case "Firefox":
			if(result=="Pass")
			{
				row=sheet.getRow(rownum);
				cell=row.getCell(4);
				cell.setCellValue("Y");
				setCell(browser,result,wb,cell,report_path);

			}
			else
			{
				row=sheet.getRow(rownum);
				cell=row.getCell(4);
				cell.setCellValue("N");
				setCell(browser,result,wb,cell,report_path);

			}
			break;
		case "IE":
			if(result=="Pass")
			{
				row=sheet.getRow(rownum);
				cell=row.getCell(5);
				cell.setCellValue("Y");
				setCell(browser,result,wb,cell,report_path);


			}
			else
			{
				row=sheet.getRow(rownum);
				cell=row.getCell(5);
				cell.setCellValue("N");
				setCell(browser,result,wb,cell,report_path);

			}
			break;
		}

		FileOutputStream fout=new FileOutputStream(dt_path);
		wb.write(fout);
		fout.flush();
		fout.close();
	}


	public static void setCell(String browser, String result,HSSFWorkbook wb,HSSFCell cell,String report_path){

		CreationHelper createHelper = wb.getCreationHelper();
		HSSFCellStyle titlestyle=wb.createCellStyle();
		
		if(result.equals("Pass"))
		{
			titlestyle.setFillForegroundColor(new HSSFColor.GREEN().getIndex());
		}
		else
		{
			titlestyle.setFillForegroundColor(new HSSFColor.RED().getIndex());
		}
		titlestyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cell.setCellStyle(titlestyle);

		HSSFFont hlinkfont = wb.createFont();
		hlinkfont.setUnderline(HSSFFont.U_SINGLE);
		hlinkfont.setColor(HSSFColor.BLUE.index);
		titlestyle.setFont(hlinkfont);
		HSSFHyperlink link = (HSSFHyperlink)createHelper.createHyperlink(Hyperlink.LINK_URL);
		link.setAddress(report_path);
		cell.setHyperlink((HSSFHyperlink) link);

		cell.setCellStyle(titlestyle);
	}
}

