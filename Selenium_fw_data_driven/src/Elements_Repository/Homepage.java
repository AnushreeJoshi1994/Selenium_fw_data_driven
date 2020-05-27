package Elements_Repository;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Homepage {

	WebDriver driver;

	@FindBy(xpath="//div[1]/div[2]/button")
	WebElement dd_category;
	
	@FindBy(xpath="//a[@href='/courses/category/Software Testing']")
	WebElement opt_sw_testing;
	
	@FindBy(xpath="//div[2]/div[2]/button")
	WebElement dd_author;
	
	@FindBy(xpath="//a[@href='/courses/author/154569']")
	WebElement opt_letscodeit;
	
	@FindBy(xpath="//img[@role='presentation']")
	List<WebElement> img_courses;
	
	@FindBy(xpath="//a/div[@class=\"col-lg-12\"]/div[2]")
	List<WebElement> we_courses_names;
	
	@FindBy(xpath="(//img[@role='presentation'])[1]")
	WebElement img_selenium_course;
	
	@FindBy(xpath="(//ul[@role='menu'])[2]//a")
	List<WebElement> opt_auth_dd;
	
	List<String> courses_names=new ArrayList<String>();
	List<String> opt_author=new ArrayList<String>();
	
	public Homepage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(this.driver, this);
	}

	public void click_cat_dd()
	{
		dd_category.click();
	}
	
	public void click_sw_testing()
	{
		opt_sw_testing.click();
	}
	

	public void click_dd_author()
	{
		try 
		{
			dd_author.click();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	}
	
	public void click_opt_letscodeit()
	{
		opt_letscodeit.click();
	}

	public int num_course_img()
	{
		return img_courses.size();
	}
	
	public List<String> list_courses()
	{
		for(WebElement name:we_courses_names)
		{
			courses_names.add(name.getText());
		}
		return courses_names;
	}
	
	public void click_sel_course()
	{
		img_selenium_course.click();
	}
	
	public List<String> get_opt_author()
	{
		for(WebElement opt:opt_auth_dd)
		{
			opt_author.add(opt.getText());
		}
		return opt_author;
	}
}
