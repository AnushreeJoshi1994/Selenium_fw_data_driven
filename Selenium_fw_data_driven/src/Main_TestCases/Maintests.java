package Main_TestCases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


import Data_Provider.Login_data;
import Elements_Repository.Homepage;
//import ElementsRepository.Launchpadpage;
import Elements_Repository.Loginpage;
import Elements_Repository.OrderSummarypage;
import Elements_Repository.SelCourseDetailspage;

public class Maintests {

	WebDriver driver;
	String path="./Test_Data/TestData.xlsx";
	
	@BeforeTest
	public void start_test()
	{
		System.setProperty("webdriver.driver.chrome", "./driver/chromedriver.exe");
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(25,TimeUnit.SECONDS);
		
	   //url is given just for an example(i.e. invalid), you can give any valid url/link
		driver.get("https://xyz.abcd.com/");
		System.out.println("before test");
	}
	
	@Test(priority=1,dataProvider="get_login_data")
	public void tests(String slno, String email, String pwd) throws EncryptedDocumentException, FileNotFoundException, IOException, InterruptedException
	{
		try
		{
		Loginpage lp=new Loginpage(driver);
		lp.click_login();
		lp.enter_email(email);
		lp.enter_pwd(pwd);
		lp.click_submit();
		
		//code to decide login successful or failure
			
		String result="";
		//url is given just for an example(i.e. invalid), you can give any valid url/link
		String actual_url="https://xyz.abcd.com/";
	
		String url=driver.getCurrentUrl();
	
		SoftAssert sa = new SoftAssert();
				
				
		if(url.equals(actual_url))
		{		
			System.out.println("Login Succesful");
			result="Pass";
			Thread.sleep(3000);
			sa.assertTrue(true);
					
		}
		else
		{
			System.out.println("Login failure");
			result="Fail";
			//url to return back to login page
			driver.get("https://xyz.abcd.com/");
			sa.assertTrue(false);
		}
				
		
		int slnum = Integer.parseInt(slno);
				
		//writing into excel		
		//open xl file
		Workbook wb = WorkbookFactory.create(new FileInputStream(path));
		Sheet sheet=wb.getSheet("LoginData");
				
		//write into sheet1->row->cell->setvalue
		wb.getSheet("LoginData").getRow(slnum).createCell(4).setCellValue(result);
				
		//save it
		wb.write(new FileOutputStream(path));
				
	
		System.out.println(result);
		wb.close();
		sa.assertAll();
		}
		catch(Exception e)
		{
			Reporter.log(e.toString());
			e.printStackTrace();
		}
		
	}
	
	
	@Test(priority=2)
	public void Test_dd_contents()
	{
		try
		{
		Homepage hp=new Homepage(driver);
		hp.click_dd_author();
		List<String> opt_webpage=hp.get_opt_author();
		
		Workbook wb = WorkbookFactory.create(new FileInputStream(path));
		Sheet sheet=wb.getSheet("DropdownContents");
		
		Hashtable<String,String> opt_excel= get_course_opt();
		SoftAssert sa=new SoftAssert();
		int count=0;
		
		if(opt_webpage.size()==opt_excel.size())
		{
			int slnum=0;
			for(int i=0;i<opt_webpage.size();i++)
			{
				slnum=Integer.parseInt(opt_excel.get(opt_webpage.get(i)));
				if(opt_excel.containsKey(opt_webpage.get(i)))
				{
					wb.getSheet("DropdownContents").getRow(slnum).createCell(2).setCellValue("Yes");
					count++;
				}
				else
				{
					wb.getSheet("DropdownContents").getRow(slnum).createCell(2).setCellValue("No");
				}
			}
			
			sa.assertEquals(opt_excel.size(), count);
			wb.write(new FileOutputStream(path));
			wb.close();
			sa.assertAll();
		}
		else
			System.out.println("Number of test options in author dropdown and options present on excel file are not matching");
		}
		catch(Exception e)
		{
			Reporter.log(e.toString());
			e.printStackTrace();
		}
		
	}
	
	
	@Test(priority=3)
	public void filter_list_courses() throws InterruptedException, EncryptedDocumentException, FileNotFoundException, IOException
	{
		try
		{
		System.out.println("Method to filtering and listing courses");
		Homepage hp=new Homepage(driver);
		Thread.sleep(1000);
		hp.click_cat_dd();
		hp.click_sw_testing();
		
		int course_count=hp.num_course_img();
		List<String> courses_webpage=hp.list_courses();
		
		System.out.println("Names of courses after filtering from webpage\n"+courses_webpage);
		
		
		//Comparing excel data and webpage data and writing into excel
		Workbook wb = WorkbookFactory.create(new FileInputStream(path));
		Sheet sheet=wb.getSheet("FilteredCourses");
		Hashtable<String,String> courses_excel= get_course_data();
		SoftAssert sa=new SoftAssert();
	
		System.out.println("Names of courses to be present according excel"+courses_excel);
		int slnum=0,count=0;;
	
		System.out.println("ht excel size "+courses_excel.size());
		System.out.println("list webpage size "+courses_webpage.size());
		if(courses_excel.size()==courses_webpage.size())
		{
		for(int i=0;i<courses_excel.size();i++)
		{
			String c=courses_webpage.get(i);
			
			slnum=Integer.parseInt(courses_excel.get(c));
			if(courses_excel.containsKey(c))
			{
				wb.getSheet("FilteredCourses").getRow(slnum).createCell(2).setCellValue("Yes");
				count++;
			}
			else
			{
				wb.getSheet("FilteredCourses").getRow(slnum).createCell(2).setCellValue("No");
				
			}
		}
		//save it
		wb.write(new FileOutputStream(path));
		wb.close();
		sa.assertEquals(courses_webpage.size(), count);
		sa.assertAll();
		}
		else
			System.out.println("Number of test data courses and courses present on webpage are not matching");
		}
		catch(Exception e)
		{
			Reporter.log(e.toString());
			e.printStackTrace();
		}

	
		
	}
	
	@Test(priority=4)
	public void verify_video_btn()
	{
		try
		{
			Homepage hp=new Homepage(driver);
			hp.click_sel_course();
			SoftAssert sa=new SoftAssert();
			SelCourseDetailspage sc=new SelCourseDetailspage(driver);
			sc.click_watchpromo();
			boolean enabled=sc.click_btnpaly();
			sa.assertTrue(enabled);
			if(enabled==true)
			{
				System.out.println("Promotion video's play button is enabled");
			}
			else
			{
				System.out.println("Promotion video's play button is disabled");
			}
			sc.click_close();
		}
		catch(Exception e)
		{
			Reporter.log(e.toString());
			e.printStackTrace();
		}
	}
	
	@Test(priority=5)
	public void test_payment_url()
	{
		try
		{
		SelCourseDetailspage scd=new SelCourseDetailspage(driver);
		scd.click_enroll();
		OrderSummarypage os=new OrderSummarypage(driver);
		os.payment_click();
		os.pp_click();
		os.checkbox_click();
		os.enroll_click();
		boolean crct_url=os.geturl();
		SoftAssert sa = new SoftAssert();
		sa.assertEquals(true, crct_url);
		if(crct_url==true)
		{
			System.out.println("Course enrollment Payment procedure went correct URL");
		}
		else
		{
			System.out.println("Course enrollment Payment procedure went wrong URL");
		}
		}
		catch(Exception e)
		{
			Reporter.log(e.toString());
			e.printStackTrace();
		}
	}
	
	@DataProvider
	public Object[][] get_login_data() throws EncryptedDocumentException, FileNotFoundException, IOException
	{
		Login_data ld=new Login_data(driver);
		Object[][] data=ld.get_excel_data("LoginData");
		
		return data;
		
	}
	
	
	
	public Hashtable<String,String> get_course_data() throws EncryptedDocumentException, FileNotFoundException, IOException
	{
		Workbook wb=WorkbookFactory.create(new FileInputStream(path));
		Sheet sheet=wb.getSheet("FilteredCourses");
		
		
		Hashtable<String,String> course=new Hashtable<String,String>();
		String c_name="",slno="";
		for(int i=0;i<sheet.getLastRowNum();i++)
		{
			
			for(int j=0;j<2;j++)
			{
				
				if(j==0) {
					slno=new DataFormatter().formatCellValue(sheet.getRow(i+1).getCell(j));
					
				}
				else
				{
				c_name=sheet.getRow(i+1).getCell(j).toString();
				}
				
			}
			course.put(c_name,slno);
			System.out.println("course(key)= "+c_name+"SLno(value)= "+slno+"\n");
		}

		return course;
	}
	
	public Hashtable<String,String> get_course_opt()
	{
		
		Hashtable<String,String> course=new Hashtable<String,String>();
		String opt_name="",slno="";
		try
		{
		Workbook wb=WorkbookFactory.create(new FileInputStream(path));
		Sheet sheet=wb.getSheet("DropdownContents");
	
		for(int i=0;i<sheet.getLastRowNum();i++)
		{
			
			for(int j=0;j<2;j++)
			{
				if(j==0) {
					slno=new DataFormatter().formatCellValue(sheet.getRow(i+1).getCell(j));
					
				}
				else
				{
				opt_name=sheet.getRow(i+1).getCell(j).toString();
				}
				
			}
			course.put(opt_name,slno);
			System.out.println("\n\n author dropdown contents: ");
			System.out.println("course(key)= "+opt_name+"SLno(value)= "+slno+"\n");
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Reporter.log(e.toString());
		}

		return course;
		
	}
	
	
	
	
	@AfterTest
	public void end_test()
	{
		System.out.println("after test");
		driver.close();
		
	}
	
	
}
