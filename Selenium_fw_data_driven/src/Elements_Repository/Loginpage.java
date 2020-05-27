package Elements_Repository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Loginpage {

	WebDriver driver;
	
	@FindBy(xpath="//a[@href='/sign_in']")
	WebElement btn_login;
		
	@FindBy(id="user_email")
	WebElement txt_email;

	@FindBy(id="user_password")
	WebElement txt_pwd;
	
	@FindBy(xpath="//input[@value='Log In']")
	WebElement btn_submit;
	
	
	public Loginpage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(this.driver, this);
	}
	
	public void click_login()
	{
		btn_login.click();
	}
	
	public void enter_email(String email)
	{
		txt_email.sendKeys(email);
	}
	
	public void enter_pwd(String pwd)
	{
		txt_pwd.sendKeys(pwd);
	}
	
	public void click_submit()
	{
		btn_submit.click();
	}
	
}
